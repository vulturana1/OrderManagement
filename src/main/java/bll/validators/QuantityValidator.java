package bll.validators;

import model.Product;

/***
 * Clasa folosita pentru a valida cantitatea unui produs
 */
public class QuantityValidator implements Validator<Product> {

	public void validate(Product t) {
		if (t.getQuantity() < 0) {
			throw new IllegalArgumentException("Cantitatea nu poate fi negativa!");
		}
	}
}
