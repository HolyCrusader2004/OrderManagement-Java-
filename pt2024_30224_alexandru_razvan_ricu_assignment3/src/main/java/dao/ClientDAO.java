package dao;

import model.Client;
/**
 * The ClientDAO class extends the AbstractDAO class and provides specific data access operations for the Client entity.
 * It inherits generic CRUD (Create, Read, Update, Delete) operations from AbstractDAO.
 */
public class ClientDAO extends AbstractDAO<Client> {
    /**
     * Constructor for the ClientDAO class.
     * Initializes the DAO to handle Client entities.
     */
    public ClientDAO() {
    }
}
