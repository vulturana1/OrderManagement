package presentation;

import bll.*;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderWindow {
	private static JFrame frame;
	private JTextField textField;

	OrderWindow() {
		frame = new JFrame("Order");

		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 494, 168);
		frame.getContentPane().setLayout(null);

		JLabel lblClient = new JLabel("Select client:");
		lblClient.setBounds(26, 25, 86, 13);
		frame.getContentPane().add(lblClient);

		JLabel lblProd = new JLabel("Select product:");
		lblProd.setBounds(134, 25, 92, 13);
		frame.getContentPane().add(lblProd);

		JLabel lblInsert = new JLabel("Insert quantity:");
		lblInsert.setBounds(256, 25, 103, 13);
		frame.getContentPane().add(lblInsert);

		JComboBox comboBoxC = new JComboBox();
		comboBoxC.setBounds(10, 44, 88, 21);
		ArrayList<Client> clients = new ArrayList<>();
		ClientBll clientBll = new ClientBll();
		clients = clientBll.findAllClients();
		int i = 0;
		String[] data = new String[clients.size()];
		for (Client c : clients) {
			data[i] = c.getName();
			comboBoxC.addItem(data[i]);
			i++;
		}

		frame.getContentPane().add(comboBoxC);

		JComboBox comboBoxP = new JComboBox();
		comboBoxP.setBounds(120, 44, 106, 21);
		ArrayList<Product> products = new ArrayList<>();
		ProductBll productsBll = new ProductBll();
		products = productsBll.findAllProducts();
		i = 0;
		data = new String[products.size()];
		for (Product p : products) {
			data[i] = p.getName();
			comboBoxP.addItem(data[i]);
			i++;
		}
		frame.getContentPane().add(comboBoxP);

		textField = new JTextField();
		textField.setBounds(252, 45, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnOrder = new JButton("Place order");
		btnOrder.setBounds(364, 74, 106, 33);
		btnOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int quant = Integer.valueOf(textField.getText());
				String nameC = comboBoxC.getSelectedItem().toString();
				String nameP = comboBoxP.getSelectedItem().toString();
				Order order = new Order(nameC, nameP, quant);
				OrderBll orderBll = new OrderBll();
				try {
					int id = orderBll.insertOrder(order);
					Bill bill = new Bill();
					bill.generateBill(order);

				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		frame.getContentPane().add(btnOrder);
		frame.setVisible(true);
	}
}
