package presentation;

import bll.ProductBll;
import model.Product;
import start.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductWindow {

	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	private static JFrame frame;
	private JTable table;
	private JTextField txtName;
	private JTextField txtQuantity;
	private JTextField txtName1;
	private JTextField txtPrice;
	private JTextField txtName3;
	private JTextField txtQuantity2;
	private JTextField txtPrice2;

	ProductWindow() {
		frame = new JFrame("Product");

		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 494, 168);
		frame.getContentPane().setLayout(null);

		JButton btnAdd = new JButton("Add new product");
		btnAdd.setBounds(10, 10, 132, 21);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String quantity = txtQuantity.getText();
				String price = txtPrice.getText();
				Product product = new Product(name, Integer.parseInt(quantity), Double.parseDouble(price));
				ProductBll productBll = new ProductBll();
				try {
					int id = productBll.insertProduct(product);
				} catch (Exception ex) {
					LOGGER.log(Level.INFO, ex.getMessage());
				}
			}
		});
		frame.getContentPane().add(btnAdd);

		JButton btnEdit = new JButton("Edit product");
		btnEdit.setBounds(10, 41, 132, 21);
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName3.getText();
				int quant = Integer.parseInt(txtQuantity2.getText());
				double price = Double.parseDouble(txtPrice2.getText());
				Product product = new Product(name, quant, price);
				ProductBll productBll = new ProductBll();
				productBll.updateProduct(product);
			}
		});
		frame.getContentPane().add(btnEdit);

		JButton btnDelete = new JButton("Delete product");
		btnDelete.setBounds(10, 72, 132, 21);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName1.getText();
				ProductBll productBll = new ProductBll();
				try {
					productBll.findProduct(name);
					int id = productBll.deleteProduct(name);
				} catch (Exception ex) {
					LOGGER.log(Level.INFO, ex.getMessage());
				}
			}
		});
		frame.getContentPane().add(btnDelete);

		JButton btnView = new JButton("View all products");
		btnView.setBounds(10, 103, 132, 21);
		btnView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewProducts viewProducts = new ViewProducts();
			}
		});
		frame.getContentPane().add(btnView);

		txtName = new JTextField();
		txtName.setText("Name");
		txtName.setBounds(150, 11, 96, 19);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);

		txtQuantity = new JTextField();
		txtQuantity.setText("Quantity");
		txtQuantity.setBounds(256, 11, 96, 19);
		frame.getContentPane().add(txtQuantity);
		txtQuantity.setColumns(10);

		txtName1 = new JTextField();
		txtName1.setText("Name");
		txtName1.setColumns(10);
		txtName1.setBounds(150, 73, 96, 19);
		frame.getContentPane().add(txtName1);

		txtPrice = new JTextField();
		txtPrice.setText("Price");
		txtPrice.setBounds(362, 11, 96, 19);
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);
		frame.setVisible(true);

		txtName3 = new JTextField();
		txtName3.setText("Name");
		txtName3.setColumns(10);
		txtName3.setBounds(150, 42, 96, 19);
		frame.getContentPane().add(txtName3);

		txtQuantity2 = new JTextField();
		txtQuantity2.setText("Quantity");
		txtQuantity2.setColumns(10);
		txtQuantity2.setBounds(256, 42, 96, 19);
		frame.getContentPane().add(txtQuantity2);

		txtPrice2 = new JTextField();
		txtPrice2.setText("Price");
		txtPrice2.setColumns(10);
		txtPrice2.setBounds(362, 42, 96, 19);
		frame.getContentPane().add(txtPrice2);
	}
}
