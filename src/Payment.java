/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chowy
 */
public abstract class Payment {

    private double totalPaid;
    private int paymentID;
    
    public abstract void paymentSuccessful(double totalPaid, int paymentID);

    public Payment(double totalPaid, int paymentID) {
        this.totalPaid = totalPaid;
        this.paymentID = paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

}
