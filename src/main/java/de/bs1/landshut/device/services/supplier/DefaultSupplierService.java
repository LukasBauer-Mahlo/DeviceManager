package de.bs1.landshut.device.services.supplier;

import de.bs1.landshut.device.database.DatabaseDriver;
import de.bs1.landshut.device.services.location.LocationService;
import de.bs1.landshut.device.services.location.object.Location;
import de.bs1.landshut.device.services.supplier.object.Supplier;
import de.bs1.landshut.device.services.supplier.object.contact.SupplierContact;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DefaultSupplierService implements SupplierService {

  private final DatabaseDriver databaseDriver;
  private final LocationService locationService;

  public DefaultSupplierService(DatabaseDriver databaseDriver, LocationService locationService) {
    this.databaseDriver = databaseDriver;
    this.locationService = locationService;

    this.databaseDriver.executeUpdate("CREATE TABLE IF NOT EXISTS suppliers"
        + "("
        + "  id              INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
        + "  name            VARCHAR(128)                   NOT NULL,"
        + "  locationId      INT                            NOT NULL,"
        + "  contactPersonId INT                            NOT NULL,"
        + "  FOREIGN KEY (locationId) REFERENCES locations (id),"
        + "  FOREIGN KEY (contactPersonId) REFERENCES suppliers_contact (id)"
        + ");"
    );

    this.databaseDriver.executeUpdate(
        "CREATE TABLE IF NOT EXISTS suppliers_contact"
            + "("
            + "  id        INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
            + "  firstName VARCHAR(32)                    NOT NULL,"
            + "  lastName  VARCHAR(32)                    NOT NULL,"
            + "  mail      VARCHAR(64)                    NOT NULL"
            + ");"
    );
  }

  @Override
  public @NotNull Supplier createSupplier(@NotNull String name, @NotNull Location location, @NotNull SupplierContact supplierContact) {
    int id = this.databaseDriver.executeUpdateWithKeys("INSERT INTO `suppliers` (`name`, `locationId`, `contactPersonId`) VALUES (?, ?, ?);", statement -> {
      statement.setString(1, name);
      statement.setInt(2, location.getId());
      statement.setInt(3, supplierContact.getId());
    }, resultSet -> {
      if (!resultSet.next()) {
        throw new RuntimeException("Unable to generate id for supplier");
      }

      return resultSet.getInt(1);
    });

    return new Supplier(id, name, location, supplierContact);
  }

  @Override
  public @Nullable Supplier getSupplier(int id) {
    for (Supplier supplier : this.getAllSuppliers()) {
      if (supplier.getId() == id) {
        return supplier;
      }
    }

    return null;
  }

  @Override
  public @NotNull List<Supplier> getAllSuppliers() {
    List<Supplier> suppliers = new ArrayList<>();
    this.databaseDriver.executeQuery(
        "SELECT s.id, s.name, s.locationId, s.contactPersonId, sc.firstName, sc.lastName, sc.mail FROM suppliers s INNER JOIN suppliers_contact sc ON s.contactPersonId = sc.id;", resultSet -> {
          while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int locationId = resultSet.getInt("locationId");
            int contactPersonId = resultSet.getInt("contactPersonId");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String mail = resultSet.getString("mail");

            suppliers.add(new Supplier(
                id,
                name,
                this.locationService.getLocation(locationId),
                new SupplierContact(
                    contactPersonId,
                    firstName,
                    lastName,
                    mail
                )
            ));
          }

          return null;
        });

    return suppliers;
  }

  @Override
  public @NotNull SupplierContact createContact(@NotNull String firstName, @NotNull String lastName, @NotNull String mail) {
    int id = this.databaseDriver.executeUpdateWithKeys("INSERT INTO `suppliers_contact` (`firstName`, `lastName`, `mail`) VALUES (?, ?, ?);", statement -> {
      statement.setString(1, firstName);
      statement.setString(2, lastName);
      statement.setString(3, mail);
    }, resultSet -> {
      if (!resultSet.next()) {
        throw new RuntimeException("Unable to generate id for supplier contact");
      }

      return resultSet.getInt(1);
    });
    return new SupplierContact(id, firstName, lastName, mail);
  }
}
