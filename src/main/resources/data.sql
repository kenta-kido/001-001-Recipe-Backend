-- user_entity テーブルにデータを挿入
INSERT INTO user_entity (id, email, extra_info, password, role) VALUES 
(1, 'user2@kenta.com', 'This is a test user.', '$2a$12$OBnerD3ZrnkqY/ofkaxune1jnpUscFhTGCcuVA9x5lgAGAtr6Bss2', 'ROLE_USER');

-- recipe テーブルにデータを挿入
INSERT INTO recipe (recipe_id, timestamp, user_id, title) VALUES 
(1, '2024-12-28T00:00:00', 1, 'テストレシピ1');

-- description テーブルにデータを挿入
INSERT INTO description (description_id, recipe_id, description, sequence) VALUES 
(1, 1, '手順1: 材料を混ぜる', 1),
(2, 1, '手順2: 生地をこねる', 2),
(3, 1, '手順3: オーブンで焼く', 3);

-- photo テーブルにBase64エンコードされた画像データを挿入
INSERT INTO photo (photo_id, description_id, binary_photo, sequence) VALUES 
(1, 1, 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAADElEQVR4nGP4z8AAAAMBAQDJ/pLvAAAAAElFTkSuQmCC', 1),
(2, 1, 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAADElEQVR4nGNgaGAAAAEEAIFw9selAAAAAElFTkSuQmCC', 2),
(3, 1, 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAADElEQVR4nGNgYPgPAAEDAQAIicLsAAAAAElFTkSuQmCC', 3);
