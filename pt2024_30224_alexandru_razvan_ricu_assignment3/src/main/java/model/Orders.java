package model;
/**
 * The Orders class represents an order placed by a client for a product.
 * It encapsulates information about the order, such as the order id, client id, product id, and quantity.
 *
 * This class provides constructors to initialize an order object with or without an id.
 * It also provides getter and setter methods for accessing and modifying the order's properties.
 */
public class Orders {
    private int id;
    private int id_client;
    private int id_product;
    private int quantity;

    /**
     * Default constructor for the Orders class.
     * Initializes an empty order object.
     */
    public Orders() {
    }
    /**
     * Parameterized constructor for the Orders class.
     * Initializes an order object with the provided id, client id, product id, and quantity.
     *
     * @param id The unique identifier for the order
     * @param id_client The unique identifier for the client placing the order
     * @param id_product The unique identifier for the product being ordered
     * @param quantity The quantity of the product being ordered
     */
    public Orders(int id, int id_client, int id_product, int quantity) {
        super();
        this.id = id;
        this.id_client = id_client;
        this.id_product = id_product;
        this.quantity = quantity;
    }
    /**
     * Parameterized constructor for the Orders class.
     * Initializes an order object without an id.
     *
     * @param id_client The unique identifier for the client placing the order
     * @param id_product The unique identifier for the product being ordered
     * @param quantity The quantity of the product being ordered
     */
    public Orders(int id_client, int id_product, int quantity) {
        super();
        this.id_client = id_client;
        this.id_product = id_product;
        this.quantity = quantity;
    }
    /**
     * Getter method to retrieve the id of the order.
     *
     * @return The id of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method to set the id of the order.
     *
     * @param id The id to set for the order
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method to retrieve the quantity of the product in the order.
     *
     * @return The quantity of the product in the order
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method to set the quantity of the product in the order.
     *
     * @param quantity The quantity to set for the product in the order
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method to retrieve the id of the client placing the order.
     *
     * @return The id of the client placing the order
     */
    public int getId_client() {
        return id_client;
    }

    /**
     * Setter method to set the id of the client placing the order.
     *
     * @param id_client The id to set for the client placing the order
     */
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    /**
     * Getter method to retrieve the id of the product being ordered.
     *
     * @return The id of the product being ordered
     */
    public int getId_product() {
        return id_product;
    }

    /**
     * Setter method to set the id of the product being ordered.
     *
     * @param id_product The id to set for the product being ordered
     */
    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    /**
     * Overrides the default toString method to provide a string representation of the Orders object.
     *
     * @return A string representation of the Orders object
     */
    @Override
    public String toString() {
        return "Order [id=" + id + ", id_client=" + id_client + ", id_product=" + id_product + ", quantity=" + quantity + "]";
    }

}
