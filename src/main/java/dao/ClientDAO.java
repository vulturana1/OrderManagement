package dao;

import connection.ConnectionFactory;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa contine principalele metode de manipulare a bazei de date pentru
 * tabelul "client"
 */
public class ClientDAO {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO client (name,address)" + " VALUES (?,?)";
	private final static String deleteStatementString = "DELETE FROM client where name = ? and address = ?";
	private final static String findStatementString = "SELECT * FROM client where name = ? and address = ?";
	private final static String findNameStatementString = "SELECT * FROM client where name = ?";
	private final static String findAllStatementString = "SELECT * FROM client";
	private final static String updateStatementString = "UPDATE client SET address = ? where name = ?";

	/**
	 * Metoda care adauga un client in baza de date in tabelul "client"
	 * 
	 * @param client
	 * @return
	 */
	public static int insert(Client client) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, client.getName());
			insertStatement.setString(2, client.getAddress());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Metoda pentru a sterge un client din baza de date din tabelul "client"
	 * 
	 * @param client
	 * @return
	 */
	public static int delete(Client client) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int deletedId = -1;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setString(1, client.getName());
			deleteStatement.setString(2, client.getAddress());
			deleteStatement.executeUpdate();

			ResultSet rs = deleteStatement.getGeneratedKeys();
			if (rs.next()) {
				deletedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return deletedId;
	}

	/**
	 * Metoda care modifia adresa unui client din tabelul "client
	 * 
	 * @param name    nume clientului
	 * @param address adresa clientului
	 */
	public static void editClient(String name, String address) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
			updateStatement.setString(2, name);
			updateStatement.setString(1, address);
			updateStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
	}

	/**
	 * Metoda care cauta un client dupa nume si adresa
	 * 
	 * @param name    nume clientului
	 * @param address adresa clientului
	 * @return
	 */
	public static Client findClient(String name, String address) {
		Client toReturn = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setString(1, name);
			findStatement.setString(2, address);
			rs = findStatement.executeQuery();
			int id = 0;
			if (rs.next()) {
				id = rs.getInt("idClient");
				toReturn = new Client(id, name, address);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:find " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda care cauta un client dupa nume
	 * 
	 * @param name nume clientului
	 * @return
	 */
	public static Client findClientByName(String name) {
		Client toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findNameStatementString);
			findStatement.setString(1, name);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("idClient");
			String address = rs.getString("address");
			toReturn = new Client(id, name, address);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Metoda care returneaza o lista cu toti clientii
	 * 
	 * @return
	 */
	public static ArrayList<Client> findAll() {
		Client toReturn = null;
		ArrayList<Client> list = new ArrayList<Client>();
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findAllStatementString);
			rs = findStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("idClient");
				String name = rs.getString("name");
				String address = rs.getString("address");
				toReturn = new Client(id, name, address);
				list.add(toReturn);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return list;
	}
}
