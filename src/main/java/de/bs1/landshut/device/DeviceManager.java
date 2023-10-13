package de.bs1.landshut.device;

import de.bs1.landshut.device.database.DatabaseDriver;
import de.bs1.landshut.device.database.MySQLDatabaseDriver;
import de.bs1.landshut.device.services.ServiceRegistry;
import de.bs1.landshut.device.util.javalin.DefaultAccessManager;
import de.bs1.landshut.device.util.GsonJsonMapper;
import de.bs1.landshut.device.web.administration.AccountRoute;
import de.bs1.landshut.device.web.auth.AuthenticationRoutes;
import de.bs1.landshut.device.web.brand.BrandRoutes;
import de.bs1.landshut.device.web.category.CategoryRoutes;
import de.bs1.landshut.device.web.devices.DevicesRoutes;
import de.bs1.landshut.device.web.supplier.SupplierRoutes;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.jetbrains.annotations.NotNull;

public class DeviceManager {

  private static final DeviceManager DEVICE_MANAGER = new DeviceManager();

  private DatabaseDriver databaseDriver;
  private ServiceRegistry serviceRegistry;

  public static void main(String[] args) {
    DEVICE_MANAGER.start();
  }

  private void start() {
    this.databaseDriver = new MySQLDatabaseDriver();
    this.serviceRegistry = new ServiceRegistry(this.databaseDriver);

    Javalin javalin = Javalin.create().updateConfig(config -> {
      config.plugins.enableCors(cors -> cors.add(CorsPluginConfig::anyHost));
      config.showJavalinBanner = false;
      config.jsonMapper(new GsonJsonMapper());
      config.accessManager(new DefaultAccessManager(this.serviceRegistry.getTokenService(), this.serviceRegistry.getAccountService()));
    }).start(1887);
    //register web handlers

    new AuthenticationRoutes(javalin);
    new DevicesRoutes(javalin);
    new BrandRoutes(javalin);
    new CategoryRoutes(javalin);
    new SupplierRoutes(javalin);
    new AccountRoute(javalin);

  }

  @NotNull
  public static DeviceManager getInstance() {
    return DEVICE_MANAGER;
  }

  @NotNull
  public DatabaseDriver getDatabaseDriver() {
    return this.databaseDriver;
  }

  @NotNull
  public ServiceRegistry getServices() {
    return this.serviceRegistry;
  }

}
