package de.bs1.landshut.device.services.device.object;

import de.bs1.landshut.device.services.brands.object.Brand;
import de.bs1.landshut.device.services.category.object.Category;
import de.bs1.landshut.device.services.supplier.object.Supplier;
import org.jetbrains.annotations.NotNull;

public class DeviceInfo {

  private final Device device;
  private final Category category;
  private final Brand brand;
  private final Supplier supplier;

  public DeviceInfo(Device device, Category category, Brand brand, Supplier supplier) {
    this.device = device;
    this.category = category;
    this.brand = brand;
    this.supplier = supplier;
  }

  @NotNull
  public Device getDevice() {
    return this.device;
  }

  @NotNull
  public Category getCategory() {
    return this.category;
  }

  @NotNull
  public Brand getBrand() {
    return this.brand;
  }

  @NotNull
  public Supplier getSupplier() {
    return this.supplier;
  }

}
