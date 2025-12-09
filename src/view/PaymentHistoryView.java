package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import service.PaymentService;
import service.OrderService;
import java.util.ArrayList;

/**
 * Payment History View - Modern JFrame UI for viewing payment history and
 * reports.
 * Provides a user-friendly interface for viewing payment records and
 * statistics.
 * 
 * @author Software Maintenance Team
 */
public class PaymentHistoryView extends JFrame {

    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;
    private JButton refreshButton;
    private JButton reportButton;
    private JLabel totalPaymentsLabel;
    private JLabel totalAmountLabel;
    private JLabel cashCountLabel;
    private JLabel cardCountLabel;

    /**
     * Constructor - Initializes the Payment History UI.
     */
    public PaymentHistoryView() {
        initializeUI();
        loadPayments();
        updateReport();

        // Add window listener to show main menu when closed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                Login.showMainMenu();
            }
        });
    }

    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        setTitle("Payment History & Report - TARUMT Grocery POS System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Create main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Payment History & Report", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Report summary panel
        JPanel reportPanel = createReportPanel();
        mainPanel.add(reportPanel, BorderLayout.NORTH);

        // Payment table with comprehensive details
        String[] columnNames = { "Payment ID", "Order ID", "Total (RM)", "Discount (RM)", "Subtotal (RM)",
                "Payment Method" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        paymentTable = new JTable(tableModel);
        paymentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paymentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        paymentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        paymentTable.setRowHeight(25);

        // Set column widths
        paymentTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        paymentTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        paymentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        paymentTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        paymentTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        paymentTable.getColumnModel().getColumn(5).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(paymentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Payment Records"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        searchButton = new JButton("Search Payment");
        searchButton.addActionListener(e -> searchPayment());
        reportButton = new JButton("View Detailed Report");
        reportButton.addActionListener(e -> showDetailedReport());
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadPayments();
            updateReport();
        });

        buttonPanel.add(searchButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(refreshButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Creates the report summary panel showing key statistics.
     */
    private JPanel createReportPanel() {
        JPanel reportPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        reportPanel.setBorder(BorderFactory.createTitledBorder("Payment Summary Report"));

        reportPanel.add(new JLabel("Total Payments:", JLabel.RIGHT));
        totalPaymentsLabel = new JLabel("0");
        totalPaymentsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        reportPanel.add(totalPaymentsLabel);

        reportPanel.add(new JLabel("Total Amount:", JLabel.RIGHT));
        totalAmountLabel = new JLabel("RM 0.00");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        reportPanel.add(totalAmountLabel);

        reportPanel.add(new JLabel("Cash Payments:", JLabel.RIGHT));
        cashCountLabel = new JLabel("0");
        cashCountLabel.setFont(new Font("Arial", Font.BOLD, 12));
        reportPanel.add(cashCountLabel);

        reportPanel.add(new JLabel("Credit Card Payments:", JLabel.RIGHT));
        cardCountLabel = new JLabel("0");
        cardCountLabel.setFont(new Font("Arial", Font.BOLD, 12));
        reportPanel.add(cardCountLabel);

        return reportPanel;
    }

    /**
     * Updates the report statistics.
     */
    private void updateReport() {
        ArrayList<String[]> payments = PaymentService.getAllPayments();

        int totalCount = payments.size();
        double totalAmount = 0;
        int cashCount = 0;
        int cardCount = 0;

        for (String[] payment : payments) {
            // Use subtotal (total - discount) for total amount calculations
            double amount;
            if (payment.length >= 6) {
                double total = Double.parseDouble(payment[2]);
                double discount = Double.parseDouble(payment[3]);
                amount = total - discount; // Subtotal
            } else {
                amount = Double.parseDouble(payment[1]);
            }
            totalAmount += amount;

            String method = payment[payment.length - 1].replace("_", " ");
            if (method.equals("Cash")) {
                cashCount++;
            } else if (method.equals("Credit Card")) {
                cardCount++;
            }
        }

        totalPaymentsLabel.setText(String.valueOf(totalCount));
        totalAmountLabel.setText("RM " + String.format("%.2f", totalAmount));
        cashCountLabel.setText(String.valueOf(cashCount));
        cardCountLabel.setText(String.valueOf(cardCount));
    }

    /**
     * Loads payments from service and displays them in the table with comprehensive
     * details.
     */
    private void loadPayments() {
        tableModel.setRowCount(0);

        ArrayList<String[]> payments = PaymentService.getAllPayments();
        for (String[] payment : payments) {
            String formattedMethod = payment[payment.length - 1].replace("_", " ");

            // New format: [paymentId, orderId, total, discount, subtotal, totalPaid,
            // paymentMethod]
            if (payment.length >= 6) {
                double total = Double.parseDouble(payment[2]);
                double discount = Double.parseDouble(payment[3]);
                double subtotal = total - discount;

                tableModel.addRow(new Object[] {
                        payment[0], // Payment ID
                        payment[1], // Order ID
                        String.format("%.2f", total), // Total
                        String.format("%.2f", discount), // Discount
                        String.format("%.2f", subtotal), // Subtotal
                        formattedMethod // Payment Method
                });
            }
            // Old format: [paymentId, amount, paymentMethod] - backward compatibility
            else {
                double amount = Double.parseDouble(payment[1]);
                tableModel.addRow(new Object[] {
                        payment[0], // Payment ID
                        "N/A", // Order ID (unknown)
                        String.format("%.2f", amount), // Total
                        "0.00", // Discount (unknown)
                        String.format("%.2f", amount), // Subtotal
                        formattedMethod // Payment Method
                });
            }
        }
    }

    /**
     * Shows a detailed report dialog with comprehensive statistics and order
     * details.
     */
    private void showDetailedReport() {
        ArrayList<String[]> payments = PaymentService.getAllPayments();

        int totalCount = payments.size();
        double totalAmount = 0;
        double totalDiscount = 0;
        int cashCount = 0;
        int cardCount = 0;
        double cashAmount = 0;
        double cardAmount = 0;

        for (String[] payment : payments) {
            // Use subtotal (total - discount) for calculations
            double amount;
            if (payment.length >= 6) {
                double total = Double.parseDouble(payment[2]);
                double discount = Double.parseDouble(payment[3]);
                amount = total - discount; // Subtotal
            } else {
                amount = Double.parseDouble(payment[1]);
            }
            totalAmount += amount;

            if (payment.length >= 6) {
                totalDiscount += Double.parseDouble(payment[3]);
            }

            String method = payment[payment.length - 1].replace("_", " ");
            if (method.equals("Cash")) {
                cashCount++;
                cashAmount += amount;
            } else if (method.equals("Credit Card")) {
                cardCount++;
                cardAmount += amount;
            }
        }

        double cashPercentage = totalCount > 0 ? (cashCount * 100.0 / totalCount) : 0;
        double cardPercentage = totalCount > 0 ? (cardCount * 100.0 / totalCount) : 0;
        double cashAmountPercentage = totalAmount > 0 ? (cashAmount * 100.0 / totalAmount) : 0;
        double cardAmountPercentage = totalAmount > 0 ? (cardAmount * 100.0 / totalAmount) : 0;

        // Build comprehensive report
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════\n");
        report.append("          PAYMENT HISTORY DETAILED REPORT\n");
        report.append("═══════════════════════════════════════════════════════\n\n");

        report.append("OVERALL STATISTICS:\n");
        report.append("───────────────────────────────────────────────────────\n");
        report.append(String.format("Total Payments:              %d\n", totalCount));
        report.append(String.format("Total Amount Collected:      RM %.2f\n", totalAmount));
        report.append(String.format("Total Discount Given:        RM %.2f\n", totalDiscount));
        report.append(String.format("Net Revenue:                 RM %.2f\n\n", totalAmount - totalDiscount));

        report.append("PAYMENT METHOD BREAKDOWN:\n");
        report.append("───────────────────────────────────────────────────────\n");
        report.append(String.format("Cash Payments:               %d (%.1f%%)\n", cashCount, cashPercentage));
        report.append(
                String.format("Cash Amount:                 RM %.2f (%.1f%%)\n\n", cashAmount, cashAmountPercentage));
        report.append(String.format("Credit Card Payments:        %d (%.1f%%)\n", cardCount, cardPercentage));
        report.append(
                String.format("Credit Card Amount:          RM %.2f (%.1f%%)\n\n", cardAmount, cardAmountPercentage));

        if (totalCount > 0) {
            report.append("AVERAGE TRANSACTION:\n");
            report.append("───────────────────────────────────────────────────────\n");
            report.append(String.format("Average Amount:              RM %.2f\n", totalAmount / totalCount));
            report.append(String.format("Average Discount:            RM %.2f\n\n", totalDiscount / totalCount));
        }

        report.append("PAYMENT DETAILS:\n");
        report.append("───────────────────────────────────────────────────────\n");
        report.append(String.format("%-8s %-8s %-12s %-12s %-12s %-15s\n",
                "Pay ID", "Order ID", "Total", "Discount", "Subtotal", "Method"));
        report.append("───────────────────────────────────────────────────────\n");

        for (String[] payment : payments) {
            String method = payment[payment.length - 1].replace("_", " ");
            if (payment.length >= 6) {
                double total = Double.parseDouble(payment[2]);
                double discount = Double.parseDouble(payment[3]);
                double subtotal = total - discount;
                report.append(String.format("%-8s %-8s %-12.2f %-12.2f %-12.2f %-15s\n",
                        payment[0], payment[1], total, discount, subtotal, method));
            } else {
                double amount = Double.parseDouble(payment[1]);
                report.append(String.format("%-8s %-8s %-12.2f %-12s %-12.2f %-15s\n",
                        payment[0], "N/A", amount, "N/A", amount, method));
            }
        }

        report.append("\n═══════════════════════════════════════════════════════\n");

        JTextArea textArea = new JTextArea(report.toString());
        textArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(this, scrollPane, "Detailed Payment Report", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Searches for a payment by ID and shows order details.
     */
    private void searchPayment() {
        String idText = JOptionPane.showInputDialog(this, "Enter Payment ID to search:", "Search Payment",
                JOptionPane.QUESTION_MESSAGE);

        if (idText == null || idText.trim().isEmpty()) {
            return;
        }

        try {
            int paymentId = Integer.parseInt(idText.trim());
            String[] payment = PaymentService.getPaymentById(paymentId);

            if (payment != null) {
                String formattedMethod = payment[payment.length - 1].replace("_", " ");

                StringBuilder details = new StringBuilder();
                details.append("═══════════════════════════════════════\n");
                details.append("         PAYMENT DETAILS\n");
                details.append("═══════════════════════════════════════\n\n");

                details.append(String.format("Payment ID:     %s\n", payment[0]));

                if (payment.length >= 6) {
                    int orderId = Integer.parseInt(payment[1]);
                    double total = Double.parseDouble(payment[2]);
                    double discount = Double.parseDouble(payment[3]);
                    double subtotal = total - discount;

                    details.append(String.format("Order ID:       %s\n", payment[1]));
                    details.append(String.format("Total:          RM %.2f\n", total));
                    details.append(String.format("Discount:       RM %.2f\n", discount));
                    details.append(String.format("Subtotal:       RM %.2f\n", subtotal));
                    details.append(String.format("Payment Method: %s\n\n", formattedMethod));

                    // Get order details
                    String[] order = OrderService.getOrderById(orderId);
                    if (order != null) {
                        details.append("ORDER DETAILS:\n");
                        details.append("───────────────────────────────────────\n");
                        String[] orderItems = order[1].split(",");
                        for (String item : orderItems) {
                            item = item.trim();
                            if (item.isEmpty())
                                continue;

                            String productName;
                            double itemPrice;
                            int quantity;

                            // Check if using pipe delimiter (new format) or space delimiter (old format)
                            if (item.contains("|")) {
                                // New format: productName|price|quantity
                                String[] parts = item.split("\\|", 3);
                                if (parts.length == 3) {
                                    productName = parts[0];
                                    itemPrice = Double.parseDouble(parts[1]);
                                    quantity = Integer.parseInt(parts[2]);
                                } else {
                                    continue; // Skip invalid format
                                }
                            } else {
                                // Old format: productName price quantity (backward compatibility)
                                String[] parts = item.split(" ");
                                if (parts.length >= 3) {
                                    try {
                                        quantity = Integer.parseInt(parts[parts.length - 1]);
                                        itemPrice = Double.parseDouble(parts[parts.length - 2]);
                                        productName = String.join(" ",
                                                java.util.Arrays.copyOfRange(parts, 0, parts.length - 2));
                                    } catch (NumberFormatException e) {
                                        continue; // Skip invalid entries
                                    }
                                } else {
                                    continue; // Skip invalid format
                                }
                            }

                            details.append(String.format("%s x%d @ RM %.2f = RM %.2f\n",
                                    productName, quantity, itemPrice, itemPrice * quantity));
                        }
                    }
                } else {
                    double amount = Double.parseDouble(payment[1]);
                    details.append(String.format("Amount:         RM %.2f\n", amount));
                    details.append(String.format("Payment Method: %s\n", formattedMethod));
                }

                details.append("\n═══════════════════════════════════════\n");

                JOptionPane.showMessageDialog(this, details.toString(), "Payment Found",
                        JOptionPane.INFORMATION_MESSAGE);

                // Highlight in table
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).toString().equals(String.valueOf(paymentId))) {
                        paymentTable.setRowSelectionInterval(i, i);
                        paymentTable.scrollRectToVisible(paymentTable.getCellRect(i, 0, true));
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Payment ID " + paymentId + " not found.", "Not Found",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid payment ID format.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Shows the Payment History window.
     */
    public static void showPaymentHistoryView() {
        SwingUtilities.invokeLater(() -> {
            PaymentHistoryView view = new PaymentHistoryView();
            view.setVisible(true);
        });
    }
}
