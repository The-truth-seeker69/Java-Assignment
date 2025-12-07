package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Wei Quan
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import model.Product;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Wei Quan
 */
public class ProductModule {

    static ArrayList<String> productsArr = Product.getProductsArr();
    static ArrayList<Double> productsPriceArr = Product.getProductsPriceArr();

    String productName;
    Double productPrice;
    Scanner scan = new Scanner(System.in);
    char cont;
    char confirmAdd;

    public void invalidMessage() {
        System.out.print("Invalid Option\n");
    }

    public static int initializeProducts() {
        int count = 0;
        try {
            File products = new File("productName.txt");
            File productsPrice = new File("productPrice.txt");
            Scanner productsReader = new Scanner(products);
            Scanner productsPriceReader = new Scanner(productsPrice);

            productsArr.clear();  // Clear the arrays before populating them again
            productsPriceArr.clear();

            while (productsReader.hasNextLine() && productsPriceReader.hasNextLine()) {
                String productName = productsReader.nextLine();
                Double productPrice = Double.parseDouble(productsPriceReader.nextLine());
                productsArr.add(productName);
                productsPriceArr.add(productPrice);
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while initializing products.");
            e.printStackTrace();
        }

        return count;
    }

    public void addProduct(String newProductstr, double newProductPrice) throws Exception {

        try {
            FileWriter productNameFile = new FileWriter("productName.txt", true);
            FileWriter productPriceFile = new FileWriter("productPrice.txt", true);
            BufferedWriter writerName = new BufferedWriter(productNameFile);
            BufferedWriter writerPrice = new BufferedWriter(productPriceFile);
            writerName.write(newProductstr + "\n");
            writerPrice.write(newProductPrice + "\n");
            writerName.close();
            writerPrice.close();
            // Exit the inner loop when valid input
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void productsMenu() {
        try {
            File products = new File("productName.txt");
            File productsPrice = new File("productPrice.txt");
            Scanner productsReader = new Scanner(products);
            Scanner productsPriceReader = new Scanner(productsPrice);

            int itemNumber = 1;
            while (productsReader.hasNextLine()) {
                while (productsPriceReader.hasNextLine()) {
                    String product = productsReader.nextLine();
                    Double productPrice = Double.parseDouble(productsPriceReader.nextLine());
                    System.out.printf(" %-15d %-15s %-30s \n", itemNumber, product, productPrice);

                    itemNumber++;
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void deleteProduct() throws Exception {

        Scanner scan = new Scanner(System.in);
        boolean itemFound = false;
        String dltProductName;
        char confirmDlt;
        try {
            File products = new File("productName.txt");
            File productsPrice = new File("productPrice.txt");
            Scanner productsReader = new Scanner(products);
            Scanner productsPriceReader = new Scanner(productsPrice);

            productsArr.clear();  // Clear the arrays before populating them again
            productsPriceArr.clear();
            //loop through the text file and store them at each array
            while (productsReader.hasNextLine() && productsPriceReader.hasNextLine()) {

                productName = productsReader.nextLine();
                productPrice = Double.parseDouble(productsPriceReader.nextLine());

                productsArr.add(productName);
                productsPriceArr.add(productPrice);

            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();

        }
        productsMenu();
        System.out.print("Enter the product NAME that you want to delete : ");
        dltProductName = scan.nextLine();

        //  get the index of item that want to delete
        int indexOfItemToRemove = productsArr.indexOf(dltProductName);

        if (indexOfItemToRemove != -1) {
            while (true) {
                System.out.print("Confirm delete product? (Y/N): ");
                confirmDlt = scan.next().charAt(0);
                if (confirmDlt == 'Y' || confirmDlt == 'y') {
                    if (indexOfItemToRemove >= 0 && indexOfItemToRemove < productsArr.size()) {

                        productsArr.remove(indexOfItemToRemove);
                        productsPriceArr.remove(indexOfItemToRemove);
                        itemFound = true; // Set the flag to true since the item was found and removed
                        break; // Exit the inner while loop
                    } else {
                        System.out.println("Invalid index detected. The item may have been removed by another process.");
                    }

                } else if (confirmDlt == 'N' || confirmDlt == 'n') {
                    break; // Exit the inner while loop
                } else {
                    invalidMessage();
                }
            }
        } else {
            System.out.println("Item not found in the ArrayList.");
        }
        if (itemFound) { // Only update the files if the item was found and removed
            FileWriter productNameFile = new FileWriter("productName.txt");
            FileWriter productPriceFile = new FileWriter("productPrice.txt");
            BufferedWriter writerName = new BufferedWriter(productNameFile);
            BufferedWriter writerPrice = new BufferedWriter(productPriceFile);

            for (int i = 0; i < productsArr.size(); i++) {
                // Loop through the array and update them inside the text file
                writerName.write(productsArr.get(i) + "\n");
                writerPrice.write(productsPriceArr.get(i) + "\n");
            }
            writerName.close();
            writerPrice.close();
            System.out.println("Item delete successfully");
        }

    }

    public void modifyProduct() throws Exception {
        String newProductName;
        Double newProductPrice;
        Scanner scan = new Scanner(System.in);
        char confirmMod;
        boolean itemFound = false;

        try {
            File products = new File("productName.txt");
            File productsPrice = new File("productPrice.txt");
            Scanner productsReader = new Scanner(products);
            Scanner productsPriceReader = new Scanner(productsPrice);

            productsArr.clear();  // Clear the arrays before populating them again
            productsPriceArr.clear();
            //loop through the text file and store them at each array
            while (productsReader.hasNextLine() && productsPriceReader.hasNextLine()) {

                productName = productsReader.nextLine();
                productPrice = Double.parseDouble(productsPriceReader.nextLine());

                productsArr.add(productName);
                productsPriceArr.add(productPrice);

            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();

        }
//ask product name then check if the product exist inside the array
//if no prompt error and ask again
// if yes loop through the array and look for the product's index
// ask to enter new product name and price
//ask and double confirm again
// update the array
// loop through the array then write into text file

        productsMenu();
        System.out.print("Enter the product NAME that you want to modify : ");
        productName = scan.nextLine();

        int indexOfItemToModify = productsArr.indexOf(productName);

        if (indexOfItemToModify != -1) {
            while (true) {
                System.out.print("Enter new product name to replace " + productsArr.get(indexOfItemToModify) + ": ");
                newProductName = scan.nextLine();

                while (true) {
                    System.out.print("Enter new product price to replace " + productsPriceArr.get(indexOfItemToModify) + ": ");
                    if (scan.hasNextInt()) {
                        newProductPrice = scan.nextDouble();
                        scan.nextLine();
                        break;
                    } else {
                        scan.next();
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }

                while (true) {

                    System.out.print("Confirm modify? (Y/N): ");
                    confirmMod = scan.next().charAt(0);

                    if (confirmMod == 'Y' || confirmMod == 'y') {
                        productsArr.set(indexOfItemToModify, newProductName);
                        productsPriceArr.set(indexOfItemToModify, newProductPrice);
                        itemFound = true;
                        break;
                    } else if (confirmMod == 'N' || confirmMod == 'n') {
                        break; // Exit the inner while loop
                    } else {
                        invalidMessage();
                    }
                }
                break;
            }
        } else {
            System.out.println("Item not found in the ArrayList.");
        }

        if (itemFound) { // Only update the files if the item was found and removed
            FileWriter productNameFile = new FileWriter("productName.txt");
            FileWriter productPriceFile = new FileWriter("productPrice.txt");
            BufferedWriter writerName = new BufferedWriter(productNameFile);
            BufferedWriter writerPrice = new BufferedWriter(productPriceFile);

            for (int i = 0; i < productsArr.size(); i++) {
                // Loop through the array and update them inside the text file
                writerName.write(productsArr.get(i) + "\n");
                writerPrice.write(productsPriceArr.get(i) + "\n");
            }
            writerName.close();
            writerPrice.close();
            System.out.print("\nItem modified successfully\n");
        }

    }
}
