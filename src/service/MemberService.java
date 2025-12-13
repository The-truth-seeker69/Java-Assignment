package service;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import util.AuditLogger;
import util.SessionManager;

/**
 * Member Service - Database Operations
 * Handles all database operations for members.
 */
public class MemberService {

    /**
     * Gets all members from database.
     */
    public static ArrayList<String[]> getAllMembers() {
        ArrayList<String[]> members = new ArrayList<>();
        String sql = "SELECT member_id, name, gender, age FROM members ORDER BY member_id";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                members.add(new String[] {
                        String.valueOf(rs.getInt("member_id")),
                        rs.getString("name"),
                        rs.getString("gender"),
                        String.valueOf(rs.getInt("age"))
                });
            }
        } catch (SQLException e) {
            System.err.println("Error loading members: " + e.getMessage());
        }

        return members;
    }

    /**
     * Adds a new member.
     */
    public static void addMember(int memberId, String memberName, char gender, int age) throws SQLException {
        if (memberName == null || memberName.trim().isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be empty");
        }
        if (gender != 'M' && gender != 'F') {
            throw new IllegalArgumentException("Gender must be 'M' or 'F'");
        }
        if (age < 18 || age > 90) {
            throw new IllegalArgumentException("Age must be between 18 and 90");
        }

        String sql = "INSERT INTO members (member_id, name, gender, age) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.setString(2, memberName.trim());
            pstmt.setString(3, String.valueOf(gender));
            pstmt.setInt(4, age);
            pstmt.executeUpdate();
            AuditLogger.logf("Member added: ID=%d, Name=%s by \"%s\"",
                    memberId, memberName.trim(), SessionManager.getCurrentUsername());
        }
    }

    /**
     * Deletes a member by ID.
     */
    public static void deleteMemberById(int memberId) throws SQLException {
        String sql = "DELETE FROM members WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.executeUpdate();
            AuditLogger.logf("Member deleted: ID=%d by \"%s\"",
                    memberId, SessionManager.getCurrentUsername());
        }
    }

    /**
     * Deletes a member (backward compatibility).
     */
    public static void deleteMember(ArrayList<String[]> memberList, int deleteIndex) throws SQLException {
        if (deleteIndex < 0 || deleteIndex >= memberList.size()) {
            throw new IllegalArgumentException("Invalid delete index");
        }
        int memberId = Integer.parseInt(memberList.get(deleteIndex)[0]);
        deleteMemberById(memberId);
    }

    /**
     * Updates a single member with detailed logging.
     * Logs specific changes made to the member.
     */
    public static void updateMember(int memberId, String oldName, String newName,
            char oldGender, char newGender, int oldAge, int newAge) throws SQLException {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be empty");
        }
        if (newGender != 'M' && newGender != 'F') {
            throw new IllegalArgumentException("Gender must be 'M' or 'F'");
        }
        if (newAge < 18 || newAge > 90) {
            throw new IllegalArgumentException("Age must be between 18 and 90");
        }

        String sql = "UPDATE members SET name = ?, gender = ?, age = ? WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName.trim());
            pstmt.setString(2, String.valueOf(newGender));
            pstmt.setInt(3, newAge);
            pstmt.setInt(4, memberId);

            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                // Build detailed log message
                StringBuilder logMsg = new StringBuilder();
                logMsg.append(String.format("Member modified: ID=%d", memberId));

                // Log name change
                if (!oldName.equals(newName)) {
                    logMsg.append(String.format(", Name: %s -> %s", oldName, newName));
                }

                // Log gender change
                if (oldGender != newGender) {
                    logMsg.append(String.format(", Gender: %c -> %c", oldGender, newGender));
                }

                // Log age change
                if (oldAge != newAge) {
                    logMsg.append(String.format(", Age: %d -> %d", oldAge, newAge));
                }

                logMsg.append(String.format(" by \"%s\"", SessionManager.getCurrentUsername()));
                AuditLogger.log(logMsg.toString());
            }
        }
    }

    /**
     * Updates all members (used for modify operations).
     */
    public static void updateMembers(ArrayList<String[]> memberList) throws SQLException {
        String deleteSql = "DELETE FROM members";
        String insertSql = "INSERT INTO members (member_id, name, gender, age) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Delete all
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(deleteSql);
                }
                // Insert updated
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    for (String[] member : memberList) {
                        pstmt.setInt(1, Integer.parseInt(member[0]));
                        pstmt.setString(2, member[1]);
                        pstmt.setString(3, member[2]);
                        pstmt.setInt(4, Integer.parseInt(member[3]));
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
                conn.commit();
                AuditLogger.logf("Member data updated by \"%s\"", SessionManager.getCurrentUsername());
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    /**
     * Checks if member ID exists.
     */
    public static boolean isMember(int memberId) {
        String sql = "SELECT COUNT(*) FROM members WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking member: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets member name by ID.
     */
    public static String getMemberName(int memberId) {
        String sql = "SELECT name FROM members WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting member name: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets next available member ID.
     */
    public static int getNextMemberId() {
        String sql = "SELECT COALESCE(MAX(member_id), 999) + 1 AS next_id FROM members";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return Math.max(rs.getInt("next_id"), 1000);
            }
        } catch (SQLException e) {
            System.err.println("Error getting next member ID: " + e.getMessage());
        }
        return 1000;
    }
}
