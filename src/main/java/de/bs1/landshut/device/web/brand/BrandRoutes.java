package de.bs1.landshut.device.web.brand;

import de.bs1.landshut.device.DeviceManager;
import de.bs1.landshut.device.services.brands.object.Brand;
import de.bs1.landshut.device.util.javalin.AuthenticationLevel;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class BrandRoutes {

  public BrandRoutes(Javalin javalin) {
    javalin.get("/brands", context -> {
      context.json(DeviceManager.getInstance().getServices().getBrandService().getBrands());
    });

    javalin.post("/brands/create", context -> {
      String name = context.header("name");
      if (name == null) {
        context.status(HttpStatus.BAD_REQUEST);
        return;
      }

      Brand brand = DeviceManager.getInstance().getServices().getBrandService().createBrand(name);
      context.json(brand);
    }, AuthenticationLevel.USER);
  }

}
