DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks (
    id INT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    due_date DATE
);
