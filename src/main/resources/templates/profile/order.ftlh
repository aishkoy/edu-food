<#import "../layout.ftlh" as layout>

<@layout.layout title="EduFood - Детали заказа №${order.id}">
    <div class="container mx-auto px-4 md:px-24 py-8">
        <div class="flex items-center mb-6">
            <a href="/profile/orders" class="text-teal-600 hover:text-teal-700 flex items-center">
                <i data-lucide="arrow-left" class="h-5 w-5 mr-2"></i>
                <span>Назад к списку заказов</span>
            </a>
        </div>

        <h1 class="text-3xl font-bold text-gray-800 mb-6">Заказ #${order.id}</h1>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
            <div class="lg:col-span-2">
                <div class="bg-white rounded-lg shadow-md overflow-hidden mb-6">
                    <div class="p-6 border-b">
                        <h2 class="text-xl font-semibold flex items-center">
                            <i data-lucide="shopping-bag" class="h-5 w-5 mr-2 text-teal-600"></i>
                            Детали заказа
                        </h2>
                    </div>

                    <div class="p-6">
                        <div class="flex flex-col gap-4">
                            <div class="flex justify-between py-2 border-b">
                                <span class="text-gray-600">Дата заказа:</span>
                                <span class="font-medium">${order.date?string('dd.MM.yyyy HH:mm')}</span>
                            </div>
                            <div class="flex justify-between py-2 border-b">
                                <span class="text-gray-600">Статус:</span>
                                <span class="px-3 py-1 rounded-full text-sm font-medium
                                      <#if order.status.name == 'Доставлен'>
                                          bg-green-100 text-green-800
                                      <#elseif order.status.name == 'В обработке' || order.status.name == 'Готовится'>
                                          bg-blue-100 text-blue-800
                                      <#elseif order.status.name == 'Отменен'>
                                          bg-red-100 text-red-800
                                      <#elseif order.status.name == 'Корзина'>
                                          bg-amber-100 text-amber-800
                                      <#else>
                                          bg-gray-100 text-gray-800
                                      </#if>">
                                    ${order.status.name}
                                </span>
                            </div>
                            <div class="flex justify-between py-2 border-b">
                                <span class="text-gray-600">Адрес доставки:</span>
                                <span class="font-medium">${order.user.address}</span>
                            </div>
                            <div class="flex justify-between py-2 border-b">
                                <span class="text-gray-600">Количество товаров:</span>
                                <span class="font-medium">${order.totalQuantity}</span>
                            </div>
                            <div class="flex justify-between py-2">
                                <span class="text-gray-600">Итоговая сумма:</span>
                                <span class="font-bold text-lg">${order.totalAmount} сом</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Список товаров в заказе -->
                <div class="bg-white rounded-lg shadow-md overflow-hidden mb-6">
                    <div class="p-6 border-b">
                        <h2 class="text-xl font-semibold flex items-center">
                            <i data-lucide="list" class="h-5 w-5 mr-2 text-teal-600"></i>
                            Товары в заказе
                        </h2>
                    </div>

                    <div class="p-6">
                        <div class="space-y-6">
                            <#list order.orderProducts as item>
                                <div class="flex flex-col sm:flex-row sm:items-center gap-4 py-4 <#if item_has_next>border-b</#if>">
                                    <div class="sm:flex-shrink-0">
                                        <img src="/api/products/${item.product.id}/image" alt="${item.product.name}"
                                             class="w-20 h-20 object-cover rounded-lg">
                                    </div>
                                    <div class="flex-grow">
                                        <h3 class="font-medium text-lg">${item.product.name}</h3>
                                        <p class="text-gray-600 text-sm line-clamp-2">${item.product.description!''}</p>
                                        <div class="flex gap-4 mt-1 text-sm text-gray-500">
                                            <span>Ресторан: ${item.product.restaurant.name}</span>
                                            <span>Категория: ${item.product.category.name}</span>
                                        </div>
                                    </div>
                                    <div class="sm:w-32 sm:text-right">
                                        <div class="text-gray-500">${item.quantity} x ${item.product.price} сом</div>
                                        <div class="font-bold">${item.amount} сом</div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Боковая панель -->
            <div class="lg:col-span-1">
                <!-- Информация о пользователе -->
                <div class="bg-white rounded-lg shadow-md overflow-hidden mb-6">
                    <div class="p-6 border-b">
                        <h2 class="text-xl font-semibold flex items-center">
                            <i data-lucide="user" class="h-5 w-5 mr-2 text-teal-600"></i>
                            Информация о заказчике
                        </h2>
                    </div>

                    <div class="p-6">
                        <div class="flex flex-col gap-4">
                            <div class="flex justify-between py-2 border-b">
                                <span class="text-gray-600">Имя:</span>
                                <span class="font-medium">${order.user.name} ${order.user.surname!''}</span>
                            </div>
                            <div class="flex justify-between py-2 border-b">
                                <span class="text-gray-600">Email:</span>
                                <span class="font-medium">${order.user.email}</span>
                            </div>
                            <div class="flex justify-between py-2">
                                <span class="text-gray-600">Адрес:</span>
                                <span class="font-medium">${order.user.address}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Действия -->
                <div class="bg-white rounded-lg shadow-md overflow-hidden">
                    <div class="p-6 border-b">
                        <h2 class="text-xl font-semibold flex items-center">
                            <i data-lucide="settings" class="h-5 w-5 mr-2 text-teal-600"></i>
                            Действия
                        </h2>
                    </div>

                    <div class="p-6">
                        <div class="space-y-4">
                            <a href="/profile/orders" class="w-full bg-gray-100 text-gray-700 py-2 px-4 rounded-lg font-medium hover:bg-gray-200 transition-colors flex items-center justify-center">
                                <i data-lucide="arrow-left" class="h-5 w-5 mr-2"></i>
                                Назад к списку заказов
                            </a>

                            <a href="/products" class="w-full bg-teal-600 text-white py-2 px-4 rounded-lg font-medium hover:bg-teal-700 transition-colors flex items-center justify-center">
                                <i data-lucide="shopping-bag" class="h-5 w-5 mr-2"></i>
                                Сделать новый заказ
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>