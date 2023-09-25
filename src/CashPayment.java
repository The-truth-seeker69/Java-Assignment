/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chowy
 */
public class CashPayment extends Payment {

    private double balance;

    public CashPayment(double totalPaid, int paymentID) {
        super(totalPaid,paymentID);
    }

    public double getBalance() {
        return balance;
    }

    public double calculateBalance(double totalPaid, double totalAmount) {
        balance = totalPaid - totalAmount;
        return balance;
    }

    @Override
    public void paymentSuccessful(double totalPaid, int paymentID) {
        System.out.println("Payment ID:" + paymentID);
        System.out.printf("A total payment of RM%.02f is successful with a balance of RM%.02f.\n",totalPaid,balance);
    }
}
