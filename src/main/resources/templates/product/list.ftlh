<#import "../layout.ftlh" as layout>

<#assign additionalStyles>
    .filter-box {
    transition: all 0.3s ease;
    }
    .filter-box:hover {
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -4px rgba(0, 0, 0, 0.1);
    }
    .price-slider::-webkit-slider-thumb {
    -webkit-appearance: none;
    appearance: none;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background: #0d9488;
    cursor: pointer;
    }
    .price-slider::-moz-range-thumb {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background: #0d9488;
    cursor: pointer;
    }
</#assign>

<@layout.layout title="EduFood - Каталог продуктов" additionalStyles=additionalStyles>
    <div class="container mx-auto px-4 md:px-24 py-8">
        <div class="flex flex-col md:flex-row gap-8">
            <!-- Фильтры -->
            <div class="w-full md:w-1/4">
                <div class="bg-white rounded-lg shadow-md p-6 filter-box mb-6">
                    <h2 class="text-xl font-bold mb-4 text-left">Фильтры</h2>
                    <form action="/products" method="get" id="filterForm" class="text-left">
                        <!-- Поиск по названию -->
                        <div class="mb-4">
                            <label for="name" class="block text-gray-700 mb-2">Название блюда</label>
                            <input type="text" id="name" name="name" value="${name!''}"
                                   class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-teal-600"
                                   placeholder="Введите название...">
                        </div>

                        <!-- Фильтр по цене -->
                        <div class="mb-4">
                            <label class="block text-gray-700 mb-2">Цена (сом)</label>
                            <div class="flex gap-2 mb-2">
                                <input type="number" id="minPrice" name="minPrice" value="${minPrice!''}" min="0"
                                       class="w-1/2 px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-teal-600"
                                       placeholder="От">
                                <input type="number" id="maxPrice" name="maxPrice" value="${maxPrice!''}" min="0"
                                       class="w-1/2 px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-teal-600"
                                       placeholder="До">
                            </div>
                            <div class="relative mt-4">
                                <input type="range" id="priceRange" min="0" max="5000" step="50"
                                       class="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer price-slider">
                                <div class="flex justify-between text-xs text-gray-500 mt-1">
                                    <span>0</span>
                                    <span>2500</span>
                                    <span>5000</span>
                                </div>
                            </div>
                        </div>

                        <!-- Фильтр по категории -->
                        <div class="mb-4">
                            <label for="categoryId" class="block text-gray-700 mb-2">Категория</label>
                            <select id="categoryId" name="categoryId"
                                    class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-teal-600">
                                <option value="">Все категории</option>
                                <#if categories??>
                                    <#list categories as category>
                                        <option value="${category.id}"
                                                <#if categoryId?? && categoryId == category.id>selected</#if>>${category.name}</option>
                                    </#list>
                                </#if>
                            </select>
                        </div>

                        <!-- Фильтр по ресторану -->
                        <div class="mb-4">
                            <label for="restaurantId" class="block text-gray-700 mb-2">Ресторан</label>
                            <select id="restaurantId" name="restaurantId"
                                    class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-teal-600">
                                <option value="">Все рестораны</option>
                                <#if restaurants??>
                                    <#list restaurants as restaurant>
                                        <option value="${restaurant.id}"
                                                <#if restaurantId?? && restaurantId == restaurant.id>selected</#if>>${restaurant.name}</option>
                                    </#list>
                                </#if>
                            </select>
                        </div>

                        <!-- Сортировка -->
                        <div class="mb-4">
                            <label for="sortBy" class="block text-gray-700 mb-2">Сортировать по</label>
                            <select id="sortBy" name="sortBy"
                                    class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-teal-600">
                                <option value="name" <#if sortBy == "name">selected</#if>>Названию</option>
                                <option value="price" <#if sortBy == "price">selected</#if>>Цене</option>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label for="sortDirection" class="block text-gray-700 mb-2">Порядок сортировки</label>
                            <div class="flex gap-4">
                                <label class="inline-flex items-center">
                                    <input type="radio" name="sortDirection" value="asc"
                                           <#if sortDirection == "asc">checked</#if>
                                           class="form-radio text-teal-600">
                                    <span class="ml-2">По возрастанию</span>
                                </label>
                                <label class="inline-flex items-center">
                                    <input type="radio" name="sortDirection" value="desc"
                                           <#if sortDirection == "desc">checked</#if>
                                           class="form-radio text-teal-600">
                                    <span class="ml-2">По убыванию</span>
                                </label>
                            </div>
                        </div>

                        <!-- Кнопки -->
                        <div class="flex gap-4">
                            <button type="submit"
                                    class="bg-teal-600 text-white px-4 py-2 rounded hover:bg-teal-700 transition w-full">
                                Применить
                            </button>
                            <button type="button" id="resetFilters"
                                    class="bg-gray-200 text-gray-700 px-4 py-2 rounded hover:bg-gray-300 transition w-full">
                                Сбросить
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Список продуктов -->
            <div class="w-full md:w-3/4">
                <div class="mb-6 flex flex-col sm:flex-row justify-between items-start sm:items-center">
                    <h1 class="text-3xl font-bold text-left">Каталог блюд</h1>
                    <div class="text-gray-600 mt-2 sm:mt-0 text-left sm:text-right">
                        <#if products??>
                            Найдено ${products.totalElements} блюд
                        </#if>
                    </div>
                </div>

                <#if products?? && products.content?size gt 0>
                    <!-- Сетка продуктов -->
                    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                        <#list products.content as product>
                            <div class="bg-white rounded-2xl shadow-lg overflow-hidden transform hover:scale-105 transition duration-300 flex flex-col h-full">
                                <div class="relative">
                                    <img src="/api/products/${product.id}/image" alt="${product.name}" class="w-full h-48 object-cover">
                                    <div class="absolute top-2 right-2 bg-white bg-opacity-75 rounded-full px-3 py-1">
                                        <span class="text-xs font-medium text-gray-800 uppercase">${product.category.name}</span>
                                    </div>
                                </div>
                                <div class="p-5 flex flex-col flex-grow">
                                    <div class="mb-2">
                                        <h3 class="text-xl font-semibold text-gray-800 hover:text-teal-600 transition">${product.name}</h3>
                                    </div>

                                    <div class="flex-grow mb-4">
                                        <p class="text-gray-600 text-sm overflow-hidden line-clamp-3 h-8">${product.description!''}</p>
                                        <a href="/restaurants/${product.restaurant.id}" class="block mt-2 text-sm text-gray-500 hover:text-teal-600 transition">${product.restaurant.name}</a>
                                    </div>

                                    <div class="flex items-center justify-between mt-auto pt-3 border-t border-gray-100">
                                        <span class="text-lg font-bold text-gray-900">${product.price} сом</span>
                                        <form action="/cart" method="post" class="flex justify-end">
                                            <@layout.csrf/>
                                            <input type="hidden" name="productId" value="${product.id}">
                                            <input type="hidden" name="quantity" value="1">
                                            <button type="submit" class="bg-teal-600 text-white p-3 rounded-full hover:bg-teal-700 transition duration-200">
                                                <i data-lucide="shopping-cart" class="h-5 w-5"></i>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>

                    <!-- Пагинация -->
                    <#if products.totalPages gt 1>
                        <div class="mt-8 flex justify-center">
                            <div class="flex items-center space-x-1">
                                <#if products.number gt 0>
                                    <a href="?page=${products.number}&size=${pageSize}&name=${name!''}&minPrice=${minPrice!''}&maxPrice=${maxPrice!''}&categoryId=${categoryId!''}&restaurantId=${restaurantId!''}&sortDirection=${sortDirection}&sortBy=${sortBy}"
                                       class="px-4 py-2 border rounded text-teal-600 border-teal-600 hover:bg-teal-600 hover:text-white transition">
                                        <i data-lucide="chevron-left" class="h-5 w-5"></i>
                                    </a>
                                </#if>

                                <#list 1..products.totalPages as i>
                                    <#if i == products.number + 1>
                                        <span class="px-4 py-2 border rounded bg-teal-600 text-white">
                                            ${i}
                                        </span>
                                    <#elseif i <= 3 || (i >= products.totalPages - 2) || (i >= products.number - 1 && i <= products.number + 3)>
                                        <a href="?page=${i}&size=${pageSize}&name=${name!''}&minPrice=${minPrice!''}&maxPrice=${maxPrice!''}&categoryId=${categoryId!''}&restaurantId=${restaurantId!''}&sortDirection=${sortDirection}&sortBy=${sortBy}"
                                           class="px-4 py-2 border rounded hover:bg-gray-100 transition">
                                            ${i}
                                        </a>
                                    <#elseif i == 4 || i == products.totalPages - 3>
                                        <span class="px-4 py-2">...</span>
                                    </#if>
                                </#list>

                                <#if products.number lt products.totalPages - 1>
                                    <a href="?page=${products.number + 2}&size=${pageSize}&name=${name!''}&minPrice=${minPrice!''}&maxPrice=${maxPrice!''}&categoryId=${categoryId!''}&restaurantId=${restaurantId!''}&sortDirection=${sortDirection}&sortBy=${sortBy}"
                                       class="px-4 py-2 border rounded text-teal-600 border-teal-600 hover:bg-teal-600 hover:text-white transition">
                                        <i data-lucide="chevron-right" class="h-5 w-5"></i>
                                    </a>
                                </#if>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="bg-white rounded-lg shadow-md p-8 text-center">
                        <div class="flex justify-center mb-4">
                            <i data-lucide="search-x" class="h-16 w-16 text-gray-400"></i>
                        </div>
                        <h2 class="text-2xl font-bold text-gray-800 mb-4">Продукты не найдены</h2>
                        <p class="text-gray-600 mb-6">Попробуйте изменить параметры фильтрации или сбросить все
                            фильтры.</p>
                        <button type="button" id="resetFiltersEmpty"
                                class="bg-teal-600 text-white px-6 py-3 rounded-lg hover:bg-teal-700 transition">
                            Сбросить все фильтры
                        </button>
                    </div>
                </#if>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const priceRange = document.getElementById('priceRange');
            const minPriceInput = document.getElementById('minPrice');
            const maxPriceInput = document.getElementById('maxPrice');

            if (priceRange && minPriceInput && maxPriceInput) {
                if (minPriceInput.value && maxPriceInput.value) {
                    priceRange.value = (parseInt(minPriceInput.value) + parseInt(maxPriceInput.value)) / 2;
                } else if (minPriceInput.value) {
                    priceRange.value = minPriceInput.value;
                } else if (maxPriceInput.value) {
                    priceRange.value = maxPriceInput.value;
                }

                priceRange.addEventListener('input', function () {
                    const value = parseInt(this.value);
                    const min = Math.max(0, Math.floor(value * 0.8));
                    const max = Math.ceil(value * 1.2);

                    minPriceInput.value = min;
                    maxPriceInput.value = max;
                });
            }

            const resetFiltersBtn = document.getElementById('resetFilters');
            const resetFiltersEmptyBtn = document.getElementById('resetFiltersEmpty');

            function resetFilters() {
                const nameInput = document.getElementById('name');
                if (nameInput) nameInput.value = '';

                if (minPriceInput) minPriceInput.value = '';
                if (maxPriceInput) maxPriceInput.value = '';

                const categoryIdSelect = document.getElementById('categoryId');
                if (categoryIdSelect) categoryIdSelect.value = '';

                const restaurantIdSelect = document.getElementById('restaurantId');
                if (restaurantIdSelect) restaurantIdSelect.value = '';

                const sortBySelect = document.getElementById('sortBy');
                if (sortBySelect) sortBySelect.value = 'name';

                const sortDirectionRadio = document.querySelector('input[name="sortDirection"][value="asc"]');
                if (sortDirectionRadio) sortDirectionRadio.checked = true;

                const filterForm = document.getElementById('filterForm');
                if (filterForm) filterForm.submit();
            }

            if (resetFiltersBtn) {
                resetFiltersBtn.addEventListener('click', resetFilters);
            }

            if (resetFiltersEmptyBtn) {
                resetFiltersEmptyBtn.addEventListener('click', resetFilters);
            }

            const cartForms = document.querySelectorAll('form[action="/cart"]');

            cartForms.forEach(form => {
                form.addEventListener('submit', function(e) {
                    sessionStorage.setItem('scrollPosition', window.scrollY);

                    if (!form.querySelector('input[name="redirectUrl"]')) {
                        const redirectInput = document.createElement('input');
                        redirectInput.type = 'hidden';
                        redirectInput.name = 'redirectUrl';
                        redirectInput.value = window.location.pathname + window.location.search;
                        form.appendChild(redirectInput);
                    }
                });
            });

            const scrollPosition = sessionStorage.getItem('scrollPosition');
            if (scrollPosition !== null) {
                window.scrollTo(0, parseInt(scrollPosition));
                sessionStorage.removeItem('scrollPosition');
            }
        });
    </script>
</@layout.layout>