package presentation;

import bll.ClientBll;
import bll.ProductBll;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Order;
import model.Product;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/***
 * Clasa contine metode care genereaza o factura pentru fiecare comanda in
 * format pdf
 */

public class Bill {
	private static int contor = 0;

	/**
	 * Genereaza o factura care contine produsele unei comenzi //@param client
	 * 
	 * @param order
	 */
	public void generateBill(Order order) {
		contor++;
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("BillPdf" + contor + ".pdf"));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		doc.open();
		Paragraph par = new Paragraph("Bill " + order.getId() + " \n");

		ClientBll clientBll = new ClientBll();
		Client client = clientBll.findClientByName(order.getClientName());

		par.add("Client Name: " + client.getName() + "            Address: " + client.getAddress() + "\n");
		PdfPTable table = new PdfPTable(3);
		table.addCell("Product");
		table.addCell("Quantity");
		table.addCell("Price");

		ProductBll productBll = new ProductBll();
		Product product = productBll.findProduct(order.getProductName());
		table.addCell(order.getProductName());
		table.addCell(String.valueOf(order.getQuantity()));
		table.addCell(String.valueOf(product.getPrice()));
		par.add(table);
		par.add("\nTotal Price:  " + product.getPrice() * order.getQuantity() + "\n");

		try {
			doc.add(par);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.close();
	}

	/**
	 * Genereaza un pdf cu mesaj de eroare daca stocul pentru produsul comandat nu
	 * este suficient
	 * 
	 * @param order
	 * @param quantity
	 */
	public void generateUnderStock(Order order, int quantity) {
		contor++;
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("errorBillPdf" + contor + ".pdf"));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		doc.open();
		Paragraph par = new Paragraph("Error \nProduct  " + order.getProductName() + " stock is: " + quantity + "\n");
		par.add("Your demand is " + order.getQuantity());

		try {
			doc.add(par);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		doc.close();
	}
}
