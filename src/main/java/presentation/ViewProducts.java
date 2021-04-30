package presentation;

import bll.ProductBll;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;

import javax.swing.*;
import java.util.ArrayList;

public class ViewProducts {

	private static JFrame frame;
	private JTable table;
	private JScrollPane sp;

	public ViewProducts() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 494, 168);

		String[] columns = { "id", "Nume", "Cantitate", "Pret" };
		ArrayList<Product> products = new ArrayList<>();
		ProductBll productsBll = new ProductBll();
		products = productsBll.findAllProducts();
		int i = 0;
		String[][] data = new String[products.size()][4];
		for (Product p : products) {
			data[i][0] = String.valueOf(p.getId());
			data[i][1] = p.getName();
			data[i][2] = String.valueOf(p.getQuantity());
			data[i][3] = String.valueOf(p.getPrice());
			i++;
		}
		table = new JTable(data, columns);
		table.setBounds(100, 100, 494, 168);
		sp = new JScrollPane(table);
		frame.add(sp);
		frame.setVisible(true);
	}

}
