BEGIN;

CREATE SCHEMA IF NOT EXISTS game_db;

CREATE TABLE game_db.CARD (
    id SERIAL PRIMARY KEY,
    attack INT NOT NULL,
    stamina INT NOT NULL,
    image_url VARCHAR(255) UNIQUE NOT NULL,
    card_name VARCHAR(255) NOT NULL
);

CREATE TABLE game_db.PLAYER (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    score INT NOT NULL
);

CREATE TABLE game_db.SHOP (
    id INT PRIMARY KEY,
    date DATE NOT NULL
);

CREATE TABLE game_db.MATCH (
    id SERIAL PRIMARY KEY,
    n_rounds INT NOT NULL
);

CREATE TABLE game_db.ROUND (
    id INT PRIMARY KEY,
    player INT NOT NULL,
    deck_cards VARCHAR(255) NOT NULL,
    match_id INT NOT NULL,
    FOREIGN KEY (player) REFERENCES game_db.PLAYER(id),
    FOREIGN KEY (match_id) REFERENCES game_db.MATCH(id)
);

CREATE TABLE game_db.ZONE (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    effect VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL
);

CREATE TABLE game_db.DIFFICULTY (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    deck_cards VARCHAR(255) NOT NULL
);

CREATE TABLE game_db.HAS_PLAYER_CARD (
    player_id INT,
    card_id INT,
    PRIMARY KEY (player_id, card_id),
    FOREIGN KEY (player_id) REFERENCES game_db.PLAYER(id),
    FOREIGN KEY (card_id) REFERENCES game_db.CARD(id)
);

CREATE TABLE game_db.BUYS_PLAYER_CARD (
    player_id INT,
    card_id INT,
    PRIMARY KEY (player_id, card_id),
    FOREIGN KEY (player_id) REFERENCES game_db.PLAYER(id),
    FOREIGN KEY (card_id) REFERENCES game_db.CARD(id)
);

CREATE TABLE game_db.HAS_SHOP_CARD (
    shop_id INT,
    card_id INT,
    PRIMARY KEY (shop_id, card_id),
    FOREIGN KEY (shop_id) REFERENCES game_db.SHOP(id),
    FOREIGN KEY (card_id) REFERENCES game_db.CARD(id)
);

CREATE TABLE game_db.PLAYS_IN (
    player_id INT,
    match_id INT,
    PRIMARY KEY (player_id, match_id),
    FOREIGN KEY (player_id) REFERENCES game_db.PLAYER(id),
    FOREIGN KEY (match_id) REFERENCES game_db.MATCH(id)
);

CREATE TABLE game_db.REGISTRATES (
    match_id INT,
    round_id INT,
    PRIMARY KEY (match_id, round_id),
    FOREIGN KEY (match_id) REFERENCES game_db.MATCH(id),
    FOREIGN KEY (round_id) REFERENCES game_db.ROUND(id)
);

CREATE TABLE game_db.HAS_MATCH_ZONE (
    match_id INT,
    zone_id INT,
    PRIMARY KEY (match_id, zone_id),
    FOREIGN KEY (match_id) REFERENCES game_db.MATCH(id),
    FOREIGN KEY (zone_id) REFERENCES game_db.ZONE(id)
);

CREATE TABLE game_db.HAS_DIFFICULTY_MATCH (
    difficulty_id INT,
    match_id INT,
    PRIMARY KEY (difficulty_id, match_id),
    FOREIGN KEY (difficulty_id) REFERENCES game_db.DIFFICULTY(id),
    FOREIGN KEY (match_id) REFERENCES game_db.MATCH(id)
);

INSERT INTO game_db.CARD (attack, stamina, image_url, card_name) VALUES
(2, 1, '2', 'Agent13'),
(2, 1, '3', 'Angel'),
(2, 1, '4', 'America Chavez'),
(3, 2, '5', 'ARMOR'),
(3, 2, '6', 'Cable'),
(3, 2, '7', 'Doctor Strange'),
(2, 2, '8', 'Daredevil'),
(2, 2, '9', 'FORGE'),
(3, 2, '10', 'HULK BUSTER'),
(3, 3, '11', 'Black Widow'),
(3, 3, '12', 'Crystal'),
(4, 3, '13', 'Cyclops'),
(3, 4, '14', 'Ghost Rider'),
(5, 4, '15', 'Enchantress'),
(5, 4, '16', 'Loki'),
(6, 4, '17', 'War Machine'),
(10, 5, '18', 'Doctor Octopus'),
(7, 5, '19', 'Sandman'),
(8, 5, '20', 'MODOK'),
(5, 6, '21', 'Doctor Doom'),
(5, 6, '22', 'Galactus'),
(10, 6, '23', 'Helicarrier');

INSERT INTO game_db.PLAYER (name, password, score) VALUES
('Player1', '123', 1000);

INSERT INTO game_db.ZONE (name, effect, image_url) VALUES
('Jardin', 'Nada', '1'),
('Ciudad', 'Nada', '2'),
('Almussafes', 'Nada', '3');

INSERT INTO game_db.DIFFICULTY (name, deck_cards) VALUES
('Facil', '2,3,4,5,6,23,10,11,12,13,17,18'),
('Medio', '2,3,4,8,9,10,19,20,21,22,,11,12'),
('Dificil', '5,6,7,8,9,23,22,21,20,17,16,15');

COMMIT;