package de.bs1.landshut.device.services;

import de.bs1.landshut.device.database.DatabaseDriver;
import de.bs1.landshut.device.services.account.AccountService;
import de.bs1.landshut.device.services.account.DefaultAccountService;
import de.bs1.landshut.device.services.brands.BrandService;
import de.bs1.landshut.device.services.brands.DefaultBrandService;
import de.bs1.landshut.device.services.category.CategoryService;
import de.bs1.landshut.device.services.category.DefaultCategoryService;
import de.bs1.landshut.device.services.device.DefaultDeviceService;
import de.bs1.landshut.device.services.device.DeviceService;
import de.bs1.landshut.device.services.location.DefaultLocationService;
import de.bs1.landshut.device.services.location.LocationService;
import de.bs1.landshut.device.services.order.DefaultOrderService;
import de.bs1.landshut.device.services.order.OrderService;
import de.bs1.landshut.device.services.supplier.DefaultSupplierService;
import de.bs1.landshut.device.services.supplier.SupplierService;
import de.bs1.landshut.device.services.token.DefaultTokenService;
import de.bs1.landshut.device.services.token.TokenService;
import org.jetbrains.annotations.NotNull;

public class ServiceRegistry {

  private final AccountService accountService;
  private final TokenService tokenService;

  private final LocationService locationService;
  private final SupplierService supplierService;
  private final DeviceService deviceService;

  private final CategoryService categoryService;
  private final BrandService brandService;

  private final OrderService orderService;

  public ServiceRegistry(DatabaseDriver databaseDriver) {
    this.accountService = new DefaultAccountService(databaseDriver);
    this.tokenService = new DefaultTokenService(databaseDriver);

    this.locationService = new DefaultLocationService(databaseDriver);
    this.supplierService = new DefaultSupplierService(databaseDriver, locationService);
    this.deviceService = new DefaultDeviceService(databaseDriver, supplierService);

    this.categoryService = new DefaultCategoryService(databaseDriver);
    this.brandService = new DefaultBrandService(databaseDriver);

    this.orderService = new DefaultOrderService(databaseDriver, this.deviceService);

    /*
    SupplierContact supplierContact = this.supplierService.createContact("Lukas", "Bauer", "lukas.bauer@mahlo.com");
    Location location = this.locationService.createLocation("93333", "Neustadt", "Schubertstraße", "42");

    Supplier supplier = this.supplierService.createSupplier("Mahlo GmbH + Co. KG", location, supplierContact);

    Category category = this.categoryService.createCategory("Keyboards");
    Brand brand = this.brandService.createBrand("Samsung");

    this.deviceService.createDevice("Gaming Monitor", category.getId(), brand.getId(), 5, supplier.getId());
    this.deviceService.createDevice("Logitech Keyboard", category.getId(), brand.getId(), 120, supplier.getId());
    this.deviceService.createDevice("MSI Gaming X Trio", category.getId(), brand.getId(), 12, supplier.getId());


     */
  }

  @NotNull
  public AccountService getAccountService() {
    return this.accountService;
  }

  @NotNull
  public TokenService getTokenService() {
    return this.tokenService;
  }

  @NotNull
  public LocationService getLocationService() {
    return this.locationService;
  }

  @NotNull
  public SupplierService getSupplierService() {
    return this.supplierService;
  }

  @NotNull
  public DeviceService getDeviceService() {
    return this.deviceService;
  }

  @NotNull
  public CategoryService getCategoryService() {
    return this.categoryService;
  }

  @NotNull
  public BrandService getBrandService() {
    return this.brandService;
  }

  @NotNull
  public OrderService getOrderService() {
    return this.orderService;
  }

}
