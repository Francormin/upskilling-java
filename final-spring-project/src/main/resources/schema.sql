-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);

-- Crear tabla de categorías de gastos
CREATE TABLE IF NOT EXISTS expense_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Crear tabla de gastos
CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE PRECISION NOT NULL,
    date VARCHAR(255) NOT NULL,
    description TEXT,
    category_id BIGINT,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES expense_categories(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
