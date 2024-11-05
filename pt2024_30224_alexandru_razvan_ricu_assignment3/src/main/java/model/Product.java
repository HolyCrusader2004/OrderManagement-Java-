package model;
/**
 * The Product class represents a product available for sale.
 * It encapsulates information about the product, such as its id, name, quantity, and price.
 *
 * This class provides constructors to initialize a product object with or without an id.
 * It also provides getter and setter methods for accessing and modifying the product's properties.
 */
public class Product {
    private int id;
    private String name;
    private int quantity;
    private int price;
    /**
     * Default constructor for the Product class.
     * Initializes an empty product object.
     */
    public Product() {
    }
    /**
     * Parameterized constructor for the Product class.
     * Initializes a product object with the provided id, name, quantity, and price.
     *
     * @param id The unique identifier for the product
     * @param name The name of the product
     * @param quantity The quantity of the product available
     * @param price The price of the product
     */
    public Product(int id, String name, int quantity, int price) {
        super();
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    /**
     * Parameterized constructor for the Product class.
     * Initializes a product object without an id.
     *
     * @param name The name of the product
     * @param quantity The quantity of the product available
     * @param price The price of the product
     */
    public Product(String name, int quantity, int price) {
        super();
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    /**
     * Getter method to retrieve the id of the product.
     *
     * @return The id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method to set the id of the product.
     *
     * @param id The id to set for the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method to retrieve the name of the product.
     *
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method to set the name of the product.
     *
     * @param name The name to set for the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method to retrieve the quantity of the product.
     *
     * @return The quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method to set the quantity of the product.
     *
     * @param quantity The quantity to set for the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method to retrieve the price of the product.
     *
     * @return The price of the product
     */
    public int getPrice() {
        return price;
    }

    /**
     * Setter method to set the price of the product.
     *
     * @param price The price to set for the product
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Overrides the default toString method to provide a string representation of the Product object.
     *
     * @return A string representation of the Product object
     */
    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", quantity=" + quantity + ", price=" + price + "]";
    }
}
