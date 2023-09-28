/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.util.*;

/**
 *
 * @author Yap Kah Seng
 */
public class LoginPage {

    public static void main(String[] args) {
        String usernameInput;
        String passwordInput;
        Scanner scanner = new Scanner(System.in);
        int menuChoice = -1;
        char continueMenu = 0;

        User defaultUser = new User("admin", "admin");

        System.out.print("Enter Username: ");
        usernameInput = scanner.nextLine();

        System.out.print("Enter PassWord: ");
        passwordInput = scanner.nextLine();

        while (!defaultUser.equalsUsername(usernameInput) || !defaultUser.equalsPassword(passwordInput)) {
            System.out.println("Invalid Login Credentials. Please try again.\n");
            System.out.print("Enter Username: ");
            usernameInput = scanner.nextLine();

            System.out.print("Enter PassWord: ");
            passwordInput = scanner.nextLine();
            
            if(defaultUser.equalsUsername(usernameInput) && defaultUser.equalsPassword(passwordInput))
                break;
        }

        System.out.println("Successfully Logged In.");
        
        do {
            System.out.println("\nWhat category do you wanna view?");
            System.out.println("1. Order\n"
                    + "2. Product \n"
                    + "3. Member\n"
                    + "0. Logout\n");
            System.out.print("Enter your option: ");
            while (true) {
                try {
                    menuChoice = scanner.nextInt();
                    scanner.nextLine();  // clear the newline character
                    if (menuChoice >= 0 && menuChoice <= 3) {
                        break;
                    } else {
                        System.out.println("\nInvalid input. Please enter a valid option.");
                        System.out.print("Enter Your Option: ");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a valid option.");
                    scanner.next();  // Clear the invalid input
                    System.out.print("Enter Your Option: ");
                }
            }

            switch (menuChoice) {
                case 1: {
                    System.out.println("1");
                    break;
                }

                case 2: {
                    System.out.println("2");
                    break;
                }

                case 3: {
                    System.out.println("3");
                    break;
                }

                case 0: {
                    System.out.println("Ciao");
                    break;
                }
            }
            
            if (menuChoice == 0) {
                break;
            }
            
            System.out.print("Do you want to continue to view the other Modules? (Y/N): ");
            continueMenu = scanner.next().charAt(0);
            scanner.nextLine();

            while (continueMenu != 'Y' && continueMenu != 'y' && continueMenu != 'N' && continueMenu != 'n') {
                System.out.println("Invalid input, Please Try Again.");
                System.out.print("Do you want to continue to view the other Modules? (Y/N): ");
                continueMenu = scanner.next().charAt(0);
                scanner.nextLine();
            }

        } while (continueMenu == 'Y' || continueMenu == 'y');
        System.out.println("Thanks for using XXX Grocery POS System.");
        System.out.println("Hope to see you next time.");
    }
}
