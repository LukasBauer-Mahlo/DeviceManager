package de.bs1.landshut.device.util.javalin;

import de.bs1.landshut.device.DeviceManager;
import de.bs1.landshut.device.services.account.AccountService;
import de.bs1.landshut.device.services.account.object.Account;
import de.bs1.landshut.device.services.token.TokenService;
import de.bs1.landshut.device.util.Constants;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class DefaultAccessManager implements AccessManager {

  private final TokenService tokenService;
  private final AccountService accountService;

  public DefaultAccessManager(TokenService tokenService, AccountService accountService) {
    this.tokenService = tokenService;
    this.accountService = accountService;
  }

  @Override
  public void manage(@NotNull Handler handler, @NotNull Context context, @NotNull Set<? extends RouteRole> set) throws Exception {
    DeviceManager.getInstance().getLogger().log("Loading path: " + context.path());

    if (set.isEmpty()) {
      handler.handle(context);
      return;
    }

    String token = context.header("token");
    if (token == null) {
      context.result("Unable to find provided authorization token.");
      context.status(HttpStatus.UNAUTHORIZED);
      return;
    }

    Integer userId = this.tokenService.getUserIdByToken(token).orElse(null);
    if (userId == null) {
      context.result("The entered token is invalid. Token entered: " + token);
      context.status(HttpStatus.UNAUTHORIZED);
      return;
    }

    Account account = this.accountService.getAccount(userId).orElse(null);
    if (account == null) {
      context.result("Unable to find account with id " + userId+ " mapped to entered token " + token);
      context.status(HttpStatus.FORBIDDEN);
      return;
    }

    if (account.isDisabled()) {
      context.result("Your account is disabled by the account administrator.");
      context.status(HttpStatus.FORBIDDEN);
      return;
    }

    if (set.contains(AuthenticationLevel.ADMIN) && !account.isAdministrator()) {
      context.result("This route is only accessible with administration permissions.");
      context.status(HttpStatus.FORBIDDEN);
      return;
    }

    context.attribute(Constants.ACCOUNT_ATTRIBUTE_KEY, account);
    handler.handle(context);
  }

}
