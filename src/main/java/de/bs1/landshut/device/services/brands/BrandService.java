package de.bs1.landshut.device.services.brands;

import de.bs1.landshut.device.services.brands.object.Brand;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface BrandService {

  @NotNull Brand createBrand(@NotNull String name);

  void deleteBrand(int brandId);

  @NotNull List<Brand> getBrands();

}
