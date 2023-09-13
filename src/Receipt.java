/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chowy
 */
import java.util.Date;
public class Receipt {
    private double subtotal;
    private double tax;
    private double finaltotal;
    private double[] itemPrice={0};
    private double totalPayment;
    private Date transactionDate;
    private String transcationId;
    
  public double calculateSubtotal(double[] itemPrice){
      for(double items:itemPrice){
          subtotal+=items;
      }
      
      return subtotal;
  }
  
  public void printReceipt(){
      System.out.println("===================================");
      System.out.println("Receipt for");
      System.out.println("===================================");
  }
  
  
     
}


