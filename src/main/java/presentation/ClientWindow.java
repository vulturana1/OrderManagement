package presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import bll.ClientBll;
import model.Client;
import start.Start;

public class ClientWindow {

	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
	private static JFrame frame;
	private JTable table;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtName1;
	private JTextField txtAddress1;
	private JTextField txtName2;
	private JTextField txtAddress2;

	ClientWindow() {
		frame = new JFrame("Client");

		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 494, 168);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Add new client");
		btnNewButton.setBounds(10, 10, 132, 21);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtAddress.getText();

				Client client = new Client(name, address);
				ClientBll clientBll = new ClientBll();
				int id = clientBll.insertClient(client);
			}
		});
		frame.getContentPane().add(btnNewButton);

		JButton btnEdit = new JButton("Edit client");
		btnEdit.setBounds(10, 41, 132, 21);
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName2.getText();
				String address = txtAddress2.getText();

				Client client = new Client(name, address);
				ClientBll clientBll = new ClientBll();
				clientBll.editClient(name, address);
			}
		});
		frame.getContentPane().add(btnEdit);

		JButton btnDelete = new JButton("Delete client");
		btnDelete.setBounds(10, 72, 132, 21);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String name = txtName1.getText();
				String address = txtAddress1.getText();
				Client client = new Client(name, address);
				ClientBll clientBll = new ClientBll();
				try {
					clientBll.findClient(name, address);
					clientBll.deleteClient(client);
				} catch (Exception ex) {
					LOGGER.log(Level.INFO, ex.getMessage());
				}
			}
		});
		frame.getContentPane().add(btnDelete);

		JButton btnView = new JButton("View all clients");
		btnView.setBounds(10, 103, 132, 21);
		btnView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewClients viewClients = new ViewClients();
			}
		});
		frame.getContentPane().add(btnView);

		txtName = new JTextField();
		txtName.setText("Name");
		txtName.setBounds(150, 11, 96, 19);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);

		txtAddress = new JTextField();
		txtAddress.setText("Address");
		txtAddress.setBounds(256, 11, 96, 19);
		frame.getContentPane().add(txtAddress);
		txtAddress.setColumns(10);

		txtName1 = new JTextField();
		txtName1.setText("Name");
		txtName1.setColumns(10);
		txtName1.setBounds(150, 73, 96, 19);
		frame.getContentPane().add(txtName1);

		txtAddress1 = new JTextField();
		txtAddress1.setText("Address");
		txtAddress1.setColumns(10);
		txtAddress1.setBounds(256, 73, 96, 19);
		frame.getContentPane().add(txtAddress1);
		frame.setVisible(true);

		txtName2 = new JTextField();
		txtName2.setText("Name");
		txtName2.setBounds(150, 42, 96, 19);
		frame.getContentPane().add(txtName2);
		txtName2.setColumns(10);

		txtAddress2 = new JTextField();
		txtAddress2.setText("Address");
		txtAddress2.setBounds(256, 42, 96, 19);
		frame.getContentPane().add(txtAddress2);
		txtAddress2.setColumns(10);
		frame.setVisible(true);

	}
}
