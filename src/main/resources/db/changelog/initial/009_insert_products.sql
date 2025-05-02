-- changelog Aisha: 009 insert products

-- Faiza
insert into products(name, description, category_id, price, image, restaurant_id)
values
    ('Самса с мясом', 'Сочная самса с говядиной и луком', (select id from product_categories where name = 'Закуски'), 120.00, 'samsa.jpg', (select id from restaurants where name = 'Faiza')),
    ('Салат Оливье', 'Классический салат с колбасой', (select id from product_categories where name = 'Салаты'), 180.00, 'olivier.jpg', (select id from restaurants where name = 'Faiza')),
    ('Шорпо', 'Национальный мясной суп', (select id from product_categories where name = 'Супы'), 200.00, 'shorpo.jpg', (select id from restaurants where name = 'Faiza')),
    ('Плов', 'Плов с бараниной и морковью', (select id from product_categories where name = 'Горячие блюда'), 250.00, 'plov.jpg', (select id from restaurants where name = 'Faiza')),
    ('Лагман', 'Тянутая лапша с мясом и овощами', (select id from product_categories where name = 'Горячие блюда'), 260.00, 'lagman.jpg', (select id from restaurants where name = 'Faiza')),
    ('Картофель фри', 'Жареный картофель', (select id from product_categories where name = 'Гарниры'), 100.00, 'fries.jpg', (select id from restaurants where name = 'Faiza')),
    ('Морковча', 'Пряная корейская морковка', (select id from product_categories where name = 'Салаты'), 90.00, 'carrot.jpg', (select id from restaurants where name = 'Faiza')),
    ('Чак-чак', 'Сладкое блюдо из теста и мёда', (select id from product_categories where name = 'Десерты'), 150.00, 'chakchak.jpg', (select id from restaurants where name = 'Faiza')),
    ('Чай черный', 'Крепкий черный чай в пиале', (select id from product_categories where name = 'Напитки'), 50.00, 'blacktea.jpg', (select id from restaurants where name = 'Faiza')),
    ('Компот', 'Фруктовый компот', (select id from product_categories where name = 'Напитки'), 70.00, 'kompot.jpg', (select id from restaurants where name = 'Faiza'));

-- Faiza - уже добавлено в примере

-- Navat
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Манты', 'Крупные мясные манты с бульоном', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 230.00, 'manty.jpg', (SELECT id FROM restaurants WHERE name = 'Navat')),
    ('Куурдак', 'Жареное мясо с картофелем', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 280.00, 'kuurdak.jpg', (SELECT id FROM restaurants WHERE name = 'Navat')),
    ('Салат Ашлян-фу', 'Холодный салат с лапшой и овощами', (SELECT id FROM product_categories WHERE name = 'Салаты'), 190.00, 'ashlanfu.jpg', (SELECT id FROM restaurants WHERE name = 'Navat')),
    ('Кесме', 'Домашняя лапша с бульоном', (SELECT id FROM product_categories WHERE name = 'Супы'), 210.00, 'kesme.jpg', (SELECT id FROM restaurants WHERE name = 'Navat')),
    ('Боорсоки', 'Жареные кусочки теста', (SELECT id FROM product_categories WHERE name = 'Закуски'), 100.00, 'boorsok.jpg', (SELECT id FROM restaurants WHERE name = 'Navat')),
    ('Рис отварной', 'Гарнир из риса', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 80.00, 'rice.jpg', (SELECT id FROM restaurants WHERE name = 'Navat')),
    ('Чучук', 'Национальная колбаса из конины', (SELECT id FROM product_categories WHERE name = 'Закуски'), 350.00, 'chuchuk.jpg', (SELECT id FROM restaurants WHERE name = 'Navat')),
    ('Шербет', 'Сладкий охлаждающий напиток', (SELECT id FROM product_categories WHERE name = 'Напитки'), 90.00, 'sherbet.jpg', (SELECT id FROM restaurants WHERE name = 'Navat')),
    ('Курут', 'Соленые шарики из сушеного йогурта', (SELECT id FROM product_categories WHERE name = 'Закуски'), 120.00, 'kurut.jpg', (SELECT id FROM restaurants WHERE name = 'Navat')),
    ('Жалал-Абадский мёд', 'Натуральный горный мёд', (SELECT id FROM product_categories WHERE name = 'Десерты'), 200.00, 'honey.jpg', (SELECT id FROM restaurants WHERE name = 'Navat'));

-- Arzu
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Бешбармак', 'Национальное блюдо с мясом и тестом', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 320.00, 'beshbarmak.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu')),
    ('Дымдама', 'Овощное рагу с мясом', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 270.00, 'dymdama.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu')),
    ('Ачик-чучук', 'Салат из помидоров и лука', (SELECT id FROM product_categories WHERE name = 'Салаты'), 150.00, 'achik.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu')),
    ('Машхурда', 'Суп с машем и рисом', (SELECT id FROM product_categories WHERE name = 'Супы'), 180.00, 'mashkhurda.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu')),
    ('Кукси', 'Холодный суп с лапшой', (SELECT id FROM product_categories WHERE name = 'Супы'), 200.00, 'kuksi.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu')),
    ('Шакароб', 'Салат из помидоров, лука и перца', (SELECT id FROM product_categories WHERE name = 'Салаты'), 160.00, 'shakarob.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu')),
    ('Гречка с грибами', 'Гречневая каша с грибами', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 130.00, 'grechka.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu')),
    ('Сливочный чизкейк', 'Десерт на основе творожного сыра', (SELECT id FROM product_categories WHERE name = 'Десерты'), 220.00, 'cheesecake.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu')),
    ('Айран', 'Кисломолочный напиток', (SELECT id FROM product_categories WHERE name = 'Напитки'), 60.00, 'ayran.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu')),
    ('Катык', 'Кисломолочный напиток из молока', (SELECT id FROM product_categories WHERE name = 'Напитки'), 70.00, 'katyk.jpg', (SELECT id FROM restaurants WHERE name = 'Arzu'));

-- Barashek
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Шашлык из баранины', 'Маринованное мясо на углях', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 350.00, 'shashlyk.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek')),
    ('Долма', 'Фаршированные виноградные листья', (SELECT id FROM product_categories WHERE name = 'Закуски'), 220.00, 'dolma.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek')),
    ('Хумус', 'Паста из нута с оливковым маслом', (SELECT id FROM product_categories WHERE name = 'Закуски'), 180.00, 'hummus.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek')),
    ('Табуле', 'Салат из булгура и зелени', (SELECT id FROM product_categories WHERE name = 'Салаты'), 200.00, 'tabouleh.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek')),
    ('Чечевичный суп', 'Суп из красной чечевицы', (SELECT id FROM product_categories WHERE name = 'Супы'), 190.00, 'lentilsoup.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek')),
    ('Фалафель', 'Жареные шарики из нута', (SELECT id FROM product_categories WHERE name = 'Закуски'), 170.00, 'falafel.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek')),
    ('Кускус', 'Гарнир из манной крупы', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 140.00, 'couscous.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek')),
    ('Пахлава', 'Сладкая выпечка с орехами', (SELECT id FROM product_categories WHERE name = 'Десерты'), 160.00, 'baklava.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek')),
    ('Турецкий кофе', 'Кофе, приготовленный в джезве', (SELECT id FROM product_categories WHERE name = 'Напитки'), 120.00, 'turkishcoffee.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek')),
    ('Айран', 'Прохладный йогуртовый напиток', (SELECT id FROM product_categories WHERE name = 'Напитки'), 80.00, 'ayran2.jpg', (SELECT id FROM restaurants WHERE name = 'Barashek'));

-- Paul
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Круассан с миндалем', 'Слоеная выпечка с миндальным кремом', (SELECT id FROM product_categories WHERE name = 'Десерты'), 180.00, 'croissant.jpg', (SELECT id FROM restaurants WHERE name = 'Paul')),
    ('Киш Лорен', 'Открытый пирог с беконом и сыром', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 250.00, 'quiche.jpg', (SELECT id FROM restaurants WHERE name = 'Paul')),
    ('Французский луковый суп', 'Суп с карамелизированным луком', (SELECT id FROM product_categories WHERE name = 'Супы'), 220.00, 'onionsoup.jpg', (SELECT id FROM restaurants WHERE name = 'Paul')),
    ('Салат Нисуаз', 'Салат с тунцом и оливками', (SELECT id FROM product_categories WHERE name = 'Салаты'), 280.00, 'nicoise.jpg', (SELECT id FROM restaurants WHERE name = 'Paul')),
    ('Сэндвич с ростбифом', 'Багет с говядиной и горчицей', (SELECT id FROM product_categories WHERE name = 'Закуски'), 260.00, 'sandwich.jpg', (SELECT id FROM restaurants WHERE name = 'Paul')),
    ('Рататуй', 'Овощное рагу по-провансальски', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 230.00, 'ratatouille.jpg', (SELECT id FROM restaurants WHERE name = 'Paul')),
    ('Картофельный гратен', 'Запеченный картофель с сыром', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 190.00, 'gratin.jpg', (SELECT id FROM restaurants WHERE name = 'Paul')),
    ('Эклер', 'Пирожное с заварным кремом', (SELECT id FROM product_categories WHERE name = 'Десерты'), 150.00, 'eclair.jpg', (SELECT id FROM restaurants WHERE name = 'Paul')),
    ('Капучино', 'Кофе с молочной пеной', (SELECT id FROM product_categories WHERE name = 'Напитки'), 140.00, 'cappuccino.jpg', (SELECT id FROM restaurants WHERE name = 'Paul')),
    ('Французский сидр', 'Слабоалкогольный яблочный напиток', (SELECT id FROM product_categories WHERE name = 'Напитки'), 200.00, 'cider.jpg', (SELECT id FROM restaurants WHERE name = 'Paul'));

-- Chicken Star
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Корейские крылышки', 'Острые куриные крылышки', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 290.00, 'wings.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star')),
    ('Токпокки', 'Рисовые клёцки в остром соусе', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 220.00, 'tokpokki.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star')),
    ('Кимчи', 'Острая квашеная капуста', (SELECT id FROM product_categories WHERE name = 'Салаты'), 150.00, 'kimchi.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star')),
    ('Рамён', 'Острый суп с лапшой', (SELECT id FROM product_categories WHERE name = 'Супы'), 200.00, 'ramen.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star')),
    ('Кимпаб', 'Корейские роллы с овощами и мясом', (SELECT id FROM product_categories WHERE name = 'Закуски'), 190.00, 'kimpab.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star')),
    ('Пибимпаб', 'Рис с овощами и яйцом', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 260.00, 'bibimbap.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star')),
    ('Кукси', 'Холодный суп с лапшой и овощами', (SELECT id FROM product_categories WHERE name = 'Супы'), 210.00, 'kuksi2.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star')),
    ('Пунчики', 'Сладкие рисовые шарики', (SELECT id FROM product_categories WHERE name = 'Десерты'), 140.00, 'punchiki.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star')),
    ('Суджоенги', 'Корейский рисовый пунш', (SELECT id FROM product_categories WHERE name = 'Напитки'), 130.00, 'sikhye.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star')),
    ('Соджу', 'Корейская водка', (SELECT id FROM product_categories WHERE name = 'Напитки'), 250.00, 'soju.jpg', (SELECT id FROM restaurants WHERE name = 'Chicken Star'));

-- Taksim
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Донер-кебаб', 'Мясо на вертеле с лавашом', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 240.00, 'doner.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim')),
    ('Искендер', 'Кебаб на хлебе с томатным соусом', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 280.00, 'iskender.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim')),
    ('Чечевичный суп', 'Суп из красной чечевицы', (SELECT id FROM product_categories WHERE name = 'Супы'), 170.00, 'mercimek.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim')),
    ('Шакшука', 'Яичница с овощами и специями', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 190.00, 'shakshuka.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim')),
    ('Хумус', 'Паста из нута с оливковым маслом', (SELECT id FROM product_categories WHERE name = 'Закуски'), 160.00, 'hummus2.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim')),
    ('Лахмаджун', 'Тонкая лепешка с мясным фаршем', (SELECT id FROM product_categories WHERE name = 'Закуски'), 180.00, 'lahmacun.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim')),
    ('Пилав', 'Рис с овощами и специями', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 120.00, 'pilav.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim')),
    ('Пахлава', 'Сладкая выпечка с орехами и медом', (SELECT id FROM product_categories WHERE name = 'Десерты'), 150.00, 'baklava2.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim')),
    ('Турецкий чай', 'Крепкий черный чай', (SELECT id FROM product_categories WHERE name = 'Напитки'), 60.00, 'turkishtea.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim')),
    ('Айран', 'Прохладный йогуртовый напиток', (SELECT id FROM product_categories WHERE name = 'Напитки'), 70.00, 'ayran3.jpg', (SELECT id FROM restaurants WHERE name = 'Taksim'));

-- Cyclone
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Клаб-сэндвич', 'Многослойный сэндвич с курицей', (SELECT id FROM product_categories WHERE name = 'Закуски'), 230.00, 'clubsandwich.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone')),
    ('Авокадо-тост', 'Тост с авокадо и яйцом пашот', (SELECT id FROM product_categories WHERE name = 'Закуски'), 210.00, 'avocadotoast.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone')),
    ('Цезарь с курицей', 'Салат с куриной грудкой', (SELECT id FROM product_categories WHERE name = 'Салаты'), 250.00, 'caesar.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone')),
    ('Тыквенный суп', 'Крем-суп из тыквы', (SELECT id FROM product_categories WHERE name = 'Супы'), 180.00, 'pumpkinsoup.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone')),
    ('Паста Карбонара', 'Спагетти с беконом и яйцом', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 270.00, 'carbonara.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone')),
    ('Картофель фри', 'С трюфельным маслом и пармезаном', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 160.00, 'trufflefries.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone')),
    ('Брауни', 'Шоколадный десерт с орехами', (SELECT id FROM product_categories WHERE name = 'Десерты'), 170.00, 'brownie.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone')),
    ('Чизкейк', 'Классический ньюйоркский чизкейк', (SELECT id FROM product_categories WHERE name = 'Десерты'), 190.00, 'cheesecake2.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone')),
    ('Флэт уайт', 'Кофе с молоком', (SELECT id FROM product_categories WHERE name = 'Напитки'), 140.00, 'flatwhite.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone')),
    ('Смузи', 'Ягодный смузи с бананом', (SELECT id FROM product_categories WHERE name = 'Напитки'), 180.00, 'smoothie.jpg', (SELECT id FROM restaurants WHERE name = 'Cyclone'));

-- Coffee House
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Латте', 'Кофе с молоком', (SELECT id FROM product_categories WHERE name = 'Напитки'), 150.00, 'latte.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House')),
    ('Американо', 'Черный кофе', (SELECT id FROM product_categories WHERE name = 'Напитки'), 120.00, 'americano.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House')),
    ('Тирамису', 'Классический итальянский десерт', (SELECT id FROM product_categories WHERE name = 'Десерты'), 200.00, 'tiramisu.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House')),
    ('Чизкейк', 'Нежный десерт из сливочного сыра', (SELECT id FROM product_categories WHERE name = 'Десерты'), 190.00, 'cheesecake3.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House')),
    ('Круассан', 'Слоеная выпечка с маслом', (SELECT id FROM product_categories WHERE name = 'Закуски'), 130.00, 'croissant2.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House')),
    ('Греческий салат', 'Свежий салат с фетой', (SELECT id FROM product_categories WHERE name = 'Салаты'), 220.00, 'greeksalad.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House')),
    ('Сэндвич с тунцом', 'На цельнозерновом хлебе', (SELECT id FROM product_categories WHERE name = 'Закуски'), 210.00, 'tunasandwich.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House')),
    ('Овощной суп', 'Легкий суп из сезонных овощей', (SELECT id FROM product_categories WHERE name = 'Супы'), 170.00, 'vegetablesoup.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House')),
    ('Матча-латте', 'Напиток из зеленого чая', (SELECT id FROM product_categories WHERE name = 'Напитки'), 180.00, 'matcha.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House')),
    ('Лимонад', 'Домашний лимонад с мятой', (SELECT id FROM product_categories WHERE name = 'Напитки'), 160.00, 'lemonade.jpg', (SELECT id FROM restaurants WHERE name = 'Coffee House'));

-- Bellagio
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Стейк Рибай', 'Премиальный стейк из говядины', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 700.00, 'ribeye.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio')),
    ('Фуа-гра', 'Гусиная печень с ягодным соусом', (SELECT id FROM product_categories WHERE name = 'Закуски'), 450.00, 'foiegras.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio')),
    ('Устрицы', 'Свежие устрицы с лимоном', (SELECT id FROM product_categories WHERE name = 'Закуски'), 600.00, 'oysters.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio')),
    ('Лобстер', 'Запеченный лобстер с маслом', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 900.00, 'lobster.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio')),
    ('Крем-суп из лесных грибов', 'Нежный суп с трюфельным маслом', (SELECT id FROM product_categories WHERE name = 'Супы'), 350.00, 'mushroomsoup.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio')),
    ('Салат с креветками', 'Свежий салат с тигровыми креветками', (SELECT id FROM product_categories WHERE name = 'Салаты'), 400.00, 'shrimpsalad.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio')),
    ('Ризотто с шафраном', 'Классическое итальянское ризотто', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 320.00, 'risotto.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio')),
    ('Крем-брюле', 'Десерт со сливками и карамелью', (SELECT id FROM product_categories WHERE name = 'Десерты'), 250.00, 'cremebrulee.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio')),
    ('Шампанское', 'Бокал французского шампанского', (SELECT id FROM product_categories WHERE name = 'Напитки'), 500.00, 'champagne.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio')),
    ('Красное вино', 'Бокал итальянского вина', (SELECT id FROM product_categories WHERE name = 'Напитки'), 350.00, 'redwine.jpg', (SELECT id FROM restaurants WHERE name = 'Bellagio'));

-- Forrest
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Вегетарианский бургер', 'Котлета из нута и овощей', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 250.00, 'vegburger.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest')),
    ('Фалафель', 'Шарики из нута с соусом тахини', (SELECT id FROM product_categories WHERE name = 'Закуски'), 180.00, 'falafel2.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest')),
    ('Овощной салат', 'Микс овощей с оливковым маслом', (SELECT id FROM product_categories WHERE name = 'Салаты'), 200.00, 'vegsalad.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest')),
    ('Томатный суп', 'Суп-пюре из спелых томатов', (SELECT id FROM product_categories WHERE name = 'Супы'), 170.00, 'tomatosoup.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest')),
    ('Паста с овощами', 'Паста с сезонными овощами', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 220.00, 'vegpasta.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest')),
    ('Булгур', 'Отварной булгур с зеленью', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 120.00, 'bulgur.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest')),
    ('Печёная тыква', 'Тыква с травами и специями', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 150.00, 'pumpkin.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest')),
    ('Фруктовый салат', 'Сезонные фрукты с мёдом', (SELECT id FROM product_categories WHERE name = 'Десерты'), 190.00, 'fruitsalad.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest')),
    ('Смузи', 'Зеленый смузи со шпинатом', (SELECT id FROM product_categories WHERE name = 'Напитки'), 180.00, 'greensmoothie.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest')),
    ('Комбуча', 'Ферментированный чайный гриб', (SELECT id FROM product_categories WHERE name = 'Напитки'), 160.00, 'kombucha.jpg', (SELECT id FROM restaurants WHERE name = 'Forrest'));

-- Bravo
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Пицца Маргарита', 'Классическая пицца с моцареллой', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 280.00, 'margherita.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo')),
    ('Паста Болоньезе', 'Спагетти с мясным соусом', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 260.00, 'bolognese.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo')),
    ('Лазанья', 'Слоёное блюдо с мясом и соусом', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 290.00, 'lasagna.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo')),
    ('Тирамису', 'Десерт с маскарпоне и кофе', (SELECT id FROM product_categories WHERE name = 'Десерты'), 220.00, 'tiramisu2.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo')),
    ('Капрезе', 'Салат с моцареллой и томатами', (SELECT id FROM product_categories WHERE name = 'Салаты'), 240.00, 'caprese.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo')),
    ('Минестроне', 'Овощной суп по-итальянски', (SELECT id FROM product_categories WHERE name = 'Супы'), 200.00, 'minestrone.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo')),
    ('Антипасти', 'Ассорти итальянских закусок', (SELECT id FROM product_categories WHERE name = 'Закуски'), 350.00, 'antipasti.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo')),
    ('Ризотто с грибами', 'Рис с белыми грибами', (SELECT id FROM product_categories WHERE name = 'Гарниры'), 280.00, 'risotto2.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo')),
    ('Панна-котта', 'Сливочный десерт с ягодами', (SELECT id FROM product_categories WHERE name = 'Десерты'), 200.00, 'pannacotta.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo')),
    ('Эспрессо', 'Крепкий итальянский кофе', (SELECT id FROM product_categories WHERE name = 'Напитки'), 100.00, 'espresso.jpg', (SELECT id FROM restaurants WHERE name = 'Bravo'));

-- Sierra
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Капучино', 'Кофе с молочной пеной', (SELECT id FROM product_categories WHERE name = 'Напитки'), 140.00, 'cappuccino2.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra')),
    ('Латте', 'Мягкий кофейный напиток с молоком', (SELECT id FROM product_categories WHERE name = 'Напитки'), 150.00, 'latte2.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra')),
    ('Американо', 'Черный кофе', (SELECT id FROM product_categories WHERE name = 'Напитки'), 120.00, 'americano2.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra')),
    ('Круассан', 'Французская слоеная выпечка', (SELECT id FROM product_categories WHERE name = 'Закуски'), 130.00, 'croissant3.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra')),
    ('Сэндвич с курицей', 'На чиабатте с соусом', (SELECT id FROM product_categories WHERE name = 'Закуски'), 210.00, 'chickensandwich.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra')),
    ('Цезарь', 'Салат с куриной грудкой', (SELECT id FROM product_categories WHERE name = 'Салаты'), 250.00, 'caesar2.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra')),
    ('Морковный торт', 'Домашний торт с кремом', (SELECT id FROM product_categories WHERE name = 'Десерты'), 180.00, 'carrotcake.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra')),
    ('Смузи с бананом', 'На основе йогурта', (SELECT id FROM product_categories WHERE name = 'Напитки'), 170.00, 'bananasmoothie.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra')),
    ('Греческий йогурт', 'С гранолой и ягодами', (SELECT id FROM product_categories WHERE name = 'Десерты'), 160.00, 'yogurt.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra')),
    ('Куриный суп', 'С вермишелью и зеленью', (SELECT id FROM product_categories WHERE name = 'Супы'), 180.00, 'chickensoup.jpg', (SELECT id FROM restaurants WHERE name = 'Sierra'));

-- Booblik
INSERT INTO products(name, description, category_id, price, image, restaurant_id)
VALUES
    ('Бейгл с лососем', 'С творожным сыром и зеленью', (SELECT id FROM product_categories WHERE name = 'Закуски'), 250.00, 'salmombagel.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik')),
    ('Бейгл с авокадо', 'С творожным сыром и авокадо', (SELECT id FROM product_categories WHERE name = 'Закуски'), 220.00, 'avocadobagel.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik')),
    ('Кофе Раф', 'Сливочный кофе с ванилью', (SELECT id FROM product_categories WHERE name = 'Напитки'), 160.00, 'rafcoffee.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik')),
    ('Синнабон', 'Булочка с корицей и глазурью', (SELECT id FROM product_categories WHERE name = 'Десерты'), 180.00, 'cinnabon.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik')),
    ('Буррата', 'Свежий сыр с томатами', (SELECT id FROM product_categories WHERE name = 'Закуски'), 290.00, 'burrata.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik')),
    ('Салат с киноа', 'С овощами и авокадо', (SELECT id FROM product_categories WHERE name = 'Салаты'), 240.00, 'quinoasalad.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik')),
    ('Тыквенный суп', 'Пюре из тыквы со сливками', (SELECT id FROM product_categories WHERE name = 'Супы'), 190.00, 'pumpkinsoup2.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik')),
    ('Шакшука', 'Израильское блюдо с яйцами', (SELECT id FROM product_categories WHERE name = 'Горячие блюда'), 230.00, 'shakshuka2.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik')),
    ('Лимонад', 'Домашний лимонад с мятой', (SELECT id FROM product_categories WHERE name = 'Напитки'), 150.00, 'lemonade2.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik')),
    ('Чизкейк', 'Нью-йоркский чизкейк', (SELECT id FROM product_categories WHERE name = 'Десерты'), 200.00, 'cheesecake4.jpg', (SELECT id FROM restaurants WHERE name = 'Booblik'));