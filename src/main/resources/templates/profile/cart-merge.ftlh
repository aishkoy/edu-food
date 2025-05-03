<#import "../layout.ftlh" as main>

<@main.layout title="Объединение корзин">
    <div class="container mx-auto px-4 py-8 max-w-6xl">
        <h1 class="text-3xl font-bold text-center mb-8 text-teal-700">У вас есть товары в корзине</h1>

        <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
            <div class="bg-white rounded-lg shadow-md p-6">
                <h2 class="text-xl font-semibold mb-4 text-teal-600">Товары в вашей временной корзине:</h2>
                <table class="w-full">
                    <thead>
                    <tr class="border-b">
                        <th class="text-left py-2">Продукт</th>
                        <th class="text-center py-2">Количество</th>
                        <th class="text-center py-2">Цена</th>
                        <th class="text-right py-2">Сумма</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list guestCart.orderProducts as item>
                        <tr class="border-b">
                            <td class="text-left py-4">${item.product.name}</td>
                            <td class="text-center py-4">${item.quantity}</td>
                            <td class="text-center py-4">${item.product.price} сом</td>
                            <td class="text-right py-4">${item.amount} сом</td>
                        </tr>
                    </#list>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="2" class="text-right font-bold py-4">Итого:</td>
                        <td class="text-center font-bold py-4">${guestCart.totalQuantity}</td>
                        <td class="text-right font-bold py-4">${guestCart.totalAmount} сом</td>
                    </tr>
                    </tfoot>
                </table>
            </div>

            <#if userCart?? && userCart.orderProducts?? && userCart.orderProducts?size gt 0>
                <div class="bg-white rounded-lg shadow-md p-6">
                    <h2 class="text-xl font-semibold mb-4 text-amber-600">Товары в вашей личной корзине:</h2>
                    <table class="w-full">
                        <thead>
                        <tr class="border-b">
                            <th class="text-left py-2">Продукт</th>
                            <th class="text-center py-2">Количество</th>
                            <th class="text-center py-2">Цена</th>
                            <th class="text-right py-2">Сумма</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list userCart.orderProducts as item>
                            <tr class="border-b">
                                <td class="text-left py-4">${item.product.name}</td>
                                <td class="text-center py-4">${item.quantity}</td>
                                <td class="text-center py-4">${item.product.price} сом</td>
                                <td class="text-right py-4">${item.amount} сом</td>
                            </tr>
                        </#list>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="2" class="text-right font-bold py-4">Итого:</td>
                            <td class="text-center font-bold py-4">${userCart.totalQuantity}</td>
                            <td class="text-right font-bold py-4">${userCart.totalAmount} сом</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            <#else>
                <div class="bg-white rounded-lg shadow-md p-6 flex items-center justify-center">
                    <div class="text-center">
                        <h2 class="text-xl font-semibold mb-4 text-amber-600">Ваша личная корзина пуста</h2>
                        <p class="text-gray-600">У вас нет сохраненных товаров в личной корзине.</p>
                    </div>
                </div>
            </#if>
        </div>

        <div class="mb-8 max-w-5xl mx-auto">
            <h2 class="text-xl font-semibold mb-6 text-teal-600 text-center">Что вы хотите сделать?</h2>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition duration-200">
                    <h3 class="text-lg font-semibold mb-3 text-teal-700">Объединить корзины</h3>
                    <p class="text-gray-600 mb-6">Добавить товары из временной корзины к вашей существующей корзине.</p>
                    <form action="/cart/merge" method="post" class="text-center">
                        <@main.csrf/>
                        <input type="hidden" name="action" value="merge">
                        <button type="submit" class="bg-teal-600 text-white py-2 px-6 rounded-full hover:bg-teal-700 transition duration-200">
                            Объединить
                        </button>
                    </form>
                </div>

                <div class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition duration-200">
                    <h3 class="text-lg font-semibold mb-3 text-teal-700">Заменить корзину</h3>
                    <p class="text-gray-600 mb-6">Использовать только товары из временной корзины, очистив вашу существующую корзину.</p>
                    <form action="/cart/merge" method="post" class="text-center">
                        <@main.csrf/>
                        <input type="hidden" name="action" value="replace">
                        <button type="submit" class="bg-amber-500 text-white py-2 px-6 rounded-full hover:bg-amber-600 transition duration-200">
                            Заменить
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <div class="text-center">
            <a href="/cart" class="border border-teal-600 text-teal-600 py-2 px-6 rounded-full hover:bg-teal-50 transition duration-200 inline-block">
                Вернуться к вашей корзине без изменений
            </a>
        </div>
    </div>
</@main.layout>