package de.bs1.landshut.device.web.devices;

import de.bs1.landshut.device.DeviceManager;
import io.javalin.Javalin;

public class ListDevicesRoute {

  public ListDevicesRoute(Javalin javalin) {
    javalin.get("/devices", context -> {
      context.json(DeviceManager.getInstance().getServices().getDeviceService().getDevicesWithInfo());
    });
  }

}
