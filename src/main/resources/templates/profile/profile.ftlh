<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<#macro navItem href icon label currentSection sectionName>
    <#assign isActive = (currentSection == sectionName)>
    <a href="${href}" class="block px-6 py-4 hover:bg-gray-50 transition flex items-center font-medium
        ${isActive?string('bg-gray-50 text-teal-600', '')}">
        <i data-lucide="${icon}" class="h-5 w-5 mr-3 ${isActive?string('text-teal-600', 'text-gray-500')}"></i>
        <span>${label}</span>
    </a>
</#macro>


<@layout.layout title="EduFood - Профиль пользователя">
    <div class="container mx-auto px-4 md:px-24 py-8">
        <h1 class="text-3xl font-bold text-gray-800 mb-6">Личный кабинет</h1>

        <div class="flex flex-col lg:flex-row gap-8">
            <div class="w-full lg:w-1/4">
                <div class="bg-white rounded-lg shadow-md overflow-hidden mb-6">
                    <div class="p-6 flex flex-col items-center border-b">
                        <div class="relative mb-4">
                            <form action="/profile/avatar" method="post" enctype="multipart/form-data">
                                <@layout.csrf/>
                                <div class="w-24 h-24 rounded-full overflow-hidden border-4 border-teal-600 relative">
                                    <img src="/api/users/${user.id}/image"
                                         alt="Аватар пользователя"
                                         class="w-full h-full object-cover">

                                    <label for="avatar-upload"
                                           class="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center opacity-0 hover:opacity-100 transition-opacity cursor-pointer">
                                        <i data-lucide="camera" class="h-8 w-8 text-white"></i>
                                    </label>
                                </div>
                                <input type="file" id="avatar-upload" name="avatar" accept="image/*" class="hidden"
                                       onchange="this.form.submit()">
                            </form>
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
                                <a href="/logout"
                                   class="block px-6 py-4 hover:bg-gray-50 transition flex items-center text-red-500 font-medium">
                                    <i data-lucide="log-out" class="h-5 w-5 mr-3"></i>
                                    <span>Выйти</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Правая колонка (информация и редактирование) -->
            <div class="w-full lg:w-3/4">
                <!-- Форма профиля -->
                <div class="bg-white rounded-lg shadow-md overflow-hidden mb-6">
                    <div class="p-6 border-b">
                        <h2 class="text-xl font-semibold flex items-center">
                            <i data-lucide="user" class="h-5 w-5 mr-2 text-teal-600"></i>
                            Личные данные
                        </h2>
                    </div>

                    <div class="p-6">
                        <form action="/profile/update" method="post">
                            <@layout.csrf/>

                            <#if errorMessage??>
                                <div class="mb-6 p-4 bg-red-100 text-red-700 rounded-lg">
                                    ${errorMessage}
                                </div>
                            </#if>

                            <#if successMessage??>
                                <div class="mb-6 p-4 bg-green-100 text-green-700 rounded-lg">
                                    ${successMessage}
                                </div>
                            </#if>

                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                                <div>
                                    <label for="name" class="block text-gray-700 text-sm font-medium mb-2">Имя</label>
                                    <@spring.formInput "userDto.name", 'class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500"', "text"/>
                                    <@spring.showErrors '<br>', 'text-red-500 text-xs mt-1'/>
                                </div>
                                <div>
                                    <label for="surname" class="block text-gray-700 text-sm font-medium mb-2">Фамилия</label>
                                    <@spring.formInput "userDto.surname", 'class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500"', "text"/>
                                    <@spring.showErrors '<br>', 'text-red-500 text-xs mt-1'/>
                                </div>
                            </div>

                            <div class="mb-6">
                                <label for="address" class="block text-gray-700 text-sm font-medium mb-2">Адрес</label>
                                <@spring.formTextarea "userDto.address", 'class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500" rows="3"'/>
                                <@spring.showErrors '<br>', 'text-red-500 text-xs mt-1'/>
                            </div>

                            <div class="flex justify-end">
                                <button type="submit" class="bg-teal-600 text-white py-2 px-6 rounded-lg font-semibold hover:bg-teal-700 transition-colors">
                                    Сохранить изменения
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Последние заказы -->
                <div class="bg-white rounded-lg shadow-md overflow-hidden">
                    <div class="p-6 border-b flex justify-between items-center">
                        <h2 class="text-xl font-semibold flex items-center">
                            <i data-lucide="shopping-bag" class="h-5 w-5 mr-2 text-teal-600"></i>
                            Последние заказы
                        </h2>
                        <a href="/profile/orders"
                           class="text-teal-600 hover:text-teal-700 font-medium flex items-center">
                            <span>Все заказы</span>
                            <i data-lucide="chevron-right" class="h-5 w-5 ml-1"></i>
                        </a>
                    </div>

                    <div class="p-6">
                        <#if recentOrders?? && recentOrders?size gt 0>
                            <div class="space-y-6">
                                <#list recentOrders as order>
                                    <div class="border rounded-lg overflow-hidden">
                                        <div class="bg-gray-50 p-4 flex justify-between items-center">
                                            <div>
                                                <span class="text-sm text-gray-500">Заказ #${order.id}</span>
                                                <p class="font-medium">${order.date?string('dd.MM.yyyy HH:mm')}</p>
                                            </div>
                                            <div class="flex items-center gap-4">
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
                                                <span class="font-bold">${order.totalAmount} сом</span>
                                            </div>
                                        </div>
                                        <div class="p-4 flex items-center justify-between">
                                            <div>
                                                <span class="text-gray-600 text-sm">Товаров: ${order.totalQuantity}</span>
                                            </div>
                                            <div>
                                                <#if order.status.name == 'Корзина'>
                                                    <a href="/cart"
                                                       class="text-teal-600 hover:text-teal-700 font-medium">
                                                        Перейти в корзину
                                                    </a>
                                                <#else>
                                                    <a href="/profile/orders/${order.id}"
                                                       class="text-teal-600 hover:text-teal-700 font-medium">
                                                        Подробнее
                                                    </a>
                                                </#if>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                        <#else>
                            <div class="text-center py-8">
                                <div class="flex justify-center mb-4">
                                    <i data-lucide="shopping-bag" class="h-12 w-12 text-gray-300"></i>
                                </div>
                                <h3 class="text-xl font-semibold text-gray-800 mb-2">У вас еще нет заказов</h3>
                                <p class="text-gray-600 mb-4">Начните с просмотра меню наших ресторанов.</p>
                                <a href="/products"
                                   class="bg-teal-600 text-white py-2 px-6 rounded-lg font-semibold hover:bg-teal-700 transition-colors inline-block">
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