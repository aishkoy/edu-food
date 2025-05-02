<#import "layout.ftlh" as layout>

<#assign additionalStyles>
    .wave-bottom {
    position: relative;
    height: 150px;
    overflow: hidden;
    }

    .wave-bottom::before {
    content: "";
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 100px;
    background-color: white;
    border-radius: 100% 100% 0 0;
    transform: translateY(70px);
    }

    .category-circle {
    transition: all 0.3s ease;
    }

    .category-circle:hover {
    transform: translateY(-5px);
    }
</#assign>

<@layout.layout title="EduFood - Доставка еды" additionalStyles=additionalStyles>
    <div class="mx-auto w-full bg-amber-300 min-h-screen-lg relative pb-36 pt-24">
        <div class="text-center mb-16">
            <h2 class="text-5xl font-bold text-black mb-8">Доставка еды</h2>
            <div class="max-w-3xl mx-auto">
                <div class="flex items-center bg-white rounded-full shadow-md">
                    <div class="flex items-center pl-4">
                        <i data-lucide="map-pin" class="h-5 w-5 text-gray-400"></i>
                    </div>
                    <input type="text" placeholder="Введите ваш адрес доставки"
                           class="flex-grow p-4 bg-transparent focus:outline-none rounded-l-full"/>
                </div>
            </div>
        </div>
        <div class="wave-bottom absolute bottom-0 w-full"></div>


        <div class="flex flex-wrap justify-center gap-4 md:gap-8">
            <#if categories??>
                <#list categories as category>
                    <a href="/products?categoryId=${category.id}">
                        <div class="flex flex-col items-center">
                            <div class="bg-white rounded-full p-6 w-24 h-24 flex items-center justify-center shadow-md category-circle">
                                <i data-lucide="${category.icon}" class="h-12 w-12 text-amber-500"></i>
                            </div>
                            <p class="mt-3 font-medium">${category.name}</p>
                        </div>
                    </a>
                </#list>
            </#if>
        </div>
    </div>

    <div class="container mx-auto px-4 md:px-24 py-16">
        <div class="flex justify-center mb-12">
            <div class="bg-amber-100 rounded-full p-6 w-32 h-32 flex items-center justify-center">
                <i data-lucide="utensils" class="h-16 w-16 text-amber-500"></i>
            </div>
        </div>

        <div class="text-center mb-16">
            <div class="flex justify-between items-center mb-12">
                <h2 class="text-4xl font-bold">Популярные блюда в Бишкеке</h2>
                <a href="/products" class="text-teal-600 font-medium hover:text-teal-700 transition flex items-center">
                    Смотреть все
                    <i data-lucide="arrow-right" class="h-5 w-5 ml-1"></i>
                </a>
            </div>
            <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                <#if products?? && products?size gt 0>
                    <#list products as product>
                        <div class="bg-white rounded-lg shadow-md p-4 hover:shadow-lg transition-all">
                            <img src="/api/products/${product.id}/image" alt="${product.name}"
                                 class="w-full h-40 object-cover rounded-lg mb-3">
                            <h3 class="font-semibold text-lg">${product.name}</h3>
                            <p class="text-gray-600 mt-1">Категория: ${product.category.name}</p>

                            <div class="flex flex-row items-center justify-around mt-4">
                                <p class="text-gray-600 font-bold text-lg">${product.price} сом</p>

                                <div>
                                    <form action="/cart" method="post">
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
                <#else>
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">Продуктов не найдено</h2>
                </#if>
            </div>
        </div>

        <div class="text-center mb-16">
            <div class="flex justify-between items-center mb-12">
                <h2 class="text-4xl font-bold">Популярные рестораны</h2>
                <a href="/restaurants"
                   class="text-teal-600 font-medium hover:text-teal-700 transition flex items-center">
                    Смотреть все
                    <i data-lucide="arrow-right" class="h-5 w-5 ml-1"></i>
                </a>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                <#if restaurants?? && restaurants?size gt 0>
                    <#list restaurants as restaurant>
                        <a href="/restaurants/${restaurant.id}" class="block">
                            <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-all">
                                <img src="/api/restaurants/${restaurant.id}/image" alt="Restaurant ${restaurant.name}"
                                     class="w-full h-48 object-cover">
                                <div class="p-4">
                                    <h3 class="font-bold text-xl">Ресторан ${restaurant.name}</h3>
                                    <p class="text-gray-600 mb-3">${restaurant.description}</p>
                                    <div class="flex justify-between items-center">
                                        <span class="text-gray-500 text-sm">Адрес: </span>
                                        <div class="flex flex-row items-center text-gray-600 text-sm gap-2">
                                            <i data-lucide="map-pin"
                                               class="h-5 w-5 text-gray-400"></i>${restaurant.address}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </#list>
                <#else>
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">Ресторанов не найдено</h2>
                </#if>
            </div>
        </div>
    </div>
</@layout.layout>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const cartForms = document.querySelectorAll('form[action="/cart"]');
        cartForms.forEach(form => {
            form.addEventListener('submit', function () {
                sessionStorage.setItem('scrollPosition', window.scrollY);
            });
        });

        if (sessionStorage.getItem('scrollPosition')) {
            window.scrollTo(0, sessionStorage.getItem('scrollPosition'));
            sessionStorage.removeItem('scrollPosition'); // Clear it after use
        }
    });
</script>