package de.bs1.landshut.device.web.account;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.bs1.landshut.device.DeviceManager;
import de.bs1.landshut.device.services.account.object.Account;
import de.bs1.landshut.device.util.javalin.AuthenticationLevel;
import io.javalin.Javalin;

public class ListAccountsRoute {

  public ListAccountsRoute(Javalin javalin) {
    javalin.get("/accounts", context -> {
      JsonArray jsonArray = new JsonArray();
      for (Account account : DeviceManager.getInstance().getServices().getAccountService().getAccounts()) {
        JsonObject accountObject = new JsonObject();
        accountObject.addProperty("accountId", account.getId());
        accountObject.addProperty("userName", account.getUserName());
        accountObject.addProperty("firstName", account.getFirstName());
        accountObject.addProperty("lastName", account.getLastName());
        jsonArray.add(accountObject);
      }

      context.json(jsonArray);
    }, AuthenticationLevel.ADMIN);
  }

}
