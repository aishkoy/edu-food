package kg.attractor.edufood.service.impl;

import kg.attractor.edufood.dto.ProductCategoryDto;
import kg.attractor.edufood.entity.ProductCategory;
import kg.attractor.edufood.exception.nsee.ProductCategoryNotFoundException;
import kg.attractor.edufood.mapper.ProductCategoryMapper;
import kg.attractor.edufood.repository.ProductCategoryRepository;
import kg.attractor.edufood.service.interfaces.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategoryDto> getAllCategories() {
        List<ProductCategoryDto> categories = productCategoryRepository.findAll()
                .stream()
                .map(productCategoryMapper::toDto)
                .toList();

        if (categories.isEmpty()) {
            throw new ProductCategoryNotFoundException("Категории продуктов не были найдены!");
        }

        log.info("Получено категорий продуктов: {}", categories.size());
        return categories;
    }

    @Override
    public ProductCategoryDto getProductCategoryById(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Категория продукта с таким id не была найдена!"));
        log.info("Получена категория по id {}", categoryId);
        return productCategoryMapper.toDto(productCategory);
    }

    @Override
    public ProductCategoryDto getProductCategoryByName(String categoryName){
        ProductCategory productCategory = productCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ProductCategoryNotFoundException("Категория продукта с таким именем была найдена!"));
        log.info("Получена категория по имени {}", categoryName);
        return productCategoryMapper.toDto(productCategory);
    }

}
