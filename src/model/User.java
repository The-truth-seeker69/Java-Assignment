package model;

/**
 * User model class representing a system user.
 * Stores user credentials for authentication.
 * 
 * @author Software Maintenance Team
 */
public class User {
    
    /** Username for authentication */
    private String username;
    
    /** Password for authentication */
    private String password;
    
    /**
     * Constructor for creating a User instance.
     * 
     * @param username The username
     * @param password The password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /**
     * Gets the username.
     * 
     * @return The username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Sets the username.
     * 
     * @param username The new username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Gets the password.
     * 
     * @return The password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the password.
     * 
     * @param password The new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Checks if the provided username matches this user's username.
     * 
     * @param s The username to compare
     * @return true if usernames match, false otherwise
     */
    public boolean equalsUsername(String s) {
        return this.getUsername().equals(s);
    }
    
    /**
     * Checks if the provided password matches this user's password.
     * 
     * @param s The password to compare
     * @return true if passwords match, false otherwise
     */
    public boolean equalsPassword(String s) {
        return this.getPassword().equals(s);
    }
}
