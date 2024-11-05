package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
/**
 * The InsertScreen class represents a JPanel used for inserting data into the system.
 */
public class InsertScreen extends JPanel {
    private JTextField textField;
    private JComboBox clientsComboBox;
    private JComboBox productsComboBox;
    /**
     * Constructs a new InsertScreen object with the specified parameters.
     *
     * @param contentType The type of content for which data is being inserted.
     * @param screens     The Screens object associated with the insert screen.
     * @param homeScreen  The HomeScreen object associated with the insert screen.
     */
    public InsertScreen(Class contentType,Screens screens,HomeScreen homeScreen){
        ControllerOrderScreen controllerOrderScreen = new ControllerOrderScreen(this, screens, homeScreen);
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new GridBagLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(controllerOrderScreen);
        topPanel.add(backButton, gbc);

        gbc.gridx = 1;
        JButton actionButton = new JButton("Insert");
        actionButton.addActionListener(controllerOrderScreen);
        topPanel.add(actionButton, gbc);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.anchor = GridBagConstraints.WEST;
        gbc2.insets = new Insets(5, 5, 10, 5);

        ClientBLL clientBLL = new ClientBLL();
        ArrayList<Client> clientsAvailable = clientBLL.findAllClients();

        ProductBLL productBLL =new ProductBLL();
        ArrayList<Product> productsAvailable = productBLL.findAllProducts();
        String[] clients = new String[clientsAvailable.size()];
        String[] products = new String[productsAvailable.size()];
        int i=0;
        for(Client client:clientsAvailable) {
          clients[i] = client.toString();
          i++;
        }
        i=0;
        for(Product product:productsAvailable) {
            products[i] = product.toString();
            i++;
        }
        for (String columnName : extractColumnNames(contentType)) {
            if(columnName.equals("id")){
                continue;
            }
            gbc2.gridx=0;
            JLabel label = new JLabel(columnName);
            formPanel.add(label, gbc2);
            gbc2.gridx++;
            switch (columnName) {
                case "quantity" -> {
                    textField = new JTextField(20);
                    formPanel.add(textField, gbc2);
                }
                case "id_client" -> {
                    clientsComboBox = new JComboBox<>(clients);
                    formPanel.add(clientsComboBox, gbc2);
                }
                case "id_product" -> {
                    productsComboBox = new JComboBox<>(products);
                    formPanel.add(productsComboBox, gbc2);
                }
            }
            gbc2.gridy++;
        }
        add(topPanel, BorderLayout.NORTH);
        add(formPanel,BorderLayout.CENTER);
    }
    /**
     * Extracts column names from the specified class.
     *
     * @param contentType The type of content from which to extract column names.
     * @return An array of column names.
     */
    private String[] extractColumnNames(Class contentType) {
        Field[] fields = contentType.getDeclaredFields();
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }
        return columnNames;
    }
    /**
     * Gets the text field.
     *
     * @return The JTextField object.
     */
    public JTextField getTextField() {
        return textField;
    }
    /**
     * Gets the clients combo box.
     *
     * @return The JComboBox object for clients.
     */
    public JComboBox getClientsComboBox() {
        return clientsComboBox;
    }
    /**
     * Gets the products combo box.
     *
     * @return The JComboBox object for products.
     */
    public JComboBox getProductsComboBox() {
        return productsComboBox;
    }
}
