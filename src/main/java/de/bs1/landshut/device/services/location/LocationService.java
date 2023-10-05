package de.bs1.landshut.device.services.location;

import de.bs1.landshut.device.services.location.object.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LocationService {

  @Nullable Location getLocation(int id);

  @NotNull Location createLocation(@NotNull String postalCode, @NotNull String city, @NotNull String street, @NotNull String houseNumber);

}
