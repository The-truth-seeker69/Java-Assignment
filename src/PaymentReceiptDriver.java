/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chowy
 */
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class PaymentReceiptDriver {

    public static void main(String[] args) {

        boolean checkCardNum;
        double totalPaid;
        
        String selectPayment = "";
        // Create a Date object
        Date currentDate = new Date();

        // Define a date and time pattern
        String pattern = "dd-MM-yyyy HH:mm:ss"; // Example pattern

        // Create a SimpleDateFormat object with the specified pattern
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        // Convert the Date to a String using the SimpleDateFormat
        String dateStr = dateFormat.format(currentDate);

        // Print the formatted date as a String
        System.out.println("Formatted Date: " + dateStr);

        Scanner scan = new Scanner(System.in);

        do{
        do {
            System.out.println("1.Credit card");
            System.out.println("2.Cash");
            System.out.println("0.Back");
            System.out.print("Select payment method:");
            selectPayment = scan.nextLine();

            if (selectPayment.charAt(0) != '1' && selectPayment.charAt(0) != '2' || selectPayment.length() != 1) {
                System.out.println("Invalid input!");
            }
        } while (selectPayment.charAt(0) != '1' && selectPayment.charAt(0) != '2' || selectPayment.length() != 1);

        if (selectPayment.charAt(0) == '1' && selectPayment.length() == 1) {
            do {
                System.out.print("\nEnter credit card number(12digits):");
                String inputCardNum = scan.nextLine();
                CreditCardPayment creditCardPayment = new CreditCardPayment(99, 1001,inputCardNum);
                checkCardNum = creditCardPayment.checkCardID(inputCardNum);
                if (!checkCardNum) {
                    System.out.println("Credit card number is invalid!");
                } else {
                    creditCardPayment.paymentSuccessful(99, creditCardPayment.getPaymentID());
                }
            } while (!checkCardNum);

        } else {
            System.out.print("Enter total paid:");
            totalPaid = scan.nextDouble();
            CashPayment cashPayment = new CashPayment(totalPaid,1001);
            cashPayment.calculateBalance(totalPaid, 90);
            cashPayment.paymentSuccessful(totalPaid, cashPayment.getPaymentID());
            scan.nextLine();
        }
        System.out.print("Continue?");
        selectPayment=scan.nextLine();
        
        }while(selectPayment.charAt(0)!='0'||selectPayment.length()!=1);

    }

}
