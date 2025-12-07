package model;

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

public abstract class Product {

    public static ArrayList<String> getProductsArr() {
        return productsArr;
    }

    public static void setProductsArr(ArrayList<String> productsArr) {
        Product.productsArr = productsArr;
    }

    public static ArrayList<Double> getProductsPriceArr() {
        return productsPriceArr;
    }

    public static void setProductsPriceArr(ArrayList<Double> productsPriceArr) {
        Product.productsPriceArr = productsPriceArr;
    }

    static ArrayList<String> productsArr = new ArrayList<>();
    static ArrayList<Double> productsPriceArr = new ArrayList<>();

    private String productName;
    private double productPrice;
    private char confirmAdd;
    private char cont;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public char getConfirmAdd() {
        return confirmAdd;
    }

    public void setConfirmAdd(char confirmAdd) {
        this.confirmAdd = confirmAdd;
    }

    public Product(String productName, double productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

}
