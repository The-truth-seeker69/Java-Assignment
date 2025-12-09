package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Receipt View - Modern JFrame UI for displaying receipts.
 * Shows a formatted receipt with all order and payment details.
 * 
 * @author Software Maintenance Team
 */
public class ReceiptView extends JFrame {

    /**
     * Constructor - Creates and displays a receipt.
     * 
     * @param total         The total amount before discount
     * @param discount      The discount applied
     * @param subtotal      The subtotal after discount
     * @param currDate      The current date/time string
     * @param items         Array of item names
     * @param itemPrices    Array of item prices
     * @param itemQuantity  Array of item quantities
     * @param numOfItems    Number of items
     * @param change        The change amount (for cash payments)
     * @param totalPaid     The total amount paid
     * @param orderID       The order ID
     * @param paymentID     The payment ID
     * @param paymentMethod The payment method
     */
    public ReceiptView(double total, double discount, double subtotal, String currDate, String[] items,
            double[] itemPrices, int[] itemQuantity, int numOfItems,
            double change, double totalPaid, int orderID,
            int paymentID, String paymentMethod) {
        initializeUI(total, discount, subtotal, currDate, items, itemPrices, itemQuantity,
                numOfItems, change, totalPaid, orderID, paymentID, paymentMethod);
    }

    /**
     * Initializes the receipt UI.
     */
    private void initializeUI(double total, double discount, double subtotal, String currDate, String[] items,
            double[] itemPrices, int[] itemQuantity, int numOfItems,
            double change, double totalPaid, int orderID,
            int paymentID, String paymentMethod) {
        setTitle("Receipt - Order #" + orderID);
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true); // Make receipt window appear on top

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header - properly aligned
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("TARUMT Grocery POS System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel receiptLabel = new JLabel("RECEIPT", JLabel.CENTER);
        receiptLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(Box.createVerticalStrut(5), BorderLayout.SOUTH);

        headerPanel.add(titlePanel, BorderLayout.NORTH);
        headerPanel.add(receiptLabel, BorderLayout.CENTER);
        headerPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        // Receipt details
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(new JLabel("Order ID:"), gbc);
        gbc.gridx = 1;
        detailsPanel.add(new JLabel(String.valueOf(orderID)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(new JLabel("Payment ID:"), gbc);
        gbc.gridx = 1;
        detailsPanel.add(new JLabel(String.valueOf(paymentID)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        detailsPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        detailsPanel.add(new JLabel(currDate), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        detailsPanel.add(new JLabel("Payment Method:"), gbc);
        gbc.gridx = 1;
        detailsPanel.add(new JLabel(paymentMethod), gbc);

        headerPanel.add(detailsPanel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Items table
        String[] columns = { "Item", "Qty", "Price (RM)", "Total (RM)" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < numOfItems; i++) {
            double itemTotal = itemPrices[i] * itemQuantity[i];
            tableModel.addRow(new Object[] {
                    items[i],
                    itemQuantity[i],
                    String.format("%.2f", itemPrices[i]),
                    String.format("%.2f", itemTotal)
            });
        }

        JTable itemsTable = new JTable(tableModel);
        itemsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        itemsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        itemsTable.setRowHeight(25);
        itemsTable.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Items"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Summary panel with detailed breakdown
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;

        int row = 0;

        // Total (before discount)
        gbc.gridx = 0;
        gbc.gridy = row;
        summaryPanel.add(new JLabel("Total:"), gbc);
        gbc.gridx = 1;
        JLabel totalLabel = new JLabel("RM " + String.format("%.2f", total));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryPanel.add(totalLabel, gbc);

        // Discount (if applied)
        if (discount > 0) {
            row++;
            gbc.gridx = 0;
            gbc.gridy = row;
            summaryPanel.add(new JLabel("Discount Applied:"), gbc);
            gbc.gridx = 1;
            JLabel discountLabel = new JLabel("- RM " + String.format("%.2f", discount));
            discountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            discountLabel.setForeground(new Color(0, 128, 0)); // Green color for discount
            summaryPanel.add(discountLabel, gbc);
        }

        // Grand Total (subtotal after discount)
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        summaryPanel.add(new JLabel("Grand Total:"), gbc);
        gbc.gridx = 1;
        JLabel grandTotalLabel = new JLabel("RM " + String.format("%.2f", subtotal));
        grandTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryPanel.add(grandTotalLabel, gbc);

        // Paid
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        summaryPanel.add(new JLabel("Paid:"), gbc);
        gbc.gridx = 1;
        JLabel paidLabel = new JLabel("RM " + String.format("%.2f", totalPaid));
        paidLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryPanel.add(paidLabel, gbc);

        // Change (if any)
        if (change > 0) {
            row++;
            gbc.gridx = 0;
            gbc.gridy = row;
            summaryPanel.add(new JLabel("Change:"), gbc);
            gbc.gridx = 1;
            JLabel changeLabel = new JLabel("RM " + String.format("%.2f", change));
            changeLabel.setFont(new Font("Arial", Font.BOLD, 14));
            summaryPanel.add(changeLabel, gbc);
        }

        // Combine summary and button in a panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(summaryPanel, BorderLayout.CENTER);

        JButton printButton = new JButton("Close");
        printButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(printButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Shows the receipt window.
     */
    public static void showReceipt(double total, double discount, double subtotal, String currDate, String[] items,
            double[] itemPrices, int[] itemQuantity, int numOfItems,
            double change, double totalPaid, int orderID,
            int paymentID, String paymentMethod) {
        // Create and show receipt directly (not using invokeLater to prevent multiple
        // instances)
        ReceiptView view = new ReceiptView(total, discount, subtotal, currDate, items, itemPrices,
                itemQuantity, numOfItems, change, totalPaid,
                orderID, paymentID, paymentMethod);
        view.setVisible(true);
        view.toFront(); // Bring to front
        view.requestFocus(); // Request focus
    }
}
