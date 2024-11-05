package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import bll.validators.QuantityAndPriceValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;
/**
 * The ProductBLL class handles business logic operations related to the Product entity.
 * It provides methods to interact with the ProductDAO for CRUD operations.
 */
public class ProductBLL {

    private final List<Validator<Product>> validators;
    private final ProductDAO productDAO;
    /**
     * Constructor for the ProductBLL class.
     * Initializes the list of validators and the ProductDAO.
     */
    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new QuantityAndPriceValidator());
        productDAO = new ProductDAO();
    }
    /**
     * Finds a product by its id.
     *
     * @param id The id of the product to find
     * @return The product object if found
     * @throws NoSuchElementException If the product is not found
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return product;
    }
    /**
     * Retrieves all products from the database.
     *
     * @return A list of all products
     */
    public ArrayList<Product> findAllProducts() {
        return (ArrayList<Product>) productDAO.findAll();
    }
    /**
     * Adds a new product to the database after validating it.
     *
     * @param product The product object to add
     */
    public void addProduct(Product product){
        for(Validator<Product>validator:validators){
            validator.validate(product);
        }
        productDAO.insert(product);
    }
    /**
     * Deletes a product from the database by its id.
     *
     * @param id The id of the product to delete
     * @throws NoSuchElementException If the product is not found
     */
    public void deleteProduct(int id){
        findProductById(id);
        productDAO.delete(id);
    }
    /**
     * Updates an existing product in the database after validating it.
     *
     * @param product The product object to update
     * @throws NoSuchElementException If the product is not found
     */
    public void updateProduct(Product product){
        findProductById(product.getId());
        for(Validator<Product>validator:validators){
            validator.validate(product);
        }
        productDAO.update(product);
    }
}
