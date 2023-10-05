package de.bs1.landshut.device.services.brands;

import de.bs1.landshut.device.database.DatabaseDriver;
import de.bs1.landshut.device.services.brands.object.Brand;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DefaultBrandService implements BrandService {

  private final DatabaseDriver databaseDriver;

  public DefaultBrandService(DatabaseDriver databaseDriver) {
    this.databaseDriver = databaseDriver;

    this.databaseDriver.executeUpdate(
        "CREATE TABLE IF NOT EXISTS `brands` ("
            + "    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
            + "    name VARCHAR(128) NOT NULL"
            + ");"
    );
  }

  @Override
  public @NotNull Brand createBrand(@NotNull String name) {
    int categoryId = this.databaseDriver.executeUpdateWithKeys("INSERT INTO `brands` (`name`) VALUES (?);", statement -> {
      statement.setString(1, name);
    }, resultSet -> {
      if (!resultSet.next()) {
        throw new RuntimeException("Unable to generate auto incremented key for brand " + name);
      }

      return resultSet.getInt(1);
    });

    return new Brand(categoryId, name);
  }

  @Override
  public void deleteBrand(int brandId) {
    this.databaseDriver.executeUpdate("DELETE FROM `brands` WHERE `id` = ?;", statement -> statement.setInt(1, brandId));
  }

  @Override
  public @NotNull List<Brand> getBrands() {
    return this.databaseDriver.executeQuery("SELECT * FROM `brands`;", resultSet -> {
      List<Brand> brands = new ArrayList<>();
      while (resultSet.next()) {
        brands.add(
            new Brand(resultSet.getInt("id"), resultSet.getString("name"))
        );
      }

      return brands;
    });
  }

}
