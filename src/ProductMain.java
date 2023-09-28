
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Wei Quan
 */
public class ProductMain {

    static ArrayList<Double> productsPriceArr = Product.getProductsPriceArr();
    static ArrayList<String> productsArr = Product.getProductsArr();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        String productName;
        Double productPrice;
        Scanner scan = new Scanner(System.in);
        char cont;
        char confirmAdd;
        int itemNum = 1;
        char continueProduct;

        ProductModule productmodule = new ProductModule();
        int choice = -1;

        char contDlt;
        char contMod;

        do {

            System.out.println("\n\n Product Menu");
            System.out.println("------------------");
            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Delete Products");
            System.out.println("4. Modify Products");
            System.out.println("0. Back");
            System.out.print("Enter Your Option: ");

            while (true) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();  // clear the newline character
                    if (choice >= 0 && choice <= 4) {
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

            switch (choice) {
                case 1: {
                    do {
                        System.out.print("Enter the product name you wanna add: ");
                        productName = scan.nextLine();
                        productName = productName.substring(0, 1).toUpperCase() + productName.substring(1).toLowerCase();
                        while (true) {
                            System.out.print("Enter the product price : ");
                            if (scan.hasNextInt()) {
                                productPrice = scan.nextDouble();
                                scan.nextLine();
                                break;
                            } else {
                                scan.next();
                                System.out.println("Invalid input. Please enter a valid number.");
                            }
                        }

                        while (true) {
                            System.out.print("Confirm add product? (Y/N): ");
                            System.out.print("");
                            confirmAdd = scan.next().charAt(0);

                            if (confirmAdd == 'Y' || confirmAdd == 'y') {
                                productmodule.addProduct(productName, productPrice);
                                break; // Exit the loop when valid input
                            } else if (confirmAdd == 'N' || confirmAdd == 'n') {
                                break;
                            } else {
                                productmodule.invalidMessage();
                            }
                        }

                        while (true) {
                            System.out.print("Do you still want to continue adding products? (Y/N): ");
                            cont = scan.next().charAt(0);
                            scan.nextLine();

                            if (cont == 'Y' || cont == 'y' || cont == 'N' || cont == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }

                    } while (cont != 'N' && cont != 'n');
                    break;
                }

                case 2: {
                    System.out.printf(" %-15s %-15s %-30s \n", "Product Code ", "ProductName", "Product Price(RM)");
                    productmodule.productsMenu();
                    break;
                }
                case 3: {

                    do {
                        productmodule.deleteProduct();
                        while (true) {
                            System.out.print("Do you still wanna continue delete item? (Y/N): ");
                            contDlt = scan.next().charAt(0);
                            scan.nextLine();

                            if (contDlt == 'Y' || contDlt == 'y' || contDlt == 'N' || contDlt == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contDlt != 'N' && contDlt != 'n');
                    break;
                }

                case 4: {
                    do {
                        productmodule.modifyProduct();
                        while (true) {
                            System.out.print("Do you still wanna continue modify item? (Y/N): ");
                            contMod = scan.next().charAt(0);
                            scan.nextLine();

                            if (contMod == 'Y' || contMod == 'y' || contMod == 'N' || contMod == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contMod != 'N' && contMod != 'n');
                    break;
                }

                case 0: {
                    break;
                }
            }

            if (choice == 0) {
                break;
            }

            System.out.print("Do you want to continue to view the Product's Module? (Y/N): ");
            continueProduct = scanner.next().charAt(0);
            scanner.nextLine();

            while (continueProduct != 'Y' && continueProduct != 'y' && continueProduct != 'N' && continueProduct != 'n') {
                System.out.println("Invalid input, Please Try Again.");
                System.out.print("Do you want to continue to view the Product's Module? (Y/N): ");
                continueProduct = scanner.next().charAt(0);
                scanner.nextLine();
            }

        } while (continueProduct != 'n' && continueProduct != 'N');
        System.out.println("Thank you for viewing the product module, Redirecting you back out to the main menu.");
    }
}
