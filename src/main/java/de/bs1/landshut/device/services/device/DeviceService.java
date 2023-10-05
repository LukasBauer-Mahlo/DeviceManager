package de.bs1.landshut.device.services.device;

import de.bs1.landshut.device.services.device.object.Device;
import de.bs1.landshut.device.services.device.object.DeviceInfo;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface DeviceService {

  @NotNull Device createDevice(@NotNull String name, int categoryId, int brandId, int amount, int supplierId);

  void updateDevice(@NotNull Device device);

  void deleteDevice(int deviceId);

  @NotNull List<Device> getDevices();

  @NotNull List<DeviceInfo> getDevicesWithInfo();

  @NotNull Optional<DeviceInfo> getDeviceWithInfo(int id);

}
