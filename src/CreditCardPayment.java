/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chowy
 */
public class CreditCardPayment extends Payment {

    private String creditCardID;
    private String paymentMethod="Credit card";
    private final double BALANCE = 0;

    public String getCreditCardID() {
        return creditCardID;
    }

    public double getBALANCE() {
        return BALANCE;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setCreditCardID(String creditCardID) {
        this.creditCardID = creditCardID;
    }

    public CreditCardPayment(double totalPaid, int paymentID, String creditCardID) {
        super(totalPaid,paymentID);
        this.creditCardID = creditCardID;
    }

    public boolean checkCardID(String cardID) {
        if (cardID.length() < 13) {
            if (cardID.matches("^[0-9]{12}$")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void paymentSuccessful(double totalPaid, int paymentID) {
        System.out.println("Payment ID:" + paymentID);
        System.out.printf("Payment for account %s is successful! A total of RM%.02f is paid.\n",creditCardID,totalPaid);
        super.setPaymentID(paymentID++);
    }

}
