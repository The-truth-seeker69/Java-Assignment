package view;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import service.PaymentService;
import model.CashPayment;
import model.CreditCardPayment;
import util.OrderParser;
import util.OrderParser.OrderItem;
import util.UIHelper;

/**
 * Payment View
 * Handles payment processing for orders.
 */
public class PaymentView extends JFrame {

    private double total, discount, subtotal;
    private String orderDetails;
    private int orderId;
    private Payment currentPayment;
    private boolean showingReceipt = false;

    // UI Components
    private JLabel subtotalLabel, changeLabel;
    private JTextField amountField, cardNumberField;
    private JButton cashButton, cardButton, receiptButton;

    /**
     * Payment information holder.
     */
    private class Payment {
        int paymentId;
        double amount;
        String method;
        double change;

        Payment(int paymentId, double amount, String method, double change) {
            this.paymentId = paymentId;
            this.amount = amount;
            this.method = method;
            this.change = change;
        }
    }

    /**
     * Constructor.
     */
    public PaymentView(double total, double discount, double subtotal, String orderDetails, int orderId,
            JFrame parentFrame) {
        this.total = total;
        this.discount = discount;
        this.subtotal = subtotal;
        this.orderDetails = orderDetails;
        this.orderId = orderId;
        initializeUI();
        setupWindowListener(parentFrame);
    }

    /**
     * Sets up window listener.
     */
    private void setupWindowListener(JFrame parentFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (!showingReceipt) {
                    if (parentFrame != null) {
                        parentFrame.dispose();
                    }
                    Login.showMainMenu();
                }
            }
        });
    }

    /**
     * Initializes UI.
     */
    private void initializeUI() {
        setTitle("Payment Processing - TARUMT Grocery POS System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Payment Processing", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Payment panel
        mainPanel.add(createPaymentPanel(), BorderLayout.CENTER);
        add(mainPanel);
    }

    /**
     * Creates payment panel.
     */
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Payment Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Subtotal
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Subtotal:"), gbc);
        gbc.gridx = 1;
        subtotalLabel = new JLabel(UIHelper.formatCurrencyRM(subtotal));
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(subtotalLabel, gbc);

        // Payment buttons
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        cashButton = new JButton("Cash Payment");
        cashButton.setPreferredSize(new Dimension(150, 40));
        cashButton.addActionListener(e -> processCashPayment());
        cardButton = new JButton("Credit Card Payment");
        cardButton.setPreferredSize(new Dimension(150, 40));
        cardButton.addActionListener(e -> processCardPayment());
        buttonPanel.add(cashButton);
        buttonPanel.add(cardButton);
        panel.add(buttonPanel, gbc);

        // Cash fields
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Amount Paid (Cash):"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(15);
        amountField.setEnabled(false);
        panel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Change:"), gbc);
        gbc.gridx = 1;
        changeLabel = new JLabel("RM 0.00");
        changeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(changeLabel, gbc);

        // Card field
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Card Number (12 digits):"), gbc);
        gbc.gridx = 1;
        cardNumberField = new JTextField(15);
        cardNumberField.setEnabled(false);
        panel.add(cardNumberField, gbc);

        // Receipt button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        receiptButton = new JButton("Print Receipt");
        receiptButton.setEnabled(false);
        receiptButton.addActionListener(e -> printReceipt());
        panel.add(receiptButton, gbc);

        return panel;
    }

    /**
     * Processes cash payment.
     */
    private void processCashPayment() {
        amountField.setEnabled(true);
        cardNumberField.setEnabled(false);
        cardNumberField.setText("");
        changeLabel.setText("RM 0.00");

        String amountText = UIHelper.input(this,
                "Enter amount paid (must be >= " + UIHelper.formatCurrencyRM(subtotal) + "):",
                "Cash Payment");
        if (amountText == null || amountText.trim().isEmpty()) {
            return;
        }

        try {
            double amountPaid = Double.parseDouble(amountText.trim());
            if (amountPaid < subtotal) {
                UIHelper.showError(this, "Amount paid cannot be less than subtotal!");
                return;
            }

            amountField.setText(UIHelper.formatCurrency(amountPaid));
            double change = amountPaid - subtotal;
            changeLabel.setText(UIHelper.formatCurrencyRM(change));

            // Process payment
            int paymentId = PaymentService.getNextPaymentId();
            CashPayment cashPayment = new CashPayment(amountPaid, paymentId);
            cashPayment.calculateBalance(amountPaid, subtotal);
            cashPayment.paymentSuccessful(amountPaid, paymentId);

            PaymentService.addPayment(paymentId, orderId, total, discount, subtotal, amountPaid, "Cash");
            currentPayment = new Payment(paymentId, amountPaid, "Cash", change);
            receiptButton.setEnabled(true);

            UIHelper.showInfo(this, "Payment successful!\nPayment ID: " + paymentId +
                    "\nChange: " + UIHelper.formatCurrencyRM(change) + "\n\nClick 'Print Receipt' to view receipt.");
        } catch (NumberFormatException e) {
            UIHelper.showError(this, "Invalid amount format.");
        } catch (Exception e) {
            UIHelper.showError(this, "Error processing payment: " + e.getMessage());
        }
    }

    /**
     * Processes credit card payment.
     */
    private void processCardPayment() {
        cardNumberField.setEnabled(true);
        amountField.setEnabled(false);
        amountField.setText("");
        changeLabel.setText("RM 0.00");

        String cardNumber = UIHelper.input(this, "Enter credit card number (12 digits):", "Credit Card Payment");
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            return;
        }

        cardNumber = cardNumber.trim();
        cardNumberField.setText(cardNumber);

        // Validate card
        CreditCardPayment testPayment = new CreditCardPayment(subtotal, 0, cardNumber);
        if (!testPayment.checkCardID(cardNumber)) {
            UIHelper.showError(this, "Invalid credit card number. Must be exactly 12 digits.");
            return;
        }

        // Process payment
        try {
            int paymentId = PaymentService.getNextPaymentId();
            CreditCardPayment cardPayment = new CreditCardPayment(subtotal, paymentId, cardNumber);
            cardPayment.paymentSuccessful(subtotal, paymentId);

            PaymentService.addPayment(paymentId, orderId, total, discount, subtotal, subtotal, "Credit Card");
            currentPayment = new Payment(paymentId, subtotal, "Credit Card", 0);
            receiptButton.setEnabled(true);

            UIHelper.showInfo(this, "Payment successful!\nPayment ID: " + paymentId +
                    "\n\nClick 'Print Receipt' to view receipt.");
        } catch (Exception e) {
            UIHelper.showError(this, "Error processing payment: " + e.getMessage());
        }
    }

    /**
     * Prints receipt.
     */
    private void printReceipt() {
        if (currentPayment == null) {
            UIHelper.showError(this, "No payment processed yet.");
            return;
        }

        // Parse order items
        OrderItem[] items = OrderParser.parseOrderDetails(orderDetails);
        String[] itemNames = new String[items.length];
        double[] itemPrices = new double[items.length];
        int[] itemQuantities = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            itemNames[i] = items[i].productName;
            itemPrices[i] = items[i].price;
            itemQuantities[i] = items[i].quantity;
        }

        // Create date string
        String dateStr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

        // Close payment window
        showingReceipt = true;
        dispose();

        // Show receipt
        ReceiptView.showReceipt(total, discount, subtotal, dateStr, itemNames, itemPrices,
                itemQuantities, items.length, currentPayment.change, currentPayment.amount,
                orderId, currentPayment.paymentId, currentPayment.method);
    }

    /**
     * Shows payment view.
     */
    public static void showPaymentView(double total, double discount, double subtotal,
            String orderDetails, int orderId, JFrame parentFrame) {
        SwingUtilities.invokeLater(() -> {
            PaymentView view = new PaymentView(total, discount, subtotal, orderDetails, orderId, parentFrame);
            view.setVisible(true);
        });
    }
}
