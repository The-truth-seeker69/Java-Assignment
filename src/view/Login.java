package view;

import javax.swing.*;
import java.awt.*;
import model.User;
import service.UserService;
import util.AuditLogger;
import util.SessionManager;

public class Login {

    private static JFrame mainMenuFrame = null; // Track main menu instance to prevent duplicates

    public static void main(String[] args) {
        AuditLogger.log("System started");
        showLogin();
    }

    public static void showLogin() {
        JFrame frame = new JFrame("Login");
        frame.setSize(380, 225);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 25);
        JTextField userText = new JTextField();
        userText.setBounds(120, 20, 180, 25);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 60, 80, 25);
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(120, 60, 180, 25);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 100, 100, 30);

        loginButton.addActionListener(e -> {
            String usernameInput = userText.getText();
            String passwordInput = new String(passwordText.getPassword());

            // Authenticate against database using UserService
            User authenticatedUser = UserService.authenticate(usernameInput, passwordInput);

            if (authenticatedUser != null) {
                SessionManager.setCurrentUser(authenticatedUser);
                AuditLogger.logf("\"%s\" Logged into the system", authenticatedUser.getUsername());
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                // after login show the menu
                showMainMenu();
            } else {
                AuditLogger.logf("Failed login attempt for username: %s", usernameInput);
                JOptionPane.showMessageDialog(frame, "Invalid Login Credentials!");
                userText.setText("");
                passwordText.setText("");
            }
        });

        frame.add(userLabel);
        frame.add(userText);
        frame.add(passwordLabel);
        frame.add(passwordText);
        frame.add(loginButton);

        frame.setVisible(true);
    }

    /**
     * Shows the main menu after successful login.
     * Provides access to all system modules.
     * Prevents duplicate main menu windows from opening.
     */
    public static void showMainMenu() {
        // Check if main menu is already open and visible
        if (mainMenuFrame != null && mainMenuFrame.isDisplayable()) {
            mainMenuFrame.toFront();
            mainMenuFrame.requestFocus();
            return;
        }

        // Create new main menu frame
        JFrame frame = new JFrame("TARUMT Grocery POS System");
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        mainMenuFrame = frame; // Store reference

        // Main panel with padding and layout
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Buttons for modules - styled with better appearance
        JButton orderBtn = new JButton("1. Order Management");
        orderBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        orderBtn.setPreferredSize(new Dimension(200, 50));

        JButton productBtn = new JButton("2. Product Management");
        productBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        productBtn.setPreferredSize(new Dimension(200, 50));

        JButton memberBtn = new JButton("3. Member Management");
        memberBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        memberBtn.setPreferredSize(new Dimension(200, 50));

        JButton paymentBtn = new JButton("4. Payment History");
        paymentBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        paymentBtn.setPreferredSize(new Dimension(200, 50));

        JButton logoutBtn = new JButton("0. Logout");
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutBtn.setPreferredSize(new Dimension(200, 50));

        // Button actions - Open respective module views
        orderBtn.addActionListener(e -> {
            OrderView.showOrderView();
        });

        productBtn.addActionListener(e -> {
            ProductView.showProductView();
        });

        memberBtn.addActionListener(e -> {
            MemberView.showMemberView();
        });

        paymentBtn.addActionListener(e -> {
            PaymentHistoryView.showPaymentHistoryView();
        });

        logoutBtn.addActionListener(e -> {
            String username = SessionManager.getCurrentUsername();
            AuditLogger.logf("\"%s\" Logged out of the system", username);
            SessionManager.clearSession();
            JOptionPane.showMessageDialog(frame,
                    "Thanks for using TARUMT Grocery POS System.\nHope to see you next time.");
            mainMenuFrame = null; // Clear reference before disposing
            frame.dispose();
        });

        // Clear reference when frame is closed
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                mainMenuFrame = null;
            }
        });

        mainPanel.add(orderBtn);
        mainPanel.add(productBtn);
        mainPanel.add(memberBtn);
        mainPanel.add(paymentBtn);
        mainPanel.add(logoutBtn);

        // Add main panel to frame using BorderLayout
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
