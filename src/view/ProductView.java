package view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import service.ProductService;
import util.UIHelper;

/**
 * Product Management View
 * Handles product CRUD operations.
 */
public class ProductView extends JFrame {

    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton addButton, modifyButton, deleteButton, refreshButton;

    /**
     * Constructor.
     */
    public ProductView() {
        initializeUI();
        loadProducts();
        setupWindowListener();
    }

    /**
     * Sets up window listener.
     */
    private void setupWindowListener() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                Login.showMainMenu();
            }
        });
    }

    /**
     * Initializes UI.
     */
    private void initializeUI() {
        setTitle("Product Management - TARUMT Grocery POS System");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Product Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = { "Product Code", "Product Name", "Price (RM)" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setFont(new Font("Arial", Font.PLAIN, 12));
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        productTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(productTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        mainPanel.add(createButtonPanel(), BorderLayout.EAST);
        add(mainPanel);
    }

    /**
     * Creates button panel.
     */
    private JPanel createButtonPanel() {
        addButton = new JButton("Add Product");
        modifyButton = new JButton("Modify Product");
        deleteButton = new JButton("Delete Product");
        refreshButton = new JButton("Refresh");

        addButton.addActionListener(e -> addProduct());
        modifyButton.addActionListener(e -> modifyProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        refreshButton.addActionListener(e -> loadProducts());

        return UIHelper.createVerticalButtonPanel(addButton, modifyButton, deleteButton, refreshButton);
    }

    /**
     * Loads products into table.
     */
    private void loadProducts() {
        ProductService.reloadProducts();
        tableModel.setRowCount(0);

        ArrayList<String[]> products = ProductService.getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i);
            tableModel.addRow(new Object[] {
                    i + 1,
                    product[0],
                    UIHelper.formatCurrency(Double.parseDouble(product[1]))
            });
        }
    }

    /**
     * Shows dialog to add product.
     */
    private void addProduct() {
        JTextField nameField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JPanel panel = createProductInputPanel(nameField, priceField);
        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();

            if (!validateProductInput(name, priceText)) {
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                name = capitalizeName(name);

                if (ProductService.productNameExists(name)) {
                    UIHelper.showError(this, "Product name already exists! Please use a different name.");
                    return;
                }

                if (UIHelper.confirm(this, "Add product: " + name + " at " + UIHelper.formatCurrencyRM(price) + "?")) {
                    ProductService.addProduct(name, price);
                    UIHelper.showSuccess(this, "Product added successfully!");
                    loadProducts();
                }
            } catch (NumberFormatException e) {
                UIHelper.showError(this, "Invalid price format. Please enter a valid number.");
            } catch (Exception e) {
                UIHelper.showError(this, "Error adding product: " + e.getMessage());
            }
        }
    }

    /**
     * Shows dialog to modify product.
     */
    private void modifyProduct() {
        int row = productTable.getSelectedRow();
        if (row == -1) {
            UIHelper.showWarning(this, "Please select a product to modify.");
            return;
        }

        String oldName = (String) tableModel.getValueAt(row, 1);
        String oldPrice = (String) tableModel.getValueAt(row, 2);

        JTextField nameField = new JTextField(oldName, 20);
        JTextField priceField = new JTextField(oldPrice, 20);
        JPanel panel = createProductInputPanel(nameField, priceField);
        int result = JOptionPane.showConfirmDialog(this, panel, "Modify Product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String newName = nameField.getText().trim();
            String priceText = priceField.getText().trim();

            if (!validateProductInput(newName, priceText)) {
                return;
            }

            try {
                double newPrice = Double.parseDouble(priceText);
                newName = capitalizeName(newName);

                // Check for duplicate (excluding current product)
                if (!newName.equalsIgnoreCase(oldName) && ProductService.productNameExists(newName, oldName)) {
                    UIHelper.showError(this, "Product name already exists! Please use a different name.");
                    return;
                }

                if (UIHelper.confirm(this, "Modify product from:\n" + oldName + " (" + oldPrice + ")\nto:\n" +
                        newName + " (" + UIHelper.formatCurrencyRM(newPrice) + ")?")) {
                    boolean success = ProductService.modifyProduct(oldName, newName, newPrice);
                    if (success) {
                        UIHelper.showSuccess(this, "Product modified successfully!");
                        loadProducts();
                    } else {
                        UIHelper.showError(this, "Product not found. Please refresh and try again.");
                    }
                }
            } catch (NumberFormatException e) {
                UIHelper.showError(this, "Invalid price format. Please enter a valid number.");
            } catch (Exception e) {
                UIHelper.showError(this, "Error modifying product: " + e.getMessage());
            }
        }
    }

    /**
     * Deletes selected product.
     */
    private void deleteProduct() {
        int row = productTable.getSelectedRow();
        if (row == -1) {
            UIHelper.showWarning(this, "Please select a product to delete.");
            return;
        }

        String productName = (String) tableModel.getValueAt(row, 1);
        if (UIHelper.confirm(this, "Are you sure you want to delete:\n" + productName + "?")) {
            try {
                boolean success = ProductService.deleteProduct(productName);
                if (success) {
                    UIHelper.showSuccess(this, "Product deleted successfully!");
                    loadProducts();
                } else {
                    UIHelper.showError(this, "Product not found. Please refresh and try again.");
                }
            } catch (Exception e) {
                UIHelper.showError(this, "Error deleting product: " + e.getMessage());
            }
        }
    }

    /**
     * Creates product input panel.
     */
    private JPanel createProductInputPanel(JTextField nameField, JTextField priceField) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Price (RM):"), gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        return panel;
    }

    /**
     * Validates product input.
     */
    private boolean validateProductInput(String name, String priceText) {
        if (name.isEmpty()) {
            UIHelper.showError(this, "Please enter a product name.");
            return false;
        }
        if (priceText.isEmpty()) {
            UIHelper.showError(this, "Please enter a product price.");
            return false;
        }
        try {
            double price = Double.parseDouble(priceText);
            if (price <= 0) {
                UIHelper.showError(this, "Price must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            UIHelper.showError(this, "Invalid price format.");
            return false;
        }
        return true;
    }

    /**
     * Capitalizes product name.
     */
    private String capitalizeName(String name) {
        if (name.isEmpty())
            return name;
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    /**
     * Shows product view.
     */
    public static void showProductView() {
        SwingUtilities.invokeLater(() -> {
            ProductView view = new ProductView();
            view.setVisible(true);
        });
    }
}
