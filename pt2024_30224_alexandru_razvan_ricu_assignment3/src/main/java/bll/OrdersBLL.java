package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.OrderValidator;
import bll.validators.Validator;
import dao.OrdersDAO;
import model.Orders;
/**
 * The OrdersBLL class handles business logic operations related to the Orders entity.
 * It provides methods to interact with the OrdersDAO for CRUD operations.
 */
public class OrdersBLL {

    private final List<Validator<Orders>> validators;
    private final OrdersDAO orderDAO;
    /**
     * Constructor for the OrdersBLL class.
     * Initializes the list of validators and the OrdersDAO.
     */
    public OrdersBLL() {
        validators = new ArrayList<>();
        validators.add(new OrderValidator());
        orderDAO = new OrdersDAO();
    }
    /**
     * Finds an order by its id.
     *
     * @param id The id of the order to find
     * @return The order object if found
     * @throws NoSuchElementException If the order is not found
     */
    public Orders findOrderById(int id) {
        Orders order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        }
        return order;
    }
    /**
     * Retrieves all orders from the database.
     *
     * @return A list of all orders
     */
    public ArrayList<Orders> findAllOrders() {
        return (ArrayList<Orders>) orderDAO.findAll();
    }
    /**
     * Adds a new order to the database after validating it.
     *
     * @param order The order object to add
     */
    public void addOrder(Orders order){
        for(Validator<Orders>validator:validators){
            validator.validate(order);
        }
        orderDAO.insertOrder(order);
    }
    /**
     * Deletes an order from the database by its id.
     *
     * @param id The id of the order to delete
     * @throws NoSuchElementException If the order is not found
     */
    public void deleteOrder(int id){
        findOrderById(id);
        orderDAO.delete(id);
    }
    /**
     * Updates an existing order in the database after validating it.
     *
     * @param order The order object to update
     */
    public void updateOrder(Orders order){
        for(Validator<Orders>validator:validators){
            validator.validate(order);
        }
        orderDAO.update(order);
    }
}
