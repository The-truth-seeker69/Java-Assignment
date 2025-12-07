package service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chowy
 */
public class Receipt {

    public void printReceipt(double totalAmount, String currDate, String[] items, double[] itemPrices, int[] itemQuantity, int numOfItems, double change, double totalPaid, int orderID, int paymentID, String paymentMethod) {
        System.out.println("====================================================");
        System.out.println("    Receipt for Order ID:" + orderID + " Payment ID:" + paymentID + "\n    Date: " + currDate + " Method: " + paymentMethod);
        System.out.println("====================================================");
        System.out.printf("    %-15s\tQuantity:\tPrice:\n", "Items:");
        for (int i = 0; i < numOfItems; i++) {
            System.out.printf("    %-15s\t%9d\tRM%7.02f\n", items[i], itemQuantity[i], itemPrices[i]);
        }
        System.out.println("----------------------------------------------------");
        System.out.printf("    Total : \t\t\t\tRM%7.02f\n", totalAmount);
        System.out.printf("    Paid  : \t\t\t\tRM%7.02f\n", totalPaid);
        System.out.printf("    Change: \t\t\t\tRM%7.02f\n", change);
        System.out.println("====================================================");
    }

}
