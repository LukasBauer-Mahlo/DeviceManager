package de.bs1.landshut.device.web.devices;

import de.bs1.landshut.device.DeviceManager;
import de.bs1.landshut.device.util.IntegerUtilities;
import de.bs1.landshut.device.util.javalin.AuthenticationLevel;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class DevicesRoutes {

  public DevicesRoutes(Javalin javalin) {
    javalin.get("/devices", context -> {
      context.json(DeviceManager.getInstance().getServices().getDeviceService().getDevicesWithInfo());
    });

    javalin.post("/devices/create", context -> {
      String name = context.header("deviceName");
      String categoryAsString = context.header("categoryId");
      String brandAsString = context.header("brandId");
      String amountAsString = context.header("amount");
      String supplierIdAsString = context.header("supplierId");

      if (name == null || categoryAsString == null || brandAsString == null || amountAsString == null || supplierIdAsString == null) {
        context.status(HttpStatus.BAD_REQUEST);
        return;
      }

      Integer categoryId = IntegerUtilities.getFromString(categoryAsString);
      Integer brandId = IntegerUtilities.getFromString(brandAsString);
      Integer amount = IntegerUtilities.getFromString(amountAsString);
      Integer supplierId = IntegerUtilities.getFromString(supplierIdAsString);

      if (categoryId == null || brandId == null || amount == null || supplierId == null) {
        context.status(HttpStatus.BAD_REQUEST);
        return;
      }

      DeviceManager.getInstance().getServices().getDeviceService().createDevice(name, categoryId, brandId, amount, supplierId);
    }, AuthenticationLevel.USER);
  }

}
