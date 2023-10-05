package de.bs1.landshut.device.services.order.object;

import de.bs1.landshut.device.services.device.object.DeviceInfo;
import org.jetbrains.annotations.NotNull;

public class Order {

  private final int id;
  private final long time;
  private final String executorName;
  private final DeviceInfo deviceInfo;

  public Order(int id, long time, String executorName, DeviceInfo deviceInfo) {
    this.id = id;
    this.time = time;
    this.executorName = executorName;
    this.deviceInfo = deviceInfo;
  }

  public int getId() {
    return id;
  }

  public long getTime() {
    return time;
  }

  @NotNull
  public String getExecutorName() {
    return this.executorName;
  }

  @NotNull
  public DeviceInfo getDeviceInfo() {
    return this.deviceInfo;
  }

}
