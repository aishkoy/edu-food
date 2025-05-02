-- changelog Aisha: 003 create restaurant table
create table restaurants
(
    id          long auto_increment primary key,
    name        varchar(255) not null,
    description varchar(255),
    image       varchar(255),
    address     varchar(255) not null
);

INSERT INTO restaurants (name, description, image, address)
VALUES ('Faiza', 'Популярная чайхана с национальной кухней', 'faiza.jpg', 'ул. Киевская 157'),
       ('Navat', 'Уютный ресторан с кыргызской атмосферой', 'navat.jpg', 'пр. Чуй 168'),
       ('Arzu', 'Национальные блюда и живая музыка', 'arzu.jpg', 'ул. Тоголок Молдо 2'),
       ('Barashek', 'Восточная кухня в современном интерьере', 'barashek.jpg', 'ул. Панфилова 225'),
       ('Paul', 'Французская пекарня и кафе', 'paul.jpg', 'ул. Исанова 42'),
       ('Chicken Star', 'Корейская уличная еда', 'chickenstar.jpg', 'ул. Киевская 99'),
       ('Taksim', 'Турецкая кухня и шашлыки', 'taksim.jpg', 'ул. Байтик Баатыра 19'),
       ('Cyclone', 'Кофейня и закуски в стиле лофт', 'cyclone.jpg', 'ул. Абдрахманова 170'),
       ('Coffee House', 'Место для встреч и кофе с десертами', 'coffeehouse.jpg', 'ул. Манаса 57'),
       ('Bellagio', 'Европейская кухня и хорошее вино', 'bellagio.jpg', 'ул. Тыныстанова 213'),
       ('Forrest', 'Эко-ресторан с вегетарианским меню', 'forrest.jpg', 'ул. Гоголя 77'),
       ('Bravo', 'Итальянская кухня и пицца', 'bravo.jpg', 'ул. Московская 195'),
       ('Sierra', 'Сеть кафе с кофе и выпечкой', 'sierra.jpg', 'ул. Боконбаева 132'),
       ('Booblik', 'Десерты, бейглы и кофе', 'booblik.jpg', 'ул. Турусбекова 30');