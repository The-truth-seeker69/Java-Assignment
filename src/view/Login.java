package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.User;

public class Login {

    public static void main(String[] args) {
        showLogin();
    }
    public static void showLogin() {
        User defaultUser = new User("admin", "1234");

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

            if (defaultUser.equalsUsername(usernameInput) && defaultUser.equalsPassword(passwordInput)) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose(); 
                //after login show the menu
                showMainMenu();
            } else {
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


    public static void showMainMenu() {
        JFrame frame = new JFrame("TARUMT Grocery POS System");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1, 10, 10));
        frame.setLocationRelativeTo(null); 

        // Buttons for modules
        JButton orderBtn = new JButton("1. Order");
        JButton productBtn = new JButton("2. Product");
        JButton memberBtn = new JButton("3. Member");
        JButton paymentBtn = new JButton("4. Payment");
        JButton logoutBtn = new JButton("0. Logout");

        // Button actions
        orderBtn.addActionListener(e -> {
            // OrderModule.orderModule();
            JOptionPane.showMessageDialog(frame, "Order Module called");
        });

        productBtn.addActionListener(e -> {
            // ProductModule.productModule();
            JOptionPane.showMessageDialog(frame, "Product Module called");
        });

        memberBtn.addActionListener(e -> {
            // MemberModule.memberModule();
            JOptionPane.showMessageDialog(frame, "Member Module called");
        });

        paymentBtn.addActionListener(e -> {
            // PaymentModule.paymentModule();
            JOptionPane.showMessageDialog(frame, "Payment Module called");
        });

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Thanks for using TARUMT Grocery POS System.\nHope to see you next time.");
            frame.dispose();
        });

        frame.add(orderBtn);
        frame.add(productBtn);
        frame.add(memberBtn);
        frame.add(paymentBtn);
        frame.add(logoutBtn);

        frame.setVisible(true);
    }
}
