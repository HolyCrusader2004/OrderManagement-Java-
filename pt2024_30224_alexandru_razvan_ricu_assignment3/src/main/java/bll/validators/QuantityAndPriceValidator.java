package bll.validators;

import model.Product;
/**
 * The QuantityAndPriceValidator class implements the Validator interface for validating Product objects.
 * It ensures that the price and quantity of a product are valid.
 */
public class QuantityAndPriceValidator implements Validator<Product> {
    /**
     * Validates a Product object's price and quantity.
     *
     * @param product The Product object to be validated.
     * @throws IllegalArgumentException if the product price or quantity is less than zero.
     */
    @Override
    public void validate(Product product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("The Product price needs to be higher than zero!");
        }
        if (product.getQuantity() < 0){
            throw new IllegalArgumentException("The Product quantity needs to be higher than zero!");
        }
    }
}
