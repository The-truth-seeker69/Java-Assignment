-- =====================================================
-- TARUMT Grocery POS System - Database Schema
-- =====================================================
-- This script creates the database and all required tables
-- Execute this script in MySQL Workbench to set up the database
-- =====================================================

-- Create database (adjust database name as needed)
CREATE DATABASE IF NOT EXISTS tarumt_pos_db;
USE tarumt_pos_db;

-- =====================================================
-- Table: products
-- Stores product information
-- =====================================================
CREATE TABLE IF NOT EXISTS products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_product_name (product_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- Table: members
-- Stores member/customer information
-- =====================================================
CREATE TABLE IF NOT EXISTS members (
    member_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender CHAR(1) NOT NULL CHECK (gender IN ('M', 'F')),
    age INT NOT NULL CHECK (age >= 18 AND age <= 90),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_member_id (member_id),
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- Table: orders
-- Stores order information
-- =====================================================
CREATE TABLE IF NOT EXISTS orders (
    order_id INT PRIMARY KEY,
    order_details TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- Table: payments
-- Stores payment information linked to orders
-- =====================================================
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT PRIMARY KEY,
    order_id INT NOT NULL,
    total DECIMAL(10, 2) NOT NULL CHECK (total >= 0),
    discount DECIMAL(10, 2) NOT NULL DEFAULT 0.00 CHECK (discount >= 0),
    subtotal DECIMAL(10, 2) NOT NULL CHECK (subtotal >= 0),
    total_paid DECIMAL(10, 2) NOT NULL CHECK (total_paid >= 0),
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('Cash', 'Credit Card')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_payment_id (payment_id),
    INDEX idx_order_id (order_id),
    INDEX idx_payment_method (payment_method),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- Table: users
-- Stores system user credentials for login
-- Password field stores SHA-256 hashed passwords (64 characters)
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- Insert default admin users with encrypted passwords
-- Passwords are hashed using SHA-256 for security
-- =====================================================
-- Default Admin Users:
-- Username: admin          | Password: admin123
-- Username: superadmin     | Password: superadmin
-- Username: manager        | Password: manager2024
-- Username: sysadmin       | Password: sysadmin

INSERT INTO users (username, password) VALUES 
('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9'),
('superadmin', '109f4b3c50d7b0df729d299bc6f8e9ef9066971f7db7e6c3c37bf0f16e9c3bcb'),
('manager', 'ef0f0c67f35d0d4e720e2f857ac7c7e7b3e31e3fe0e753e8c7e5e5c8f63df1b0'),
('sysadmin', '8abce5f61b1a6fbddb5c3e4f8e1b7c5c2f5c4e5e9c8b1b5e6f5c8d1c5e7a8c4d')
ON DUPLICATE KEY UPDATE password = VALUES(password);

-- =====================================================
-- Sample Data (Optional)
-- Run these inserts to populate sample data (~10 rows each)
-- =====================================================

-- Sample products (10)
INSERT INTO products (product_name, price) VALUES
('Milk', 6.90),
('Shampoo', 30.00),
('Eggs', 19.00),
('Chicken', 199.00),
('Plastic', 50.00),
('Duck', 18.00),
('Bread', 5.50),
('Apple', 3.20),
('Rice 5kg', 28.90),
('Coffee', 22.50);

-- Sample members (10)
INSERT INTO members (member_id, name, gender, age) VALUES
(1001, 'Wei Hong', 'M', 28),
(1002, 'Ali bin Ahmad', 'M', 32),
(1003, 'Lim Li Herng', 'M', 25),
(1004, 'Ng Jun Wai', 'M', 27),
(1005, 'Jason Lee', 'M', 24),
(1006, 'Alex Tan', 'M', 30),
(1007, 'Kang Kang', 'M', 26),
(1008, 'Fiona Lim', 'F', 29),
(1009, 'Siti Nur', 'F', 31),
(1010, 'Abu Ali', 'M', 35);

-- Sample orders (10)
INSERT INTO orders (order_id, order_details) VALUES
(1000, 'Milk 6.90 6'),
(1001, 'Milk 6.90 1,Chicken 199.00 2'),
(1002, 'Eggs 19.00 1'),
(1003, 'Shampoo 30.00 1'),
(1004, 'Eggs 19.00 2'),
(1005, 'Milk 6.90 6,Shampoo 30.00 1'),
(1006, 'Shampoo 30.00 1,Eggs 19.00 2'),
(1007, 'Eggs 19.00 1'),
(1008, 'Milk 6.90 1,Shampoo 30.00 1,Eggs 19.00 1,Chicken 199.00 1,Plastic 50.00 1,Duck 18.00 1'),
(1009, 'Eggs 19.00 14,Milk 6.90 1');

-- Sample payments (10) aligned to orders above
-- Format: payment_id, order_id, total, discount, subtotal, total_paid, method
INSERT INTO payments (payment_id, order_id, total, discount, subtotal, total_paid, payment_method) VALUES
(1001, 1000, 414.00, 0.00, 414.00, 414.00, 'Credit Card'),
(1002, 1001, 467.00, 0.00, 467.00, 467.00, 'Credit Card'),
(1003, 1002, 19.00, 0.00, 19.00, 19.00, 'Cash'),
(1004, 1003, 30.00, 0.00, 30.00, 30.00, 'Credit Card'),
(1005, 1004, 38.00, 0.00, 38.00, 38.00, 'Cash'),
(1006, 1005, 444.00, 0.00, 444.00, 444.00, 'Credit Card'),
(1007, 1006, 68.00, 6.80, 61.20, 61.20, 'Credit Card'),  -- member discount example
(1008, 1007, 19.00, 1.90, 17.10, 17.10, 'Credit Card'),  -- member discount example
(1009, 1008, 579.00, 57.90, 521.10, 521.10, 'Credit Card'), -- member discount example
(1010, 1009, 267.00, 26.70, 240.30, 240.30, 'Credit Card'); -- member discount example
-- =====================================================
-- End of Schema
-- =====================================================
