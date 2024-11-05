package presentation;

import model.Bill;
import model.Client;
import model.Orders;
import model.Product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The ControllerHomeScreen class implements the ActionListener interface
 * to handle actions performed on the HomeScreen buttons.
 */
public class ControllerHomeScreen implements ActionListener {
    private final HomeScreen home;
    /**
     * Constructs a new ControllerHomeScreen with the specified HomeScreen instance.
     *
     * @param home The HomeScreen instance to associate with the controller.
     */
    public ControllerHomeScreen(HomeScreen home) {
        this.home = home;
    }
    /**
     * Handles action events triggered by buttons on the HomeScreen.
     *
     * @param e The ActionEvent representing the action performed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Clients" -> {
                home.getContentPane().removeAll();
                home.getContentPane().add(new Screens<>(Client.class, home));
                home.revalidate();
                home.repaint();
            }
            case "Products" -> {
                home.getContentPane().removeAll();
                home.getContentPane().add(new Screens<>(Product.class, home));
                home.revalidate();
                home.repaint();
            }
            case "Orders" -> {
                home.getContentPane().removeAll();
                home.getContentPane().add(new Screens<>(Orders.class, home));
                home.revalidate();
                home.repaint();
            }
        }
    }
}
