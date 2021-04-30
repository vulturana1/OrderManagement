package bll;

import bll.validators.QuantityOrderValidator;
import bll.validators.QuantityValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;

import java.util.ArrayList;
import java.util.List;

/***
 * Clasa utilizata pentru a apela metodele corespunzatoare din pachetul DAO
 * incapsuleaza logica aplicatiei in cazul tabelului "orderr"
 */
public class OrderBll {

	private List<Validator<Order>> validators;

	public OrderBll() {
		validators = new ArrayList<Validator<Order>>();
		validators.add(new QuantityOrderValidator());
	}

	public int insertOrder(Order order) {
		for (Validator<Order> v : validators) {
			v.validate(order);
		}
		ProductDAO.update(order);
		return OrderDAO.insert(order);
	}
}
