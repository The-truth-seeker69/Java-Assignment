/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Wei Quan
 */
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

//add order
//display product menu
// ask user to enter no of product (product index+1)
// ask quantity of product
// ask confirm
// ask continue or not
// if yes continue until user say no
//ask if user a member
// ask after subtotal
// if yes give discount
// display the subtotal, tax, total
//delete order
//ask which order to delete and display order
// ask orderId
public class Order {

    ArrayList<String> orderArr = new ArrayList<>();
    private int quantity;
    private int orderNo;
    private double subtotal;
    private final double TAX = 0.05;
    private double total;
    private boolean isMember;
    String[] item;

    char cont;
    Scanner scan = new Scanner(System.in);

    public ArrayList<String> getOrderArr() {
        return orderArr;
    }

    public void setOrderArr(ArrayList<String> orderArr) {
        this.orderArr = orderArr;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

}
