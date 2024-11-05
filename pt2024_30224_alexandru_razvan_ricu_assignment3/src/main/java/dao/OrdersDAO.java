package dao;

import bll.ProductBLL;
import connection.ConnectionFactory;
import model.Orders;
import model.Product;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.logging.Level;
/**
 * The OrdersDAO class extends the AbstractDAO class and provides specific data access operations for the Orders entity.
 * It inherits generic CRUD (Create, Read, Update, Delete) operations from AbstractDAO.
 */
public class OrdersDAO extends AbstractDAO<Orders> {
    private final Class<Orders> type2;
    private int prod_id = 0;
    private int order_quantity = 0;
    /**
     * Constructor for the OrdersDAO class.
     * Initializes the DAO to handle Orders entities and retrieves the type parameter.
     */
    public OrdersDAO() {
        type2 = (Class<Orders>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    /**
     * Creates an SQL INSERT query string for the Orders entity.
     *
     * @return The SQL INSERT query string
     */
    private String createInsertOrderQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type2.getSimpleName());
        sb.append(" (");
        for (Field field : type2.getDeclaredFields()) {
            sb.append(field.getName()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(") VALUES (");
        for (int i = 0; i < type2.getDeclaredFields().length; i++) {
            sb.append("?, ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");
        return sb.toString();
    }
    /**
     * Inserts an order record into the database.
     * Additionally, updates the product quantity based on the order quantity.
     *
     * @param t The order object to insert
     * @return The inserted order object
     */
    public Orders insertOrder(Orders t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProductBLL productbll = new ProductBLL();
        String query = createInsertOrderQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            for (Field field : type2.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(t);
                if(field.getName().equals("id_product")){
                    prod_id = (int) value;
                }
                if(field.getName().equals("quantity")){
                    order_quantity = (int) value;
                }
                statement.setObject(index++, value);
            }
            Product prod = productbll.findProductById(prod_id);
            prod.setQuantity(prod.getQuantity()-order_quantity);
            productbll.updateProduct(prod);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type2.getName() + "DAO:insertOrder " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }
}
