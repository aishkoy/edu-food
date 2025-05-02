<#import "../layout.ftlh" as layout>

<@layout.layout title="EduFood - Некорректный запрос" showBackButton=true>
    <div class="mx-auto max-w-2xl mt-18 my-24">
        <div class="flex justify-center mb-8">
            <div class="bg-amber-100 rounded-full p-6 w-32 h-32 flex items-center justify-center">
                <i data-lucide="file-warning" class="h-16 w-16 text-amber-500"></i>
            </div>
        </div>

        <h1 class="text-5xl font-bold text-gray-800 mb-4">400</h1>
        <h2 class="text-2xl font-medium text-gray-600 mb-8">Некорректный запрос</h2>
        <p class="text-gray-600 mb-8">Сервер не может обработать ваш запрос из-за некорректных данных. Пожалуйста, проверьте введенную информацию и попробуйте снова.</p>

        <div class="flex flex-wrap justify-center gap-4">
            <a href="/" class="bg-teal-600 text-white font-bold py-3 px-6 rounded-xl flex items-center">
                <i data-lucide="home" class="h-5 w-5 mr-2"></i>
                На главную
            </a>
            <button onclick="history.back()" class="bg-gray-200 text-gray-800 font-bold py-3 px-6 rounded-xl flex items-center">
                <i data-lucide="arrow-left" class="h-5 w-5 mr-2"></i>
                Вернуться назад
            </button>
        </div>
    </div>
</@layout.layout>