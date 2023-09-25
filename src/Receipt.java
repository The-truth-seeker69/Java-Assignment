/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chowy
 */
public class Receipt {

    private int receiptID;
    private int firstID = 1001;

    public Receipt() {
        this.receiptID = firstID;
        firstID++;
    }

    public int getReceiptID() {
        return receiptID;
    }

    public void printReceipt(double totalAmount, String currDate, String[] items, double[] itemPrices, int[] itemQuantity, int memID, int paymentID, int numOfItems, double change, double totalPaid) {
        System.out.println("===================================");
        System.out.println("Receipt ID:" + receiptID + "Payment ID:" + paymentID + " Customer ID:" + memID + " Date:" + currDate);
        System.out.println("===================================");
        System.out.println("Item:\tQuantity:\tPrice:");
        for (int i = 0; i < numOfItems; i++) {
            System.out.printf("%s\t%d\t%.02f\n", items[i], itemQuantity[i], itemPrices[i]);
        }
        System.out.println("-----------------------------------");
        System.out.printf("Total : RM$.02f", totalAmount);
        System.out.printf("Paid  : RM$.02f", totalPaid);
        System.out.printf("Change: RM$.02f", change);
        System.out.println("===================================");
    }

}
