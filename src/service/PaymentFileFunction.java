package service;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chowy
 */
public class PaymentFileFunction {
    
     public static ArrayList<String[]> readPayment() {
        ArrayList<String[]> payments = new ArrayList<>();
        String fileName = "payments.txt";

        try {
            File payment = new File(fileName);
            Scanner paymentsReader = new Scanner(payment);
            payments.clear();  // Clear the arrays before populating them again

            while (paymentsReader.hasNextLine()) {
                String line = paymentsReader.nextLine();
                String tokens[] = line.split(" ");

                if (tokens.length == 3) {
                    payments.add(tokens);
                } else {
                    System.out.println("Invalid data format. " + line);
                }
            }

            paymentsReader.close();

        } catch (FileNotFoundException e) {

            System.out.println("An error occurred while reading to the file.");
            e.printStackTrace();

        }

        return payments;
    }
     
      public static void addPayment(String newPaymentStr) {

        try {
            //Open the file for writing
            FileWriter paymentAdd = new FileWriter("payments.txt", true);
            BufferedWriter writePayment = new BufferedWriter(paymentAdd);
            writePayment.write(newPaymentStr);
            writePayment.close();

        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
      
      public static void deletePayment(ArrayList<String[]> paymentList, int deleteElement) {
        try {
            File paymentFile = new File("payments.txt");
            FileWriter fw = new FileWriter(paymentFile);
            PrintWriter pw = new PrintWriter(fw);
            int count = 0;

            for (String[] m : paymentList) {
                if (count == deleteElement) {
                    count++;
                    continue;
                } else {
                    count++;
                    String formattedPayment = String.format("%s %s %s", m[0], m[1], m[2]);
                    pw.println(formattedPayment);
                }
                
            }

            pw.close();
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
