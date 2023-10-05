package de.bs1.landshut.device.services.category;

import de.bs1.landshut.device.services.category.object.Category;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface CategoryService {

  @NotNull Category createCategory(@NotNull String name);

  void deleteCategory(int categoryId);

  @NotNull List<Category> getCategories();

}
