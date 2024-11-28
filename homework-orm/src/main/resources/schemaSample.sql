CREATE TABLE artists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR(255),
    bio TEXT
);

CREATE TABLE masterpieces (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    creation_year INT,
    artist_id BIGINT,
    FOREIGN KEY (artist_id) REFERENCES artists(id)
);

CREATE TABLE art_galleries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date DATE
);

CREATE TABLE gallery_masterpiece (
    art_gallery_id BIGINT,
    masterpiece_id BIGINT,
    PRIMARY KEY (art_gallery_id, masterpiece_id),
    FOREIGN KEY (art_gallery_id) REFERENCES art_galleries(id),
    FOREIGN KEY (masterpiece_id) REFERENCES masterpieces(id)
);
