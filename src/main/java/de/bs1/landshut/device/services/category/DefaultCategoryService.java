package de.bs1.landshut.device.services.category;

import de.bs1.landshut.device.database.DatabaseDriver;
import de.bs1.landshut.device.services.category.object.Category;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class DefaultCategoryService implements CategoryService {

  private final DatabaseDriver databaseDriver;

  public DefaultCategoryService(DatabaseDriver databaseDriver) {
    this.databaseDriver = databaseDriver;

    this.databaseDriver.executeUpdate(
        "CREATE TABLE IF NOT EXISTS `categories` ("
            + "    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
            + "    name VARCHAR(128) NOT NULL"
            + ");"
    );
  }

  @Override
  public @NotNull Category createCategory(@NotNull String name) {
    int categoryId = this.databaseDriver.executeUpdateWithKeys("INSERT INTO `categories` (`name`) VALUES (?);", statement -> {
      statement.setString(1, name);
    }, resultSet -> {
      if (!resultSet.next()) {
        throw new RuntimeException("Unable to generate auto incremented key for category " + name);
      }

      return resultSet.getInt(1);
    });

    return new Category(categoryId, name);
  }

  @Override
  public void deleteCategory(int categoryId) {
    this.databaseDriver.executeUpdate("DELETE FROM `categories` WHERE `id` = ?;", statement -> statement.setInt(1, categoryId));
  }

  @Override
  public @NotNull List<Category> getCategories() {
    return this.databaseDriver.executeQuery("SELECT * FROM `categories`;", resultSet -> {
      List<Category> categories = new ArrayList<>();
      while (resultSet.next()) {
        categories.add(
            new Category(resultSet.getInt("id"), resultSet.getString("name"))
        );
      }

      return categories;
    });
  }

}
