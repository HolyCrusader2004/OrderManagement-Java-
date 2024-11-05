package presentation;

import model.Orders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The ControllerScreens class implements the ActionListener interface
 * to handle actions related to screens, such as navigation and CRUD operations.
 */
public class ControllerScreens implements ActionListener {
    private final Screens screens;
    private final HomeScreen frame;
    private final Class contentType;
    /**
     * Constructs a new ControllerScreens object with the specified Screens, HomeScreen,
     * and content type.
     *
     * @param screens     The Screens object associated with the controller.
     * @param frame       The HomeScreen object associated with the controller.
     * @param contentType The type of content handled by the controller.
     */
    public ControllerScreens(Screens screens, HomeScreen frame, Class contentType){
        this.screens=screens;
        this.frame=frame;
        this.contentType=contentType;
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
            frame.getContentPane().removeAll();
            frame.add(frame.getContentPanel());
            frame.revalidate();
            frame.repaint();
        }
        if(command.equals("Insert")){
            frame.getContentPane().removeAll();
            if(contentType == Orders.class){
                frame.add(new InsertScreen(contentType, screens, frame));
            }else {
                frame.add(new UseScreens<>(contentType, screens, frame, "Insert"));
            }
            frame.repaint();
            frame.revalidate();
        }
        if(command.equals("Update")){
            frame.getContentPane().removeAll();
            frame.add(new UseScreens<>(contentType,screens,frame,"Update"));
            frame.repaint();
            frame.revalidate();
        }
        if(command.equals("Delete")){
            frame.getContentPane().removeAll();
            frame.add(new UseScreens<>(contentType,screens,frame,"Delete"));
            frame.repaint();
            frame.revalidate();
        }
    }
}
