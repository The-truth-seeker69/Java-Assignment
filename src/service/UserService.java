package service;

import database.DatabaseConnection;
import model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserService class for handling user authentication from database.
 * Implements password encryption using SHA-256 hashing.
 * 
 * @author Software Maintenance Team
 */
public class UserService {

    /**
     * Authenticates a user by verifying username and password from database.
     * Password is hashed using SHA-256 and compared with stored hash.
     * 
     * @param username The username to authenticate
     * @param password The plain text password to verify
     * @return User object if authentication successful, null otherwise
     */
    public static User authenticate(String username, String password) {
        String sql = "SELECT username, password FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedUsername = rs.getString("username");
                String storedPasswordHash = rs.getString("password");

                // Hash the input password and compare with stored hash
                String inputPasswordHash = hashPassword(password);

                if (inputPasswordHash != null && inputPasswordHash.equals(storedPasswordHash)) {
                    // Authentication successful
                    return new User(storedUsername, storedPasswordHash);
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error during authentication: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Authentication failed
    }

    /**
     * Hashes a password using SHA-256 algorithm.
     * 
     * @param password The plain text password to hash
     * @return The hashed password as hexadecimal string, or null if hashing fails
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convert bytes to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not available: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Utility method to generate hashed password for testing purposes.
     * This can be used to generate password hashes for inserting into database.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Example usage: generate password hashes
        String[] passwords = { "admin123", "superadmin", "manager2024", "sysadmin" };

        System.out.println("Generated Password Hashes:");
        System.out.println("===========================");
        for (String pwd : passwords) {
            System.out.println("Password: " + pwd);
            System.out.println("Hash: " + hashPassword(pwd));
            System.out.println();
        }
    }
}
