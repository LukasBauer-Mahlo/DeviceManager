package de.bs1.landshut.device.services.order;

import de.bs1.landshut.device.database.DatabaseDriver;
import de.bs1.landshut.device.services.account.object.Account;
import de.bs1.landshut.device.services.device.DeviceService;
import de.bs1.landshut.device.services.device.object.Device;
import de.bs1.landshut.device.services.order.object.Order;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DefaultOrderService implements OrderService {

  private final DatabaseDriver databaseDriver;
  private final DeviceService deviceService;

  public DefaultOrderService(DatabaseDriver databaseDriver, DeviceService deviceService) {
    this.databaseDriver = databaseDriver;
    this.deviceService = deviceService;

    this.databaseDriver.executeUpdate("CREATE TABLE IF NOT EXISTS `orders`"
        + "("
        + "  id         INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
        + "  time       BIGINT                         NOT NULL,"
        + "  executorId INT                            NOT NULL,"
        + "  deviceId   INT                            NOT NULL,"
        + "  FOREIGN KEY (executorId) REFERENCES accounts (id),"
        + "  FOREIGN KEY (deviceId) REFERENCES devices (id)"
        + ");"
    );
  }

  @Override
  public @NotNull Order createOrder(@NotNull Account executor, @NotNull Device device) {
    long creationTime = System.currentTimeMillis();
    int id = this.databaseDriver.executeUpdate("INSERT INTO `orders` (`time`, `executorId`, `deviceId`) VALUES (?, ?, ?);", statement -> {
      statement.setLong(1, creationTime);
      statement.setInt(2, executor.getId());
      statement.setInt(3, device.getId());
    });

    return new Order(
        id,
        creationTime,
        executor.getUserName(),
        this.deviceService.getDeviceWithInfo(device.getId()).orElseThrow()
    );
  }

  @Override
  public @NotNull List<Order> getOrders() {
    List<Order> orders = new ArrayList<>();
    this.databaseDriver.executeQuery("SELECT o.id, o.time, o.deviceId, a.userName FROM orders o INNER JOIN accounts a ON o.executorId = a.id;", resultSet -> {
      while (resultSet.next()) {
        orders.add(new Order(
            resultSet.getInt("id"),
            resultSet.getLong("time"),
            resultSet.getString("userName"),
            this.deviceService.getDeviceWithInfo(resultSet.getInt("deviceId")).orElseThrow()
        ));
      }

      return null;
    });

    return orders;
  }

}
