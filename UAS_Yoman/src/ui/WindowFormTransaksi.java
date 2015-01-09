package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import object.Products;
import object.DetilTransaksi;
import object.Transaksi;
import object.Users;

import system.*;
import ui.listener.CustActionListener;
import ui.listener.CustKeyListener;

public class WindowFormTransaksi extends JFrame {

	final int TGL = 0, KASIR = 1, BARANG = 2, HARGA = 3, JUMLAH = 4;

	private Core core;

	private Users user;
	private Transaksi t;

	private JPanel panLeft, panRight, panTable, panGrand;
	private JTextField tfTgl, tfKasir, tfHarga, tfJumlah;
	private JLabel l[] = new JLabel[5];
	private JComboBox cb;
	private JButton btnTambahBarang, btnTambahTransaksi;
	private JTable tbl;

	private DefaultTableModel model;

	private Container container;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Vector<String> nmBarang = new Vector<String>();
	private Vector<Products> barang = new Vector<Products>();

	public WindowFormTransaksi(Core core) {
		super("Formulir Transaksi - " + core.getDateAsString());
		this.core = core;
		this.user = core.getLoggedInUser();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(720, 400);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		getContentPane().setLayout(null);
		container = this.getContentPane();
		container.setBackground(Color.GREEN);
		model = new DefaultTableModel();
		model.addColumn("Nama Item");
		model.addColumn("Jumlah Item");
		model.addColumn("Total Harga");
		ResultSet rs = Operator.getListProducts(core.getConnection());
		nmBarang.removeAllElements();
		barang.removeAllElements();
		try {
			while (rs.next()) {
				barang.add(new Products(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getInt(5)));
				if (barang.lastElement().getstock() > 0)
					nmBarang.add(barang.lastElement().getnama());
				else
					barang.removeElement(barang.lastElement());
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		cb = new JComboBox(nmBarang);
		tfTgl = new JTextField(core.getDateAsString());
		tfKasir = new JTextField(user.getUsername());
		tfJumlah = new JTextField();
		tfHarga = new JTextField();
		fillFormByIndex(cb.getSelectedIndex());
		panGrand = new JPanel(null);
		panGrand.setBackground(Color.GREEN);
		panGrand.setBounds(0, 294, 440, 100);
		panLeft = new JPanel(null);
		panLeft.setBackground(Color.GREEN);
		panLeft.setBounds(439, 0, 275, 400);

		JMenuBar menu = new JMenuBar();
		menu.setBorderPainted(false);
		menu.setBackground(new Color(0, 255, 0));
		this.setJMenuBar(menu);

		JMenu menuUser = new JMenu(
				core.getLoggedInUser().isAdmin() ? "Kasir" : "Kasir"
						+ core.getLoggedInUser().getUsername());
		JMenuItem miLogOut = new JMenuItem("Log Out");
		miLogOut.addActionListener(new CustActionListener(core, this, miLogOut,
				CustActionListener.LOGOUT));

		/*
		 * JMenu menuTrans = new JMenu("Transaksi"); JMenuItem miTransData = new
		 * JMenuItem("Data Transaksi"); JMenuItem miTransCetak = new
		 * JMenuItem("Cetak Transaksi");
		 */
		JMenu menuBarang = new JMenu("Products");
		JMenuItem miBarangData = new JMenuItem("Data Products");
		miBarangData.addActionListener(new CustActionListener(core, this,
				miBarangData, CustActionListener.SHOW_DATA_BARANG));

		menu.add(menuUser);
		menuUser.add(miLogOut);

		/*
		 * menu.add(menuTrans); menuTrans.add(miTransData);
		 * menuTrans.add(miTransCetak);
		 */
		menu.add(menuBarang);
		menuBarang.add(miBarangData);
		// menuBarang.add(miBarangCetak);

		l[TGL] = new JLabel("Tanggal");
		l[KASIR] = new JLabel("Nama Kasir");
		l[BARANG] = new JLabel("Nama");
		l[HARGA] = new JLabel("Harga Rp.");
		l[JUMLAH] = new JLabel("Jumlah Item");

		tfTgl.setEnabled(false);
		tfKasir.setEnabled(false);
		tfHarga.setEnabled(false);
		tfTgl.setBounds(95, 11, 170, 20);
		tfKasir.setBounds(95, 36, 170, 20);
		cb.setBounds(95, 61, 170, 20);
		tfHarga.setBounds(95, 86, 170, 20);
		tfJumlah.setBounds(95, 111, 170, 20);

		l[TGL].setBounds(10, 10, 80, 20);
		l[KASIR].setBounds(10, 35, 80, 20);
		l[BARANG].setBounds(10, 60, 80, 20);
		l[HARGA].setBounds(10, 85, 80, 20);
		l[JUMLAH].setBounds(10, 110, 80, 20);

		btnTambahBarang = new JButton("Tambah Products");
		btnTambahBarang.setBounds(105, 156, 140, 20);
		btnTambahBarang.addActionListener(new CustActionListener(this,
				btnTambahBarang));
		btnTambahTransaksi = new JButton("Selesai & Print");
		btnTambahTransaksi.setBounds(165, 10, 120, 20);
		btnTambahTransaksi.addActionListener(new CustActionListener(core, this,
				btnTambahTransaksi));
		tfJumlah.addKeyListener(new CustKeyListener(this, tfJumlah,
				CustKeyListener.NUMBER_ONLY));
		tfJumlah.addKeyListener(new CustKeyListener(this, tfJumlah,
				CustKeyListener.ON_STOCK));
		cb.addActionListener(new CustActionListener(this, cb));
		panLeft.add(cb);
		panLeft.add(tfTgl);
		panLeft.add(tfKasir);
		panLeft.add(tfHarga);
		panLeft.add(tfJumlah);
		for (int i = 0; i < l.length; i++) {
			l[i].setHorizontalAlignment(JLabel.RIGHT);
			panLeft.add(l[i]);
		}
		panGrand.add(btnTambahTransaksi);
		panLeft.add(btnTambahBarang);

		container.add(panLeft);
		container.add(panGrand);
		tbl = new JTable(model);
		Operator.disableTableEdit(tbl);
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(10, 0, 430, 300);
		getContentPane().add(scrollPane);
		
				panTable = new JPanel(new BorderLayout());
				scrollPane.setRowHeaderView(panTable);
				panTable.setBackground(Color.GREEN);
				panTable.add((JTableHeader) tbl.getTableHeader(), BorderLayout.NORTH);

		resetForm();
	}

	public void fillFormByIndex(int index) {
		tfJumlah.setText("1");
		tfHarga.setText(barang.get(index).getharga()
				* Integer.parseInt(tfJumlah.getText()) + "");
	}

	public void resetForm() {
		int row = tbl.getRowCount() - 1;
		for (int i = row; i >= 0; i--)
			((DefaultTableModel) tbl.getModel()).removeRow(i);
		t = new Transaksi(core.getDate(), user);
	}

	public void addBarangToTable(DetilTransaksi dt) {
		for (int i = 0; i < tbl.getRowCount(); i++) {
			// test apakah sudah ada
		}
		model.addRow(new String[] { dt.getBarang().getnama(),
				dt.getQuantity() + "",
				dt.getBarang().getharga() * dt.getQuantity() + "" });
		t.addDetilTransaksi(dt);
	}

	public Vector<Products> getListBarang() {
		return barang;
	}

	public Products getSelectedBarang() {
		return barang.get(cb.getSelectedIndex());
	}

	public int getQtyBarang() {
		return Integer.parseInt(tfJumlah.getText());
	}

	public Transaksi getTransaksi() {
		return t;
	}

	public Vector<DetilTransaksi> getDetilTransaksi() {
		return t.getDetilTransaksi();
	}

	public void submitToDB() {
		if (Operator.tambahTransaksi(getTransaksi(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data Telah Berhasil Ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi Kesalahan!");
		}
		if (JOptionPane.showConfirmDialog(this, "Cetak transaksi?", "",
				JOptionPane.YES_NO_OPTION) == 0) {
			core.printReport(t);
		}
		resetForm();
	}
}
