package presentation;

import javax.swing.*;
import java.awt.*;
/**
 * The HomeScreen class represents the main screen of the application.
 * It contains buttons to navigate to different functionalities of the application.
 */
public class HomeScreen extends JFrame {
    private JButton clientsButton;
    private JButton productsButton;
    private JButton ordersButton;
    private JPanel contentPanel;
    private ControllerHomeScreen controllerHomeScreen;
    /**
     * Constructs a new HomeScreen with the specified title.
     *
     * @param title The title of the HomeScreen.
     */
    public HomeScreen(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        clientsButton.addActionListener(controllerHomeScreen);
        productsButton.addActionListener(controllerHomeScreen);
        ordersButton.addActionListener(controllerHomeScreen);
        addComponents();
        pack();
        setVisible(true);
    }

    /**
     * Initializes the components of the HomeScreen.
     */
    private void initComponents() {
        controllerHomeScreen = new ControllerHomeScreen(this);
        contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 20, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        clientsButton = new JButton("Clients");
        contentPanel.add(clientsButton, gbc);
        gbc.gridy = 1;
        productsButton = new JButton("Products");
        contentPanel.add(productsButton, gbc);
        gbc.gridy = 2;
        ordersButton = new JButton("Orders");
        contentPanel.add(ordersButton, gbc);

        contentPanel.setPreferredSize(new Dimension(750, 500));
    }
    /**
     * Retrieves the content panel of the HomeScreen.
     *
     * @return The content panel.
     */
    public JPanel getContentPanel() {
        setTitle("Home Screen");
        return contentPanel;
    }

    /**
     * Adds components to the HomeScreen.
     */
    private void addComponents() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

}
