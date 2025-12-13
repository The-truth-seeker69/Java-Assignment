package util;

import model.User;

/**
 * Session Manager
 * Manages the current logged-in user session.
 * Provides access to current user information for audit logging.
 */
public class SessionManager {

    private static User currentUser = null;

    /**
     * Sets the current logged-in user.
     * 
     * @param user The authenticated user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Gets the current logged-in user.
     * 
     * @return The current user, or null if not logged in
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Gets the username of the current logged-in user.
     * 
     * @return The username, or "System" if not logged in
     */
    public static String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : "System";
    }

    /**
     * Clears the current user session (logout).
     */
    public static void clearSession() {
        currentUser = null;
    }

    /**
     * Checks if a user is currently logged in.
     * 
     * @return true if user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
