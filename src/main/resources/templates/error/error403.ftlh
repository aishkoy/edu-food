<#import "../layout.ftlh" as layout>

<@layout.layout title="EduFood - Доступ запрещен">
    <div class="mx-auto max-w-2xl my-24">
        <div class="flex justify-center mb-8">
            <div class="bg-amber-100 rounded-full p-6 w-32 h-32 flex items-center justify-center">
                <i data-lucide="shield-alert" class="h-16 w-16 text-amber-500"></i>
            </div>
        </div>

        <h1 class="text-5xl font-bold text-gray-800 mb-4">403</h1>
        <h2 class="text-2xl font-medium text-gray-600 mb-8">Доступ запрещен</h2>
        <p class="text-gray-600 mb-8">У вас нет прав для доступа к этой странице. Пожалуйста, войдите в систему или свяжитесь с администратором, если считаете, что это ошибка.</p>

        <div class="flex flex-wrap justify-center gap-4">
            <a href="/" class="bg-teal-600 text-white font-bold py-3 px-6 rounded-xl flex items-center">
                <i data-lucide="home" class="h-5 w-5 mr-2"></i>
                На главную
            </a>
            <a href="/auth/login" class="bg-amber-500 text-white font-bold py-3 px-6 rounded-xl flex items-center">
                <i data-lucide="log-in" class="h-5 w-5 mr-2"></i>
                Войти
            </a>
        </div>
    </div>
</@layout.layout>