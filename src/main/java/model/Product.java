package model;

/**
 * Clasa contine detalii despre un produs: id, numele, cantitatea si pretul,
 * precum si contructori, getters si setters.
 */
public class Product {
	private int id;
	private String name;
	private int quantity;
	private double price;

	public Product(String name, int quantity, double price) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public Product(int id, String name, int quantity, double price) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Produs [id=" + id + ", nume=" + name + ", cantitate=" + quantity + ", pret=" + price + "]";
	}
}
