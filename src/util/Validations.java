package util;

/**
 * Validations Utility
 * 
 * Provides validation methods for user input (names, ages).
 * Ensures data integrity before storing in the database.
 * 
 * @author User
 */
public class Validations {

    /**
     * Validates a name string.
     * 
     * Rules:
     * - Must not be null or empty
     * - Length must be between 3 and 50 characters
     * - Only letters, spaces, hyphens, and apostrophes allowed
     * 
     * @param name The name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        // Check for non-empty and non-null
        if (name == null || name.isEmpty()) {
            return false;
        }

        // Check for length
        int minLength = 3;
        int maxLength = 50;
        if (name.length() < minLength || name.length() > maxLength) {
            return false;
        }

        // Check for allowed characters (letters, spaces, hyphen, apostrophe)
        if (!name.matches("^[a-zA-Z\\s'-]+$")) {
            return false;
        }

        return true;
    }

    /**
     * Validates an age value.
     * 
     * Rules:
     * - Must be non-negative
     * - Must be between 18 and 90 (valid membership age range)
     * 
     * @param age The age to validate
     * @return true if valid, false otherwise
     */

    public static boolean isValidAge(int age) {
        // Check if age is non-negative
        if (age < 0) {
            return false;
        }

        // Check if age is within a reasonable range (e.g., 0 to 150 years old)
        int minValidAge = 18;
        int maxValidAge = 90;
        if (age < minValidAge || age > maxValidAge) {
            return false;
        }

        return true;
    }
}
