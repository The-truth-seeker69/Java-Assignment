/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chowy
 */
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PaymentReceiptDriver {

    public static void main(String[] args) {

        boolean checkCardNum;
        double totalPaid = 90;
        String paymentMenuChoice = "";
        String selectPayment = "";
        String printChoice = "";
        boolean continueDel = false;
        // Create a Date object
        Date currentDate = new Date();

        // Define a date and time pattern
        String pattern = "dd-MM-yyyy HH:mm:ss"; // Example pattern

        // Create a SimpleDateFormat object with the specified pattern
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        // Convert the Date to a String using the SimpleDateFormat
        String dateStr = dateFormat.format(currentDate);

        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("Payment Menu");
            System.out.println("================================");
            System.out.println("    1.View payment history");
            System.out.println("    2.Search payment history");
            System.out.println("    3.Delete payment history");
            System.out.println("    0.Back");
            System.out.println("================================");
            System.out.print("Enter an option:");
            paymentMenuChoice = scan.next();
            scan.nextLine();

            if (paymentMenuChoice.charAt(0) != '0' && paymentMenuChoice.charAt(0) != '1' && paymentMenuChoice.charAt(0) != '2' && paymentMenuChoice.charAt(0) != '3' || paymentMenuChoice.length() != 1) {
                System.out.println("Invalid input!");
            }

            if (paymentMenuChoice.charAt(0) == '1' && paymentMenuChoice.length() == 1) {
                ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                int count = 0;
                System.out.println("\nPayment History");
                System.out.println("=========================================");
                System.out.printf("%-11s %-11s  %-10s\n", "Payment ID", "Total Amount", "Payment Method");
                System.out.println("=========================================");
                for (String[] m : payments) {
                    String formattedMethod = m[2].replace("_", " ");
                    System.out.printf("%-11s RM%9s   %-10s\n", m[0], m[1], formattedMethod);
                    count++;
                }
                System.out.println("=========================================");
                System.out.printf(" %21s : %d Payment(s)\n\n", "Total Payment Count", count);
            }

            if (paymentMenuChoice.charAt(0) == '2' && paymentMenuChoice.length() == 1) {
                ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                ArrayList<Integer> paymentIDList = new ArrayList<>();
                int element = 0;
                int paymentId;
                boolean paymentFound = false;

                for (String[] m : payments) {
                    paymentIDList.add(Integer.parseInt(m[0]));
                }

                while (true) {
                    try {
                        System.out.print("Enter the Payment ID you want to search: ");
                        paymentId = scan.nextInt();
                        scan.nextLine();  // clear the newline character

                        while (!paymentFound) {
                            element = 0;
                            for (Integer i : paymentIDList) {
                                if (i == paymentId) {
                                    paymentFound = true;
                                    break;
                                }
                                element++;
                            }
                            if (paymentFound) {
                                break;
                            }
                            System.out.println("Payment ID not Found. Please Try Again.");
                            System.out.print("Enter the Payment ID you want to search: ");
                            paymentId = scan.nextInt();
                            scan.nextLine();  // Consume the newline character
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("\nInvalid input. Please enter a valid integer for the Payment ID.");
                        scan.next();  // Clear the invalid input
                    }
                }

                // Print the found payment's details
                String[] m = payments.get(element);
                String formattedMethod = m[2].replace("_", " ");

                if (formattedMethod.equals("Cash")) {
                    CashPayment searchPayment;
                    searchPayment = new CashPayment(Double.parseDouble(m[1]), Integer.parseInt(m[0]));
                    System.out.println("\n" + searchPayment.toString() + ".\n");
                }else if(formattedMethod.equals("Credit Card")){
                    CreditCardPayment searchPayment;
                    searchPayment = new CreditCardPayment(Double.parseDouble(m[1]), Integer.parseInt(m[0]),"dummy");
                    System.out.println("\n" + searchPayment.toString() + ".\n");
                }

            }

            if (paymentMenuChoice.charAt(0) == '3' && paymentMenuChoice.length() == 1) {
                do {
                    ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                    ArrayList<Integer> paymentIdList = new ArrayList<>();
                    String[] delPaymentStrArr;
                    int element = 0;
                    int paymentId;
                    boolean paymentFound = false;
                    char confirmDel = 0;
                    char continueDelInput = 0;

                    for (String[] m : payments) {
                        paymentIdList.add(Integer.parseInt(m[0]));
                    }

                    while (true) {
                        try {
                            System.out.print("Enter the Payment ID you want to delete: ");
                            paymentId = scan.nextInt();
                            scan.nextLine();  // clear the newline character

                            while (!paymentFound) {
                                element = 0;
                                for (Integer i : paymentIdList) {
                                    if (i == paymentId) {
                                        paymentFound = true;
                                        break;
                                    }
                                    element++;
                                }
                                if (paymentFound) {
                                    break;
                                }
                                System.out.println("\nPayment ID not Found. Please Try Again.");
                                System.out.print("Enter the Payment ID you want to delete: ");
                                paymentId = scan.nextInt();
                                scan.nextLine();  // clear the newline character
                            }
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid integer for the member ID.");
                            scan.next();  // Clear the invalid input
                        }
                    }

                    delPaymentStrArr = payments.get(element);
                    String formattedName = delPaymentStrArr[2].replace("_", " ");

                    System.out.println("\nPayment with ID:" + paymentId + " has been found.");
                    System.out.print("Are you sure you want to delete this member? (Y / N): ");
                    confirmDel = scan.next().charAt(0);
                    scan.nextLine();

                    while (confirmDel != 'y' && confirmDel != 'Y' && confirmDel != 'n' && confirmDel != 'N') {
                        System.out.println("\nInvalid Input. Please Try Again.");
                        System.out.print("Are you sure you want to delete this member? (Y / N): ");
                        confirmDel = scan.next().charAt(0);
                        scan.nextLine();
                    }

                    if (Character.toUpperCase(confirmDel) == 'Y') {
                        PaymentFileFunction.deletePayment(payments, element);
                        System.out.println("\nPayment with ID:" + delPaymentStrArr[0] + " has been deleted successFully.");
                    } else {
                        System.out.println("Payment with ID:" + delPaymentStrArr[0] + " has not been deleted.\n");
                    }

                    System.out.print("Do you want to continue to delete a payment? (Y/N): ");
                    continueDelInput = scan.next().charAt(0);
                    scan.nextLine();

                    while (continueDelInput != 'Y' && continueDelInput != 'y' && continueDelInput != 'N' && continueDelInput != 'n') {
                        System.out.println("Invalid input, Please Try Again.");
                        System.out.print("Do you want to continue to delete a payment? (Y/N): ");
                        continueDelInput = scan.next().charAt(0);
                        scan.nextLine();
                    }

                    if (continueDelInput == 'Y' || continueDelInput == 'y') {
                        continueDel = true;
                    } else {
                        continueDel = false;
                        System.out.println("End of delete payment function.");
                    }
                } while (continueDel == true);
            }

        } while (paymentMenuChoice.charAt(0) != '0' || paymentMenuChoice.length() != 1);

        do {
            System.out.println("\nPayment Methods");
            System.out.println("==============================");
            System.out.println("    1.Credit card");
            System.out.println("    2.Cash");
            System.out.println("    0.Back");
            System.out.println("==============================");
            System.out.print("Select payment method:");
            selectPayment = scan.next();
            scan.nextLine();

            if (selectPayment.charAt(0) == '0' && selectPayment.length() == 1) {
                System.out.println("Exiting payment module...");
            }

            if (selectPayment.charAt(0) != '0' && selectPayment.charAt(0) != '1' && selectPayment.charAt(0) != '2' || selectPayment.length() != 1) {
                System.out.println("Invalid input!\n");
            }

            if (selectPayment.charAt(0) == '1' && selectPayment.length() == 1) {
                do {
                    System.out.print("\nEnter credit card number(12digits):");
                    String inputCardNum = scan.nextLine();
                    double totalAmount = 90.00;
                    CreditCardPayment creditCardPayment = new CreditCardPayment(totalAmount, 1001, inputCardNum);
                    checkCardNum = creditCardPayment.checkCardID(inputCardNum);
                    if (!checkCardNum) {
                        System.out.println("Credit card number is invalid!");
                    } else {
                        creditCardPayment.paymentSuccessful(totalAmount, creditCardPayment.getPaymentID());
                        ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                        int lastIndex = payments.size() - 1;
                        String[] lastpayment = payments.get(lastIndex);
                        String[] tempPaymentMethod = creditCardPayment.getPaymentMethod().split(" ");
                        String paymentMethod = String.join("_", tempPaymentMethod);
                        StringBuilder newPayment = new StringBuilder();
                        Integer lastPaymentInt = Integer.valueOf(lastpayment[0]) + 1;
                        newPayment.append(lastPaymentInt + " " + totalAmount + " " + paymentMethod);
                        String newPaymentStr = lastPaymentInt + " " + totalAmount + " " + paymentMethod + "\n";
                        PaymentFileFunction.addPayment(newPaymentStr);
                    }

                    if (checkCardNum) {
                        do {
                            System.out.print("Thanks for purchasing. Print receipt? (Y/N):");
                            printChoice = scan.next();
                            scan.nextLine();

                            if (printChoice.charAt(0) == 'Y' || printChoice.charAt(0) == 'y' && printChoice.length() == 1) {
                                Receipt receipt = new Receipt();
                                String[] items = {"item1", "item2", "item3"};
                                double[] prices = {1.5, 10.5, 50.5};
                                int[] quantities = {1, 2, 3};
                                receipt.printReceipt(90, dateStr, items, prices, quantities, 3, creditCardPayment.getBALANCE(), totalPaid, 1001, creditCardPayment.getPaymentID(), creditCardPayment.getPaymentMethod());
                            }

                            if (printChoice.charAt(0) != 'Y' && printChoice.charAt(0) != 'y' && printChoice.charAt(0) != 'n' && printChoice.charAt(0) != 'N' || printChoice.length() > 1) {
                                System.out.println("Invalid input!\n");
                            }
                        } while (printChoice.length() != 1 || printChoice.charAt(0) != 'Y' && printChoice.charAt(0) != 'y' && printChoice.charAt(0) != 'n' && printChoice.charAt(0) != 'N');
                    }
                } while (!checkCardNum);

            } else if (selectPayment.charAt(0) == '2' && selectPayment.length() == 1) {
                boolean validTotal = false;
                while (!validTotal) {
                    System.out.print("Enter total paid:");
                    String input = scan.nextLine();

                    try {

                        // Attempt to parse the input as a double
                        totalPaid = Double.parseDouble(input);

                        // If parsing succeeds, input is valid
                        validTotal = true;
                    } catch (NumberFormatException e) {
                        // Parsing failed, input is not a valid double
                        System.out.println("Invalid input. Please enter a valid double value.");
                    }
                }

                CashPayment cashPayment = new CashPayment(totalPaid, 1001);
                cashPayment.calculateBalance(totalPaid, 90);
                cashPayment.paymentSuccessful(totalPaid, cashPayment.getPaymentID());
                ArrayList<String[]> payments = PaymentFileFunction.readPayment();
                int lastIndex = payments.size() - 1;
                String[] lastpayment = payments.get(lastIndex);
                String[] tempPaymentMethod = cashPayment.getPaymentMethod().split(" ");
                String paymentMethod = String.join("_", tempPaymentMethod);
                StringBuilder newPayment = new StringBuilder();
                Integer lastPaymentInt = Integer.valueOf(lastpayment[0]) + 1;
                newPayment.append(lastPaymentInt + " " + totalPaid + " " + paymentMethod);
                String newPaymentStr = lastPaymentInt + " " + totalPaid + " " + paymentMethod + "\n";
                PaymentFileFunction.addPayment(newPaymentStr);

                do {
                    System.out.print("Thanks for purchasing. Print receipt? (Y/N):");
                    printChoice = scan.next();
                    scan.nextLine();

                    if (printChoice.charAt(0) == 'Y' || printChoice.charAt(0) == 'y' && printChoice.length() == 1) {
                        Receipt receipt = new Receipt();
                        String[] items = {"item1", "item2", "item3"};
                        double[] prices = {1.5, 10.5, 50.5};
                        int[] quantities = {1, 2, 3};
                        receipt.printReceipt(90, dateStr, items, prices, quantities, 3, cashPayment.getBalance(), totalPaid, 1001, cashPayment.getPaymentID(), cashPayment.getPaymentMethod());
                    }
                    if (printChoice.charAt(0) != 'Y' && printChoice.charAt(0) != 'y' && printChoice.charAt(0) != 'n' && printChoice.charAt(0) != 'N' || printChoice.length() > 1) {
                        System.out.println("Invalid input!\n");
                    }
                } while (printChoice.length() != 1 || printChoice.charAt(0) != 'Y' && printChoice.charAt(0) != 'y' && printChoice.charAt(0) != 'n' && printChoice.charAt(0) != 'N');
            }

        } while (selectPayment.charAt(0) != '0' && selectPayment.charAt(0) != '1' && selectPayment.charAt(0) != '2' || selectPayment.length() != 1);

    }

}
