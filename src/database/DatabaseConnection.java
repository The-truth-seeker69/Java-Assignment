package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Manager
 * 
 * Centralized database connection management for the POS system.
 * Provides a single point of access for database connections.
 * 
 * Configuration:
 * - Update the constants below with your MySQL database credentials
 * - Ensure MySQL JDBC driver is in your classpath
 * 
 * @author Software Maintenance Team
 */
public class DatabaseConnection {
    
    // Database configuration - UPDATE THESE VALUES
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tarumt_pos_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Gets a database connection.
     * 
     * This method creates and returns a new database connection.
     * The caller is responsible for closing the connection when done.
     * 
     * @return A Connection object to the database
     * @throws SQLException If database connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName(DB_DRIVER);
            
            // Create and return connection
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Please add mysql-connector-java to your classpath.", e);
        }
    }
    
    /**
     * Tests the database connection.
     * Useful for verifying database configuration.
     * 
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}




