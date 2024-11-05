package bll.validators;

import dao.ClientDAO;
import dao.ProductDAO;
import model.Orders;
/**
 * The OrderValidator class implements the Validator interface for validating Orders objects.
 * It ensures that an order meets certain criteria before it is processed.
 */
public class OrderValidator implements Validator<Orders>{
    /**
     * Validates an Orders object.
     *
     * @param order The Orders object to be validated.
     * @throws IllegalArgumentException if the order quantity is less than zero, if the product or client associated with the order does not exist or if there is insufficient quantity of the product in stock.
     */
    @Override
    public void validate(Orders order) {
        ProductDAO prod =new ProductDAO();
        ClientDAO client = new ClientDAO();
        if(order.getQuantity() < 0){
            throw new IllegalArgumentException("The Order quantity needs to be higher than zero!");
        }
        if(prod.findById(order.getId_product())==null || client.findById(order.getId_client())==null){
            throw new IllegalArgumentException("The Order's product id or client id does not exist!");
        }
        if(prod.findById(order.getId_product()).getQuantity() - order.getQuantity() < 0){
            throw new IllegalArgumentException("There is not that much quantity in stock!");
        }
    }
}
