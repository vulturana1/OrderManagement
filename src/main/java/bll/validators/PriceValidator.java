package bll.validators;

import model.Product;

/**
 * Clasa utilizata pt a valida pretul produsului
 */

public class PriceValidator implements Validator<Product> {
	public void validate(Product t) {
		if (t.getPrice() < 0) {
			throw new IllegalArgumentException("Pretul nu poate fi negativ!");
		}
	}
}
