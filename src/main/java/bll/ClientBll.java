package bll;

import dao.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Clasa utilizata pentru a apela metodele corespunzatoare din pachetul DAO
 * incapsuleaza logica aplicatiei in cazul tabelului "client"
 */
public class ClientBll {

	public int insertClient(Client client) {
		return ClientDAO.insert(client);
	}

	public int deleteClient(Client client) {
		return ClientDAO.delete(client);
	}

	public void editClient(String name, String address) {
		Client cl = ClientDAO.findClientByName(name);
		if (cl == null) {
			throw new NoSuchElementException("The client with name " + name + " was not found!");
		}
		ClientDAO.editClient(name, address);
	}

	public Client findClient(String name, String address) {
		Client cl = ClientDAO.findClient(name, address);
		if (cl == null) {
			throw new NoSuchElementException(
					"The client with name " + name + " and address " + address + " was not found!");
		}
		return cl;
	}

	public Client findClientByName(String name) {
		Client cl = ClientDAO.findClientByName(name);
		if (cl == null) {
			throw new NoSuchElementException("The client with name " + name + " was not found!");
		}
		return cl;
	}

	public ArrayList<Client> findAllClients() {
		return ClientDAO.findAll();
	}
}
