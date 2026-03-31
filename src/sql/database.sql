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

-- ================= THÊM DỮ LIỆU MẪU =================

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
    -- 5 Sản phẩm iPhone (category_id = 1)
    ('iPhone 13', 'Apple', '128GB', 'Black', 15000000, 20, 'iPhone 13 bản tiêu chuẩn, hiệu năng ổn định', 1),
    ('iPhone 14 Plus', 'Apple', '256GB', 'Purple', 20000000, 15, 'Màn hình lớn, pin trâu', 1),
    ('iPhone 14 Pro Max', 'Apple', '512GB', 'Gold', 25000000, 10, 'Flagship 2022 của Apple, camera sắc nét', 1),
    ('iPhone 15', 'Apple', '128GB', 'Pink', 21000000, 25, 'Thiết kế mới với Dynamic Island', 1),
    ('iPhone 15 Pro Max', 'Apple', '256GB', 'Titanium', 29000000, 8, 'Khung viền Titanium cao cấp, chip A17 Pro mạnh mẽ', 1),

    -- 5 Sản phẩm Samsung (category_id = 2)
    ('Samsung Galaxy A54', 'Samsung', '128GB', 'Awesome Lime', 8000000, 30, 'Điện thoại tầm trung bán chạy nhất', 2),
    ('Samsung Galaxy S22', 'Samsung', '256GB', 'Phantom White', 14000000, 12, 'Flagship nhỏ gọn, cầm nắm dễ dàng', 2),
    ('Samsung Galaxy S23 Ultra', 'Samsung', '512GB', 'Phantom Black', 24000000, 10, 'Camera zoom 100x đỉnh cao, có bút S-Pen', 2),
    ('Samsung Galaxy Z Fold5', 'Samsung', '256GB', 'Icy Blue', 32000000, 5, 'Điện thoại gập cao cấp, màn hình siêu lớn', 2),
    ('Samsung Galaxy S24 Ultra', 'Samsung', '1TB', 'Titanium Gray', 38000000, 7, 'Trí tuệ nhân tạo Galaxy AI, viền Titanium', 2),

    -- 5 Sản phẩm Xiaomi (category_id = 3)
    ('Redmi Note 12', 'Xiaomi', '128GB', 'Blue', 4500000, 40, 'Điện thoại quốc dân, giá rẻ cấu hình cao', 3),
    ('Redmi Note 13 Pro+', 'Xiaomi', '256GB', 'Aurora Purple', 8500000, 25, 'Màn hình cong, camera 200MP siêu nét', 3),
    ('Poco F5 Pro', 'Xiaomi', '256GB', 'Black', 11500000, 15, 'Cấu hình gaming siêu mạnh mẽ', 3),
    ('Xiaomi 13', 'Xiaomi', '256GB', 'White', 16000000, 10, 'Thiết kế vuông vức, camera hợp tác Leica', 3),
    ('Xiaomi 14 Ultra', 'Xiaomi', '512GB', 'Black', 27000000, 6, 'Quái vật nhiếp ảnh, phụ kiện camera chuyên nghiệp', 3);

-- Set quyền ADMIN (Giữ nguyên lệnh của bạn)
UPDATE users SET role = 'ADMIN' WHERE email = 'manhadmin@gmail.com';