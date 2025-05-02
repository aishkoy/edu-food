package kg.attractor.edufood.service.impl;

import kg.attractor.edufood.dto.ProductDto;
import kg.attractor.edufood.entity.Product;
import kg.attractor.edufood.exception.nsee.ProductNotFoundException;
import kg.attractor.edufood.mapper.ProductMapper;
import kg.attractor.edufood.repository.ProductRepository;
import kg.attractor.edufood.repository.specification.ProductSpecifications;
import kg.attractor.edufood.service.interfaces.ProductCategoryService;
import kg.attractor.edufood.service.interfaces.ProductService;
import kg.attractor.edufood.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryService productCategoryService;

    @Override
    public List<ProductDto> getMostOrderedProducts(int limit) {
        return findAndValidate(() -> productRepository.findMostOrderedProducts(limit));
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с таким id не был найден!"));

        log.info("Получен продукт по id: {}", id);
        return productMapper.toDto(product);
    }

    @Override
    public Page<ProductDto> getFilteredProducts(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                                Long categoryId, Long restaurantId, Pageable pageable) {
        log.info("Получение продуктов с фильтрами: name={}, priceRange=[{} - {}], categoryId={}, restaurantId={}",
                name, minPrice, maxPrice, categoryId, restaurantId);

        if (minPrice != null || maxPrice != null) {
            validatePricesGreaterThanZero(minPrice != null ? minPrice : BigDecimal.ONE,
                    maxPrice != null ? maxPrice : BigDecimal.valueOf(1000000));

            if (minPrice != null && maxPrice != null) {
                validateMinPriceLessThanMaxPrice(minPrice, maxPrice);
            }
        }

        if (categoryId != null) {
            productCategoryService.getProductCategoryById(categoryId);
        }

        Specification<Product> spec = Specification.where(ProductSpecifications.hasName(name))
                .and(ProductSpecifications.hasPriceBetween(minPrice, maxPrice))
                .and(ProductSpecifications.hasCategoryId(categoryId))
                .and(ProductSpecifications.hasRestaurantId(restaurantId));

        return getProductPage(() -> productRepository.findAll(spec, pageable));
    }

    @Override
    public Page<ProductDto> getProductsByName(String name, Pageable pageable) {
        log.info("Поиск продуктов по имени: {}", name);

        Specification<Product> spec = ProductSpecifications.hasName(name);
        return getProductPage(() -> productRepository.findAll(spec, pageable),
                "Продуктов с таким именем не было найдено!");
    }

    @Override
    public Page<ProductDto> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        log.info("Поиск продуктов по категории: {}", categoryId);
        productCategoryService.getProductCategoryById(categoryId);

        Specification<Product> spec = ProductSpecifications.hasCategoryId(categoryId);
        return getProductPage(() -> productRepository.findAll(spec, pageable),
                "Нет продуктов с такой категорией!");
    }

    @Override
    public Page<ProductDto> getAllRestaurantsProducts(Long restaurantId, Pageable pageable) {
        log.info("Поиск продуктов по ресторану: {}", restaurantId);

        Specification<Product> spec = ProductSpecifications.hasRestaurantId(restaurantId);
        return getProductPage(() -> productRepository.findAll(spec, pageable),
                "Продукты ресторана на этой странице не были найдены!");
    }

    @Override
    public ResponseEntity<?> getProductImage(Long productId) {
        ProductDto productDto = getProductById(productId);

        String filename = productDto.getImage();

        if(filename == null || filename.isBlank()){
            return FileUtil.getStaticFile("product.jpg", "images/products", MediaType.IMAGE_JPEG);
        }

        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        MediaType mediaType = extension.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;

        log.info("Получение фотографии для блюда с id {}", productId);
        try {
            return FileUtil.getStaticFile(filename, "images/products", mediaType);
        } catch (NoSuchElementException e) {
            return FileUtil.getStaticFile("product.jpg", "images/products", MediaType.IMAGE_JPEG);
        }
    }

    @Override
    public Pageable createPageableWithSort(int page, int size, String sortDirection, String sortBy) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
    }

    private Page<ProductDto> getProductPage(Supplier<Page<Product>> supplier) {
        return getProductPage(supplier, null);
    }

    private List<ProductDto> findAndValidate(Supplier<List<Product>> supplier) {
        List<Product> list = supplier.get();
        if (list.isEmpty()) {
            throw new ProductNotFoundException(
                    "Продукты не были найдены!"
            );
        }
        log.info("Получено продуктов {}", list.size());
        return list.stream().map(productMapper::toDto).toList();
    }

    private Page<ProductDto> getProductPage(Supplier<Page<Product>> supplier, String notFoundMessage) {
        Page<Product> page = supplier.get();
        if (page.isEmpty()) {
            throw new ProductNotFoundException(
                    notFoundMessage != null ? notFoundMessage : "Продукты на странице не найдены"
            );
        }
        log.info("Получено {} продуктов на странице", page.getSize());
        return page.map(productMapper::toDto);
    }

    private void validatePricesGreaterThanZero(BigDecimal... prices) {
        for (BigDecimal price : prices) {
            if (price != null && price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Цена должна быть больше нуля!");
            }
        }
    }

    private void validateMinPriceLessThanMaxPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) >= 0) {
            throw new IllegalArgumentException("Минимальная цена должна быть меньше максимальной!");
        }
    }
}