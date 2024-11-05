package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
/**
 * The AbstractDAO class provides generic data access operations for database entities.
 * It includes methods for CRUD operations (Create, Read, Update, Delete) and object mapping.
 *
 * @param <T> The type of entity handled by the DAO
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
	private final Class<T> type;
	/**
	 * Constructor for the AbstractDAO class.
	 * Initializes the type parameter based on the generic type argument.
	 */
	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}
	private String createSelectAllQuery(){
		StringBuilder sb =new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		return sb.toString();
	}
	/**
	 * Retrieves all entities of type T from the database.
	 *
	 * @return A list of entities
	 */
	public List<T> findAll() {
		Connection connection =null;
		PreparedStatement statement =null;
		ResultSet resultSet = null;
		String query = createSelectAllQuery();
		try{
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		}catch (SQLException e){
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}
	/**
	 * Retrieves an entity of type T from the database by its id.
	 *
	 * @param id The id of the entity to retrieve
	 * @return The entity with the specified id, or null if not found
	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			List<T> objects = createObjects(resultSet);
			if(objects.isEmpty()) {
				return null;
			} else {
				return objects.get(0);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**
	 * Creates a list of objects of type T from a ResultSet.
	 *
	 * @param resultSet The ResultSet containing data from the database
	 * @return A list of objects of type T
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

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
	 * Inserts a new entity of type T into the database.
	 *
	 * @param t The entity to insert
	 * @return The inserted entity
	 */
	public T insert(T t) {
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
			//System.out.println(statement.toString());
			statement.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return t;
	}
	private String createUpdateQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET ");
		Field[] fields = type.getDeclaredFields();
        for (Field f : fields) {
            if (!f.getName().equals("id")) {
                sb.append(f.getName()).append(" = ?, ");
            }
        }
		sb.delete(sb.length() - 2, sb.length());
		sb.append(" WHERE ").append(field).append(" = ?");
		return sb.toString();
	}
	/**
	 * Updates an existing entity of type T in the database.
	 *
	 * @param t The entity to update
	 * @return The updated entity
	 */
	public T update(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createUpdateQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			for (Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				if (!field.getName().equals("id")) {
					Object value = field.get(t);
					statement.setObject(index++, value);
				}
			}
			Field primaryKeyField = type.getDeclaredField("id");
			primaryKeyField.setAccessible(true);
			Object primaryKeyValue = primaryKeyField.get(t);
			statement.setObject(index, primaryKeyValue);
			statement.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return t;
	}
	private String createDeleteQuery(){
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append("orders");
		sb.append(" WHERE ").append("id_").append(type.getSimpleName()).append(" = ?");
		return sb.toString();
	}
	private String createDeleteQuery2(){
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE ").append("id").append(" = ?");
		return sb.toString();
	}
	/**
	 * Deletes an entity of type T from the database by its id.
	 *
	 * @param id The id of the entity to delete
	 */
	public T delete(int id){
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		String query = createDeleteQuery();
		String query2 = createDeleteQuery2();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement2 = connection.prepareStatement(query2);
			statement.setInt(1, id);
			statement2.setInt(1, id);
			statement.execute();
			statement2.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement2);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}
}
