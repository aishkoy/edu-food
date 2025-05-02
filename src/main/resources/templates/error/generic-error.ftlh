<#import "../layout.ftlh" as layout>

<@layout.layout title="EduFood - Вход в систему">
    <div class="mx-auto my-24 max-w-md w-full bg-white rounded-lg shadow-lg p-8">
        <div class="flex justify-center mb-6">
            <div class="bg-amber-100 rounded-full p-4 w-20 h-20 flex items-center justify-center">
                <i data-lucide="user" class="h-10 w-10 text-amber-500"></i>
            </div>
        </div>

        <h2 class="text-3xl font-bold text-gray-800 mb-6 text-center">Вход в систему</h2>

        <form action="/login" method="post">
            <div class="mb-4">
                <label for="email" class="block text-gray-700 text-sm font-semibold mb-2 text-left">Email</label>
                <div class="flex items-center border border-gray-300 rounded-lg overflow-hidden">
                    <div class="px-3 py-2 bg-gray-100">
                        <i data-lucide="mail" class="h-5 w-5 text-gray-500"></i>
                    </div>
                    <input type="email" id="email" name="email" required
                           class="flex-grow px-4 py-2 focus:outline-none"
                           placeholder="Введите ваш email">
                </div>
            </div>

            <div class="mb-6">
                <label for="password" class="block text-gray-700 text-sm font-semibold mb-2 text-left">Пароль</label>
                <div class="flex items-center border border-gray-300 rounded-lg overflow-hidden">
                    <div class="px-3 py-2 bg-gray-100">
                        <i data-lucide="lock" class="h-5 w-5 text-gray-500"></i>
                    </div>
                    <input type="password" id="password" name="password" required
                           class="flex-grow px-4 py-2 focus:outline-none"
                           placeholder="Введите ваш пароль">
                </div>
            </div>

            <button type="submit" class="w-full bg-teal-600 text-white py-3 px-4 rounded-lg font-bold hover:bg-teal-700 transition-colors">
                Войти
            </button>

            <div class="flex justify-between items-center mt-4">
                <div class="flex items-center">
                    <input type="checkbox" id="remember" name="remember"
                           class="h-4 w-4 text-teal-600 focus:ring-teal-500 border-gray-300 rounded">
                    <label for="remember" class="ml-2 block text-sm text-gray-700">
                        Запомнить меня
                    </label>
                </div>
                <div>
                    <a href="/forgot-password" class="text-sm text-teal-600 hover:text-teal-500">
                        Забыли пароль?
                    </a>
                </div>
            </div>
        </form>

        <div class="mt-6 text-center">
            <p class="text-gray-600">Еще нет аккаунта?
                <a href="/register" class="text-teal-600 font-medium hover:text-teal-500">
                    Зарегистрироваться
                </a>
            </p>
        </div>
    </div>
</@layout.layout>