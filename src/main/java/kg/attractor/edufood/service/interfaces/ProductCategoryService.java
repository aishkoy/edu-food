package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDto> getAllCategories();

    ProductCategoryDto getProductCategoryById(Long categoryId);

    ProductCategoryDto getProductCategoryByName(String categoryName);
}
