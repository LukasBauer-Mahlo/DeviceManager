package de.bs1.landshut.device.web.supplier;

import de.bs1.landshut.device.DeviceManager;
import de.bs1.landshut.device.services.location.object.Location;
import de.bs1.landshut.device.services.supplier.object.Supplier;
import de.bs1.landshut.device.services.supplier.object.contact.SupplierContact;
import de.bs1.landshut.device.util.javalin.AuthenticationLevel;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class SupplierRoutes {

  public SupplierRoutes(Javalin javalin) {
    javalin.get("/suppliers", context -> {
      context.json(DeviceManager.getInstance().getServices().getSupplierService().getAllSuppliers());
    }, AuthenticationLevel.USER);

    javalin.post("/suppliers/create", context -> {

      String supplierName = context.header("supplierName");

      String contactFirstName = context.header("contactFirstName");
      String contactLastName = context.header("contactLastName");
      String contactMail = context.header("contactMail");

      String postalCode = context.header("postalCode");
      String city = context.header("city");
      String street = context.header("street");
      String houseNumber = context.header("houseNumber");

      if (supplierName == null || contactFirstName == null || contactLastName == null || contactMail == null || postalCode == null || city == null || street == null || houseNumber == null) {
        context.status(HttpStatus.BAD_REQUEST);
        return;
      }

      Location location = DeviceManager.getInstance().getServices().getLocationService().createLocation(postalCode, city, street, houseNumber);
      SupplierContact supplierContact = DeviceManager.getInstance().getServices().getSupplierService().createContact(contactFirstName, contactLastName, contactMail);
      Supplier supplier = DeviceManager.getInstance().getServices().getSupplierService().createSupplier(supplierName, location, supplierContact);
      context.json(supplier);
    }, AuthenticationLevel.USER);
  }

}
