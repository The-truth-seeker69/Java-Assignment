package service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Validations {
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

        // Additional custom rules can be added here based on your requirements

        return true;
    }
    
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
