import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.InputMismatchException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Wei Quan
 */
public class OrderModule {

    static Scanner scan = new Scanner(System.in);

    int productIndex;
    int prodQuantity;

    static String filePath = "order.txt";

    public static void main(String[] args) throws Exception {
        ProductModule productmodule = new ProductModule();
        char contAddOrder = 0;
        char contSearchOrder = 0;
        char contDelOrder = 0;
        int orderChoice;
        char continueOrder;
        do {
            System.out.println("Order Menu");
            System.out.println("==========");
            System.out.println("1. Add Order");
            System.out.println("2. Display Order");
            System.out.println("3. Search Order");
            System.out.println("4. Delete Order");
            System.out.println("0. Back");
            System.out.print("Enter your Option:");
            while (true) {
                try {
                    orderChoice = scan.nextInt();
                    scan.nextLine();  // clear the newline character
                    if (orderChoice >= 0 && orderChoice <= 4) {
                        break;
                    } else {
                        System.out.println("\nInvalid input. Please enter a valid option.");
                        System.out.print("Enter Your Option: ");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a valid option.");
                    scan.next();  // Clear the invalid input
                    System.out.print("Enter Your Option: ");
                }
            }

            switch (orderChoice) {
                case 1: {
                    do {
                        addOrder();
                        while (true) {
                            System.out.print("Do you still wanna continue to add an order? (Y/N): ");
                            contAddOrder = scan.next().charAt(0);
                            scan.nextLine();

                            if (contAddOrder == 'Y' || contAddOrder == 'y' || contAddOrder == 'N' || contAddOrder == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contAddOrder != 'N' && contAddOrder != 'n');
                    break;
                }

                case 2: {
                    displayOrder();
                    break;
                }

                case 3: {
                    do {
                        searchOrder();
                        while (true) {

                            System.out.print("Do you still wanna continue to search an order? (Y/N): ");
                            contSearchOrder = scan.next().charAt(0);
                            scan.nextLine();

                            if (contSearchOrder == 'Y' || contSearchOrder == 'y' || contSearchOrder == 'N' || contSearchOrder == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contSearchOrder != 'N' && contSearchOrder != 'n');
                    break;
                }

                case 4: {
                    do {
                        deleteOrder();
                        while (true) {

                            System.out.print("Do you still wanna continue to delete an order? (Y/N): ");
                            contDelOrder = scan.next().charAt(0);
                            scan.nextLine();

                            if (contDelOrder == 'Y' || contDelOrder == 'y' || contDelOrder == 'N' || contDelOrder == 'n') {
                                break; // Exit the loop when valid input
                            } else {
                                productmodule.invalidMessage();
                            }
                        }
                    } while (contDelOrder != 'N' && contDelOrder != 'n');
                    break;
                }

                case 0:
                    break;
            }

            if (orderChoice == 0) {
                break;
            }
            System.out.print("Do you want to continue to view the Order's Module? (Y/N): ");
            continueOrder = scan.next().charAt(0);
            scan.nextLine();

            while (continueOrder != 'Y' && continueOrder != 'y' && continueOrder != 'N' && continueOrder != 'n') {
                System.out.println("Invalid input, Please Try Again.");
                System.out.print("Do you want to continue to view the Order's Module? (Y/N): ");
                continueOrder = scan.next().charAt(0);
                scan.nextLine();
            }
        } while (continueOrder != 'n' && continueOrder != 'N');

        System.out.println("Thank you for viewing the order module, Redirecting you back out to the main menu.");
    }

    public static void addOrder() throws Exception {
        int orderCount = 0;
        char confirmAdd;
        char cont;
        Integer orderNo;
        int lastOrderNo;
        final double discount = 0.1;
        double discountAmt = 0;
        boolean isMember;
        double subtotal = 0;
        double total = 0;
        String joinedOrderString;
        int productIndex = 0;
        int prodQuantity = 0;
        int memberId;

        File order = new File("order.txt");
        Scanner orderReader = new Scanner(order);
        ArrayList<Integer> orderNoList = new ArrayList<>();
        String[] orderDetailsArr;
        String orderDetails;

        while (orderReader.hasNextLine()) {
            orderDetails = orderReader.nextLine();
            orderDetailsArr = orderDetails.split("_");
            String tempOrderNo = orderDetailsArr[0];
            orderNo = Integer.valueOf(tempOrderNo);
            orderNoList.add(orderNo);
        }

        int lastOrderNoElement = orderNoList.size() - 1;
        lastOrderNo = orderNoList.get(lastOrderNoElement);
        ArrayList<String> orderArr = Order.getOrderArr();

        FileWriter orderFile = new FileWriter("order.txt", true);
        BufferedWriter writerOrder = new BufferedWriter(orderFile);
        ProductModule Productmodule = new ProductModule();

        int count = Productmodule.initializeProducts();

        do {
            System.out.println("Product Menu\n==========================");
            Productmodule.productsMenu();
            while (true) {
                System.out.print("Enter the product no you want to add to the order: ");
                if (scan.hasNextInt()) {
                    productIndex = scan.nextInt();
                    scan.nextLine();

                    if (productIndex > 0 && productIndex <= count) {
                        // Valid input, break out of the loop
                        break;
                    } else {
                        System.out.println("Invalid Product ID. Please enter a valid number.");
                    }
                } else {
                    scan.next(); // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            while (true) {
                System.out.print("Enter the quantity: ");

                if (scan.hasNextInt()) {
                    prodQuantity = scan.nextInt();

                    if (prodQuantity > 0) {
                        // Valid input, break out of the loop
                        break;
                    } else {
                        System.out.println("Invalid Quantity. Please enter a valid number.");
                    }
                } else {
                    scan.next(); // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            String orderLine = Product.getProductsArr().get(productIndex - 1) + " "
                    + Product.getProductsPriceArr().get(productIndex - 1) + " "
                    + prodQuantity;
            orderArr.add(orderLine);

            joinedOrderString = String.join(",", orderArr);
            System.out.println("\nYour Order Details:");

            for (String orderArrPrint : orderArr) {
                String[] tempOrder = orderArrPrint.split(" ");

                System.out.println(tempOrder[0] + " x " + tempOrder[2] + " units.");
            }

            total += Product.getProductsPriceArr().get(productIndex - 1) * prodQuantity;
            // Write the order details to the text file

            while (true) {
                System.out.print("\nContinue adding to the order? (Y/N): ");
                cont = scan.next().charAt(0);

                if (cont == 'Y' || cont == 'y') {
                    // Increment orderNo if the user continues to add to the order
                    break;
                } else if (cont == 'N' || cont == 'n') {
                    break;
                }
                System.out.println("Invalid Option.");
            }

        } while (cont != 'N' && cont != 'n');
        while (true) {
            System.out.print("Are you a Member? (Y/N): ");
            cont = scan.next().charAt(0);

            if (cont == 'Y' || cont == 'y') {

                while (true) {
                    System.out.print("Enter your member ID: ");
                    if (scan.hasNextInt()) {
                        memberId = scan.nextInt();
                        scan.nextLine();
                        break;
                    } else {
                        scan.next();
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }

                isMember = Member.isMember(memberId);
                if (isMember) {
                    System.out.println("Member " + memberId + " is a member of our company.");
                    System.out.println("10% Discount is provided.");

                    discountAmt = total * discount;

                } else {
                    System.out.println("Sorry, Unfortunately Member " + memberId + " is not a member of our company.");
                    System.out.println("No discount provided.");
                }
                break;
            } else if (cont == 'N' || cont == 'n') {
                break;
            }
            System.out.println("Invalid Option.");
        }
        subtotal = total - discountAmt;
        System.out.println("\nYour Sum Order Details:");
        System.out.printf("%-15s %-15s %-15s\n", "Product Name", "Price", "Quantity");
        String[] sumOrderArr = joinedOrderString.split(",");
        for (String s : sumOrderArr) {
            String[] tempS = s.split(" ");
            System.out.printf("%-15s %-15s %-15s\n", tempS[0], tempS[1], tempS[2]);
        }
        System.out.printf("\nTotal Amount (RM): %.2f\n", total);
        System.out.printf("Discounted Amount (RM): %.2f\n", discountAmt);
        System.out.printf("Subtotal (RM): %.2f\n", subtotal);

        //call payment
        try {
            lastOrderNo++;
            writerOrder.write(lastOrderNo + "_" + joinedOrderString);
            writerOrder.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writerOrder.close();
    }
//display order function

    public static void displayOrder() {
        String orderDetails;
        String orderNo;
        int moreThanOneItem = 0;
        double subtotal = 0;

        try {
            File order = new File("order.txt");
            Scanner orderReader = new Scanner(order);

            ArrayList<String[]> orderDetailsList = new ArrayList<>();
            System.out.printf("%-15s %-15s %-10s %-10s\n", "Order ID", "Product Name", "Price", "Quantity");
            System.out.println("===================================================\n");
            while (orderReader.hasNextLine()) {
                orderDetails = orderReader.nextLine();
                String[] temp = orderDetails.split("_");
                orderDetailsList.add(temp);
            }

            for (String[] s : orderDetailsList) {
                orderNo = s[0];
                String[] tempDetails = s[1].split(",");
                System.out.printf("%-15s ", orderNo);

                for (String tod : tempDetails) {
                    String[] tempOrderDetails = tod.split(" ");
                    if (moreThanOneItem == 0) {
                        System.out.printf("%-15s %-10s %-10s\n", tempOrderDetails[0], tempOrderDetails[1], tempOrderDetails[2]);
                        subtotal += Double.parseDouble(tempOrderDetails[1]) * Double.parseDouble(tempOrderDetails[2]);
                        moreThanOneItem++;
                    } else {
                        System.out.printf("%-15s %-15s %-10s %-10s\n", "", tempOrderDetails[0], tempOrderDetails[1], tempOrderDetails[2]);
                        subtotal += Double.parseDouble(tempOrderDetails[1]) * Double.parseDouble(tempOrderDetails[2]);
                    }
                }

                moreThanOneItem = 0;

                System.out.println("Subtotal For Order No " + orderNo + " is RM " + subtotal + "\n\n");
                subtotal = 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deleteOrder() throws Exception {
        ArrayList<String[]> orderDetailsList = new ArrayList<>();
        ArrayList<String> orderNoList = new ArrayList<>();
        int ordDltNo;
        String orderDetails;
        char confirmDlt = 0;

        try {
            File order = new File("order.txt");
            Scanner orderReader = new Scanner(order);

            while (orderReader.hasNextLine()) {
                orderDetails = orderReader.nextLine();
                String[] orderDetailsArray = orderDetails.split("_");
                orderDetailsList.add(orderDetailsArray);
                // order id
                orderNoList.add(orderDetailsArray[0]);
            }

            orderReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        displayOrder();

        while (true) {
            System.out.print("\nEnter the order id that you want to delete: ");
            if (scan.hasNextInt()) {
                ordDltNo = scan.nextInt();
                scan.nextLine();
                break;
            } else {
                scan.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        int element = -1; // Initialize to an invalid index
        for (int i = 0; i < orderNoList.size(); i++) {
            if (Integer.parseInt(orderNoList.get(i)) == ordDltNo) {
                element = i;
                break;
            }
        }

        if (element != -1) {
            while (true) {
                System.out.printf("Do you want to delete Order ID %03d ? (Y/N): ", ordDltNo);
                confirmDlt = scan.next().charAt(0);
                scan.nextLine();

                if (confirmDlt == 'Y' || confirmDlt == 'y' || confirmDlt == 'N' || confirmDlt == 'n') {
                    break; // Exit the loop when valid input
                } else {
                    System.out.println("Invalid Option.");
                }
            }

            if (confirmDlt == 'N' || confirmDlt == 'n') {
                System.out.printf("Order ID %03d is Not Deleted.\n", ordDltNo);
            } else {

                orderDetailsList.remove(element);

                try {
                    File order = new File("order.txt");
                    FileWriter orderFile = new FileWriter(order);
                    PrintWriter writerOrder = new PrintWriter(orderFile);

                    for (String[] s : orderDetailsList) {
                        writerOrder.write(s[0] + "_" + s[1] + "\n");
                    }

                    writerOrder.close();

                    System.out.printf("Order ID %03d Deleted Successfully.\n", ordDltNo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Order ID not found.");
        }
    }

    public static void searchOrder() throws Exception {
        String orderNo;
        int moreThanOneItem = 0;
        double subtotal = 0;
        int element = 0;
        char cont;
        ArrayList<String[]> orderDetailsList = new ArrayList<>();
        int searchedOrderId;
        String orderDetails;

        try {
            File order = new File("order.txt");
            Scanner orderReader = new Scanner(order);

            while (orderReader.hasNextLine()) {
                orderDetails = orderReader.nextLine();
                String[] orderDetailsArray = orderDetails.split("_");
                orderDetailsList.add(orderDetailsArray);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean orderFound = false;

        while (true) {
            System.out.print("\nEnter the order ID that you want to search : ");
            if (scan.hasNextInt()) {
                searchedOrderId = scan.nextInt();
                scan.nextLine();
                break;
            } else {
                scan.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        while (orderFound == false) {
            element = 0;
            for (String[] s : orderDetailsList) {
                if (Integer.parseInt(s[0]) == searchedOrderId) {
                    orderFound = true;
                    System.out.printf("\n%-15s %-15s %-10s %-10s\n", "Order ID", "Product Name", "Price", "Quantity");
                    System.out.println("===================================================");
                    orderNo = s[0];
                    String[] tempDetails = s[1].split(",");
                    System.out.printf("%-15s ", orderNo);

                    for (String tod : tempDetails) {
                        String[] tempOrderDetails = tod.split(" ");
                        if (moreThanOneItem == 0) {
                            System.out.printf("%-15s %-10s %-10s\n", tempOrderDetails[0], tempOrderDetails[1], tempOrderDetails[2]);
                            subtotal += Double.parseDouble(tempOrderDetails[1]) * Double.parseDouble(tempOrderDetails[2]);
                            moreThanOneItem++;
                        } else {
                            System.out.printf("%-15s %-15s %-10s %-10s\n", "", tempOrderDetails[0], tempOrderDetails[1], tempOrderDetails[2]);
                            subtotal += Double.parseDouble(tempOrderDetails[1]) * Double.parseDouble(tempOrderDetails[2]);
                        }
                    }

                    moreThanOneItem = 0;

                    System.out.println("Subtotal For Order No " + orderNo + " is RM " + subtotal + "\n\n");
                    subtotal = 0;

                    break;
                } else {
                    element++;
                }
            }

            if (orderFound == false) {
                System.out.println("Order ID not found. Please Try Again");
                System.out.print("\nEnter the order id that you want to search :");
                searchedOrderId = scan.nextInt();
            }
        }

    }

}
