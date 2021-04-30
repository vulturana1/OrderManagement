package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {

	public View() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 192);
		getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Client");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientWindow w = new ClientWindow();
			}
		});

		btnNewButton.setBounds(43, 80, 85, 21);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Product");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ProductWindow w = new ProductWindow();
			}
		});
		btnNewButton_1.setBounds(185, 80, 85, 21);
		getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Order");
		btnNewButton_2.setBounds(319, 80, 85, 21);
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OrderWindow w = new OrderWindow();
			}
		});
		getContentPane().add(btnNewButton_2);

		JLabel lblNewLabel = new JLabel("Order Management");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(168, 10, 207, 34);
		getContentPane().add(lblNewLabel);

		this.setVisible(true);
	}

//    class ClientListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            ClientWindow w = new ClientWindow();
//        }
//    }
//
//    class ProductListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            ProductWindow w = new ProductWindow();
//        }
//    }
//
//    class OrderListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            OrderWindow w = new OrderWindow();
//        }
//    }
}
