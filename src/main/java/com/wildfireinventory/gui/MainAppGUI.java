package com.wildfireinventory.gui;

import com.wildfireinventory.dao.ItemDAO;
import com.wildfireinventory.dao.RoomDAO;
import com.wildfireinventory.dao.UserDAO;
import com.wildfireinventory.model.Item;
import com.wildfireinventory.model.Room;
import com.wildfireinventory.model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MainAppGUI {
    private final JFrame frame;
    private User currentUser;
    private final UserDAO userDAO = new UserDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final ItemDAO itemDAO = new ItemDAO();

    public MainAppGUI() {
        frame = new JFrame("Wildfire Inventory");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        showLoginOrRegisterScreen();
        frame.setVisible(true);
    }

    private void showLoginOrRegisterScreen() {
        frame.getContentPane().removeAll();
        frame.setTitle("Wildfire Inventory - Login or Register");

        ImagePanel panel = new ImagePanel(
                "src/main/java/com/wildfireinventory/resource/LoginPageBackgroundi.jpeg");

        // Create buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        // Set fonts (optional)
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));

        // ✅ Set custom sizes and positions: (x, y, width, height)
        loginButton.setBounds(90, 270, 220, 60); // Login button
        registerButton.setBounds(360, 270, 220, 60); // Register button
        exitButton.setBounds(380, 380, 180, 50); // Exit button

        // Add action listeners
        loginButton.addActionListener(e -> showLoginScreen());
        registerButton.addActionListener(e -> showRegisterScreen());
        exitButton.addActionListener(e -> System.exit(0));

        // Add buttons to panel
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(exitButton);

        // Apply the panel to the frame
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showLoginScreen() {
        frame.getContentPane().removeAll();
        frame.setTitle("Wildfire Inventory - Login");

        ImagePanel panel = new ImagePanel(
                "src/main/java/com/wildfireinventory/resource/LoginPageBackgroundi.jpeg");
        panel.setLayout(null); // manual positioning

        Font labelFont = new Font("Arial", Font.BOLD, 25);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        emailLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        emailLabel.setBounds(200, 150, 150, 30);
        passwordLabel.setBounds(200, 200, 150, 30);

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        emailField.setFont(fieldFont);
        passwordField.setFont(fieldFont);

        emailField.setBounds(400, 150, 220, 40);
        passwordField.setBounds(400, 200, 220, 40);

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));

        loginButton.setBounds(100, 320, 220, 60);
        backButton.setBounds(400, 320, 220, 60);

        // Action listeners
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            try {
                List<User> users = userDAO.getAllUsers();
                for (User u : users) {
                    if (u.getEmail().equals(email)) {
                        currentUser = u;
                        showHomeScreen();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "User not found.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> showLoginOrRegisterScreen());

        // Add everything to panel
        panel.add(emailLabel);
        panel.add(passwordLabel);
        panel.add(emailField);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showRegisterScreen() {
        frame.getContentPane().removeAll();
        frame.setTitle("Wildfire Inventory - Register");

        ImagePanel panel = new ImagePanel(
                "src/main/java/com/wildfireinventory/resource/LoginPageBackgroundi.jpeg");
        panel.setLayout(null); // manual positioning

        Font labelFont = new Font("Arial", Font.BOLD, 25);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        firstNameLabel.setFont(labelFont);
        lastNameLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        firstNameLabel.setBounds(200, 100, 150, 30);
        lastNameLabel.setBounds(200, 150, 150, 30);
        emailLabel.setBounds(200, 200, 150, 30);
        passwordLabel.setBounds(200, 250, 150, 30);

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        firstNameField.setFont(fieldFont);
        lastNameField.setFont(fieldFont);
        emailField.setFont(fieldFont);
        passwordField.setFont(fieldFont);

        firstNameField.setBounds(390, 100, 230, 40);
        lastNameField.setBounds(390, 150, 230, 40);
        emailField.setBounds(390, 200, 230, 40);
        passwordField.setBounds(390, 250, 230, 40);

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));

        registerButton.setBounds(180, 320, 180, 50);
        backButton.setBounds(430, 320, 180, 50);

        registerButton.addActionListener(e -> {
            User user = new User();
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setEmail(emailField.getText());
            user.setPasswordHash(new String(passwordField.getPassword()));
            try {
                userDAO.createUser(user);
                JOptionPane.showMessageDialog(frame, "Registration successful. Please log in.");
                showLoginScreen();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Registration failed.");
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> showLoginOrRegisterScreen());

        // Add to panel
        panel.add(firstNameLabel);
        panel.add(lastNameLabel);
        panel.add(emailLabel);
        panel.add(passwordLabel);
        panel.add(firstNameField);
        panel.add(lastNameField);
        panel.add(emailField);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(backButton);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showHomeScreen() {
        frame.getContentPane().removeAll();
        frame.setTitle("Wildfire Inventory - Home");

        ImagePanel panel = new ImagePanel("src/main/java/com/wildfireinventory/resource/Home.jpeg");

        panel.setLayout(null); // Manual positioning

        JLabel welcomeLabel = new JLabel(
                "<html><div style='text-align: center;'>Welcome to Wildfire<br>Inventory Application</div></html>");

        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 35));
        // welcomeLabel.setForeground(Color.WHITE); // Change if background is light
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(30, 100, 600, 100); // Adjust as needed

        JButton getStartedButton = new JButton("Get Started");
        getStartedButton.setFont(new Font("Arial", Font.BOLD, 20));
        getStartedButton.setBounds(250, 320, 200, 60);

        getStartedButton.addActionListener(e -> showMainDashboard());

        panel.add(welcomeLabel);
        panel.add(getStartedButton);
        welcomeLabel.setOpaque(false);
        getStartedButton.setContentAreaFilled(false);
        getStartedButton.setOpaque(false);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showMainDashboard() {
        frame.getContentPane().removeAll();
        frame.setTitle("Wildfire Inventory - Dashboard");

        // Optional: Add background image like Home screen
        ImagePanel panel = new ImagePanel("src/main/java/com/wildfireinventory/resource/Home.jpeg");
        panel.setLayout(null); // manual layout

        // Create and configure buttons
        JButton addRoomBtn = new JButton("Add Room");
        JButton addItemBtn = new JButton("Add Item");
        JButton viewInventoryBtn = new JButton("View Inventory");
        JButton backButton = new JButton("← Logout");

        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        addRoomBtn.setFont(buttonFont);
        addItemBtn.setFont(buttonFont);
        viewInventoryBtn.setFont(buttonFont);
        backButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Set size and position
        addRoomBtn.setBounds(230, 100, 240, 50);
        addItemBtn.setBounds(230, 170, 240, 50);
        viewInventoryBtn.setBounds(230, 240, 240, 50);
        backButton.setBounds(230, 310, 240, 50);

        // Add actions
        addRoomBtn.addActionListener(e -> showAddRoomDialog());
        addItemBtn.addActionListener(e -> showAddItemDialog());
        viewInventoryBtn.addActionListener(e -> showInventory());
        backButton.addActionListener(e -> showLoginOrRegisterScreen());

        // Make buttons transparent if you want background visible
        addRoomBtn.setContentAreaFilled(false);
        addRoomBtn.setOpaque(false);
        addItemBtn.setContentAreaFilled(false);
        addItemBtn.setOpaque(false);
        viewInventoryBtn.setContentAreaFilled(false);
        viewInventoryBtn.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);

        // Add to panel
        panel.add(addRoomBtn);
        panel.add(addItemBtn);
        panel.add(viewInventoryBtn);
        panel.add(backButton);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showAddRoomDialog() {
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();

        JLabel nameLabel = new JLabel("Room Name:");
        JLabel descLabel = new JLabel("Description:");

        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        descLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        descField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");

        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panel = new JPanel(null); // Manual layout
        panel.setPreferredSize(new Dimension(400, 220));

        nameLabel.setBounds(30, 20, 120, 30);
        nameField.setBounds(160, 20, 200, 30);
        descLabel.setBounds(30, 70, 120, 30);
        descField.setBounds(160, 70, 200, 30);
        saveButton.setBounds(60, 130, 100, 40);
        backButton.setBounds(220, 130, 100, 40);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(descLabel);
        panel.add(descField);
        panel.add(saveButton);
        panel.add(backButton);

        JDialog dialog = new JDialog(frame, "Add Room", true);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

        saveButton.addActionListener(e -> {
            Room room = new Room();
            room.setUserId(currentUser.getUserId());
            room.setRoomName(nameField.getText());
            room.setRoomDescription(descField.getText());
            try {
                roomDAO.createRoom(room);
                JOptionPane.showMessageDialog(frame, "Room added successfully.");
                dialog.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void showAddItemDialog() {
        try {
            List<Room> rooms = roomDAO.getRoomsByUserId(currentUser.getUserId());
            String[] roomNames = rooms.stream().map(Room::getRoomName).toArray(String[]::new);

            JLabel roomLabel = new JLabel("Room:");
            JLabel nameLabel = new JLabel("Item Name:");
            JLabel brandLabel = new JLabel("Brand:");
            JLabel priceLabel = new JLabel("Price:");

            roomLabel.setFont(new Font("Arial", Font.BOLD, 16));
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            brandLabel.setFont(new Font("Arial", Font.BOLD, 16));
            priceLabel.setFont(new Font("Arial", Font.BOLD, 16));

            JComboBox<String> roomDropdown = new JComboBox<>(roomNames);
            JTextField nameField = new JTextField();
            JTextField brandField = new JTextField();
            JTextField priceField = new JTextField();

            roomDropdown.setFont(new Font("Arial", Font.PLAIN, 14));
            nameField.setFont(new Font("Arial", Font.PLAIN, 14));
            brandField.setFont(new Font("Arial", Font.PLAIN, 14));
            priceField.setFont(new Font("Arial", Font.PLAIN, 14));

            JButton saveButton = new JButton("Save");
            JButton backButton = new JButton("Back");

            saveButton.setFont(new Font("Arial", Font.BOLD, 14));
            backButton.setFont(new Font("Arial", Font.BOLD, 14));

            JPanel panel = new JPanel(null); // Manual layout
            panel.setPreferredSize(new Dimension(480, 320));

            // Set component positions
            roomLabel.setBounds(30, 20, 120, 30);
            roomDropdown.setBounds(160, 20, 270, 30);

            nameLabel.setBounds(30, 70, 120, 30);
            nameField.setBounds(160, 70, 270, 30);

            brandLabel.setBounds(30, 120, 120, 30);
            brandField.setBounds(160, 120, 270, 30);

            priceLabel.setBounds(30, 170, 120, 30);
            priceField.setBounds(160, 170, 270, 30);

            saveButton.setBounds(80, 230, 130, 40);
            backButton.setBounds(260, 230, 130, 40);

            // Add to panel
            panel.add(roomLabel);
            panel.add(roomDropdown);
            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(brandLabel);
            panel.add(brandField);
            panel.add(priceLabel);
            panel.add(priceField);
            panel.add(saveButton);
            panel.add(backButton);

            // Dialog setup
            JDialog dialog = new JDialog(frame, "Add Item", true);
            dialog.setContentPane(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);

            saveButton.addActionListener(e -> {
                try {
                    Item item = new Item();
                    item.setRoomId(rooms.get(roomDropdown.getSelectedIndex()).getRoomId());
                    item.setItemName(nameField.getText());
                    item.setBrand(brandField.getText());
                    item.setPurchaseDate(Date.valueOf("2024-01-01"));

                    BigDecimal price = new BigDecimal(priceField.getText());
                    item.setPurchasePrice(price);
                    item.setCurrentValue(price);

                    item.setCondition("Good");
                    item.setDescription("Sample description");

                    itemDAO.createItem(item);
                    JOptionPane.showMessageDialog(frame, "Item added.");
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please check your entries.");
                    ex.printStackTrace();
                }
            });

            backButton.addActionListener(e -> dialog.dispose());

            dialog.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showInventory() {
        try {
            List<Room> rooms = roomDAO.getRoomsByUserId(currentUser.getUserId());
            StringBuilder sb = new StringBuilder();
            for (Room room : rooms) {
                sb.append("\nRoom: ").append(room.getRoomName()).append("\n");
                List<Item> items = itemDAO.getItemsByRoomId(room.getRoomId());
                for (Item item : items) {
                    sb.append("  - ").append(item.getItemName())
                            .append(" (Brand: ").append(item.getBrand())
                            .append(", $").append(item.getCurrentValue())
                            .append(")\n");
                }
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            JButton exportButton = new JButton("Export Report");
            exportButton.addActionListener(e -> exportReport(textArea.getText()));
            panel.add(exportButton, BorderLayout.SOUTH);

            JOptionPane.showMessageDialog(frame, panel, "Inventory", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void exportReport(String reportText) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Report");
            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try (java.io.FileWriter writer = new java.io.FileWriter(fileToSave)) {
                    writer.write(reportText);
                    JOptionPane.showMessageDialog(frame, "Report exported successfully.");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Failed to export report.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainAppGUI::new);
    }
}
