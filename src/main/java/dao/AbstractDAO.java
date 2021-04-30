package dao;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

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

	private String createUpdateQuery(String setField, String whereField) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE warehouse.");
		sb.append(type.getSimpleName());
		sb.append(" SET " + setField + " =? ");
		sb.append(" WHERE " + whereField + " =?");
		return sb.toString();
	}

	private String createInsertQuery() {
		StringBuilder sb = new StringBuilder();
		StringBuilder aux = new StringBuilder();
		sb.append("Insert ");
		sb.append(" INTO warehouse.");
		sb.append(type.getSimpleName());
		sb.append(" ( ");
		for (Field field : type.getDeclaredFields()) {
			if (field.getName() != "id") {
				sb.append(field.getName() + " , ");
				aux.append(" ? , ");
			}
		}
		sb.delete(sb.length() - 3, sb.length() - 1);
		aux.delete(aux.length() - 3, aux.length() - 1);
		sb.append(" ) ");

		sb.append(" VALUES ( ");
		sb.append(aux);
		sb.append(" )");

		return sb.toString();
	}

	private String createSelectAllQuery()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM warehouse.");
		sb.append(type.getSimpleName());
		return sb.toString();
	}

	public List<T> findAll() {
		Connection dbConnection = null;
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		String query = createSelectAllQuery();
		try {
			dbConnection = ConnectionFactory.getConnection();
			findStatement = dbConnection.prepareStatement(query);
			rs = findStatement.executeQuery();
			return createObjects(rs);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,type.getName() + "DAO:findAll " + e.getMessage());
		}
		finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return null;
	}

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

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();

		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
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

	public T insert(T t) {
		Connection dbConnection = null;
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		String query = createInsertQuery();
		try {
			dbConnection = ConnectionFactory.getConnection();
			insertStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			for (Field field : t.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.getName() != "id") {
					if (field.getType().getSimpleName().equals("String"))
						insertStatement.setString(index, field.get(t).toString());
					else if (field.getType().getSimpleName().equals("int"))
						insertStatement.setInt(index, field.getInt(t));
					else {
						insertStatement.setFloat(index, field.getFloat(t));
					}
					index++;
				}
			}
			insertStatement.executeUpdate();
		} catch (SQLException | IllegalAccessException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return null;
	}

	public void update(int newValue,String setFieldName,int whereValue,String whereFieldName) {
		Connection dbConnection = null;
		PreparedStatement findStatement = null;
		String query = createUpdateQuery(setFieldName,whereFieldName);
		try {
			dbConnection = ConnectionFactory.getConnection();
			findStatement = dbConnection.prepareStatement(query);
			findStatement.setInt(1, newValue);
			findStatement.setInt(2, whereValue);
			findStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,type.getName() + "DAO:update " + e.getMessage());
		}
		finally {
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
}
