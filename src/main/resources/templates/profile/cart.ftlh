<#import "../layout.ftlh" as layout>

<@layout.layout title="EduFood - Корзина">
    <div class="mx-auto max-w-4xl w-full my-8">
        <h1 class="text-3xl font-bold text-gray-800 mb-6 text-center">Корзина</h1>

        <#if cart?? && cart.orderProducts?has_content>
            <div class="bg-white rounded-lg shadow-md overflow-hidden mb-6">
                <div class="p-6">
                    <div class="flex justify-between border-b pb-4 mb-4">
                        <h2 class="text-xl font-semibold">Ваш заказ</h2>
                        <span class="text-gray-500">Количество: ${cart.totalQuantity}</span>
                    </div>
                    <div class="space-y-4">
                        <#list cart.orderProducts as productItem>
                            <div class="flex items-center justify-between py-4 border-b">
                                <div class="flex items-center">
                                    <img src="/api/products/${productItem.product.id}/image" alt="${productItem.product.name}"
                                         class="w-16 h-16 object-cover rounded-lg mr-4">
                                    <div class="text-left">
                                        <h3 class="font-medium">${productItem.product.name}</h3>
                                        <p class="text-gray-500 text-sm">${productItem.product.description}</p>
                                        <p class="text-gray-500 text-sm">${productItem.product.restaurant.name}</p>
                                    </div>
                                </div>
                                <div class="flex items-center">
                                    <div class="flex items-center border rounded-lg mr-4">
                                        <form action="/cart/update" method="post" class="quantity-form">
                                            <@layout.csrf/>
                                            <input type="hidden" name="productId" value="${productItem.product.id}">
                                            <input type="hidden" name="quantityChange" value="-1">
                                            <input type="hidden" name="redirectUrl" value="/cart">
                                            <button type="submit" class="px-3 py-1">
                                                <i data-lucide="minus" class="h-4 w-4"></i>
                                            </button>
                                        </form>

                                        <span class="px-3">${productItem.quantity}</span>

                                        <form action="/cart/update" method="post" class="quantity-form">
                                            <@layout.csrf/>
                                            <input type="hidden" name="productId" value="${productItem.product.id}">
                                            <input type="hidden" name="quantityChange" value="1">
                                            <input type="hidden" name="redirectUrl" value="/cart">
                                            <button type="submit" class="px-3 py-1">
                                                <i data-lucide="plus" class="h-4 w-4"></i>
                                            </button>
                                        </form>
                                    </div>
                                    <span class="font-medium">${productItem.amount} сом</span>
                                </div>
                            </div>
                        </#list>
                    </div>

                    <!-- Информация о сумме заказа -->
                    <div class="mt-6 pt-4 border-t">
                        <div class="flex justify-between mb-2">
                            <span>Сумма заказа</span>
                            <span>${cart.totalAmount} сом</span>
                        </div>
                        <div class="flex justify-between font-bold text-lg">
                            <span>Итого</span>
                            <span>${cart.totalAmount} сом</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Блок оформления заказа -->
            <div class="bg-white rounded-lg shadow-md overflow-hidden mb-6">
                <div class="p-6">
                    <h2 class="text-xl font-semibold mb-4">Адрес доставки</h2>

                    <form>
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                            <div>
                                <label class="block text-gray-700 text-sm font-medium mb-2">Улица</label>
                                <input type="text" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500" placeholder="Введите улицу">
                            </div>
                            <div>
                                <label class="block text-gray-700 text-sm font-medium mb-2">Дом/квартира</label>
                                <input type="text" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500" placeholder="Дом, квартира">
                            </div>
                        </div>

                        <div class="mb-4">
                            <label class="block text-gray-700 text-sm font-medium mb-2">Примечание к заказу</label>
                            <textarea class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500" rows="3" placeholder="Особые пожелания к заказу или доставке"></textarea>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Действия пользователя -->
            <div class="flex justify-between">
                <a href="/" class="flex items-center text-teal-600 hover:text-teal-700">
                    <i data-lucide="arrow-left" class="h-4 w-4 mr-1"></i>
                    Продолжить покупки
                </a>

                <button class="bg-teal-600 text-white py-3 px-6 rounded-lg font-bold hover:bg-teal-700 transition-colors">
                    Оформить заказ
                </button>
            </div>
        <#else>
            <div class="bg-white rounded-lg shadow-md p-8 text-center">
                <div class="flex justify-center mb-6">
                    <div class="bg-amber-100 rounded-full p-6 w-24 h-24 flex items-center justify-center">
                        <i data-lucide="shopping-cart" class="h-12 w-12 text-amber-500"></i>
                    </div>
                </div>

                <h2 class="text-2xl font-bold text-gray-800 mb-4">Корзина пуста</h2>
                <p class="text-gray-600 mb-6">Добавьте товары из меню ресторанов, чтобы сделать заказ</p>

                <a href="/" class="bg-teal-600 text-white py-3 px-6 rounded-lg font-bold hover:bg-teal-700 transition-colors inline-flex items-center">
                    <i data-lucide="arrow-left" class="h-5 w-5 mr-2"></i>
                    Перейти к меню
                </a>
            </div>
        </#if>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const scrollPosition = sessionStorage.getItem('scrollPosition');
            if (scrollPosition !== null) {
                window.scrollTo(0, parseInt(scrollPosition));
                sessionStorage.removeItem('scrollPosition');
            }

            const quantityForms = document.querySelectorAll('.quantity-form');
            quantityForms.forEach(form => {
                form.addEventListener('submit', function() {
                    sessionStorage.setItem('scrollPosition', window.scrollY);
                });
            });
        });
    </script>
</@layout.layout>