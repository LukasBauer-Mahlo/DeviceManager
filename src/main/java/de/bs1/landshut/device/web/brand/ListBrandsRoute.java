package de.bs1.landshut.device.web.brand;

import de.bs1.landshut.device.DeviceManager;
import io.javalin.Javalin;

public class ListBrandsRoute {

  public ListBrandsRoute(Javalin javalin) {
    javalin.get("/brands", context -> {
      context.json(DeviceManager.getInstance().getServices().getBrandService().getBrands());
    });
  }

}
