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
import java.util.List;
import java.util.Map;
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

        boolean hasNameFilter = name != null && !name.trim().isEmpty();
        boolean hasPriceRangeFilter = minPrice != null || maxPrice != null;

        String key = (hasNameFilter ? "1" : "0") +
                (hasPriceRangeFilter ? "1" : "0") +
                (categoryId != null ? "1" : "0") +
                (restaurantId != null ? "1" : "0");

        Map<String, Supplier<Page<ProductDto>>> strategies = buildFilterStrategies(
                name, minPrice, maxPrice, categoryId, restaurantId, pageable);

        return strategies.getOrDefault(key, () -> getAllProducts(pageable)).get();
    }

    private Map<String, Supplier<Page<ProductDto>>> buildFilterStrategies(
            String name, BigDecimal minPrice, BigDecimal maxPrice,
            Long categoryId, Long restaurantId, Pageable pageable) {

        return Map.ofEntries(
                Map.entry("0000", () -> getAllProducts(pageable)),
                Map.entry("0001", () -> getAllRestaurantsProducts(restaurantId, pageable)),
                Map.entry("0010", () -> getProductsByCategoryId(categoryId, pageable)),
                Map.entry("0011", () -> getProductsByCategoryAndRestaurant(categoryId, restaurantId, pageable)),
                Map.entry("0100", () -> getProductsByPriceBetween(minPrice, maxPrice, pageable)),
                Map.entry("0101", () -> getByPriceBetweenAndRestaurant(minPrice, maxPrice, restaurantId, pageable)),
                Map.entry("0110", () -> getByPriceBetweenAndCategory(minPrice, maxPrice, categoryId, pageable)),
                Map.entry("0111", () -> getByPriceBetweenAndCetgoryAndRestaurant(minPrice, maxPrice, categoryId, restaurantId, pageable)),
                Map.entry("1000", () -> getProductsByName(name, pageable)),
                Map.entry("1001", () -> getByNameAndRestaurant(name, restaurantId, pageable)),
                Map.entry("1010", () -> getByNameAndCategory(name, categoryId, pageable)),
                Map.entry("1011", () -> getByNameAndCategoryAndRestaurant(name, categoryId, restaurantId, pageable)),
                Map.entry("1100", () -> getProductsByNameAndPriceRange(name, minPrice, maxPrice, pageable)),
                Map.entry("1101", () -> getByNameAndPriceRangeAndRestaurant(name, minPrice, maxPrice, restaurantId, pageable)),
                Map.entry("1110", () -> getByNameAndPriceRangeAndCategory(name, minPrice, maxPrice, categoryId, pageable)),
                Map.entry("1111", () -> getByNameAndPriceRangeAndCategoryAndRestaurant(
                        name, minPrice, maxPrice, categoryId, restaurantId, pageable))
        );
    }

    @Override
    public Page<ProductDto> getByPriceBetweenAndCategory(BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Pageable pageable) {
        return getProductPage(() -> productRepository.findByPriceBetweenAndCategoryId(
                        minPrice, maxPrice, categoryId, pageable),
                "Продукты в указанном ценовом диапазоне с такой категорией не найдены!");

    }

    @Override
    public Page<ProductDto> getByPriceBetweenAndRestaurant(BigDecimal minPrice, BigDecimal maxPrice, Long restaurantId, Pageable pageable) {
        return getProductPage(() -> productRepository.findByPriceBetweenAndRestaurantId(
                        minPrice, maxPrice, restaurantId, pageable),
                "Продукты в указанном ценовом диапазоне в этом ресторане не найдены!");
    }

    @Override
    public Page<ProductDto> getByPriceBetweenAndCetgoryAndRestaurant(BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Long restaurantId, Pageable pageable) {
        return getProductPage(() -> productRepository.findByPriceBetweenAndCategoryIdAndRestaurantId(
                        minPrice, maxPrice, categoryId, restaurantId, pageable),
                "Продукты в указанном ценовом диапазоне с такой категорией и рестораном не найдены!");
    }


    @Override
    public Page<ProductDto> getByNameAndRestaurant(String name, Long restaurantId, Pageable pageable) {
        try {
            return getProductPage(() -> productRepository.findByNameStartingWithAndRestaurantId(name, restaurantId, pageable));
        } catch (NoSuchElementException e) {
            return getProductPage(() -> productRepository.findByNameContainingAndRestaurantId(
                            name, restaurantId, pageable),
                    "Продукты с таким названием и рестораном не найдены!");
        }
    }

    @Override
    public Page<ProductDto> getByNameAndCategory(String name, Long categoryId, Pageable pageable) {
        try {
            return getProductPage(() -> productRepository.findByNameStartingWithAndCategoryId(name, categoryId, pageable));
        } catch (NoSuchElementException e) {
            return getProductPage(() -> productRepository.findByNameContainingAndCategoryId(
                            name, categoryId, pageable),
                    "Продукты с таким названием и категорией не найдены!");
        }
    }

    @Override
    public Page<ProductDto> getByNameAndCategoryAndRestaurant(String name, Long categoryId, Long restaurantId, Pageable pageable) {
        try {
            return getProductPage(() -> productRepository.findByNameStartingWithAndCategoryIdAndRestaurantId(name, categoryId, restaurantId, pageable));
        } catch (NoSuchElementException e) {
            return getProductPage(() -> productRepository.findByNameContainingAndCategoryIdAndRestaurantId(
                            name, categoryId, restaurantId, pageable),
                    "Продукты с таким названием, категорией и рестораном не найдены!");
        }
    }

    @Override
    public Page<ProductDto> getProductsByCategoryAndRestaurant(Long categoryId, Long restaurantId, Pageable pageable) {
        productCategoryService.getProductCategoryById(categoryId);

        return getProductPage(() -> productRepository.findByCategoryIdAndRestaurantId(categoryId, restaurantId, pageable),
                "Продукты с указанной категорией и рестораном не найдены!");
    }

    @Override
    public Page<ProductDto> getAllRestaurantsProducts(Long restaurantId, Pageable pageable) {
        return getProductPage(() -> productRepository.findByRestaurantId(restaurantId, pageable),
                "Продукты ресторана на этой странице не были найдены!");
    }

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return getProductPage(() -> productRepository.findAll(pageable),
                "Продукты на странице не были найдены!");
    }

    @Override
    public Page<ProductDto> getProductsByName(String name, Pageable pageable) {
        try {
            return getProductPage(() -> productRepository.findByNameStartingWith(name, pageable));
        } catch (NoSuchElementException e) {
            return getProductPage(() -> productRepository.findByNameContaining(name, pageable),
                    "Продуктов с таким именем не было найдено!");
        }
    }

    @Override
    public Page<ProductDto> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        productCategoryService.getProductCategoryById(categoryId);
        return getProductPage(() -> productRepository.findByCategoryId(categoryId, pageable),
                "Нет продуктов с такой категорией!");
    }

    @Override
    public Page<ProductDto> getProductsByPriceGreaterThanEqual(BigDecimal price, Pageable pageable) {
        validatePricesGreaterThanZero(price);
        return getProductPage(() -> productRepository.findByPriceGreaterThanEqual(price, pageable),
                "Продукты с ценой выше " + price + " не были найдены!");
    }

    @Override
    public Page<ProductDto> getProductsByPriceLessThanEqual(BigDecimal price, Pageable pageable) {
        validatePricesGreaterThanZero(price);
        return getProductPage(() -> productRepository.findByPriceLessThanEqual(price, pageable),
                "Продукты с ценой ниже " + price + " не были найдены!");
    }

    @Override
    public Page<ProductDto> getProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if (maxPrice == null) {
            return getProductsByPriceGreaterThanEqual(minPrice, pageable);
        } else if (minPrice == null) {
            return getProductsByPriceGreaterThanEqual(maxPrice, pageable);
        } else {
            validatePricesGreaterThanZero(minPrice, maxPrice);
            validateMinPriceLessThanMaxPrice(minPrice, maxPrice);
            return getProductPage(() -> productRepository.findByPriceBetween(minPrice, maxPrice, pageable),
                    "Продукты с ценой в диапазоне от " + minPrice + " до " + maxPrice + " не были найдены!");
        }
    }

    @Override
    public Page<ProductDto> getProductsByNameAndPriceRange(String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        validatePricesGreaterThanZero(minPrice, maxPrice);
        validateMinPriceLessThanMaxPrice(minPrice, maxPrice);

        try {
            return getProductPage(() -> productRepository.findByNameStartingWithAndPriceBetween(
                    name, minPrice, maxPrice, pageable));
        } catch (NoSuchElementException e) {
            return getProductPage(() -> productRepository.findByNameContainingAndPriceBetween(
                            name, minPrice, maxPrice, pageable),
                    "Продукты с названием '" + name + "' и ценой в диапазоне от " + minPrice + " до " + maxPrice + " не найдены!");
        }
    }

    @Override
    public Page<ProductDto> getByNameAndPriceRangeAndCategory(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                                              Long categoryId, Pageable pageable) {
        validatePricesGreaterThanZero(minPrice, maxPrice);
        validateMinPriceLessThanMaxPrice(minPrice, maxPrice);
        productCategoryService.getProductCategoryById(categoryId);

        try {
            return getProductPage(() -> productRepository.findByNameStartingWithAndPriceBetweenAndCategoryId(
                    name, minPrice, maxPrice, categoryId, pageable));
        } catch (NoSuchElementException e) {
            return getProductPage(() -> productRepository.findByNameContainingAndPriceBetweenAndCategoryId(
                            name, minPrice, maxPrice, categoryId, pageable),
                    "Продукты с названием '" + name + "', ценой в диапазоне от " + minPrice + " до " + maxPrice +
                            " и категорией " + categoryId + " не найдены!");
        }
    }

    @Override
    public Page<ProductDto> getByNameAndPriceRangeAndRestaurant(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                                                Long restaurantId, Pageable pageable) {
        validatePricesGreaterThanZero(minPrice, maxPrice);
        validateMinPriceLessThanMaxPrice(minPrice, maxPrice);

        try {
            return getProductPage(() -> productRepository.findByNameStartingWithAndPriceBetweenAndRestaurantId(
                    name, minPrice, maxPrice, restaurantId, pageable));
        } catch (NoSuchElementException e) {
            return getProductPage(() -> productRepository.findByNameContainingAndPriceBetweenAndRestaurantId(
                            name, minPrice, maxPrice, restaurantId, pageable),
                    "Продукты с названием '" + name + "', ценой в диапазоне от " + minPrice + " до " + maxPrice +
                            " и рестораном " + restaurantId + " не найдены!");
        }
    }

    @Override
    public Page<ProductDto> getByNameAndPriceRangeAndCategoryAndRestaurant(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                                                           Long categoryId, Long restaurantId, Pageable pageable) {
        validatePricesGreaterThanZero(minPrice, maxPrice);
        validateMinPriceLessThanMaxPrice(minPrice, maxPrice);
        productCategoryService.getProductCategoryById(categoryId);

        try {
            return getProductPage(() -> productRepository.findByNameStartingWithAndPriceBetweenAndCategoryIdAndRestaurantId(
                    name, minPrice, maxPrice, categoryId, restaurantId, pageable));
        } catch (NoSuchElementException e) {
            return getProductPage(() -> productRepository.findByNameContainingAndPriceBetweenAndCategoryIdAndRestaurantId(
                            name, minPrice, maxPrice, categoryId, restaurantId, pageable),
                    "Продукты с названием '" + name + "', ценой в диапазоне от " + minPrice + " до " + maxPrice +
                            ", категорией " + categoryId + " и рестораном " + restaurantId + " не найдены!");
        }
    }

    @Override
    public ResponseEntity<?> getProductImage(Long productId) {
        ProductDto productDto = getProductById(productId);

        String filename = productDto.getImage();
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
