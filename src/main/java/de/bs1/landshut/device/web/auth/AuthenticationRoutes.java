package de.bs1.landshut.device.web.auth;

import de.bs1.landshut.device.DeviceManager;
import de.bs1.landshut.device.services.account.object.Account;
import de.bs1.landshut.device.util.Constants;
import de.bs1.landshut.device.util.javalin.AuthenticationLevel;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationRoutes {

  public AuthenticationRoutes(Javalin javalin) {
    javalin.post("/auth/login", context -> {
      String userName = context.header("userName");
      String password = context.header("password");

      if (userName == null || password == null) {
        context.status(HttpStatus.BAD_REQUEST);
        return;
      }

      DeviceManager.getInstance().getLogger().log("Trying to login user " + userName);
      DeviceManager.getInstance().getServices().getAccountService().getAccount(userName).ifPresentOrElse(account -> {
        if (!BCrypt.checkpw(password, account.getPassword())) {
          DeviceManager.getInstance().getLogger().log("Login cancelled for user " + userName + " due to invalid password.");
          context.status(HttpStatus.FORBIDDEN);
          return;
        }

        context.result(DeviceManager.getInstance().getServices().getTokenService().generateToken(account.getId()));
      }, () -> context.status(HttpStatus.FORBIDDEN));
    });

    javalin.post("/auth/logout", context -> {
      Account account = context.attribute(Constants.ACCOUNT_ATTRIBUTE_KEY);
      if (account == null) {
        return; // not possible
      }

      DeviceManager.getInstance().getServices().getTokenService().invalidateAllTokens(account.getId());
    }, AuthenticationLevel.USER);

    javalin.get("/auth/check", context -> {
      // checked due to access manager
      context.status(HttpStatus.OK);
    }, AuthenticationLevel.USER);
  }

}
