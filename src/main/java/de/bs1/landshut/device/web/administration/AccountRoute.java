package de.bs1.landshut.device.web.administration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.bs1.landshut.device.DeviceManager;
import de.bs1.landshut.device.services.account.object.Account;
import de.bs1.landshut.device.util.javalin.AuthenticationLevel;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class AccountRoute {

  public AccountRoute(Javalin javalin) {
    javalin.get("/accounts", context -> {
      JsonArray jsonArray = new JsonArray();
      for (Account account : DeviceManager.getInstance().getServices().getAccountService().getAccounts()) {
        JsonObject accountObject = new JsonObject();
        accountObject.addProperty("accountId", account.getId());
        accountObject.addProperty("userName", account.getUserName());
        accountObject.addProperty("firstName", account.getFirstName());
        accountObject.addProperty("lastName", account.getLastName());
        accountObject.addProperty("administrator", account.isAdministrator());
        jsonArray.add(accountObject);
      }

      context.json(jsonArray);
    }, AuthenticationLevel.ADMIN);

    javalin.post("/accounts/create", context -> {
      String userName = context.header("userName");
      String firstName = context.header("firstName");
      String lastName = context.header("lastName");
      String password = context.header("password");
      String adminBooleanAsString = context.header("admin");

      if (userName == null || firstName == null || lastName == null || password == null || adminBooleanAsString == null) {
        context.status(HttpStatus.BAD_REQUEST);
        return;
      }

      Account account = DeviceManager.getInstance().getServices().getAccountService().createAccount(userName, firstName, lastName, password, Boolean.parseBoolean(adminBooleanAsString));
      context.json(account);
    });
  }

}
