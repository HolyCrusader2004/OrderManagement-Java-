package dao;

import model.Product;
/**
 * The ProductDAO class extends the AbstractDAO class and provides specific data access operations for the Product entity.
 * It inherits generic CRUD (Create, Read, Update, Delete) operations from AbstractDAO.
 */
public class ProductDAO extends AbstractDAO<Product> {
    /**
     * Constructor for the ProductDAO class.
     * Initializes the DAO to handle Product entities.
     */
    public ProductDAO() {
    }
}
