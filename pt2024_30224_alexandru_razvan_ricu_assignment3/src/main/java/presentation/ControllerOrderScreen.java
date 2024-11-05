package presentation;

import bll.BillBLL;
import bll.OrdersBLL;

import model.Bill;
import model.Orders;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.*;
/**
 * The ControllerOrderScreen class implements the ActionListener interface
 * to handle actions related to orders, such as inserting new orders and
 * creating PDF invoices.
 */
public class ControllerOrderScreen implements ActionListener {
    private final Screens screens;
    private final InsertScreen insertScreen;
    private final HomeScreen homeScreen;
    /**
     * Constructs a new ControllerOrderScreen object with the specified
     * InsertScreen, Screens, and HomeScreen.
     *
     * @param insertScreen The InsertScreen object associated with the controller.
     * @param screens      The Screens object associated with the controller.
     * @param homeScreen   The HomeScreen object associated with the controller.
     */
    public ControllerOrderScreen(InsertScreen insertScreen, Screens screens, HomeScreen homeScreen){
        this.screens=screens;
        this.insertScreen=insertScreen;
        this.homeScreen = homeScreen;
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
        if(command.equals("Insert")){
            OrdersBLL ordersBLL=new OrdersBLL();
            BillBLL billBLL=new BillBLL();
            String clientInfo = (String) insertScreen.getClientsComboBox().getSelectedItem();
            String productInfo = (String) insertScreen.getProductsComboBox().getSelectedItem();

            int startIndex = clientInfo.indexOf("id=") + 3;
            int endIndex = clientInfo.indexOf(",", startIndex);
            int id_client = Integer.parseInt(clientInfo.substring(startIndex,endIndex));
            startIndex = productInfo.indexOf("id=") + 3;
            endIndex = productInfo.indexOf(",", startIndex);
            int id_product = Integer.parseInt(productInfo.substring(startIndex,endIndex));

            startIndex = clientInfo.indexOf("name=")+5;
            endIndex = clientInfo.indexOf(",",startIndex);
            String client_name = clientInfo.substring(startIndex,endIndex);
            startIndex = productInfo.indexOf("name=")+5;
            endIndex = productInfo.indexOf(",",startIndex);
            String product_name = productInfo.substring(startIndex,endIndex);

            startIndex = productInfo.indexOf("price=")+6;
            endIndex = productInfo.indexOf("]",startIndex);
            int product_price = Integer.parseInt(productInfo.substring(startIndex,endIndex));
            try{
                int quantity = Integer.parseInt(insertScreen.getTextField().getText());
                ordersBLL.addOrder(new Orders(id_client,id_product,quantity));
                billBLL.addBill(new Bill(client_name,product_name,product_price*quantity, LocalDateTime.now()));
                createPDF(client_name,product_name,product_price*quantity,LocalDateTime.now());
                updateScreen();
            }catch(IllegalArgumentException | IOException exception){
                JOptionPane.showMessageDialog(new JFrame(),exception.getMessage());
            }
        }
    }
    /**
     * Creates a PDF invoice for the given order details.
     *
     * @param client_name  The name of the client.
     * @param product_name The name of the product.
     * @param totalPrice   The total price of the order.
     * @param timestamp    The timestamp of the order.
     * @throws IOException If an I/O error occurs while creating the PDF.
     */
    private void createPDF(String client_name, String product_name, int totalPrice, LocalDateTime timestamp) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        PDImageXObject backgroundImage = PDImageXObject.createFromFile("D:\\PT2024_30224_Alexandru_Razvan_Ricu\\pt2024_30224_alexandru_razvan_ricu_assignment3.0\\Logo.jpg", document);
        contentStream.drawImage(backgroundImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());

        PDImageXObject smallImage = PDImageXObject.createFromFile("D:\\PT2024_30224_Alexandru_Razvan_Ricu\\pt2024_30224_alexandru_razvan_ricu_assignment3.0\\Logo2.jpg", document);
        float smallImageWidth = smallImage.getWidth();
        float smallImageHeight = smallImage.getHeight();
        float pageHeight = page.getMediaBox().getHeight();
        contentStream.drawImage(smallImage, 10, pageHeight - smallImageHeight - 10, smallImageWidth, smallImageHeight);

        contentStream.beginText();

        contentStream.newLineAtOffset(100, 700);
        contentStream.setFont(new PDType1Font(HELVETICA_BOLD), 14);
        contentStream.setNonStrokingColor(Color.BLACK);

        contentStream.showText("Invoice");
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15*3);
        contentStream.setNonStrokingColor(Color.BLACK);


        PDFont font = new PDType1Font(HELVETICA);
        contentStream.setFont(font, 12);
        contentStream.showText("Client Name: " + client_name);
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15);

        contentStream.showText("Product Name: " + product_name);
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15);

        contentStream.showText("Total Price: $" + totalPrice);
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15);

        contentStream.showText("Time of transaction: " +timestamp.getYear()+"-"+timestamp.getMonth()+"-"+timestamp.getDayOfMonth()+"-"+timestamp.getHour()+":"+timestamp.getMinute()+":"+timestamp.getSecond());
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15*5);

        contentStream.showText("Thank you for buying from us. We hope to see you again in the future!");
        contentStream.newLine();
        contentStream.newLineAtOffset(0, -15);

        contentStream.showText("For any complaints or additional information do not hesitate to contact us");
        contentStream.endText();
        contentStream.close();
        String path = "D:\\PT2024_30224_Alexandru_Razvan_Ricu\\pt2024_30224_alexandru_razvan_ricu_assignment3.0\\Bills\\bill-"
                +timestamp.getYear()+"-"+timestamp.getMonth()+"-"+timestamp.getDayOfMonth()+"-"+timestamp.getHour()+"-"+timestamp.getMinute()
                +"-"+timestamp.getSecond()+ ".pdf";
        document.save(new File(path));
        document.close();
    }

    /**
     * Updates the screen after an action has been performed.
     */
    private void updateScreen(){
        homeScreen.getContentPane().removeAll();
        homeScreen.add(screens.getScreen());
        homeScreen.revalidate();
        homeScreen.repaint();
    }

}
