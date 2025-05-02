<#import "../layout.ftlh" as layout>

<#assign additionalStyles>
    .filter-box {
    transition: all 0.3s ease;
    }
    .filter-box:hover {
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -4px rgba(0, 0, 0, 0.1);
    }
</#assign>

<@layout.layout title="EduFood - Рестораны" additionalStyles=additionalStyles>
    <div class="container mx-auto px-4 md:px-24 py-8">
        <div class="flex flex-col md:flex-row gap-8">
            <div class="w-full md:w-1/4">
                <div class="bg-white rounded-lg shadow-md p-6 filter-box mb-6">
                    <h2 class="text-xl font-bold mb-4 text-left">Фильтры</h2>
                    <form action="/restaurants" method="get" id="filterForm" class="text-left">
                        <div class="mb-4">
                            <label for="name" class="block text-gray-700 mb-2">Название ресторана</label>
                            <input type="text" id="name" name="name" value="${name!''}"
                                   class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-teal-600"
                                   placeholder="Введите название...">
                        </div>

                        <div class="mb-4">
                            <label for="sortBy" class="block text-gray-700 mb-2">Сортировать по</label>
                            <select id="sortBy" name="sortBy"
                                    class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-teal-600">
                                <option value="name" <#if sortBy == "name">selected</#if>>Названию</option>
                                <option value="address" <#if sortBy == "address">selected</#if>>Адресу</option>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label for="sortDirection" class="block text-gray-700 mb-2">Порядок сортировки</label>
                            <div class="flex gap-4">
                                <label class="inline-flex items-center">
                                    <input type="radio" name="sortDirection" value="asc" <#if sortDirection == "asc">checked</#if>
                                           class="form-radio text-teal-600">
                                    <span class="ml-2">По возрастанию</span>
                                </label>
                                <label class="inline-flex items-center">
                                    <input type="radio" name="sortDirection" value="desc" <#if sortDirection == "desc">checked</#if>
                                           class="form-radio text-teal-600">
                                    <span class="ml-2">По убыванию</span>
                                </label>
                            </div>
                        </div>

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

            <div class="w-full md:w-3/4">
                <div class="mb-6 flex flex-col sm:flex-row justify-between items-start sm:items-center">
                    <h1 class="text-3xl font-bold text-left">Рестораны</h1>
                    <div class="text-gray-600 mt-2 sm:mt-0 text-left sm:text-right">
                        <#if restaurants??>
                            Найдено ${restaurants.totalElements} ресторанов
                        </#if>
                    </div>
                </div>

                <#if restaurants?? && restaurants.content?size gt 0>
                    <div class="grid grid-cols-1 gap-6">
                        <#list restaurants.content as restaurant>
                            <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-all">
                                <div class="flex flex-col md:flex-row">
                                    <div class="md:w-1/3">
                                        <a href="/restaurants/${restaurant.id}">
                                            <img src="/api/restaurants/${restaurant.id}/image" alt="${restaurant.name}"
                                                 class="w-full h-full object-cover" style="min-height: 200px;">
                                        </a>
                                    </div>
                                    <div class="md:w-2/3 p-6">
                                        <div class="flex justify-between items-start">
                                            <a href="/restaurants/${restaurant.id}">
                                                <h3 class="font-bold text-xl hover:text-teal-600 transition">${restaurant.name}</h3>
                                            </a>
                                            <span class="bg-teal-100 text-teal-800 text-xs px-2 py-1 rounded-full">
                                                Ресторан
                                            </span>
                                        </div>

                                        <div class="flex items-start mt-2">
                                            <i data-lucide="map-pin" class="h-5 w-5 text-gray-500 mr-2"></i>
                                            <p class="text-gray-600">${restaurant.address}</p>
                                        </div>

                                        <p class="text-start text-gray-600 mt-4 line-clamp-3">${restaurant.description!''}</p>

                                        <div class="mt-6 flex justify-between items-center">
                                            <a href="/restaurants/${restaurant.id}"
                                               class="bg-teal-600 text-white px-6 py-2 rounded hover:bg-teal-700 transition">
                                                Просмотреть меню
                                            </a>
                                            <a href="/products?restaurantId=${restaurant.id}" class="text-teal-600 hover:text-teal-800 flex items-center">
                                                <span>Все блюда</span>
                                                <i data-lucide="chevron-right" class="h-5 w-5 ml-1"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>

                    <#if restaurants.totalPages gt 1>
                        <div class="mt-8 flex justify-center">
                            <div class="flex items-center space-x-1">
                                <#if restaurants.number gt 0>
                                    <a href="?page=${restaurants.number}&size=${pageSize}&name=${name!''}&sortDirection=${sortDirection}&sortBy=${sortBy}"
                                       class="px-4 py-2 border rounded text-teal-600 border-teal-600 hover:bg-teal-600 hover:text-white transition">
                                        <i data-lucide="chevron-left" class="h-5 w-5"></i>
                                    </a>
                                </#if>

                                <#list 1..restaurants.totalPages as i>
                                    <#if i == restaurants.number + 1>
                                        <span class="px-4 py-2 border rounded bg-teal-600 text-white">
                                            ${i}
                                        </span>
                                    <#elseif i <= 3 || (i >= restaurants.totalPages - 2) || (i >= restaurants.number - 1 && i <= restaurants.number + 3)>
                                        <a href="?page=${i}&size=${pageSize}&name=${name!''}&sortDirection=${sortDirection}&sortBy=${sortBy}"
                                           class="px-4 py-2 border rounded hover:bg-gray-100 transition">
                                            ${i}
                                        </a>
                                    <#elseif i == 4 || i == restaurants.totalPages - 3>
                                        <span class="px-4 py-2">...</span>
                                    </#if>
                                </#list>

                                <#if restaurants.number lt restaurants.totalPages - 1>
                                    <a href="?page=${restaurants.number + 2}&size=${pageSize}&name=${name!''}&sortDirection=${sortDirection}&sortBy=${sortBy}"
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
                        <h2 class="text-2xl font-bold text-gray-800 mb-4">Рестораны не найдены</h2>
                        <p class="text-gray-600 mb-6">Попробуйте изменить параметры фильтрации или сбросить все фильтры.</p>
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
        document.addEventListener('DOMContentLoaded', function() {
            const resetFiltersBtn = document.getElementById('resetFilters');
            const resetFiltersEmptyBtn = document.getElementById('resetFiltersEmpty');

            function resetFilters() {
                document.getElementById('name').value = '';
                document.getElementById('sortBy').value = 'name';
                document.querySelector('input[name="sortDirection"][value="asc"]').checked = true;
                document.getElementById('filterForm').submit();
            }

            if (resetFiltersBtn) {
                resetFiltersBtn.addEventListener('click', resetFilters);
            }

            if (resetFiltersEmptyBtn) {
                resetFiltersEmptyBtn.addEventListener('click', resetFilters);
            }

            if (sessionStorage.getItem('restaurantScrollPosition') &&
                sessionStorage.getItem('restaurantCurrentFilters') === new URLSearchParams(window.location.search).toString()) {
                window.scrollTo(0, sessionStorage.getItem('restaurantScrollPosition'));
                sessionStorage.removeItem('restaurantScrollPosition');
                sessionStorage.removeItem('restaurantCurrentFilters');
            }

            const menuLinks = document.querySelectorAll('a[href^="/restaurants/"]');
            menuLinks.forEach(link => {
                link.addEventListener('click', function() {
                    sessionStorage.setItem('restaurantScrollPosition', window.scrollY);
                    sessionStorage.setItem('restaurantCurrentFilters', new URLSearchParams(window.location.search).toString());
                });
            });
        });
    </script>
</@layout.layout>