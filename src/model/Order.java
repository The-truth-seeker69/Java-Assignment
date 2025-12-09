package model;

import java.util.ArrayList;

/**
 * Order model class representing an order in the system.
 * Stores order information including order items and quantities.
 * 
 * @author Software Maintenance Team
 */
public class Order {

    /** List of order items (product name, price, quantity) */
    private ArrayList<String> orderArr = new ArrayList<>();

    /** Order quantity */
    private int quantity;

    /** Order number */
    private int orderNo;

    /**
     * Constructor for creating an Order instance.
     */
    public Order() {
        this.orderArr = new ArrayList<>();
    }

    /**
     * Gets the list of order items.
     * 
     * @return ArrayList of order items
     */
    public ArrayList<String> getOrderArr() {
        return orderArr;
    }

    /**
     * Sets the list of order items.
     * 
     * @param orderArr The new list of order items
     */
    public void setOrderArr(ArrayList<String> orderArr) {
        this.orderArr = orderArr;
    }

    /**
     * Gets the order quantity.
     * 
     * @return The order quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the order quantity.
     * 
     * @param quantity The new order quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the order number.
     * 
     * @return The order number
     */
    public int getOrderNo() {
        return orderNo;
    }

    /**
     * Sets the order number.
     * 
     * @param orderNo The new order number
     */
    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
