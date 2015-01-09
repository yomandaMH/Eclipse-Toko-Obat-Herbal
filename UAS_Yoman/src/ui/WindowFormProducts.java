package ui;

import java.awt.Container;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import object.Products;
import system.*;
import ui.listener.CustActionListener;
import ui.listener.CustKeyListener;

public class WindowFormProducts extends JFrame {
	private Core core;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextField tfidProduct, tfnama, tfidSuppliers, tfharga, tfstok;
	private JTable tbl;
	private JLabel lbidProduct, lbNama, lbSuppliers, lbharga, lbstok;

	private Vector<Products> barang = new Vector<Products>();
	private Vector<String> nmBarang = new Vector<String>();

	public WindowFormProducts(final Core core) {
		super("Formulir Products");
		this.core = core;
		setResizable(false);

		setSize(810, 272);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(Color.GREEN);
		JMenuBar menu = new JMenuBar();
		this.setJMenuBar(menu);

		JMenu menuUser = new JMenu(
				core.getLoggedInUser().isAdmin() ? "admin" : "admin"
						+ core.getLoggedInUser().getUsername());
		JMenuItem miLogOut = new JMenuItem("Log Out");
		miLogOut.addActionListener(new CustActionListener(core, this, miLogOut,
				CustActionListener.LOGOUT));

		JMenu menuTrans = new JMenu("Transaksi");
		JMenuItem miTransData = new JMenuItem("Data Transaksi");
		miTransData.addActionListener(new CustActionListener(core, this,
				miTransData, CustActionListener.SHOW_DATA_TRANSAKSI));
		/*
		 * JMenuItem miTransCetak = new JMenuItem("Cetak Transaksi");
		 */
		JMenu menuProducts = new JMenu("Products");
		JMenuItem miProductsCetak = new JMenuItem("Cetak Products");
		miProductsCetak.addActionListener(new CustActionListener(core, this,
				miProductsCetak, CustActionListener.CETAK_BARANG));
		/*
		 * JMenuItem miTransCetak = new JMenuItem("Cetak Products");
		 */
		JMenu usr = new JMenu("usr");
		JMenuItem user = new JMenuItem("usr Products");
		user.addActionListener(new CustActionListener(core, this,
				user, CustActionListener.SHOW_DATA_USER));
		
		JMenu menuUsers = new JMenu("Users");
		JMenuItem miUsersData = new JMenuItem("Data Users");
		miUsersData.addActionListener(new CustActionListener(core, this,
				miUsersData, CustActionListener.SHOW_DATA_USER));
		
		usr.add(menuUser);
		user.add(miLogOut);
		
		menu.add(menuUser);
		menuUser.add(miLogOut);

		menu.add(menuTrans);
		// menuTrans.add(miTransCetak);
		menuTrans.add(miTransData);
		menu.add(menuProducts);
		// menuProducts.add(miProductsData);
		menuProducts.add(miProductsCetak);
		menu.add(menuUsers);
		// menuUsers.add(miUsersCetak);
		menuUsers.add(miUsersData);
		
		ResultSet rs = Operator.getListUser(core.getConnection());
		try {
			while (rs.next()) {
				barang.add(new Products(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		tbl = new JTable(Operator.resultSetToTableModel(Operator
				.getListProducts(core.getConnection())));
		Operator.disableTableEdit(tbl);
		JPanel panTbl = new JPanel();

		panTbl.setLayout(new BorderLayout());
		panTbl.setBackground(Color.WHITE);
		panTbl.add(new JScrollPane(tbl), BorderLayout.CENTER);

		tfidProduct = new JTextField();
		tfnama = new JTextField();
		tfidSuppliers = new JTextField();
		tfharga = new JTextField();
		tfstok = new JTextField();

		tfidProduct.setBounds(115, 10, 170, 20);
		tfnama.setBounds(115, 35, 170, 20);
		tfidSuppliers.setBounds(115, 60, 170, 20);
		tfharga.setBounds(115, 85, 170, 20);
		tfharga.addKeyListener(new CustKeyListener(core, this, tfharga,
				CustKeyListener.NUMBER_ONLY));
		tfstok.setBounds(115, 110, 170, 20);
		tfstok.addKeyListener(new CustKeyListener(core, this, tfstok,
				CustKeyListener.NUMBER_ONLY));

		panTbl.setBounds(295, 10, 500, 200);

		lbidProduct = new JLabel("ID Products");
		lbNama = new JLabel("Nama");
		lbSuppliers = new JLabel("ID Suppliers");
		lbharga = new JLabel("Harga");
		lbstok = new JLabel("Stock");
		
		lbidProduct.setBounds(10, 10, 100, 20);
		lbidProduct.setHorizontalAlignment(JLabel.RIGHT);
		lbNama.setBounds(10, 35, 100, 20);
		lbNama.setHorizontalAlignment(JLabel.RIGHT);
		lbSuppliers.setBounds(10, 60, 100, 20);
		lbSuppliers.setHorizontalAlignment(JLabel.RIGHT);
		lbharga.setBounds(10, 85, 100, 20);
		lbharga.setHorizontalAlignment(JLabel.RIGHT);
		lbstok.setBounds(10, 110, 100, 20);
		lbstok.setHorizontalAlignment(JLabel.RIGHT);

		JButton buttonTambah = new JButton("Tambah");
		JButton buttonDelete = new JButton("Hapus");

		buttonTambah.setBounds(205, 135, 80, 20);
		buttonTambah.addActionListener(new CustActionListener(core, this,tbl,
				buttonTambah, CustActionListener.TAMBAH_BARANG));
		buttonDelete.setBounds(115, 135, 80, 20);
		buttonDelete.addActionListener(new CustActionListener(core, this,tbl,
				buttonDelete, CustActionListener.HAPUS_BARANG));
		// Add Content
		container.add(tfidProduct);
		container.add(tfnama);
		container.add(tfidSuppliers);
		container.add(tfharga);
		container.add(tfstok);
		container.add(panTbl);
		container.add(lbidProduct);
		container.add(lbNama);
		container.add(lbSuppliers);
		container.add(lbharga);
		container.add(lbstok);

		container.add(buttonDelete);
		container.add(buttonTambah);
	}

	public Vector<Products> getListBarang() {
		return barang;
	}

	public Products getSelectedBarang() {
		return barang.get(tbl.getSelectedRow());
	}

	public void submitToDB() {
		if (Operator.tambahBarang(getBarang(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi kesalahan!");
		}
		
		((DefaultTableModel)tbl.getModel()).addRow(new Object[]{tfidProduct.getText(),tfnama.getText(),tfidSuppliers.getText(),tfharga.getText(),tfstok.getText()});
		
		tfidProduct.setText("");
		tfnama.setText("");
		tfidSuppliers.setText("");
		tfharga.setText("");
		tfstok.setText("");
	}

	public void resetForm() {
		tfidProduct.setText("");
		tfnama.setText("");
		tfidSuppliers.setText("");
		tfharga.setText("");
		tfstok.setText("");

		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel())
					.removeRow(tbl.getSelectedRow());
		}
	}


	public Products getBarang() {
		return new Products(tfidProduct.getText(),tfnama.getText(),tfidSuppliers.getText(),Integer.parseInt(tfharga.getText()),Integer.parseInt(tfstok.getText()));
				
	}
}
