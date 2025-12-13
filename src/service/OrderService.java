package service;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import util.AuditLogger;
import util.SessionManager;

/**
 * Order Service - Database Operations
 * Handles all database operations for orders.
 */
public class OrderService {

    /**
     * Gets all orders from database.
     */
    public static ArrayList<String[]> getAllOrders() {
        ArrayList<String[]> orders = new ArrayList<>();
        String sql = "SELECT order_id, order_details FROM orders ORDER BY order_id";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new String[] {
                        String.valueOf(rs.getInt("order_id")),
                        rs.getString("order_details")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error loading orders: " + e.getMessage());
        }

        return orders;
    }

    /**
     * Adds a new order.
     */
    public static void addOrder(int orderId, String orderDetails) throws SQLException {
        if (orderDetails == null || orderDetails.trim().isEmpty()) {
            throw new IllegalArgumentException("Order details cannot be empty");
        }

        String sql = "INSERT INTO orders (order_id, order_details) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.setString(2, orderDetails);
            pstmt.executeUpdate();
            AuditLogger.logf("Order created: ID=%d by \"%s\"",
                    orderId, SessionManager.getCurrentUsername());
        }
    }

    /**
     * Deletes an order by ID.
     */
    public static boolean deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            boolean deleted = pstmt.executeUpdate() > 0;
            if (deleted) {
                AuditLogger.logf("Order deleted: ID=%d by \"%s\"",
                        orderId, SessionManager.getCurrentUsername());
            }
            return deleted;
        }
    }

    /**
     * Gets an order by ID.
     */
    public static String[] getOrderById(int orderId) {
        String sql = "SELECT order_id, order_details FROM orders WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new String[] {
                            String.valueOf(rs.getInt("order_id")),
                            rs.getString("order_details")
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting order: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets next available order ID.
     */
    public static int getNextOrderId() {
        String sql = "SELECT COALESCE(MAX(order_id), 0) + 1 AS next_id FROM orders";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("next_id");
            }
        } catch (SQLException e) {
            System.err.println("Error getting next order ID: " + e.getMessage());
        }
        return 1;
    }
}
