package presentation;

import bll.ClientBLL;
import bll.OrdersBLL;
import bll.ProductBLL;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * The ControllerUseScreens class implements the ActionListener interface
 * to handle actions related to the use of screens for CRUD operations.
 */
public class ControllerUseScreens implements ActionListener {
    private final Screens screens;
    private final HomeScreen homeScreen;
    private final Class contentType;
    private ArrayList<JTextField> textFields;
    /**
     * Constructs a new ControllerUseScreens object with the specified parameters.
     *
     * @param useScreens   The UseScreens object associated with the controller.
     * @param screens      The Screens object associated with the controller.
     * @param homeScreen   The HomeScreen object associated with the controller.
     * @param contentType  The type of content handled by the controller.
     */
    public ControllerUseScreens(UseScreens useScreens, Screens screens, HomeScreen homeScreen, Class contentType){
        this.screens=screens;
        this.homeScreen = homeScreen;
        this.contentType=contentType;
        this.textFields = new ArrayList<>();
        textFields = useScreens.getTextFields();
    }
    /**
     * Handles action events triggered by buttons associated with the controller.
     *
     * @param e The ActionEvent object representing the action performed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Back")) {
            updateScreen();
        }
        if(contentType == Client.class){
            ClientBLL clientBLL = new ClientBLL();
            switch (command) {
                case "Insert" -> {
                    try {
                        Client client = new Client(textFields.get(0).getText(), textFields.get(1).getText(),
                                Integer.parseInt(textFields.get(2).getText()));
                        clientBLL.addClient(client);
                        updateScreen();
                    } catch (IllegalArgumentException exception) {
                        JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
                    }
                }
                case "Update" -> {
                     try {
                         Client client = new Client(Integer.parseInt(textFields.get(0).getText()), textFields.get(1).getText(),
                                 textFields.get(2).getText(), Integer.parseInt(textFields.get(3).getText()));
                         clientBLL.updateClient(client);
                        updateScreen();
                    } catch (IllegalArgumentException exception) {
                        JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
                    }
                }
                case "Delete" -> {
                    try {
                        clientBLL.deleteClient(Integer.parseInt(textFields.get(0).getText()));
                        updateScreen();
                    } catch (NoSuchElementException | NumberFormatException exception) {
                        JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
                    }
                }
            }
        }
        if(contentType == Product.class){
            ProductBLL productBLL = new ProductBLL();
            switch (command) {
                case "Insert" -> {
                     try {
                         Product product = new Product(textFields.get(0).getText(),
                                 Integer.parseInt(textFields.get(1).getText()), Integer.parseInt(textFields.get(2).getText()));
                         productBLL.addProduct(product);
                         updateScreen();
                    } catch (IllegalArgumentException exception) {
                         JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
                    }
                }
                case "Update" -> {
                      try {
                          Product product = new Product(Integer.parseInt(textFields.get(0).getText()), textFields.get(1).getText(),
                                  Integer.parseInt(textFields.get(2).getText()), Integer.parseInt(textFields.get(3).getText()));
                          productBLL.updateProduct(product);
                          updateScreen();
                    } catch (IllegalArgumentException exception) {
                          JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
                    }
                }
                case "Delete" -> {
                    try {
                        productBLL.deleteProduct(Integer.parseInt(textFields.get(0).getText()));
                        updateScreen();
                    } catch (NoSuchElementException | NumberFormatException exception) {
                        JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
                    }
                }
            }
        }else if(contentType == Orders.class) {
            OrdersBLL ordersBLL = new OrdersBLL();
            if (command.equals("Delete")) {
                try {
                    ordersBLL.deleteOrder(Integer.parseInt(textFields.get(0).getText()));
                    updateScreen();
                } catch (NoSuchElementException | NumberFormatException exception) {
                    JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
                }
            }
        }
    }
    /**
     * Updates the screen by removing all components and adding the updated screen content.
     */
    private void updateScreen(){
        homeScreen.getContentPane().removeAll();
        homeScreen.add(screens.getScreen());
        homeScreen.revalidate();
        homeScreen.repaint();
    }
}
