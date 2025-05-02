<#import "../layout.ftlh" as layout>

<#assign additionalStyles>
    .wave-bottom {
    position: relative;
    height: 150px;
    overflow: hidden;
    }

    .wave-bottom::before {
    content: "";
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 100px;
    background-color: white;
    border-radius: 100% 100% 0 0;
    transform: translateY(70px);
    }

    .auth-form {
    transition: all 0.3s ease;
    transform: translateY(-30px);
    }

    .form-input {
    transition: all 0.2s ease;
    border: 2px solid transparent;
    }

    .form-input:focus {
    border-color: #0D9488;
    box-shadow: 0 0 0 3px rgba(13, 148, 136, 0.2);
    }

    .auth-btn {
    transition: all 0.2s ease;
    }

    .auth-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
</#assign>

<@layout.layout title="EduFood - Авторизация" additionalStyles=additionalStyles>
    <div class="mx-auto w-full bg-amber-300 min-h-screen-lg relative pb-36 pt-20">
        <div class="container mx-auto px-4 text-center">
            <h2 class="text-5xl font-bold text-black mb-10">Вход в аккаунт</h2>

            <div class="max-w-md mx-auto bg-white rounded-2xl mt-24 shadow-lg p-8 auth-form">
                <div class="flex justify-center mb-8">
                    <div class="bg-amber-100 rounded-full p-5 w-20 h-20 flex items-center justify-center">
                        <i data-lucide="user" class="h-10 w-10 text-amber-500"></i>
                    </div>
                </div>

                <#if error??>
                    <div class="bg-red-100 text-red-700 p-4 rounded-lg mb-6">
                        ${error}
                    </div>
                </#if>

                <form action="/auth/login" method="post" class="space-y-6">
                    <@layout.csrf/>
                    <div class="text-left">
                        <label for="email" class="block text-gray-700 font-medium mb-2">Email</label>
                        <div class="relative">
                            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <i data-lucide="mail" class="h-5 w-5 text-gray-400"></i>
                            </div>
                            <input type="email"
                                   id="email"
                                   name="username"
                                   class="form-input w-full py-3 pl-10 pr-3 rounded-lg bg-gray-50 focus:outline-none"
                                   placeholder="Введите ваш email">
                        </div>
                    </div>

                    <div class="text-left">
                        <label for="password" class="block text-gray-700 font-medium mb-2">Пароль</label>
                        <div class="relative">
                            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <i data-lucide="lock" class="h-5 w-5 text-gray-400"></i>
                            </div>
                            <input type="password" id="password" name="password" required
                                   class="form-input w-full py-3 pl-10 pr-3 rounded-lg bg-gray-50 focus:outline-none"
                                   placeholder="Введите ваш пароль">
                        </div>
                    </div>

                    <div class="flex items-center justify-between">
                        <a href="/auth/forgot-password" class="text-sm font-medium text-teal-600 hover:text-teal-500">
                            Забыли пароль?
                        </a>
                    </div>

                    <div>
                        <button type="submit"
                                class="auth-btn w-full bg-teal-600 text-white font-bold py-3 px-4 rounded-lg hover:bg-teal-700 focus:outline-none focus:ring-2 focus:ring-teal-500 focus:ring-offset-2">
                            Войти
                        </button>
                    </div>
                </form>

                <div class="mt-6 text-center">
                    <p class="text-gray-600">
                        Еще нет аккаунта?
                        <a href="/auth/register" class="font-medium text-teal-600 hover:text-teal-500">
                            Зарегистрироваться
                        </a>
                    </p>
                </div>
            </div>
        </div>
        <div class="wave-bottom absolute bottom-0 w-full"></div>
    </div>

    <div class="container mx-auto px-4 md:px-24 py-16">
        <div class="max-w-3xl mx-auto text-center">
            <div class="flex justify-center mb-8">
                <div class="bg-amber-100 rounded-full p-6 w-24 h-24 flex items-center justify-center">
                    <i data-lucide="shield-check" class="h-12 w-12 text-amber-500"></i>
                </div>
            </div>

            <h2 class="text-3xl font-bold mb-6">Безопасность и удобство</h2>
            <p class="text-gray-600 mb-8">
                Войдите в свой аккаунт, чтобы получить доступ к истории заказов, сохраненным адресам доставки
                и персональным предложениям. Мы гарантируем безопасность ваших данных и удобство использования сервиса.
            </p>

            <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mt-12">
                <div class="bg-white p-6 rounded-lg shadow-md">
                    <div class="flex justify-center mb-4">
                        <div class="bg-amber-50 p-3 rounded-full">
                            <i data-lucide="clock" class="h-8 w-8 text-amber-500"></i>
                        </div>
                    </div>
                    <h3 class="font-semibold text-lg mb-2">Быстрые заказы</h3>
                    <p class="text-gray-600">Сохраняйте любимые блюда и делайте заказы в один клик</p>
                </div>

                <div class="bg-white p-6 rounded-lg shadow-md">
                    <div class="flex justify-center mb-4">
                        <div class="bg-amber-50 p-3 rounded-full">
                            <i data-lucide="truck" class="h-8 w-8 text-amber-500"></i>
                        </div>
                    </div>
                    <h3 class="font-semibold text-lg mb-2">Отслеживание</h3>
                    <p class="text-gray-600">Следите за статусом вашего заказа в реальном времени</p>
                </div>

                <div class="bg-white p-6 rounded-lg shadow-md">
                    <div class="flex justify-center mb-4">
                        <div class="bg-amber-50 p-3 rounded-full">
                            <i data-lucide="gift" class="h-8 w-8 text-amber-500"></i>
                        </div>
                    </div>
                    <h3 class="font-semibold text-lg mb-2">Бонусы</h3>
                    <p class="text-gray-600">Получайте персональные скидки и накапливайте бонусы</p>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>