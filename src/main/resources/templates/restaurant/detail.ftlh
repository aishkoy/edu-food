<#import "../layout.ftlh" as layout>

<@layout.layout title="EduFood - ${restaurant.name}">
    <div class="container mx-auto px-4 md:px-24 py-8">
        <div class="bg-white rounded-lg shadow-md overflow-hidden mb-8">
            <div class="relative">
                <img src="/api/restaurants/${restaurant.id}/image" alt="${restaurant.name}"
                     class="w-full h-64 object-cover">
                <div class="absolute inset-0 bg-gradient-to-t from-black/70 to-transparent flex items-end">
                    <div class="p-6 text-white">
                        <h1 class="text-3xl font-bold">${restaurant.name}</h1>
                        <div class="flex items-center mt-2">
                            <i data-lucide="map-pin" class="h-5 w-5 mr-2"></i>
                            <span>${restaurant.address}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="p-6">
                <h2 class="text-xl font-semibold mb-4">О ресторане</h2>
                <p class="text-gray-700">${restaurant.description!''}</p>
                <div class="mt-6 flex flex-wrap gap-4">
                    <a href="#menu" class="bg-teal-600 text-white px-4 py-2 rounded hover:bg-teal-700 transition">
                        Посмотреть меню
                    </a>
                    <a href="/products?restaurantId=${restaurant.id}" class="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300 transition">
                        Все блюда в каталоге
                    </a>
                </div>
            </div>
        </div>

        <!-- Меню ресторана -->
        <div id="menu" class="mb-8">
            <div class="flex justify-between items-center mb-6">
                <h2 class="text-2xl font-bold">Меню</h2>
                <div class="text-gray-600">
                    <#if products??>
                        Найдено ${products.totalElements} блюд
                    </#if>
                </div>
            </div>

            <#if products?? && products.content?size gt 0>
                <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                    <#list products.content as product>
                        <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-all">
                            <a href="/products/${product.id}">
                                <img src="/api/products/${product.id}/image" alt="${product.name}"
                                     class="w-full h-48 object-cover">
                            </a>
                            <div class="p-4">
                                <div class="flex justify-between items-start">
                                    <a href="/products/${product.id}">
                                        <h3 class="font-semibold text-lg hover:text-teal-600 transition">${product.name}</h3>
                                    </a>
                                    <span class="bg-amber-100 text-amber-800 text-xs px-2 py-1 rounded-full">
                                        ${product.category.name}
                                    </span>
                                </div>

                                <p class="text-gray-600 mt-2 text-sm line-clamp-2 h-10">${product.description!''}</p>

                                <div class="flex justify-between items-center mt-4">
                                    <span class="text-gray-900 font-bold text-lg">${product.price} сом</span>

                                    <form action="/cart" method="post" class="flex items-center">
                                        <@layout.csrf/>
                                        <input type="hidden" name="productId" value="${product.id}">
                                        <input type="hidden" name="quantity" value="1">
                                        <button type="submit"
                                                class="bg-teal-600 text-white p-2 rounded-full hover:bg-teal-700 transition">
                                            <i data-lucide="shopping-cart" class="h-5 w-5"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>

                <#if products.totalPages gt 1>
                    <div class="mt-8 flex justify-center">
                        <div class="flex items-center space-x-1">
                            <#if products.number gt 0>
                                <a href="?page=${products.number}&size=${pageSize}&sortDirection=${sortDirection}&sortBy=${sortBy}"
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
                                    <a href="?page=${i}&size=${pageSize}&sortDirection=${sortDirection}&sortBy=${sortBy}"
                                       class="px-4 py-2 border rounded hover:bg-gray-100 transition">
                                        ${i}
                                    </a>
                                <#elseif i == 4 || i == products.totalPages - 3>
                                    <span class="px-4 py-2">...</span>
                                </#if>
                            </#list>

                            <#if products.number lt products.totalPages - 1>
                                <a href="?page=${products.number + 2}&size=${pageSize}&sortDirection=${sortDirection}&sortBy=${sortBy}"
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
                        <i data-lucide="utensils" class="h-16 w-16 text-gray-400"></i>
                    </div>
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">В этом ресторане пока нет блюд</h2>
                    <p class="text-gray-600 mb-6">Возможно, меню скоро пополнится. Загляните позже!</p>
                    <a href="/restaurants"
                       class="bg-teal-600 text-white px-6 py-3 rounded-lg hover:bg-teal-700 transition inline-block">
                        Вернуться к списку ресторанов
                    </a>
                </div>
            </#if>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const cartForms = document.querySelectorAll('form[action="/cart"]');
            cartForms.forEach(form => {
                form.addEventListener('submit', function() {
                    sessionStorage.setItem('restaurantDetailScrollPosition', window.scrollY);
                    sessionStorage.setItem('restaurantDetailId', '${restaurant.id}');
                });
            });

            if (sessionStorage.getItem('restaurantDetailScrollPosition') &&
                sessionStorage.getItem('restaurantDetailId') === '${restaurant.id}') {
                window.scrollTo(0, sessionStorage.getItem('restaurantDetailScrollPosition'));
                sessionStorage.removeItem('restaurantDetailScrollPosition');
                sessionStorage.removeItem('restaurantDetailId');
            }
        });
    </script>
</@layout.layout>