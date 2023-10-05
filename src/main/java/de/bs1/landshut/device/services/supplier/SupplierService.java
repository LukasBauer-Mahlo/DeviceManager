package de.bs1.landshut.device.services.supplier;

import de.bs1.landshut.device.services.location.object.Location;
import de.bs1.landshut.device.services.supplier.object.Supplier;
import de.bs1.landshut.device.services.supplier.object.contact.SupplierContact;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public interface SupplierService {

  @NotNull Supplier createSupplier(@NotNull String name, @NotNull Location location, @NotNull SupplierContact supplierContact);

  @Nullable Supplier getSupplier(int id);

  @NotNull List<Supplier> getAllSuppliers();

  @NotNull SupplierContact createContact(@NotNull String firstName, @NotNull String lastName, @NotNull String mail);

}
