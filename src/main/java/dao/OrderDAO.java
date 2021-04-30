package dao;

import connection.ConnectionFactory;
import model.Order;

import java.sql.*;

/**
 * Clasa contine principalele metode de manipulare a bazei de date pentru
 * tabelul "orderr"
 */
public class OrderDAO {

	private static final String insertStatementString = "INSERT INTO orderr (clientName, productName, quantity)"
			+ " VALUES (?,?,?)";

	/**
	 * Metoda care introduce o comanda in baza de date, in tabelul "orderr"
	 * 
	 * @param order
	 * @return
	 */
	public static int insert(Order order) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, order.getClientName());
			insertStatement.setString(2, order.getProductName());
			insertStatement.setInt(3, order.getQuantity());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("OrderDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
}
