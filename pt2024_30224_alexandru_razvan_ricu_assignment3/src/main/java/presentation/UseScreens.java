package presentation;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
/**
 * The UseScreens class represents a JPanel used for input forms or modification screens
 * associated with database operations such as insert, update, or delete.
 *
 * @param <T> The type of data associated with the screen.
 */
public class UseScreens<T> extends JPanel {
    private final ArrayList<JTextField> textFields;
    /**
     * Constructs a new UseScreens object with the specified content type, screens, home screen,
     * and command.
     *
     * @param contentType The type of content associated with the screen.
     * @param screens     The Screens object associated with the screen.
     * @param homeScreen  The HomeScreen object associated with the screen.
     * @param command     The command associated with the screen (e.g., insert, update, delete).
     */
    public UseScreens(Class<T> contentType,Screens screens,HomeScreen homeScreen,String command){
        this.textFields=new ArrayList<>();
        ControllerUseScreens controllerUseScreens = new ControllerUseScreens(this, screens, homeScreen, contentType);
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new GridBagLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(controllerUseScreens);
        topPanel.add(backButton, gbc);

        gbc.gridx = 1;
        JButton actionButton = new JButton(command);
        actionButton.addActionListener(controllerUseScreens);
        topPanel.add(actionButton, gbc);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.anchor = GridBagConstraints.WEST;
        gbc2.insets = new Insets(5, 5, 5, 5);

        for (String columnName : extractColumnNames(contentType)) {
            if(columnName.equals("id")){
                if(command.equals("Insert")){
                    continue;
                }
            }
            JLabel label = new JLabel(columnName);
            JTextField textField = new JTextField(20);
            textFields.add(textField);
            formPanel.add(label, gbc2);
            gbc2.gridx++;
            formPanel.add(textField, gbc2);
            gbc2.gridx = 0;
            gbc2.gridy++;
            if(command.equals("Delete")){
                break;
            }
        }
        add(topPanel, BorderLayout.NORTH);
        add(formPanel,BorderLayout.CENTER);
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
     * Gets the list of text fields associated with the screen.
     *
     * @return An ArrayList of JTextField objects.
     */
    public ArrayList<JTextField> getTextFields(){
        return textFields;
    }
}
