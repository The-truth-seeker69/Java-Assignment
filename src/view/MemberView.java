package view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import service.MemberService;
import util.*;

/**
 * Member Management View
 * Handles member CRUD operations and search.
 */
public class MemberView extends JFrame {

    private JTable memberTable;
    private DefaultTableModel tableModel;
    private JButton addButton, modifyButton, deleteButton, searchButton, refreshButton;

    /**
     * Constructor.
     */
    public MemberView() {
        initializeUI();
        loadMembers();
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
        setTitle("Member Management - TARUMT Grocery POS System");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Member Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = { "Member ID", "Name", "Gender", "Age" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        memberTable = new JTable(tableModel);
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberTable.setFont(new Font("Arial", Font.PLAIN, 12));
        memberTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        memberTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(memberTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        mainPanel.add(createButtonPanel(), BorderLayout.EAST);
        add(mainPanel);
    }

    /**
     * Creates button panel.
     */
    private JPanel createButtonPanel() {
        addButton = new JButton("Add Member");
        modifyButton = new JButton("Modify Member");
        deleteButton = new JButton("Delete Member");
        searchButton = new JButton("Search Member");
        refreshButton = new JButton("Refresh");

        addButton.addActionListener(e -> addMember());
        modifyButton.addActionListener(e -> modifyMember());
        deleteButton.addActionListener(e -> deleteMember());
        searchButton.addActionListener(e -> searchMember());
        refreshButton.addActionListener(e -> loadMembers());

        return UIHelper.createVerticalButtonPanel(addButton, modifyButton, deleteButton, searchButton, refreshButton);
    }

    /**
     * Loads members into table.
     */
    private void loadMembers() {
        tableModel.setRowCount(0);
        for (String[] member : MemberService.getAllMembers()) {
            String formattedName = member[1].replace("_", " ");
            tableModel.addRow(new Object[] { member[0], formattedName, member[2], member[3] });
        }
    }

    /**
     * Shows dialog to add member.
     */
    private void addMember() {
        JPanel panel = createMemberInputPanel(null, null, null);
        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Member",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            JTextField nameField = getTextField(panel, 0);
            @SuppressWarnings("unchecked")
            JComboBox<String> genderCombo = (JComboBox<String>) panel.getComponent(3);
            JTextField ageField = getTextField(panel, 4);
            String name = nameField.getText().trim();
            char gender = genderCombo.getSelectedItem().toString().charAt(0);
            String ageText = ageField.getText().trim();

            if (!validateMemberInput(name, ageText)) {
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                int memberId = MemberService.getNextMemberId();

                if (UIHelper.confirm(this, "Add member:\nID: " + memberId + "\nName: " + name +
                        "\nGender: " + gender + "\nAge: " + age + "?")) {
                    MemberService.addMember(memberId, name, gender, age);
                    UIHelper.showSuccess(this, "Member added successfully!");
                    loadMembers();
                }
            } catch (NumberFormatException e) {
                UIHelper.showError(this, "Invalid age format. Please enter a valid number.");
            } catch (Exception e) {
                UIHelper.showError(this, "Error adding member: " + e.getMessage());
            }
        }
    }

    /**
     * Shows dialog to modify member.
     */
    private void modifyMember() {
        int row = memberTable.getSelectedRow();
        if (row == -1) {
            UIHelper.showWarning(this, "Please select a member to modify.");
            return;
        }

        String memberId = tableModel.getValueAt(row, 0).toString();
        String oldName = tableModel.getValueAt(row, 1).toString();
        String oldGender = tableModel.getValueAt(row, 2).toString();
        String oldAge = tableModel.getValueAt(row, 3).toString();

        JPanel panel = createMemberInputPanel(oldName, oldGender, oldAge);
        int result = JOptionPane.showConfirmDialog(this, panel, "Modify Member",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            JTextField nameField = getTextField(panel, 0);
            @SuppressWarnings("unchecked")
            JComboBox<String> genderCombo = (JComboBox<String>) panel.getComponent(3);
            JTextField ageField = getTextField(panel, 4);
            String name = nameField.getText().trim();
            char gender = genderCombo.getSelectedItem().toString().charAt(0);
            String ageText = ageField.getText().trim();

            if (!validateMemberInput(name, ageText)) {
                return;
            }

            try {
                int id = Integer.parseInt(memberId);
                int age = Integer.parseInt(ageText);

                if (UIHelper.confirm(this, "Modify member ID " + id + "?\nNew Name: " + name +
                        "\nNew Gender: " + gender + "\nNew Age: " + age)) {
                    ArrayList<String[]> members = MemberService.getAllMembers();
                    int index = findMemberIndex(members, memberId);

                    if (index != -1) {
                        members.get(index)[1] = name.replace(" ", "_");
                        members.get(index)[2] = String.valueOf(gender);
                        members.get(index)[3] = String.valueOf(age);
                        MemberService.updateMembers(members);
                        UIHelper.showSuccess(this, "Member modified successfully!");
                        loadMembers();
                    } else {
                        UIHelper.showError(this, "Member not found.");
                    }
                }
            } catch (NumberFormatException e) {
                UIHelper.showError(this, "Invalid input format.");
            } catch (Exception e) {
                UIHelper.showError(this, "Error modifying member: " + e.getMessage());
            }
        }
    }

    /**
     * Deletes selected member.
     */
    private void deleteMember() {
        int row = memberTable.getSelectedRow();
        if (row == -1) {
            UIHelper.showWarning(this, "Please select a member to delete.");
            return;
        }

        String memberId = tableModel.getValueAt(row, 0).toString();
        String memberName = tableModel.getValueAt(row, 1).toString();

        if (UIHelper.confirm(this, "Are you sure you want to delete:\nMember ID: " + memberId +
                "\nName: " + memberName + "?")) {
            try {
                ArrayList<String[]> members = MemberService.getAllMembers();
                int index = findMemberIndex(members, memberId);

                if (index != -1) {
                    MemberService.deleteMember(members, index);
                    UIHelper.showSuccess(this, "Member deleted successfully!");
                    loadMembers();
                } else {
                    UIHelper.showError(this, "Member not found.");
                }
            } catch (Exception e) {
                UIHelper.showError(this, "Error deleting member: " + e.getMessage());
            }
        }
    }

    /**
     * Searches members by name (LIKE functionality).
     */
    private void searchMember() {
        String searchName = UIHelper.input(this, "Enter member name to search (partial match):", "Search Member");
        if (searchName == null || searchName.trim().isEmpty()) {
            return;
        }

        searchName = searchName.trim().toLowerCase();
        ArrayList<String[]> allMembers = MemberService.getAllMembers();
        ArrayList<String[]> found = new ArrayList<>();

        for (String[] member : allMembers) {
            String memberName = member[1].replace("_", " ").toLowerCase();
            if (memberName.contains(searchName)) {
                found.add(member);
            }
        }

        if (found.isEmpty()) {
            UIHelper.showInfo(this, "No members found matching: " + searchName);
        } else {
            StringBuilder result = new StringBuilder("Found " + found.size() + " member(s):\n\n");
            for (String[] member : found) {
                String formattedName = member[1].replace("_", " ");
                result.append("ID: ").append(member[0]).append(" - ").append(formattedName)
                        .append(" (").append(member[2]).append(", Age: ").append(member[3]).append(")\n");
            }
            UIHelper.showInfo(this, result.toString());
            highlightMemberInTable(found.get(0)[0]);
        }
    }

    /**
     * Highlights member in table.
     */
    private void highlightMemberInTable(String memberId) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).toString().equals(memberId)) {
                memberTable.setRowSelectionInterval(i, i);
                memberTable.scrollRectToVisible(memberTable.getCellRect(i, 0, true));
                break;
            }
        }
    }

    /**
     * Creates member input panel.
     */
    private JPanel createMemberInputPanel(String name, String gender, String age) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField nameField = new JTextField(name != null ? name : "", 20);
        JComboBox<String> genderCombo = new JComboBox<>(new String[] { "M", "F" });
        if (gender != null) {
            genderCombo.setSelectedItem(gender);
        }
        JTextField ageField = new JTextField(age != null ? age : "", 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        panel.add(genderCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        panel.add(ageField, gbc);

        return panel;
    }

    /**
     * Gets text field from panel by index.
     */
    private JTextField getTextField(JPanel panel, int index) {
        return (JTextField) panel.getComponent(index);
    }

    /**
     * Validates member input.
     */
    private boolean validateMemberInput(String name, String ageText) {
        if (name.isEmpty()) {
            UIHelper.showError(this, "Please enter a member name.");
            return false;
        }
        if (!Validations.isValidName(name)) {
            UIHelper.showError(this,
                    "Invalid name format. Name must be 3-50 characters and contain only letters, spaces, hyphens, or apostrophes.");
            return false;
        }
        if (ageText.isEmpty()) {
            UIHelper.showError(this, "Please enter a member age.");
            return false;
        }
        try {
            int age = Integer.parseInt(ageText);
            if (!Validations.isValidAge(age)) {
                UIHelper.showError(this, "Invalid age. Age must be between 18 and 90.");
                return false;
            }
        } catch (NumberFormatException e) {
            UIHelper.showError(this, "Invalid age format.");
            return false;
        }
        return true;
    }

    /**
     * Finds member index in list.
     */
    private int findMemberIndex(ArrayList<String[]> members, String memberId) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i)[0].equals(memberId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Shows member view.
     */
    public static void showMemberView() {
        SwingUtilities.invokeLater(() -> {
            MemberView view = new MemberView();
            view.setVisible(true);
        });
    }
}
