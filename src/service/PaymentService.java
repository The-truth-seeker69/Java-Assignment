package service;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import util.AuditLogger;
import util.SessionManager;

/**
 * Payment Service - Database Operations
 * Handles all database operations for payments.
 */
public class PaymentService {

    /**
     * Gets all payments from database.
     */
    public static ArrayList<String[]> getAllPayments() {
        ArrayList<String[]> payments = new ArrayList<>();
        String sql = "SELECT payment_id, order_id, total, discount, total_paid, payment_method " +
                "FROM payments ORDER BY payment_id";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                payments.add(new String[] {
                        String.valueOf(rs.getInt("payment_id")),
                        String.valueOf(rs.getInt("order_id")),
                        String.valueOf(rs.getDouble("total")),
                        String.valueOf(rs.getDouble("discount")),
                        String.valueOf(rs.getDouble("total_paid")),
                        rs.getString("payment_method")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error loading payments: " + e.getMessage());
        }

        return payments;
    }

    /**
     * Adds a new payment.
     */
    public static void addPayment(int paymentId, int orderId, double total, double discount,
            double subtotal, double totalPaid, String paymentMethod) throws SQLException {
        if (total < 0 || discount < 0 || subtotal < 0 || totalPaid < 0) {
            throw new IllegalArgumentException("Payment amounts cannot be negative");
        }
        if (!paymentMethod.equals("Cash") && !paymentMethod.equals("Credit Card")) {
            throw new IllegalArgumentException("Payment method must be 'Cash' or 'Credit Card'");
        }

        String sql = "INSERT INTO payments (payment_id, order_id, total, discount, subtotal, total_paid, payment_method) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            pstmt.setInt(2, orderId);
            pstmt.setDouble(3, total);
            pstmt.setDouble(4, discount);
            pstmt.setDouble(5, subtotal);
            pstmt.setDouble(6, totalPaid);
            pstmt.setString(7, paymentMethod);
            pstmt.executeUpdate();
            AuditLogger.logf("Payment recorded: ID=%d, Order ID=%d, Amount: %.2f, Method: %s by \"%s\"",
                    paymentId, orderId, totalPaid, paymentMethod, SessionManager.getCurrentUsername());
        }
    }

    /**
     * Adds a payment (backward compatibility).
     */
    public static void addPayment(int paymentId, double amount, String paymentMethod) throws SQLException {
        addPayment(paymentId, 0, amount, 0.0, amount, amount, paymentMethod);
    }

    /**
     * Deletes a payment by ID.
     */
    public static void deletePaymentById(int paymentId) throws SQLException {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            pstmt.executeUpdate();
            AuditLogger.logf("Payment deleted: ID=%d by \"%s\"",
                    paymentId, SessionManager.getCurrentUsername());
        }
    }

    /**
     * Deletes a payment (backward compatibility).
     */
    public static void deletePayment(ArrayList<String[]> paymentList, int deleteIndex) throws SQLException {
        if (deleteIndex < 0 || deleteIndex >= paymentList.size()) {
            throw new IllegalArgumentException("Invalid delete index");
        }
        int paymentId = Integer.parseInt(paymentList.get(deleteIndex)[0]);
        deletePaymentById(paymentId);
    }

    /**
     * Gets a payment by ID.
     */
    public static String[] getPaymentById(int paymentId) {
        String sql = "SELECT payment_id, order_id, total, discount, total_paid, payment_method " +
                "FROM payments WHERE payment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new String[] {
                            String.valueOf(rs.getInt("payment_id")),
                            String.valueOf(rs.getInt("order_id")),
                            String.valueOf(rs.getDouble("total")),
                            String.valueOf(rs.getDouble("discount")),
                            String.valueOf(rs.getDouble("total_paid")),
                            rs.getString("payment_method")
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting payment: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets a payment by order ID.
     * 
     * @param orderId The order ID to search for
     * @return Payment data array, or null if not found
     */
    public static String[] getPaymentByOrderId(int orderId) {
        String sql = "SELECT payment_id, order_id, total, discount, total_paid, payment_method " +
                "FROM payments WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new String[] {
                            String.valueOf(rs.getInt("payment_id")),
                            String.valueOf(rs.getInt("order_id")),
                            String.valueOf(rs.getDouble("total")),
                            String.valueOf(rs.getDouble("discount")),
                            String.valueOf(rs.getDouble("total_paid")),
                            rs.getString("payment_method")
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting payment by order ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets next available payment ID.
     */
    public static int getNextPaymentId() {
        String sql = "SELECT COALESCE(MAX(payment_id), 0) + 1 AS next_id FROM payments";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("next_id");
            }
        } catch (SQLException e) {
            System.err.println("Error getting next payment ID: " + e.getMessage());
        }
        return 1;
    }
}
