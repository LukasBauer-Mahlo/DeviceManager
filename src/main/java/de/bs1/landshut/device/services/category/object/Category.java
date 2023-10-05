package de.bs1.landshut.device.services.category.object;

import org.jetbrains.annotations.NotNull;

public class Category {

  private final int id;
  private final String name;

  public Category(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  @NotNull
  public String getName() {
    return this.name;
  }

}
