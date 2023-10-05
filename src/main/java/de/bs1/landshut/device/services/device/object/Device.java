package de.bs1.landshut.device.services.device.object;

import org.jetbrains.annotations.NotNull;

public class Device {

  private final int id;
  private String name;
  private int categoryId;
  private int brandId;
  private int amount;
  private int supplierId;

  public Device(int id, String name, int categoryId, int brandId, int amount, int supplierId) {
    this.id = id;
    this.name = name;
    this.categoryId = categoryId;
    this.brandId = brandId;
    this.amount = amount;
    this.supplierId = supplierId;
  }

  public int getId() {
    return this.id;
  }

  @NotNull
  public String getName() {
    return this.name;
  }

  public void setName(@NotNull String name) {
    this.name = name;
  }

  public int getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getBrandId() {
    return this.brandId;
  }

  public void setBrandId(int brandId) {
    this.brandId = brandId;
  }

  public int getAmount() {
    return this.amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getSupplierId() {
    return this.supplierId;
  }

  public void setSupplierId(int supplierId) {
    this.supplierId = supplierId;
  }

}
