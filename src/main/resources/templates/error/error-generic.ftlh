<#import "../layout.ftlh" as layout>

<@layout.layout title="EduFood - ${errorTitle!defaultErrorTitle}">
    <div class="mx-auto my-24 max-w-2xl">
        <div class="flex justify-center mb-8">
            <div class="bg-amber-100 rounded-full p-6 w-32 h-32 flex items-center justify-center">
                <i data-lucide="${errorIcon!defaultErrorIcon}" class="h-16 w-16 text-amber-500"></i>
            </div>
        </div>

        <h1 class="text-5xl font-bold text-gray-800 mb-4">${status!500}</h1>
        <h2 class="text-2xl font-medium text-gray-600 mb-8">${errorSubtitle!defaultErrorSubtitle}</h2>
        <p class="text-gray-600 mb-8">${errorMessage!defaultErrorMessage}</p>

        <div class="flex flex-wrap justify-center gap-4">
            <a href="/" class="bg-teal-600 text-white font-bold py-3 px-6 rounded-xl flex items-center">
                <i data-lucide="home" class="h-5 w-5 mr-2"></i>
                На главную
            </a>
            <button onclick="location.reload()" class="bg-amber-500 text-white font-bold py-3 px-6 rounded-xl flex items-center">
                <i data-lucide="refresh-cw" class="h-5 w-5 mr-2"></i>
                Обновить страницу
            </button>
            <a href="javascript:history.back()" class="bg-gray-600 text-white font-bold py-3 px-6 rounded-xl flex items-center">
                <i data-lucide="arrow-left" class="h-5 w-5 mr-2"></i>
                Назад
            </a>
        </div>
    </div>
</@layout.layout>