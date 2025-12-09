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

    /**
     * Static list of product names (DEPRECATED - use database instead)
     * Kept for backward compatibility only
     */
    @Deprecated
    private static ArrayList<String> productsArr = new ArrayList<>();

    /**
     * Static list of product prices (DEPRECATED - use database instead)
     * Kept for backward compatibility only
     */
    @Deprecated
    private static ArrayList<Double> productsPriceArr = new ArrayList<>();

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
     * Gets the list of product names (DEPRECATED).
     * Returns empty list - data is now in database.
     * 
     * @return Empty ArrayList (for backward compatibility)
     */
    @Deprecated
    public static ArrayList<String> getProductsArr() {
        return productsArr; // Returns empty list - use ProductService.getAllProducts() instead
    }

    /**
     * Sets the list of product names (DEPRECATED).
     * 
     * @param productsArr The new list of product names
     */
    @Deprecated
    public static void setProductsArr(ArrayList<String> productsArr) {
        Product.productsArr = productsArr;
    }

    /**
     * Gets the list of product prices (DEPRECATED).
     * Returns empty list - data is now in database.
     * 
     * @return Empty ArrayList (for backward compatibility)
     */
    @Deprecated
    public static ArrayList<Double> getProductsPriceArr() {
        return productsPriceArr; // Returns empty list - use ProductService.getAllProducts() instead
    }

    /**
     * Sets the list of product prices (DEPRECATED).
     * 
     * @param productsPriceArr The new list of product prices
     */
    @Deprecated
    public static void setProductsPriceArr(ArrayList<Double> productsPriceArr) {
        Product.productsPriceArr = productsPriceArr;
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
