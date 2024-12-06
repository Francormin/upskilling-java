-- Insertar usuarios
INSERT INTO users (name, surname, email) VALUES
('John', 'Doe', 'john.doe@example.com'),
('Jane', 'Smith', 'jane.smith@example.com');

-- Insertar categorías de gastos
INSERT INTO expense_categories (name, description) VALUES
('groceries', 'Expenses for food and supplies'),
('utilities', 'Monthly utility bills'),
('transportation', 'Transportation-related costs'),
('entertainment', 'Leisure and entertainment expenses');

-- Insertar gastos
INSERT INTO expenses (amount, date, description, category_id, user_id) VALUES
(50.75, '01-12-2024', 'Weekly grocery shopping', 1, 1),
(120.00, '03-12-2024', 'Electricity bill payment', 2, 2),
(15.50, '04-12-2024', 'Bus fare for work commute', 3, 1),
(75.00, '04-12-2024', 'Dinner with friends at a restaurant', 4, 2);
