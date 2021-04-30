package dao;

import bll.ProductBll;
import connection.ConnectionFactory;
import model.Order;
import model.Product;
import start.Start;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa contine principalele metode de manipulare a bazei de date pentru
 * tabelul "product"
 */

public class ProductDAO {

	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
	private static final String insertStatementString = "INSERT INTO product (name, quantity, price)"
			+ " VALUES (?,?,?)";
	private final static String deleteStatementString = "DELETE FROM product where name = ?";
	private final static String findAllStatementString = "SELECT * FROM product";
	private static final String updateStatementString = "UPDATE product SET quantity = ? where name = ?";
	private final static String findProductStatementString = "SELECT * FROM product WHERE name = ?";
	private final static String findStatementString = "SELECT * FROM product where id = ?";
	private static final String updateProductStatementString = "UPDATE product SET quantity = ?, price = ? where name = ?";

	/**
	 * Metoda care adauga un produs in baza de date in tabelul "product"
	 * 
	 * @param prod
	 * @return
	 */
	public static int insert(Product prod) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, prod.getName());
			insertStatement.setInt(2, prod.getQuantity());
			insertStatement.setDouble(3, prod.getPrice());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Metoda pentru a sterge un produs din baza de date din tabelul "product"
	 * 
	 * @param product
	 * @return
	 */
	public static int delete(String product) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int deletedId = -1;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setString(1, product);
			deleteStatement.executeUpdate();

			ResultSet rs = deleteStatement.getGeneratedKeys();
			if (rs.next()) {
				deletedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return deletedId;
	}

	/**
	 * Metoda care modifica cantitatea unui produs. Este folosita cand se plaseaza o
	 * comanda pentru a decrementa stocul produsului din baza de date.
	 * 
	 * @param order
	 */
	public static void update(Order order) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		try {
			ProductBll productBll = new ProductBll();
			Product product = productBll.findProduct(order.getProductName());

			updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
			updateStatement.setInt(1, product.getQuantity() - order.getQuantity());
			updateStatement.setString(2, order.getProductName());
			updateStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:update " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
	}

	/**
	 * Metoda care modifica cantitatea si pretul unui produs
	 * 
	 * @param prod
	 */
	public static void updateProduct(Product prod) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		try {
			updateStatement = dbConnection.prepareStatement(updateProductStatementString,
					Statement.RETURN_GENERATED_KEYS);
			updateStatement.setInt(1, prod.getQuantity());
			updateStatement.setDouble(2, prod.getPrice());
			updateStatement.setString(3, prod.getName());
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:updateProduct " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
	}

	/**
	 * Metoda care cauta un produs dupa nume
	 * 
	 * @param nume
	 * @return
	 */
	public static Product findProduct(String nume) {
		Product toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findProductStatementString);
			findStatement.setString(1, nume);
			rs = findStatement.executeQuery();
			if (rs.next()) {
				int quantity = rs.getInt("quantity");
				double price = rs.getDouble("price");
				toReturn = new Product(nume, quantity, price);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:findProduct " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda care cauta un produs dupa id
	 * 
	 * @param id
	 * @return
	 */
	public static Product findById(int id) {
		Product toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			int quantity = rs.getInt("quantity");
			double price = rs.getDouble("price");
			toReturn = new Product(id, name, quantity, price);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda care returneaza o lista cu toate produsele
	 * 
	 * @return
	 */
	public static ArrayList<Product> findAll() {
		Product toReturn = null;
		ArrayList<Product> list = new ArrayList<Product>();
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findAllStatementString);
			rs = findStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				double price = rs.getDouble("price");
				toReturn = new Product(id, name, quantity, price);
				list.add(toReturn);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return list;
	}
}
