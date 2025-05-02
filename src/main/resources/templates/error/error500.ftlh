<#import "../layout.ftlh" as layout>

<@layout.layout title="EduFood - Внутренняя ошибка сервера">
    <div class="mx-auto my-24 max-w-2xl">
        <div class="flex justify-center mb-8">
            <div class="bg-amber-100 rounded-full p-6 w-32 h-32 flex items-center justify-center">
                <i data-lucide="alert-triangle" class="h-16 w-16 text-amber-500"></i>
            </div>
        </div>

        <h1 class="text-5xl font-bold text-gray-800 mb-4">500</h1>
        <h2 class="text-2xl font-medium text-gray-600 mb-8">Внутренняя ошибка сервера</h2>
        <p class="text-gray-600 mb-8">Что-то пошло не так на нашем сервере. Мы уже работаем над устранением проблемы. Пожалуйста, повторите попытку позже или свяжитесь с нашей службой поддержки.</p>

        <div class="flex flex-wrap justify-center gap-4">
            <a href="/" class="bg-teal-600 text-white font-bold py-3 px-6 rounded-xl flex items-center">
                <i data-lucide="home" class="h-5 w-5 mr-2"></i>
                На главную
            </a>
            <button onclick="location.reload()" class="bg-amber-500 text-white font-bold py-3 px-6 rounded-xl flex items-center">
                <i data-lucide="refresh-cw" class="h-5 w-5 mr-2"></i>
                Обновить страницу
            </button>
        </div>
    </div>
</@layout.layout>