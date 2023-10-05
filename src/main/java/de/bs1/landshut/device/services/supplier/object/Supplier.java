package de.bs1.landshut.device.services.supplier.object;

import de.bs1.landshut.device.services.supplier.object.contact.SupplierContact;
import de.bs1.landshut.device.services.location.object.Location;
import org.jetbrains.annotations.NotNull;

public class Supplier {

  private final int id;
  private String name;
  private final Location location;
  private final SupplierContact supplierContact;

  public Supplier(int id, String name, Location location, SupplierContact supplierContact) {
    this.id = id;
    this.name = name;
    this.location = location;
    this.supplierContact = supplierContact;
  }

  public int getId() {
    return id;
  }

  @NotNull
  public String getName() {
    return name;
  }

  public void setName(@NotNull String name) {
    this.name = name;
  }

  @NotNull
  public Location getLocation() {
    return location;
  }

  @NotNull
  public SupplierContact getSupplierContact() {
    return supplierContact;
  }

}
