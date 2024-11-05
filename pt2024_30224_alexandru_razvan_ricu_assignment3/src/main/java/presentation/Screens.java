package presentation;

import bll.ClientBLL;
import bll.OrdersBLL;
import bll.ProductBLL;
import model.Orders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * The Screens class represents a JPanel that displays data from a database table.
 * It provides methods to fetch data, extract column names and row data, and generate
 * a table for display.
 *
 * @param <T> The type of data to be displayed on the screen.
 */
public class Screens<T> extends JPanel {
    private List<T> dataList;
    private final Class<T> contentType;
    private final JPanel topPanel;
    /**
     * Constructs a new Screens object with the specified content type and parent frame.
     *
     * @param contentType The type of content to be displayed on the screen.
     * @param frame       The parent frame associated with the screen.
     */
    public Screens(Class<T> contentType, HomeScreen frame) {
        frame.setTitle(getWindowTitle(contentType));
        this.contentType = contentType;

        setLayout(new BorderLayout());
        dataList= new ArrayList<>();
        topPanel = new JPanel(new GridBagLayout());

        add(topPanel, BorderLayout.NORTH);
        add(tabelMethod(contentType), BorderLayout.CENTER);
        ControllerScreens controllerScreens = new ControllerScreens(this, frame, contentType);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(controllerScreens);
        topPanel.add(backButton, gbc);
        gbc.gridx = 1;
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(controllerScreens);
        topPanel.add(insertButton, gbc);
        if (contentType != Orders.class) {
            gbc.gridx = 2;
            JButton updateButton = new JButton("Update");
            updateButton.addActionListener(controllerScreens);
            topPanel.add(updateButton, gbc);
            gbc.gridx = 3;
        } else {
            gbc.gridx = 2;
        }
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(controllerScreens);
        topPanel.add(deleteButton, gbc);
    }
    /**
     * Fetches data from the database based on the content type.
     *
     * @param contentType The type of content for which data needs to be fetched.
     * @return A list containing the fetched data.
     */
    private List<T> fetchData(Class<T> contentType) {
        return switch (contentType.getSimpleName()) {
            case "Product" -> (List<T>) new ProductBLL().findAllProducts();
            case "Client" -> (List<T>) new ClientBLL().findAllClients();
            case "Orders" -> (List<T>) new OrdersBLL().findAllOrders();
            default -> Collections.emptyList();
        };
    }
    /**
     * Generates the window title based on the content type.
     *
     * @param contentType The type of content for which the window title is generated.
     * @return The window title.
     */
    private String getWindowTitle(Class<T> contentType) {
        return switch (contentType.getSimpleName()) {
            case "Product" -> "Products";
            case "Client" -> "Clients";
            case "Orders" -> "Orders";
            default -> "Window";
        };
    }
    /**
     * Generates a JPanel containing a JTable for displaying data.
     *
     * @param contentType The type of content for which the table is generated.
     * @return The JPanel containing the JTable.
     */
    public JPanel tabelMethod(Class<T> contentType) {
        JPanel middlePanel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        String[] columnNames = extractColumnNames(contentType);
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        dataList = fetchData(contentType);
        for (T item : dataList) {
            Object[] rowData = extractRowData(item);
            model.addRow(rowData);
        }
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        middlePanel.add(scrollPane, BorderLayout.CENTER);
        return middlePanel;
    }
    /**
     * Extracts column names from the class fields.
     *
     * @param contentType The type of content for which column names are extracted.
     * @return An array of column names.
     */
    private String[] extractColumnNames(Class<T> contentType) {
        Field[] fields = contentType.getDeclaredFields();
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }
        return columnNames;
    }
    /**
     * Extracts row data from an object of type T.
     *
     * @param item The object from which row data is extracted.
     * @return An array of row data.
     */
    private Object[] extractRowData(T item) {
        Field[] fields = item.getClass().getDeclaredFields();
        Object[] rowData = new Object[fields.length];
        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                rowData[i] = fields[i].get(item);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return rowData;
    }
    /**
     * Refreshes the screen with updated content.
     *
     * @return The updated screen JPanel.
     */
    public JPanel getScreen(){
        this.removeAll();
        add(topPanel, BorderLayout.NORTH);
        add(tabelMethod(contentType), BorderLayout.CENTER);
        return this;
    }
}