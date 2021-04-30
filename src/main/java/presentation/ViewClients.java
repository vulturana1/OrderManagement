package presentation;

import bll.ClientBll;
import model.Client;

import javax.swing.*;
import java.util.ArrayList;

public class ViewClients {

	private static JFrame frame;
	private JTable table;
	private JScrollPane sp;

	public ViewClients() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 494, 168);

		String[] columns = { "id", "Nume", "Adresa" };
		ArrayList<Client> clients = new ArrayList<>();
		ClientBll clientBll = new ClientBll();
		clients = clientBll.findAllClients();
		int i = 0;
		String[][] data = new String[clients.size()][3];
		for (Client c : clients) {
			data[i][0] = String.valueOf(c.getId());
			data[i][1] = c.getName();
			data[i][2] = c.getAddress();
			i++;
		}
		table = new JTable(data, columns);
		table.setBounds(100, 100, 494, 168);
		sp = new JScrollPane(table);
		frame.add(sp);
		frame.setVisible(true);
	}

}
