package de.bs1.landshut.device.services.order;

import de.bs1.landshut.device.services.account.object.Account;
import de.bs1.landshut.device.services.device.object.Device;
import de.bs1.landshut.device.services.order.object.Order;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface OrderService {

  @NotNull Order createOrder(@NotNull Account account, @NotNull Device device);

  @NotNull List<Order> getOrders();

}
