package de.bs1.landshut.device.services.device;

import de.bs1.landshut.device.database.DatabaseDriver;
import de.bs1.landshut.device.services.brands.object.Brand;
import de.bs1.landshut.device.services.category.object.Category;
import de.bs1.landshut.device.services.device.object.Device;
import de.bs1.landshut.device.services.device.object.DeviceInfo;
import de.bs1.landshut.device.services.supplier.SupplierService;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultDeviceService implements DeviceService {

  private final DatabaseDriver databaseDriver;
  private final SupplierService supplierService;

  public DefaultDeviceService(DatabaseDriver databaseDriver, SupplierService supplierService) {
    this.databaseDriver = databaseDriver;
    this.supplierService = supplierService;

    this.databaseDriver.executeUpdate("CREATE TABLE IF NOT EXISTS devices ("
        + "    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
        + "    name VARCHAR(128) NOT NULL,"
        + "    categoryId INT NOT NULL,"
        + "    brandId INT NOT NULL,"
        + "    amount INT NOT NULL,"
        + "    supplierId INT NOT NULL,"
        + "    FOREIGN KEY (categoryId) REFERENCES categories(id),"
        + "    FOREIGN KEY (brandId) REFERENCES brands(id),"
        + "    FOREIGN KEY (supplierId) REFERENCES suppliers(id)"
        + ");");
  }

  @Override
  public @NotNull Device createDevice(@NotNull String name, int categoryId, int brandId, int amount, int supplierId) {
    int generatedId = this.databaseDriver.executeUpdateWithKeys("INSERT INTO `devices` (`name`, `categoryId`, `brandId`, `amount`, `supplierId`) VALUES (?, ?, ?, ?, ?);", statement -> {
      statement.setString(1, name);
      statement.setInt(2, categoryId);
      statement.setInt(3, brandId);
      statement.setInt(4, amount);
      statement.setInt(5, supplierId);
    }, resultSet -> {
      if (!resultSet.next()) {
        throw new RuntimeException("Unable to generate device id for device " + name);
      }

      return resultSet.getInt(1);
    });

    return new Device(generatedId, name, categoryId, brandId, amount, supplierId);
  }

  @Override
  public void updateDevice(@NotNull Device device) {
    this.databaseDriver.executeUpdate("UPDATE `devices` SET `name` = ?, `categoryId` = ?, `brandId` = ?, `amount` = ?, `supplierId` = ? WHERE `id` = ?;", statement -> {
      statement.setString(1, device.getName());
      statement.setInt(2, device.getCategoryId());
      statement.setInt(3, device.getBrandId());
      statement.setInt(4, device.getAmount());
      statement.setInt(5, device.getSupplierId());
      statement.setInt(6, device.getId());
    });
  }

  @Override
  public void deleteDevice(int deviceId) {
    this.databaseDriver.executeUpdate("DELETE FROM `devices` WHERE `id` = ?;", statement -> statement.setInt(1, deviceId));
  }

  @Override
  public @NotNull List<Device> getDevices() {
    return this.databaseDriver.executeQuery("SELECT * FROM `devices`;", resultSet -> {
      List<Device> devices = new ArrayList<>();
      while (resultSet.next()) {
        devices.add(this.fromResultSet(resultSet));
      }

      return devices;
    });
  }

  @Override
  public @NotNull List<DeviceInfo> getDevicesWithInfo() {
    return this.databaseDriver.executeQuery(
        "SELECT \n"
            + "    d.id AS device_id,\n"
            + "    d.name AS device_name,\n"
            + "    d.amount AS device_amount,\n"
            + "    c.id AS category_id,\n"
            + "    c.name AS category_name,\n"
            + "    b.id AS brand_id,\n"
            + "    b.name AS brand_name,\n"
            + "    d.supplierId AS supplier_id\n"
            + "FROM \n"
            + "    devices AS d\n"
            + "JOIN \n"
            + "    categories AS c ON d.categoryId = c.id\n"
            + "JOIN \n"
            + "    brands AS b ON d.brandId = b.id;\n"

        , resultSet -> {
          List<DeviceInfo> deviceInfos = new ArrayList<>();
          while (resultSet.next()) {
            int deviceId = resultSet.getInt("device_id");
            int deviceAmount = resultSet.getInt("device_amount");
            String deviceName = resultSet.getString("device_name");
            int categoryId = resultSet.getInt("category_id");
            String categoryName = resultSet.getString("category_name");
            int brandId = resultSet.getInt("brand_id");
            String brandName = resultSet.getString("brand_name");
            int supplierId = resultSet.getInt("supplier_id");

            deviceInfos.add(new DeviceInfo(
                new Device(deviceId, deviceName, categoryId, brandId, deviceAmount, supplierId),
                new Category(categoryId, categoryName),
                new Brand(brandId, brandName),
                this.supplierService.getSupplier(supplierId)
            ));
          }

          return deviceInfos;
        });
  }

  @Override
  public @NotNull Optional<DeviceInfo> getDeviceWithInfo(int id) {
    for (DeviceInfo deviceInfo : this.getDevicesWithInfo()) {
      if (deviceInfo.getDevice().getId() == id) {
        return Optional.of(deviceInfo);
      }
    }

    return Optional.empty();
  }

  @NotNull
  private Device fromResultSet(@NotNull ResultSet resultSet) throws SQLException {
    return new Device(
        resultSet.getInt("id"),
        resultSet.getString("name"),
        resultSet.getInt("categoryId"),
        resultSet.getInt("brandId"),
        resultSet.getInt("amount"),
        resultSet.getInt("supplierId")
    );
  }

}
