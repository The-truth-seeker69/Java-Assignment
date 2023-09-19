/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import java.util.Scanner;
/**
 *
 * @author Yap Kah Seng
 */
public class LoginPage {

    private static String username = "Gavin Yap";
    private static String password = "123";
    private static boolean isLoggedIn = false;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!isLoggedIn) {
                System.out.println("Welcome to the Login Page ");
                System.out.print("Username: ");
                String inputUsername = scanner.nextLine();
                System.out.print("Password: ");
                String inputPassword = scanner.nextLine();

                if (login(inputUsername, inputPassword)) {
                    System.out.println("Login successful!!!");
                    isLoggedIn = true;
                } else {
                    System.out.println("Login failed, Please try again.");
                }
            } else {
                System.out.println("You are logged in.");
                System.out.println("Do you want to log out? (yes/no)");
                String logoutChoice = scanner.nextLine().toLowerCase();

                if (logoutChoice.equals("yes")) {
                    logout();
                    isLoggedIn = false;
                    System.out.println("Logged out successfully.");
                } else if (logoutChoice.equals("no")) {
                    System.out.println("You are still logged in on this page.");
                } else {
                    System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                }
            }
        }
    }
    
    private static boolean login(String inputUsername, String inputPassword) {
        return inputUsername.equals(username) && inputPassword.equals(password);
    }

    private static void logout() {
        System.out.println("Logging out......");
        System.out.println("Goodbye!!!");
    }
    
    
}
    
    
 
