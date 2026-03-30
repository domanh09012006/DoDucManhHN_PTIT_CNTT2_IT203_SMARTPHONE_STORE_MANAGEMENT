DROP DATABASE IF EXISTS PhoneStore;
CREATE DATABASE PhoneStore;
USE PhoneStore;

-- ================= USERS =================
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL, -- ADMIN / CUSTOMER
                       phone VARCHAR(20),
                       address VARCHAR(255)
);

-- ================= CATEGORY =================
CREATE TABLE categories (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL
);

-- ================= PRODUCT =================
CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          brand VARCHAR(50),
                          storage VARCHAR(50),
                          color VARCHAR(50),
                          price DECIMAL(10,2) NOT NULL,
                          stock INT NOT NULL,
                          description TEXT,
                          category_id INT,
                          FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- ================= ORDERS =================
CREATE TABLE orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT,
                        total DECIMAL(10,2),
                        status VARCHAR(20),
                        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ================= ORDER DETAILS =================
CREATE TABLE order_details (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               order_id INT,
                               product_id INT,
                               quantity INT,
                               price DECIMAL(10,2),
                               FOREIGN KEY (order_id) REFERENCES orders(id),
                               FOREIGN KEY (product_id) REFERENCES products(id)
);

-- ADMIN
INSERT INTO users(name, email, password, role)
VALUES ('Admin', 'admin@gmail.com', '123456', 'ADMIN');

-- CATEGORY
INSERT INTO categories(name) VALUES
                                 ('iPhone'),
                                 ('Samsung'),
                                 ('Xiaomi');

-- PRODUCT
INSERT INTO products(name, brand, storage, color, price, stock, description, category_id)
VALUES
    ('iPhone 13', 'Apple', '128GB', 'Black', 15000000, 10, 'iPhone 13 chính hãng', 1),
    ('Samsung S22', 'Samsung', '256GB', 'White', 14000000, 8, 'Samsung flagship', 2);
UPDATE users SET role = 'ADMIN' WHERE email = 'manhadmin@gmail.com';
