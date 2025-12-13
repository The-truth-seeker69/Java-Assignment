package util;

/**
 * Order Parser Utility
 * 
 * Simplifies parsing of order details string.
 * Handles both new format (pipe-delimited) and old format (space-delimited).
 * 
 * Format: "productName|price|quantity" or "productName price quantity"
 */
public class OrderParser {
    
    /**
     * Parses a single order item string.
     * 
     * @param item Order item string (e.g., "Rice 5kg|28.90|2" or "Milk 69.00 6")
     * @return OrderItem object with productName, price, quantity, or null if invalid
     */
    public static OrderItem parseItem(String item) {
        if (item == null || item.trim().isEmpty()) {
            return null;
        }
        
        item = item.trim();
        
        // New format: productName|price|quantity
        if (item.contains("|")) {
            String[] parts = item.split("\\|", 3);
            if (parts.length == 3) {
                try {
                    String productName = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    return new OrderItem(productName, price, quantity);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        } 
        // Old format: productName price quantity (backward compatibility)
        else {
            String[] parts = item.split(" ");
            if (parts.length >= 3) {
                try {
                    // Last part is quantity, second last is price, rest is product name
                    int quantity = Integer.parseInt(parts[parts.length - 1]);
                    double price = Double.parseDouble(parts[parts.length - 2]);
                    String productName = String.join(" ", java.util.Arrays.copyOfRange(parts, 0, parts.length - 2));
                    return new OrderItem(productName, price, quantity);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Parses order details string (comma-separated items).
     * 
     * @param orderDetails Order details string (e.g., "Rice|28.90|2,Milk|69.00|6")
     * @return Array of OrderItem objects
     */
    public static OrderItem[] parseOrderDetails(String orderDetails) {
        if (orderDetails == null || orderDetails.trim().isEmpty()) {
            return new OrderItem[0];
        }
        
        String[] items = orderDetails.split(",");
        java.util.ArrayList<OrderItem> orderItems = new java.util.ArrayList<>();
        
        for (String item : items) {
            OrderItem parsed = parseItem(item);
            if (parsed != null) {
                orderItems.add(parsed);
            }
        }
        
        return orderItems.toArray(new OrderItem[0]);
    }
    
    /**
     * Calculates total amount from order details.
     * 
     * @param orderDetails Order details string
     * @return Total amount
     */
    public static double calculateTotal(String orderDetails) {
        OrderItem[] items = parseOrderDetails(orderDetails);
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
    
    /**
     * Formats order details for display.
     * 
     * @param orderDetails Order details string
     * @return Formatted HTML string for display
     */
    public static String formatForDisplay(String orderDetails) {
        OrderItem[] items = parseOrderDetails(orderDetails);
        StringBuilder formatted = new StringBuilder("<html><div style='padding: 5px;'>");
        
        for (OrderItem item : items) {
            formatted.append("<div style='margin-bottom: 3px;'>");
            formatted.append(item.productName).append(" x").append(item.quantity)
                     .append(" @ RM ").append(String.format("%.2f", item.price))
                     .append(" = RM ").append(String.format("%.2f", item.getTotal()));
            formatted.append("</div>");
        }
        
        formatted.append("</div></html>");
        return formatted.toString();
    }
    
    /**
     * Formats order details for plain text display.
     * 
     * @param orderDetails Order details string
     * @return Plain text string
     */
    public static String formatForPlainText(String orderDetails) {
        OrderItem[] items = parseOrderDetails(orderDetails);
        StringBuilder formatted = new StringBuilder();
        
        for (OrderItem item : items) {
            formatted.append(item.productName).append(" x").append(item.quantity)
                     .append(" @ RM ").append(String.format("%.2f", item.price))
                     .append(" = RM ").append(String.format("%.2f", item.getTotal()))
                     .append("\n");
        }
        
        return formatted.toString();
    }
    
    /**
     * Creates order details string from items.
     * 
     * @param items Array of OrderItem objects
     * @return Order details string in new format (pipe-delimited)
     */
    public static String createOrderDetails(OrderItem[] items) {
        java.util.ArrayList<String> parts = new java.util.ArrayList<>();
        for (OrderItem item : items) {
            parts.add(item.productName + "|" + String.format("%.2f", item.price) + "|" + item.quantity);
        }
        return String.join(",", parts);
    }
    
    /**
     * Inner class to represent an order item.
     */
    public static class OrderItem {
        public String productName;
        public double price;
        public int quantity;
        
        public OrderItem(String productName, double price, int quantity) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }
        
        public double getTotal() {
            return price * quantity;
        }
    }
}






