INSERT INTO roles(role)
VALUES ('ADMIN'),
       ('MODERATOR'),
       ('USER');


INSERT INTO clients (email, full_name, password, username)
VALUES ('anton@anton.com', 'Anton Goranov',
        '$2a$10$IV/H.EI3PdfYpIxicMMbAOLe1MedkDwEUytHvFwEUoVbcV1.CoGw2', 'agoranov'),

       ('test@gmail.com', 'Test Testov',
        '$2a$10$seonkCywjMOsOcnvHpdFv.0xfXAHkGwOhTxrYrXwQGbh0uCttWcyC', 'testvam'),

       ('user@gmail.com', 'User Userov',
        '$2a$10$T9.ozvBk/uPjvVLfXIJHJ.JB.DOKXMWXyEfzZr4jyErDOt9c0eefa', 'bestuser1');

INSERT INTO clients_roles(client_id, roles_id)
VALUES (1, 1),
       (1, 3),
       (2, 3),
       (3, 3);



INSERT INTO authors (name)
VALUES ('J. C. N. Jencarris'),
       ('Jonathan Ballers'),
       ('A. Jennings Ballington'),
       ('J. C. Noziz'),
       ('Carla Ruby School'),
       ('Randolph V. Hawthorn'),
       ('Annie Anson'),
       ('Noris Jenblood'),
       ('J. C. Northcott'),
       ('Victorine Northup'),
       ('R. V. Raymonds');



INSERT INTO books (title, author_id, date_of_publishing)
VALUES ('Pilot Of Dusk', 3, '1997/09/01'),
       ('Sage Of The Mountain', 3, '2001/06/27'),
       ('Heirs Of Utopia', 9, '2018/08/17'),
       ('Knights Without Courage', 3, '2015/07/25'),
       ('Pirates And Companions', 9, '2021/06/22'),
       ('Pig Of My Country', 9, '1997/02/18'),
       ('Queen Of Fantasy', 10, '2001/12/13'),
       ('Tigers Of Excelsior', 6, '2004/03/28'),
       ('Dinos In My House', 6, '2007/02/27'),
       ('Chickens And Sheep', 10, '2013/04/29'),
       ('Follower Of The Sea', 6, '2003/09/30'),
       ('Ancestor Of Dread', 6, '2013/05/22'),
       ('Companions Of The Gods', 2, '2002/04/03'),
       ('Supporters Of The Forest', 2, '2012/01/18'),
       ('Secretaries And Ancestors', 2, '1993/10/19'),
       ('Werewolf Of The Stars', 1, '2001/05/07'),
       ('Necromancer Of The Future', 1, '2014/07/06'),
       ('Hobgoblins Of The North', 1, '2008/09/19'),
       ('Robots Without Flaws', 7, '2010/04/12'),
       ('Dwarves And Summoners', 8, '1995/10/25'),
       ('Angel In The Forest', 4, '2011/09/08'),
       ('Freak Without A Voicen', 7, '2022/03/09'),
       ('Horses At The Crypts', 4, '1998/12/22'),
       ('Snakes Without Eyes', 9, '2003/07/15'),
       ('Students And Animals', 7, '2012/08/03'),
       ('Officer Program', 11, '1996/06/22'),
       ('Parrot Of Humor', 7, '2010/02/13'),
       ('Man Mocks Me', 11, '2017/07/14'),
       ('Fish Has Been Naughty', 11, '2000/07/13'),
       ('Hunter And Butcher', 3, '2020/03/13'),
       ('Tortoise Of The Void', 5, '2000/12/20'),
       ('Soldier Of A Stranger', 3, '2016/03/16'),
       ('Men Of The Moon', 5, '2009/09/01'),
       ('Slaves In My Country', 3, '1994/10/01'),
       ('Butchers And Rebels', 8, '2017/01/15'),
       ('Men Of The Jungle', 8, '1999/06/19'),
       ('Leaders With Tradition', 8, '2010/08/19'),
       ('Insects Of Medicine', 8, '2002/07/01'),
       ('Emperors Of The Lakes', 5, '2003/11/03'),
       ('Fish And Rodents', 2, '2015/01/27'),
       ('Girlfriend Of The Ocean', 7, '1999/12/23'),
       ('Angel With A Spaceship', 10, '2001/08/11'),
       ('Leader Of The Future', 11, '2017/09/04'),
       ('Veterans Of Death', 11, '2016/09/24'),
       ('Commanders Of Mars', 6, '1999/05/13'),
       ('Children And Martians', 10, '2007/12/30');