DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, description, date_time, calories)
VALUES (100000,'Breakfast',TIMESTAMP '2011-05-16 15:36:38',1000),
       (100001,'Dinner',TIMESTAMP '2011-05-16 16:36:38',1500),
       (100000,'Dinner2',TIMESTAMP '2011-05-16 17:36:38',1500);
