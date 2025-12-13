package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import service.OrderService;
import service.ProductService;
import service.MemberService;
import util.OrderParser;
import util.OrderParser.OrderItem;
import util.UIHelper;
import java.util.ArrayList;

/**
 * Order Management View
 * Handles order creation and viewing order history.
 */
public class OrderView extends JFrame {

    // UI Components
    private JTable orderTable, cartTable;
    private DefaultTableModel orderTableModel, cartTableModel;
    private JComboBox<String> productCombo;
    private JSpinner quantitySpinner;
    private JLabel totalLabel, discountLabel, subtotalLabel;
    private JButton addToCartButton, removeFromCartButton, checkoutButton;
    private JButton searchButton, refreshButton;

    // Data
    private ArrayList<CartItem> cartItems;
    private static final double MEMBER_DISCOUNT = 0.1;
    private static OrderView currentInstance = null;

    /**
     * Cart item representation.
     */
    private class CartItem {
        String productName;
        double price;
        int quantity;

        CartItem(String productName, double price, int quantity) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }

        double getTotal() {
            return price * quantity;
        }
    }

    /**
     * Constructor - Initializes the UI.
     */
    public OrderView() {
        cartItems = new ArrayList<>();
        initializeUI();
        loadProducts();
        loadOrders();
        setupWindowListener();
    }

    /**
     * Sets up window listener to show main menu when closed.
     */
    private void setupWindowListener() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                currentInstance = null;
                Login.showMainMenu();
            }
        });
    }

    /**
     * Initializes the UI components.
     */
    private void initializeUI() {
        setTitle("Order Management - TARUMT Grocery POS System");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Order Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Create Order", createOrderPanel());
        tabbedPane.addTab("Order History", createOrderHistoryPanel());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    /**
     * Creates the order creation panel.
     */
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Product selection
        JPanel productPanel = createProductSelectionPanel();
        panel.add(productPanel, BorderLayout.NORTH);

        // Cart table
        String[] cartColumns = { "Product Name", "Price (RM)", "Quantity", "Total (RM)" };
        cartTableModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);
        cartTable.setFont(new Font("Arial", Font.PLAIN, 12));
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        cartTable.setRowHeight(25);
        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        panel.add(cartScroll, BorderLayout.CENTER);

        // Summary panel
        panel.add(createSummaryPanel(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates product selection panel.
     */
    private JPanel createProductSelectionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add Products to Order"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Product dropdown
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Product:"), gbc);
        gbc.gridx = 1;
        productCombo = new JComboBox<>();
        productCombo.setPreferredSize(new Dimension(200, 25));
        panel.add(productCombo, gbc);

        // Quantity spinner
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        quantitySpinner.setPreferredSize(new Dimension(100, 25));
        panel.add(quantitySpinner, gbc);

        // Add button
        addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> addToCart());
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(addToCartButton, gbc);

        return panel;
    }

    /**
     * Creates summary panel with totals and buttons.
     */
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Order Summary"));

        panel.add(new JLabel("Total:"));
        totalLabel = new JLabel("RM 0.00");
        panel.add(totalLabel);

        panel.add(new JLabel("Discount (10%):"));
        discountLabel = new JLabel("RM 0.00");
        panel.add(discountLabel);

        panel.add(new JLabel("Subtotal:"));
        subtotalLabel = new JLabel("RM 0.00");
        panel.add(subtotalLabel);

        removeFromCartButton = new JButton("Remove Selected");
        removeFromCartButton.addActionListener(e -> removeFromCart());
        panel.add(removeFromCartButton);

        checkoutButton = new JButton("Checkout");
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkoutButton.addActionListener(e -> checkout());
        panel.add(checkoutButton);

        return panel;
    }

    /**
     * Creates the order history panel.
     */
    private JPanel createOrderHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Order table
        String[] orderColumns = { "Order ID", "Items", "Total (RM)" };
        orderTableModel = new DefaultTableModel(orderColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTable = new JTable(orderTableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (column == 1) {
                    TableColumn col = getColumnModel().getColumn(column);
                    int width = comp.getPreferredSize().width + getIntercellSpacing().width;
                    col.setPreferredWidth(Math.max(width, col.getPreferredWidth()));
                }
                return comp;
            }
        };
        orderTable.setFont(new Font("Arial", Font.PLAIN, 12));
        orderTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        orderTable.setRowHeight(25);
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(500);
        orderTable.getColumnModel().getColumn(1).setMinWidth(300);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Order History"));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        searchButton = new JButton("Search Order");
        searchButton.addActionListener(e -> searchOrder());
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadOrders());
        JButton deleteButton = new JButton("Delete Selected Order");
        deleteButton.addActionListener(e -> deleteOrder());
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Loads products into combo box.
     */
    private void loadProducts() {
        productCombo.removeAllItems();
        for (String[] product : ProductService.getAllProducts()) {
            productCombo.addItem(product[0] + " - RM " + UIHelper.formatCurrency(Double.parseDouble(product[1])));
        }
    }

    /**
     * Loads orders into table.
     */
    private void loadOrders() {
        orderTableModel.setRowCount(0);
        for (String[] order : OrderService.getAllOrders()) {
            try {
                String formatted = OrderParser.formatForDisplay(order[1]);
                double total = OrderParser.calculateTotal(order[1]);
                orderTableModel.addRow(new Object[] { order[0], formatted, UIHelper.formatCurrency(total) });
            } catch (Exception e) {
                System.err.println("Error loading order " + order[0] + ": " + e.getMessage());
                orderTableModel.addRow(new Object[] {
                        order[0],
                        "<html><div style='color: red;'>Error loading order details</div></html>",
                        "0.00"
                });
            }
        }
        adjustRowHeights();
    }

    /**
     * Adjusts table row heights based on content.
     */
    private void adjustRowHeights() {
        for (int row = 0; row < orderTable.getRowCount(); row++) {
            try {
                Component comp = orderTable.prepareRenderer(
                        orderTable.getCellRenderer(row, 1), row, 1);
                int height = comp.getPreferredSize().height + orderTable.getRowMargin();
                if (height > orderTable.getRowHeight()) {
                    orderTable.setRowHeight(row, height);
                }
            } catch (Exception e) {
                // Skip on error
            }
        }
    }

    /**
     * Adds product to cart.
     */
    private void addToCart() {
        if (productCombo.getSelectedItem() == null) {
            UIHelper.showError(this, "Please select a product.");
            return;
        }

        // Parse selected product
        String selected = productCombo.getSelectedItem().toString();
        String productName = selected.split(" - ")[0];
        double price = Double.parseDouble(selected.split(" - RM ")[1]);

        // Get quantity
        int quantity = getQuantityFromSpinner();
        if (quantity <= 0) {
            return;
        }

        // Add or merge in cart
        mergeOrAddToCart(productName, price, quantity);
        updateCartTable();
        updateSummary();
        quantitySpinner.setValue(1);
    }

    /**
     * Gets quantity from spinner.
     */
    private int getQuantityFromSpinner() {
        try {
            Object value = quantitySpinner.getValue();
            int qty = value instanceof Integer ? (Integer) value : ((Number) value).intValue();
            if (qty <= 0) {
                UIHelper.showError(this, "Quantity must be greater than 0.");
                quantitySpinner.setValue(1);
            }
            return qty;
        } catch (Exception e) {
            UIHelper.showError(this, "Invalid quantity. Please enter a valid number.");
            quantitySpinner.setValue(1);
            return 0;
        }
    }

    /**
     * Merges or adds item to cart.
     */
    private void mergeOrAddToCart(String productName, double price, int quantity) {
        for (CartItem item : cartItems) {
            if (item.productName.equals(productName) && item.price == price) {
                item.quantity += quantity;
                return;
            }
        }
        cartItems.add(new CartItem(productName, price, quantity));
    }

    /**
     * Removes selected item from cart.
     */
    private void removeFromCart() {
        int row = cartTable.getSelectedRow();
        if (row == -1) {
            UIHelper.showWarning(this, "Please select an item to remove.");
            return;
        }
        cartItems.remove(row);
        updateCartTable();
        updateSummary();
    }

    /**
     * Updates cart table display.
     */
    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (CartItem item : cartItems) {
            cartTableModel.addRow(new Object[] {
                    item.productName,
                    UIHelper.formatCurrency(item.price),
                    item.quantity,
                    UIHelper.formatCurrency(item.getTotal())
            });
        }
    }

    /**
     * Updates summary labels.
     */
    private void updateSummary() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotal();
        }
        totalLabel.setText(UIHelper.formatCurrencyRM(total));
        discountLabel.setText(UIHelper.formatCurrencyRM(0));
        subtotalLabel.setText(UIHelper.formatCurrencyRM(total));
    }

    /**
     * Processes checkout.
     */
    private void checkout() {
        if (cartItems.isEmpty()) {
            UIHelper.showWarning(this, "Cart is empty. Please add items to cart.");
            return;
        }

        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotal();
        }
        double discount = processMemberDiscount(total);
        double subtotal = total - discount;

        if (!UIHelper.confirm(this, buildOrderSummary(total, discount, subtotal))) {
            return;
        }

        createOrder(total, discount, subtotal);
    }

    /**
     * Processes member discount.
     */
    private double processMemberDiscount(double total) {
        if (!UIHelper.confirm(this, "Do you have a member ID?")) {
            return 0;
        }

        String memberIdText = UIHelper.input(this, "Enter Member ID:", "Member ID");
        if (memberIdText == null || memberIdText.trim().isEmpty()) {
            return 0;
        }

        try {
            int memberId = Integer.parseInt(memberIdText.trim());
            if (MemberService.isMember(memberId)) {
                double discount = total * MEMBER_DISCOUNT;
                String name = MemberService.getMemberName(memberId);
                String displayName = name != null ? name.replace("_", " ") : "Unknown";
                UIHelper.showInfo(this,
                        "Member " + memberId + " (" + displayName + ") verified. 10% discount applied!");
                return discount;
            } else {
                UIHelper.showWarning(this, "Member ID " + memberId + " not found. No discount applied.");
            }
        } catch (NumberFormatException e) {
            UIHelper.showWarning(this, "Invalid member ID format. No discount applied.");
        }
        return 0;
    }

    /**
     * Builds order summary string.
     */
    private String buildOrderSummary(double total, double discount, double subtotal) {
        StringBuilder summary = new StringBuilder("Order Summary:\n\n");
        summary.append("Total: ").append(UIHelper.formatCurrencyRM(total)).append("\n");
        if (discount > 0) {
            summary.append("Member Discount (10%): ").append(UIHelper.formatCurrencyRM(discount)).append("\n");
        }
        summary.append("Subtotal: ").append(UIHelper.formatCurrencyRM(subtotal)).append("\n\n");
        summary.append("Proceed to create order?");
        return summary.toString();
    }

    /**
     * Creates order and opens payment view.
     */
    private void createOrder(double total, double discount, double subtotal) {
        try {
            // Create order details
            OrderItem[] items = new OrderItem[cartItems.size()];
            for (int i = 0; i < cartItems.size(); i++) {
                CartItem item = cartItems.get(i);
                items[i] = new OrderItem(item.productName, item.price, item.quantity);
            }
            String orderDetails = OrderParser.createOrderDetails(items);

            // Save order
            int orderId = OrderService.getNextOrderId();
            OrderService.addOrder(orderId, orderDetails);

            UIHelper.showSuccess(this,
                    "Order created successfully! Order ID: " + orderId + "\nProceeding to payment...");

            // Clear cart
            cartItems.clear();
            updateCartTable();
            updateSummary();
            loadOrders();

            // Open payment view
            PaymentView.showPaymentView(total, discount, subtotal, orderDetails, orderId, this);
        } catch (Exception e) {
            UIHelper.showError(this, "Error creating order: " + e.getMessage());
        }
    }

    /**
     * Deletes selected order and associated payment.
     * Implements cascading delete: Payment first, then Order.
     */
    private void deleteOrder() {
        int row = orderTable.getSelectedRow();
        if (row == -1) {
            UIHelper.showWarning(this, "Please select an order to delete.");
            return;
        }

        String orderIdStr = orderTableModel.getValueAt(row, 0).toString();
        try {
            int orderId = Integer.parseInt(orderIdStr);

            // Confirm deletion
            if (!UIHelper.confirm(this, "Are you sure you want to delete Order ID " + orderId +
                    "?\n\nThis will also delete the associated payment.")) {
                return;
            }

            // Find and delete associated payment first (to maintain referential integrity)
            String[] payment = service.PaymentService.getPaymentByOrderId(orderId);
            if (payment != null) {
                int paymentId = Integer.parseInt(payment[0]);
                service.PaymentService.deletePaymentById(paymentId);
                // Logging handled in PaymentService
            }

            // Delete the order
            boolean deleted = OrderService.deleteOrder(orderId);
            if (deleted) {
                UIHelper.showSuccess(this, "Order ID " + orderId + " deleted successfully!");
                loadOrders();
            } else {
                UIHelper.showError(this, "Order ID " + orderId + " not found.");
            }
        } catch (NumberFormatException e) {
            UIHelper.showError(this, "Invalid order ID format.");
        } catch (Exception e) {
            UIHelper.showError(this, "Error deleting order: " + e.getMessage());
        }
    }

    /**
     * Searches for an order by ID.
     */
    private void searchOrder() {
        String idText = UIHelper.input(this, "Enter Order ID to search:", "Search Order");
        if (idText == null || idText.trim().isEmpty()) {
            return;
        }

        try {
            int orderId = Integer.parseInt(idText.trim());
            String[] order = OrderService.getOrderById(orderId);
            if (order != null) {
                String details = "Order ID: " + order[0] + "\n\n";
                details += OrderParser.formatForPlainText(order[1]);
                details += "\nTotal: " + UIHelper.formatCurrencyRM(OrderParser.calculateTotal(order[1]));
                UIHelper.showInfo(this, details);
                highlightOrderInTable(orderId);
            } else {
                UIHelper.showInfo(this, "Order ID " + orderId + " not found.");
            }
        } catch (NumberFormatException e) {
            UIHelper.showError(this, "Invalid order ID format.");
        }
    }

    /**
     * Highlights order in table.
     */
    private void highlightOrderInTable(int orderId) {
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            if (orderTableModel.getValueAt(i, 0).toString().equals(String.valueOf(orderId))) {
                orderTable.setRowSelectionInterval(i, i);
                orderTable.scrollRectToVisible(orderTable.getCellRect(i, 0, true));
                break;
            }
        }
    }

    /**
     * Shows the Order Management window.
     */
    public static void showOrderView() {
        SwingUtilities.invokeLater(() -> {
            if (currentInstance != null && currentInstance.isDisplayable()) {
                currentInstance.toFront();
                currentInstance.requestFocus();
                return;
            }
            try {
                currentInstance = new OrderView();
                currentInstance.setVisible(true);
            } catch (Exception e) {
                System.err.println("Error opening Order View: " + e.getMessage());
                UIHelper.showError(null, "Error opening Order Management: " + e.getMessage());
            }
        });
    }
}
