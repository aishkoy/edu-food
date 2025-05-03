<#import "/spring.ftl" as spring>

<#assign known = SPRING_SECURITY_CONTEXT?? >
<#if known>
    <#assign
    currentUser = SPRING_SECURITY_CONTEXT.authentication.principal
    currentUserId = currentUser.getUserId()
    roles = currentUser.authorities
    >
</#if>

<#macro layout title="EduFood" additionalStyles="" showBackButton=false>
    <!doctype html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <script src="https://cdn.tailwindcss.com"></script>
        <script src="https://unpkg.com/lucide@latest"></script>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap"
              rel="stylesheet">
        <title>${title}</title>
        <style>
            body {
                font-family: 'Montserrat', sans-serif;
            }

            ${additionalStyles}
        </style>
    </head>
    <body class="bg-gray-50 min-h-screen flex flex-col">

    <header class="bg-amber-300 py-6 shadow">
        <div class="container px-4 md:px-24 mx-auto">
            <div class="md:mx-20 flex justify-between items-center">
                <a href="/" class="font-extrabold text-teal-700 text-4xl hover:text-teal-800 transition duration-200">
                    Edu Food
                </a>

                <div class="flex items-center gap-6">
                    <a href="/products"
                       class="text-teal-600 font-semibold px-5 py-2 rounded-full hover:bg-teal-100 transition duration-200">
                        Меню
                    </a>

                    <a href="/restaurants"
                       class="text-teal-600 font-semibold px-5 py-2 rounded-full hover:bg-teal-100 transition duration-200">
                        Рестораны
                    </a>
                </div>

                <div class="flex gap-6 items-center">
                    <a href="/cart" class="relative bg-white rounded-full p-3 shadow-md hover:shadow-lg transition-all">
                        <i data-lucide="shopping-cart" class="h-6 w-6 text-teal-600"></i>
                        <span class="absolute -top-2 -right-2 bg-amber-500 text-white text-xs font-bold rounded-full w-6 h-6 flex items-center justify-center">
                            ${(cart.totalQuantity)!0}
                        </span>
                    </a>

                    <#if currentUser??>
                        <a href="/profile"
                           class="bg-teal-600 text-white font-bold py-2 px-4 rounded-full flex items-center gap-2 hover:bg-teal-700 transition">
                            <i data-lucide="user" class="h-5 w-5"></i><span>Профиль</span>
                        </a>
                    <#else>
                        <a href="/auth/login"
                           class="bg-teal-600 text-white font-bold py-2 px-4 rounded-full flex items-center gap-2 hover:bg-teal-700 transition">
                            <i data-lucide="user" class="h-5 w-5"></i><span>Войти</span>
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    </header>


    <main>
        <div class="text-center mx-auto">
            <#nested>
        </div>
    </main>

    <footer class="bg-gray-100 py-10">
        <div class="container mx-auto px-4 md:px-24">
            <div class="flex flex-col md:flex-row justify-between items-center">
                <div class="mb-6 md:mb-0">
                    <h2 class="font-bold text-teal-600 text-3xl">Edu Food</h2>
                    <p class="text-gray-600 mt-2">Доставка еды</p>
                </div>

                <div class="flex gap-8">
                    <div>
                        <h3 class="font-semibold mb-3">О нас</h3>
                        <ul class="space-y-2">
                            <li><a href="#" class="text-gray-600 hover:text-teal-600">О компании</a></li>
                            <li><a href="#" class="text-gray-600 hover:text-teal-600">Работа у нас</a></li>
                            <li><a href="/restaurants" class="text-gray-600 hover:text-teal-600">Рестораны</a></li>
                        </ul>
                    </div>

                    <div>
                        <h3 class="font-semibold mb-3">Помощь</h3>
                        <ul class="space-y-2">
                            <li><a href="#" class="text-gray-600 hover:text-teal-600">Контакты</a></li>
                            <li><a href="#" class="text-gray-600 hover:text-teal-600">Часто задаваемые вопросы</a></li>
                            <li><a href="#" class="text-gray-600 hover:text-teal-600">Поддержка</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="border-t border-gray-300 mt-8 pt-8 text-center">
                <p class="text-gray-600">&copy; 2025 EduFood. Все права защищены.</p>
            </div>
        </div>
    </footer>

    <script>
        lucide.createIcons();
    </script>
    </body>
    </html>
</#macro>

<#macro csrf>
    <#if _csrf??>
        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
               value="${(_csrf.token)!'csrf-token'}"/>
    </#if>
</#macro>