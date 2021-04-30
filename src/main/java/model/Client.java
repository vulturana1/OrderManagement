package model;

/**
 * Clasa contine detalii despre un client: id, nume, adresa precum si
 * constructor, getters si setters
 */
public class Client {

	private int id;
	private String name;
	private String address;

	public Client(int id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public Client(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", nume=" + name + ", adresa=" + address + "]";
	}
}
