package util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * UI Helper Utility
 * 
 * Provides common UI helper methods to simplify view code.
 */
public class UIHelper {
    
    /**
     * Shows an error message dialog.
     */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Shows a warning message dialog.
     */
    public static void showWarning(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Shows an information message dialog.
     */
    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows a success message dialog.
     */
    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows a confirmation dialog.
     * 
     * @return true if user clicked Yes, false otherwise
     */
    public static boolean confirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "Confirm", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Shows an input dialog.
     * 
     * @return User input string, or null if cancelled
     */
    public static String input(Component parent, String message, String title) {
        return JOptionPane.showInputDialog(parent, message, title, JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Creates a standard button panel with vertical layout.
     */
    public static JPanel createVerticalButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int i = 0; i < buttons.length; i++) {
            if (i > 0) {
                panel.add(Box.createVerticalStrut(10));
            }
            buttons[i].setPreferredSize(new Dimension(150, 35));
            panel.add(buttons[i]);
        }
        
        return panel;
    }
    
    /**
     * Creates a standard table with common settings.
     */
    public static JTable createTable(String[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        
        return table;
    }
    
    /**
     * Formats currency amount.
     */
    public static String formatCurrency(double amount) {
        return String.format("%.2f", amount);
    }
    
    /**
     * Formats currency with RM prefix.
     */
    public static String formatCurrencyRM(double amount) {
        return "RM " + formatCurrency(amount);
    }
}

