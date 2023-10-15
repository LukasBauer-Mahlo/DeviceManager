package de.bs1.landshut.device.web.category;

import de.bs1.landshut.device.DeviceManager;
import de.bs1.landshut.device.services.category.object.Category;
import de.bs1.landshut.device.util.javalin.AuthenticationLevel;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class CategoryRoutes {

  public CategoryRoutes(Javalin javalin) {
    javalin.get("/categories", context -> {
      context.json(DeviceManager.getInstance().getServices().getCategoryService().getCategories());
    }, AuthenticationLevel.USER);

    javalin.post("/categories/create", context -> {
      String name = context.header("name");
      if (name == null) {
        context.status(HttpStatus.BAD_REQUEST);
        return;
      }

      Category category = DeviceManager.getInstance().getServices().getCategoryService().createCategory(name);
      context.json(category);
    }, AuthenticationLevel.USER);
  }

}
