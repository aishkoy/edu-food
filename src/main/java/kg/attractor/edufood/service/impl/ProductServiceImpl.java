package kg.attractor.edufood.service.impl;

import kg.attractor.edufood.dto.ProductDto;
import kg.attractor.edufood.entity.Product;
import kg.attractor.edufood.exception.nsee.ProductNotFoundException;
import kg.attractor.edufood.mapper.ProductMapper;
import kg.attractor.edufood.repository.ProductRepository;
import kg.attractor.edufood.service.interfaces.ProductCategoryService;
import kg.attractor.edufood.service.interfaces.ProductService;
import kg.attractor.edufood.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с таким id не был найден!"));

        log.info("Получен продукт по id: {}", id);

        return productMapper.toDto(product);
    }

    @Override
    public Page<ProductDto> getAllRestaurantsProducts(Long restaurantId, int page, int size, String sortDirection) {
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        return getProductPage(() -> productRepository.findByRestaurantId(restaurantId, pageable),
                "Продукты ресторана на этой странице не были найдены!");
    }

    @Override
    public Page<ProductDto> getAllProducts(int page, int size, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size);
        return getProductPage(() -> productRepository.findAll(pageable),
                "Продукты на странице не были найдены!");
    }

    @Override
    public Page<ProductDto> getProductsByName(String name, int page, int size, String sortDirection) {
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        try {
            return getProductPage(() -> productRepository.findByNameStartingWith(name, pageable));
        } catch (NoSuchElementException e) {
            return getProductPage(() -> productRepository.findByNameContaining(name, pageable),
                    "Продуктов с таким именем не было найдено!");
        }
    }

    @Override
    public Page<ProductDto> getProductsByCategoryId(Long categoryId, int page, int size, String sortDirection) {
        productCategoryService.getProductCategoryById(categoryId);
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        return getProductPage(() -> productRepository.findByCategoryId(categoryId, pageable),
                "Нет продуктов с такой категорией!");
    }
    
    @Override
    public Page<ProductDto> getProductsByPriceGreaterThanEqual(BigDecimal price, int page, int size, String sortDirection){
        validatePricesGreaterThanZero(price);
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        return getProductPage(() -> productRepository.findByPriceGreaterThanEqual(price, pageable),
                "Продукты с ценой выше " + price + " не были найдены!");
    }
    
    @Override
    public Page<ProductDto> getProductsByPriceLessThanEqual(BigDecimal price, int page, int size, String sortDirection) {
        validatePricesGreaterThanZero(price);
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        return getProductPage(() -> productRepository.findByPriceLessThanEqual(price, pageable),
                "Продукты с ценой ниже " + price + " не были найдены!");
    }

    @Override
    public Page<ProductDto> getProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, int page, int size, String sortDirection) {
        validatePricesGreaterThanZero(minPrice, maxPrice);
        validateMinPriceLessThanMaxPrice(minPrice, maxPrice);
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        return getProductPage(() -> productRepository.findByPriceBetween(minPrice, maxPrice, pageable),
                "Продукты с ценой в диапазоне от " + minPrice + " до " + maxPrice + " не были найдены!");
    }

    @Override
    public ResponseEntity<?> getProductImage(Long productId) {
        ProductDto productDto = getProductById(productId);

        if (productDto.getImage() == null || productDto.getImage().isEmpty()) {
            return FileUtil.getStaticFile("default_product.jpg", "images/", MediaType.IMAGE_JPEG);
        }

        String filename = productDto.getImage();
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        MediaType mediaType = extension.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;

        log.info("Получение фотографии для блюда с id {}", productId);
        return FileUtil.getOutputFile(filename, "images/", mediaType);
    }
    
    private Pageable createPageableWithSort(int page, int size, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return PageRequest.of(page - 1, size, direction);
    }

    private Page<ProductDto> getProductPage(Supplier<Page<Product>> supplier) {
        return getProductPage(supplier, null);
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
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Цена должна быть больше нуля!");
            }
        }
    }

    private void validateMinPriceLessThanMaxPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice.compareTo(maxPrice) >= 0) {
            throw new IllegalArgumentException("Минимальная цена должна быть меньше максимальной!");
        }
    }
}
