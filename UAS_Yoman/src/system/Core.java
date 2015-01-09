package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import javax.swing.table.*;

import ui.*;
import object.*;

public class Core {

	final public static boolean GUI = true, CUI = false;
	final public static int PRODUCTS = 0, DETIL_TRANSAKSI = 1, TRANSAKSI = 2,
			USER = 3;

	public WindowLogin frmLogin = new WindowLogin(this);
	public WindowReport frmReport;
	public WindowFormTransaksi frmFormTrans;
	public WindowFormProducts frmFormProducts;
	public WindowsDataUsers frmUsr;
	public WindowDataTransaksi frmDataTrans;
	public WindowDataProducts frmDataProducts;

	private Connection con;
	private Users loggedUser;

	private static Calendar tgl = Calendar.getInstance();
	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"E, dd MMMM yyyy");

	public Core(boolean GUI) {
		tesKoneksi();
		if (GUI) {
			frmLogin.setVisible(true);
		} else {
			// CMD STYLE (~-,-)~ NOT IMPLEMENTED YET!!!
		}
	}

	private void tesKoneksi() {
		try {
			Class.forName(Koneksi.Database_Driver);
			con = DriverManager.getConnection(Koneksi.URL,Koneksi.username,Koneksi.password);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}
	}

	public void printReport(Transaksi trns) {
		int ID = Operator.getLastIDTrans(con);

		String header = "\n"
				+ "\t Aplikasi Penjualan Pada Toko Obat Herbal HPAI"
				+ "\n\t\t Perumahan Bumi Intan Permai U - 9"
				+ "\n\t \t\t No. "
				+ ID
				+ "\nKasir : "
				+ loggedUser.getUsername()
				+ "\n=================================================================", data = "", footer = "\n"
				+ "\n---------------------------------------"
				+ "\nTotal : "
				+ trns.getTotalItem()
				+ " Item      "
				+ trns.getTotalHrg()
				+ "\n================================================================="
				+ "\nTgl " + trns.getTanggalAsString();
		for (int i = 0; i < trns.getDetilTransaksi().size(); i++) {
			DetilTransaksi dt = trns.getDetilTransaksi().get(i);
			data = data + "\n" + dt.getBarang().getnama() + "\t"
					+ dt.getQuantity() + "x\t" + dt.getQuantity()
					* dt.getBarang().getharga();
		}
		frmReport = new WindowReport(this,
				new String[] { header, data, footer });
		frmReport.setVisible(true);
	}

	public void printReport(Vector<Products> brg) {
		String header = "\n"
				+ "\t Aplikasi Penjualan Pada Toko Obat Herbal HPAI"
				+ "\n\t\t Perumahan Bumi Intan Permai U - 9"
				+ "\n\t  Stok Products Tanggal "
				+ getDateAsString()
				+ "\nKasir : "
				+ loggedUser.getUsername()
				+ "\n===============================================================", data = "", footer = "\n===============================================================";

		
		for (int i = 0; i < brg.size(); i++) {
			
			Products _brg = brg.get(i);
			data = data + "\n  " + _brg.getnama();
			for (int a = 0; a < 25 - _brg.getnama().length(); a++) {
				data = data + " ";
			}
			
			data = data + _brg.getstock();
			for (int a = 0; a < 10 - ("" + _brg.getstock()).length(); a++) {
				data = data + " ";
			}
			data += _brg.getharga();
		}
		frmReport = new WindowReport(this,
				new String[] { header, data, footer });
		frmReport.setVisible(true);
	}

	public void login(Users usr) {
		this.loggedUser = new Users(usr);
		if (usr.isAdmin()) {
			frmFormProducts = new WindowFormProducts(this);
			frmFormProducts.setVisible(true);
		} else {
			frmFormTrans = new WindowFormTransaksi(this);
			frmFormTrans.setVisible(true);
		}
	}

	public void logout() {
		if (loggedUser.isAdmin()) {
			frmFormProducts.dispose();
		} else {
			frmFormTrans.dispose();
		}
		frmLogin.dispose();
		frmLogin = new WindowLogin(this);
		frmLogin.setVisible(true);
		loggedUser = null;
	}

	public Users getLoggedInUser() {
		return loggedUser;
	}

	public Connection getConnection() {
		return con;
	}

	public Date getDate() {
		return (Date) tgl.getTime();
	}

	public String getDateAsString() {
		return formatter.format(tgl.getTime());
	}

	public void reloadForm() {

	}
}
