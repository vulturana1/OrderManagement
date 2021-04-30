package bll;

import bll.validators.*;
import dao.*;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa utilizata pentru a apela metodele corespunzatoare din pachetul DAO
 * incapsuleaza logica aplicatiei in cazul tabelului "product"
 */
public class ProductBll {

	private List<Validator<Product>> validators;

	public ProductBll() {
		validators = new ArrayList<Validator<Product>>();
		validators.add(new QuantityValidator());
		validators.add(new PriceValidator());
	}

	public int insertProduct(Product prod) {
		for (Validator<Product> v : validators) {
			v.validate(prod);
		}
		return ProductDAO.insert(prod);
	}

	public Product findProductById(int id) {
		Product st = ProductDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The Product with id = " + id + " was not found!");
		}
		return st;
	}

	public int deleteProduct(String prod) {
		return ProductDAO.delete(prod);
	}

	public void updateProduct(Product prod) {
		Product st = ProductDAO.findProduct(prod.getName());
		if (st == null) {
			throw new NoSuchElementException("The Product with name = " + prod.getName() + " was not found!");
		}
		for (Validator<Product> v : validators) {
			v.validate(prod);
		}
		ProductDAO.updateProduct(prod);
	}

	public ArrayList<Product> findAllProducts() {
		return ProductDAO.findAll();
	}

	public Product findProduct(String nume) {
		Product st = ProductDAO.findProduct(nume);
		if (st == null) {
			throw new NoSuchElementException("The Product with name = " + nume + " was not found!");
		}
		return st;
	}
}
