INSERT INTO users (id, username, password, full_name, email, bio, discount, enabled) VALUES (1, 'admin', '827ccb0eea8a706c4c34a16891f84e7b', 'Arthur Humenchuk', 'railwaytan@gmail.com', '21 y.o. designer from San Francisco', 0, true);
INSERT INTO users (id, username, password, full_name, email, bio, discount, enabled) VALUES (2, 'user1', '827ccb0eea8a706c4c34a16891f84e7b', 'user userino', 'user@gmail.com', '', 0, true);

INSERT INTO user_authorities (user_id, authorities) VALUES (1, 'USER');
INSERT INTO user_authorities (user_id, authorities) VALUES (1, 'ADMIN');
INSERT INTO user_authorities (user_id, authorities) VALUES (2, 'USER');

INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (1, 'Signapoure', 'EXCURSION', 500, 2, 'APARTMENTS', '', false);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (2, 'San Paulo, Italy', 'SHOPPING', 2000, 15, 'LODGE', '', false);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (3, 'La Femme Chinoise', 'RECREATION', 99, 15, 'APARTMENTS', 'The scent of the Orientale.', false);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (4, 'Twin Peaks Resort', 'RECREATION', 1500, 20, 'RESORT', '', false);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (5, 'American Dream', 'SHOPPING', 1780, 5, 'APARTMENTS', 'Do you like Huey Lewis and The News?', false);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (6, 'Sarajevo, Yugoslavia', 'SHOPPING', 1990, 13, 'APARTMENTS', '', false);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (7, 'Akihabara, Japan', 'SHOPPING', 3000, 10, 'APARTMENTS', 'Anime merch and electronics.', false);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (8, 'Silent Hill Resort', 'RECREATION', 2000, 5, 'RESORT', '', false);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (9, 'Good Springs ', 'EXCURSION', 500, 10, 'LODGE', '', false);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (10, 'Хмельницький базар', 'SHOPPING', 200, 30, 'APARTMENTS', 'Третій за величиною базар у Європі. Дешеві ціни, великий асортимент. Працює з 9 до 14.', true);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (11, 'Сходження на Говерлу, Україна', 'EXCURSION', 500, 12, 'LODGE', 'Вдихніть Карпати на повні груди: подивіться на краєвиди, прислухайтесь до водоспадів та відвідайте озеро Синевир, познайомтесь з місцевими мешканцями – бурими ведмедями та вовками.', true);
INSERT INTO tours (id, name, type, price, group_size, hotel, description, hot) VALUES (12, 'Martinaise, Revachol', 'EXCURSION', 600, 2, 'APARTMENTS', 'The former capital of the world, the pearl of Isulindian Isola is open to its visitors from the free countries of Moralintern Alliance.', true);