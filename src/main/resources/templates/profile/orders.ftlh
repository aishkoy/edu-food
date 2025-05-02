<#import "../layout.ftlh" as layout>
<#macro navItem href icon label currentSection sectionName>
    <#assign isActive = (currentSection == sectionName)>
    <a href="${href}" class="block px-6 py-4 hover:bg-gray-50 transition flex items-center font-medium
        ${isActive?string('bg-gray-50 text-teal-600', '')}">
        <i data-lucide="${icon}" class="h-5 w-5 mr-3 ${isActive?string('text-teal-600', 'text-gray-500')}"></i>
        <span>${label}</span>
    </a>
</#macro>

<@layout.layout title="EduFood - Мои заказы">
    <div class="container mx-auto px-4 md:px-24 py-8">
        <h1 class="text-3xl font-bold text-gray-800 mb-6">Мои заказы</h1>

        <div class="flex flex-col lg:flex-row gap-8">
            <div class="w-full lg:w-1/4">
                <div class="bg-white rounded-lg shadow-md overflow-hidden mb-6">
                    <div class="p-6 flex flex-col items-center border-b">
                        <div class="w-24 h-24 rounded-full overflow-hidden border-4 border-teal-600 mb-4">
                            <img src="/api/users/${user.id}/image"
                                 alt="Аватар пользователя"
                                 class="w-full h-full object-cover">
                        </div>
                        <h2 class="text-xl font-semibold">${user.name} ${user.surname!''}</h2>
                        <p class="text-gray-600 text-sm">${user.email}</p>
                    </div>

                    <div class="p-0">
                        <ul class="profile-nav divide-y">
                            <li>
                                <@navItem href="/profile" icon="user" label="Личные данные" currentSection=section sectionName="profile" />
                            </li>
                            <li>
                                <@navItem href="/profile/orders" icon="shopping-bag" label="Мои заказы" currentSection=section sectionName="orders" />
                            </li>
                            <li>
                                <a href="/logout" class="block px-6 py-4 hover:bg-gray-50 transition flex items-center text-red-500 font-medium">
                                    <i data-lucide="log-out" class="h-5 w-5 mr-3"></i>
                                    <span>Выйти</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Правая колонка (список заказов) -->
            <div class="w-full lg:w-3/4">
                <div class="bg-white rounded-lg shadow-md overflow-hidden mb-6">
                    <div class="p-6 border-b">
                        <h2 class="text-xl font-semibold flex items-center">
                            <i data-lucide="shopping-bag" class="h-5 w-5 mr-2 text-teal-600"></i>
                            История заказов
                        </h2>
                    </div>

                    <div class="p-6">
                        <#if allOrders?? && allOrders?size gt 0>
                            <!-- Фильтры и сортировка -->
                            <form method="get" action="/profile/orders" class="mb-6">
                                <div class="flex flex-col md:flex-row gap-4 justify-between">
                                    <div class="flex flex-col sm:flex-row gap-4">
                                        <select name="status" class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500">
                                            <option value="" <#if !status??>selected</#if>>Все статусы</option>
                                            <#list orderStatuses as statusOption>
                                                <option value="${statusOption.id}" <#if status?? && status == statusOption.id>selected</#if>>${statusOption.name}</option>
                                            </#list>
                                        </select>

                                        <select name="sortBy" class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500">
                                            <option value="date" <#if sortBy == 'date'>selected</#if>>По дате</option>
                                            <option value="amount" <#if sortBy == 'amount'>selected</#if>>По сумме</option>
                                            <option value="quantity" <#if sortBy == 'quantity'>selected</#if>>По количеству</option>
                                        </select>

                                        <select name="sortDirection" class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500">
                                            <option value="desc" <#if sortDirection == 'desc'>selected</#if>>По убыванию</option>
                                            <option value="asc" <#if sortDirection == 'asc'>selected</#if>>По возрастанию</option>
                                        </select>
                                    </div>

                                    <button type="submit" class="bg-teal-600 text-white py-2 px-6 rounded-lg font-medium hover:bg-teal-700 transition-colors">
                                        Применить
                                    </button>
                                </div>
                            </form>

                            <!-- Список всех заказов -->
                            <div class="space-y-6">
                                <#list allOrders as order>
                                    <div class="border rounded-lg overflow-hidden">
                                        <div class="bg-gray-50 p-4 flex flex-col sm:flex-row sm:justify-between sm:items-center gap-3">
                                            <div>
                                                <span class="text-sm text-gray-500">Заказ #${order.id}</span>
                                                <p class="font-medium">${order.date?string('dd.MM.yyyy HH:mm')}</p>
                                            </div>
                                            <div class="flex flex-wrap gap-3 items-center">
                                                <span class="px-3 py-1 rounded-full text-sm
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
                                                <span class="text-sm text-gray-700">
                                                    <i data-lucide="shopping-bag" class="h-4 w-4 inline mr-1"></i>
                                                    ${order.totalQuantity} товаров
                                                </span>
                                                <span class="font-bold">${order.totalAmount} сом</span>
                                            </div>
                                        </div>
                                        <div class="p-4 flex items-center justify-between">
                                            <div>
                                                <span class="text-gray-600 text-sm">
                                                    <#assign productsPreview = "">
                                                    <#assign count = 0>
                                                    <#list order.orderProducts as product>
                                                        <#if count < 2>
                                                            <#if count != 0>
                                                                <#assign productsPreview = productsPreview + ", ">
                                                            </#if>
                                                            <#assign productsPreview = productsPreview + product.product.name>
                                                            <#assign count = count + 1>
                                                        </#if>
                                                    </#list>
                                                    <#if order.orderProducts?size gt 2>
                                                        <#assign productsPreview = productsPreview + " и еще ${order.orderProducts?size - 2}">
                                                    </#if>
                                                    ${productsPreview}
                                                </span>
                                            </div>
                                            <div>
                                                <#if order.status.name == 'Корзина'>
                                                    <a href="/cart" class="text-teal-600 hover:text-teal-700 font-medium">
                                                        Перейти в корзину
                                                    </a>
                                                <#else>
                                                    <a href="/profile/orders/${order.id}" class="text-teal-600 hover:text-teal-700 font-medium">
                                                        Подробнее
                                                    </a>
                                                </#if>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>

                            <#if totalPages gt 1>
                                <div class="mt-8 flex justify-center">
                                    <div class="flex items-center space-x-1">
                                        <#if currentPage gt 1>
                                            <a href="?page=${currentPage - 1}&sortBy=${sortBy}&sortDirection=${sortDirection}<#if status??>&status=${status}</#if>"
                                               class="px-4 py-2 border rounded text-teal-600 border-teal-600 hover:bg-teal-600 hover:text-white transition">
                                                <i data-lucide="chevron-left" class="h-5 w-5"></i>
                                            </a>
                                        </#if>

                                        <#list 1..totalPages as i>
                                            <#if i == currentPage>
                                                <span class="px-4 py-2 border rounded bg-teal-600 text-white">
                                                    ${i}
                                                </span>
                                            <#elseif i <= 3 || (i >= totalPages - 2) || (i >= currentPage - 1 && i <= currentPage + 1)>
                                                <a href="?page=${i}&sortBy=${sortBy}&sortDirection=${sortDirection}<#if status??>&status=${status}</#if>"
                                                   class="px-4 py-2 border rounded hover:bg-gray-100 transition">
                                                    ${i}
                                                </a>
                                            <#elseif i == 4 || i == totalPages - 3>
                                                <span class="px-4 py-2">...</span>
                                            </#if>
                                        </#list>

                                        <#if currentPage lt totalPages>
                                            <a href="?page=${currentPage + 1}&sortBy=${sortBy}&sortDirection=${sortDirection}<#if status??>&status=${status}</#if>"
                                               class="px-4 py-2 border rounded text-teal-600 border-teal-600 hover:bg-teal-600 hover:text-white transition">
                                                <i data-lucide="chevron-right" class="h-5 w-5"></i>
                                            </a>
                                        </#if>
                                    </div>
                                </div>
                            </#if>
                        <#else>
                            <div class="text-center py-8">
                                <div class="flex justify-center mb-4">
                                    <i data-lucide="shopping-bag" class="h-12 w-12 text-gray-300"></i>
                                </div>
                                <h3 class="text-xl font-semibold text-gray-800 mb-2">У вас еще нет заказов</h3>
                                <p class="text-gray-600 mb-4">Начните с просмотра меню наших ресторанов.</p>
                                <a href="/products" class="bg-teal-600 text-white py-2 px-6 rounded-lg font-semibold hover:bg-teal-700 transition-colors inline-block">
                                    Перейти к меню
                                </a>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>