package model;

/**
 * Clasa contine detalii despre o comanda: id, numele clientului, numele
 * produsului si cantitatea, precum si contructori, getters si setters.
 */
public class Order {

	private int id;
	private String clientName;
	private String productName;
	private int quantity;

	public Order(String clientName, String productName, int quantity) {
		super();
		this.clientName = clientName;
		this.productName = productName;
		this.quantity = quantity;
	}

	public Order(int id, String clientName, String productName, int quantity) {
		super();
		this.id = id;
		this.clientName = clientName;
		this.productName = productName;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Comanda [id=" + id + ", numeClient=" + clientName + ", numeProdus=" + productName + ", cantitate="
				+ quantity + "]";
	}
}
