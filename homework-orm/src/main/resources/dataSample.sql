-- Inserción de artistas
INSERT INTO artists (name, specialty, bio)
VALUES ('Pablo Picasso', 'Cubismo', 'Pintor y escultor español, una de las figuras más destacadas del arte del siglo XX');

INSERT INTO artists (name, specialty, bio)
VALUES ('Vincent van Gogh', 'Postimpresionismo', 'Pintor neerlandés, una de las figuras más grandes de la pintura occidental');

INSERT INTO artists (name, specialty, bio)
VALUES ('Claude Monet', 'Impresionismo', 'Pintor francés, considerado uno de los padres del Impresionismo');

-- Inserción de obras
INSERT INTO masterpieces (title, genre, creation_year, artist_id)
VALUES ('Guernica', 'Pintura', 1937, 1);

INSERT INTO masterpieces (title, genre, creation_year, artist_id)
VALUES ('La noche estrellada', 'Pintura', 1889, 2);

INSERT INTO masterpieces (title, genre, creation_year, artist_id)
VALUES ('Impresión, sol naciente', 'Pintura', 1872, 3);

-- Inserción de galerías
INSERT INTO art_galleries (name, date)
VALUES ('Museo del Prado', '27-11-2024');

INSERT INTO art_galleries (name, date)
VALUES ('Museo de Arte Moderno', '27-11-2024');

INSERT INTO art_galleries (name, date)
VALUES ('Museo de Orsay', '27-11-2024');

-- Relación entre obras y galerías
INSERT INTO gallery_masterpiece (art_gallery_id, masterpiece_id)
VALUES (1, 1);

INSERT INTO gallery_masterpiece (art_gallery_id, masterpiece_id)
VALUES (1, 2);

INSERT INTO gallery_masterpiece (art_gallery_id, masterpiece_id)
VALUES (2, 3);

INSERT INTO gallery_masterpiece (art_gallery_id, masterpiece_id)
VALUES (3, 1);
