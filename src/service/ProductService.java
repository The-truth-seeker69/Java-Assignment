package service;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import util.AuditLogger;
import util.SessionManager;

/**
 * Product Service - Database Operations
 * Handles all database operations for products.
 */
public class ProductService {

    /**
     * Gets all products from database.
     */
    public static ArrayList<String[]> getAllProducts() {
        ArrayList<String[]> products = new ArrayList<>();
        String sql = "SELECT product_name, price FROM products ORDER BY product_id";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new String[] {
                        rs.getString("product_name"),
                        String.valueOf(rs.getDouble("price"))
                });
            }
        } catch (SQLException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }

        return products;
    }

    /**
     * Adds a new product.
     */
    public static void addProduct(String productName, double productPrice) throws SQLException {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (productPrice <= 0) {
            throw new IllegalArgumentException("Product price must be greater than 0");
        }

        String sql = "INSERT INTO products (product_name, price) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName.trim());
            pstmt.setDouble(2, productPrice);
            pstmt.executeUpdate();
            AuditLogger.logf("Product added: %s, Price: %.2f by \"%s\"",
                    productName.trim(), productPrice, SessionManager.getCurrentUsername());
        }
    }

    /**
     * Deletes a product by name.
     */
    public static boolean deleteProduct(String productName) throws SQLException {
        String sql = "DELETE FROM products WHERE product_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName);
            boolean deleted = pstmt.executeUpdate() > 0;
            if (deleted) {
                AuditLogger.logf("Product deleted: %s by \"%s\"",
                        productName, SessionManager.getCurrentUsername());
            }
            return deleted;
        }
    }

    /**
     * Modifies an existing product.
     */
    public static boolean modifyProduct(String oldProductName, String newProductName, double newProductPrice)
            throws SQLException {
        if (newProductPrice <= 0) {
            throw new IllegalArgumentException("Product price must be greater than 0");
        }

        String sql = "UPDATE products SET product_name = ?, price = ? WHERE product_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newProductName.trim());
            pstmt.setDouble(2, newProductPrice);
            pstmt.setString(3, oldProductName);
            boolean modified = pstmt.executeUpdate() > 0;
            if (modified) {
                AuditLogger.logf("Product modified: %s -> %s, Price: %.2f by \"%s\"",
                        oldProductName, newProductName.trim(), newProductPrice,
                        SessionManager.getCurrentUsername());
            }
            return modified;
        }
    }

    /**
     * Checks if product name exists (case-insensitive).
     */
    public static boolean productNameExists(String productName, String excludeName) {
        String sql = "SELECT COUNT(*) FROM products WHERE LOWER(product_name) = LOWER(?)";
        if (excludeName != null && !excludeName.isEmpty()) {
            sql += " AND LOWER(product_name) != LOWER(?)";
        }

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName);
            if (excludeName != null && !excludeName.isEmpty()) {
                pstmt.setString(2, excludeName);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking product name: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if product name exists.
     */
    public static boolean productNameExists(String productName) {
        return productNameExists(productName, null);
    }

    /**
     * Initializes products (for backward compatibility).
     */
    public static int initializeProducts() {
        return getAllProducts().size();
    }

    /**
     * Reloads products (for backward compatibility).
     */
    public static void reloadProducts() {
        // Products loaded on-demand from database
    }
}
