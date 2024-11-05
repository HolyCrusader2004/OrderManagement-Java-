package dao;

import connection.ConnectionFactory;
import model.Bill;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.logging.Level;

import static dao.AbstractDAO.LOGGER;
/**
 * The BillDAO class provides data access operations for the Bill entity.
 * It includes methods for inserting bill records into the database.
 */
public class BillDAO{
    private final Class type;
    /**
     * Constructor for the BillDAO class.
     * Initializes the type parameter to represent the Bill class.
     */
    public BillDAO() {
        type = Bill.class;
    }
    /**
     * Creates an SQL INSERT query string for the Bill entity.
     *
     * @return The SQL INSERT query string
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");
        for (Field field : type.getDeclaredFields()) {
            sb.append(field.getName()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(") VALUES (");
        for (int i = 0; i < type.getDeclaredFields().length; i++) {
            sb.append("?, ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");
        return sb.toString();
    }
    /**
     * Inserts a bill record into the database.
     *
     * @param t The bill object to insert
     * @return The inserted bill object
     */
    public Bill insert(Bill t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(t);
                statement.setObject(index++, value);
            }
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insertBill " + e.getMessage());
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
