<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.layout title="EduFood - Регистрация">
    <div class=" mx-auto my-8 max-w-md w-full bg-white rounded-lg shadow-lg p-8">
        <div class="flex justify-center mb-6">
            <div class="bg-amber-100 rounded-full p-4 w-20 h-20 flex items-center justify-center">
                <i data-lucide="user-plus" class="h-10 w-10 text-amber-500"></i>
            </div>
        </div>

        <h2 class="text-3xl font-bold text-gray-800 mb-6 text-center">Создать аккаунт</h2>

        <form action="/auth/register" method="post">
            <@layout.csrf/>
            <input type="hidden" name="role.id" value="${role.id}"/>
            <input type="hidden" name="role.name" value="${role.name}"/>

            <div class="mb-4">
                <label for="name" class="block text-gray-700 text-sm font-semibold mb-2 text-left">Имя</label>
                <@spring.formInput "userDto.name", "class='w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500'", "text"/>
                <@spring.showErrors "<br>", "text-red-500 text-xs mt-1"/>
            </div>

            <div class="mb-4">
                <label for="surname" class="block text-gray-700 text-sm font-semibold mb-2 text-left">Фамилия</label>
                <@spring.formInput "userDto.surname", "class='w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500'", "text"/>
                <@spring.showErrors "<br>", "text-red-500 text-xs mt-1"/>
            </div>

            <div class="mb-4">
                <label for="address" class="block text-gray-700 text-sm font-semibold mb-2 text-left">Адрес</label>
                <@spring.formInput "userDto.address", "class='w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500'", "text"/>
                <@spring.showErrors "<br>", "text-red-500 text-xs mt-1"/>
            </div>

            <div class="mb-4">
                <label for="email" class="block text-gray-700 text-sm font-semibold mb-2 text-left">Email</label>
                <@spring.formInput "userDto.email", "class='w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500'", "email"/>
                <@spring.showErrors "<br>", "text-red-500 text-xs mt-1"/>
            </div>

            <div class="mb-4">
                <label for="password" class="block text-gray-700 text-sm font-semibold mb-2 text-left">Пароль</label>
                <@spring.formPasswordInput "userDto.password", "class='w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500' minlength='8' maxlength='20'"/>
                <@spring.showErrors "<br>", "text-red-500 text-xs mt-1"/>
                <p class="mt-1 text-xs text-gray-500">Минимум 8 символов, включая буквы и цифры</p>
            </div>

            <button type="submit"
                    class="w-full bg-teal-600 text-white py-3 px-4 rounded-lg font-bold hover:bg-teal-700 transition-colors">
                Зарегистрироваться
            </button>
        </form>

        <div class="mt-6 text-center">
            <p class="text-gray-600">Уже есть аккаунт?
                <a href="/login" class="text-teal-600 font-medium hover:text-teal-500">
                    Войти
                </a>
            </p>
        </div>
    </div>
</@layout.layout>