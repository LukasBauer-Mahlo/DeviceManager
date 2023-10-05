package de.bs1.landshut.device.web.category;

import de.bs1.landshut.device.DeviceManager;
import io.javalin.Javalin;

public class ListCategoriesRoute {

  public ListCategoriesRoute(Javalin javalin) {
    javalin.get("/categories", context -> {
      context.json(DeviceManager.getInstance().getServices().getCategoryService().getCategories());
    });
  }

}
