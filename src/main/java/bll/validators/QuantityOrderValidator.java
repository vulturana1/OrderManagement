package bll.validators;

import bll.OrderBll;
import bll.ProductBll;
import model.Order;
import model.Product;
import presentation.Bill;

import javax.swing.*;

/**
 * Clasa folosita pentru a valida cantitatea unei comenzi
 */
public class QuantityOrderValidator implements Validator<Order> {

	public void validate(Order t) {
		if (t.getQuantity() < 0) {
			throw new IllegalArgumentException("Nu puteti comanda o cantitate negativa!");
		}
		ProductBll productBll = new ProductBll();
		Product product = productBll.findProduct(t.getProductName());
		if (product.getQuantity() < t.getQuantity()) {
			JOptionPane.showMessageDialog(null, "Under stock!", null, JOptionPane.INFORMATION_MESSAGE);
			Bill bill = new Bill();
			bill.generateUnderStock(t, product.getQuantity());
			throw new IllegalArgumentException("Under stock");
		}
	}
}
