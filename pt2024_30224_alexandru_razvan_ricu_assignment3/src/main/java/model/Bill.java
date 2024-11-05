package model;

import java.time.LocalDateTime;
/**
 * The Bill class represents a bill for a transaction.
 * It encapsulates information about the client, product, total price, and timestamp of the transaction.
 * This class is immutable and uses Java 14's record feature to succinctly define its properties.
 */
public record Bill(String clientName, String productName, double totalPrice, LocalDateTime timestamp) {
}
