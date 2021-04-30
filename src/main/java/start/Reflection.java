package start;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Clasa contine o metoda prin care primeste o lista de obiecte si genereaza
 * unde numele coloanelor sunt proprietatile obiectelor, si tabelul se umple cu
 * valorile elementelor din lista. Pentru aceasta metoda se folosec tehnici de
 * reflection.
 */

public class Reflection {

	private static JFrame frame;
	private JTable table;
	private JScrollPane sp;

	ArrayList<Object> objects;

	public Reflection(ArrayList<Object> objects) {
		this.objects = objects;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 494, 168);

		ArrayList<String> fields = retrieveProperties(objects);
		String[] columns = new String[fields.size()];

		for (int i = 0; i < fields.size(); i++) {
			columns[i] = fields.get(i);
		}
		String[][] data = new String[objects.size()][columns.length];
		int i = 0;
		int j = 0;
		for (Object o : objects) {
			for (Field field : o.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				Object value;
				try {
					value = field.get(o);
					if (i == columns.length) {
						j++;
						i = 0;
					}
					data[j][i++] = String.valueOf(value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		table = new JTable(data, columns);
		table.setBounds(100, 100, 494, 168);
		sp = new JScrollPane(table);
		frame.add(sp);
		frame.setVisible(true);
	}

	public static ArrayList<String> retrieveProperties(ArrayList<Object> objects) {
		ArrayList<String> fields = new ArrayList<>();
		for (Field field : objects.get(0).getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(objects.get(0));
				fields.add(field.getName());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return fields;

	}
}
