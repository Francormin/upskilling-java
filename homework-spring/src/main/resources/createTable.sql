CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(500),
    due_date DATE
);
