package model;

import java.util.ArrayList;

/**
 * Product Model Class
 * 
 * Represents a product in the system.
 * Note: Static arrays are deprecated - data is now stored in database.
 * Kept for backward compatibility with legacy code.
 * 
 * @author Software Maintenance Team
 */
public class Product {

    /** Product name */
    private String productName;

    /** Product price */
    private double productPrice;

    /**
     * Constructor for creating a Product instance.
     * 
     * @param productName  The name of the product
     * @param productPrice The price of the product
     */
    public Product(String productName, double productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

     /**
     * Gets the product name.
     * 
     * @return The product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     * 
     * @param productName The new product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the product price.
     * 
     * @return The product price
     */
    public double getProductPrice() {
        return productPrice;
    }

    /**
     * Sets the product price.
     * 
     * @param productPrice The new product price
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
