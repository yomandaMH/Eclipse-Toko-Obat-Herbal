package object;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Transaksi {
	private int noTransaksi;
	private Vector<DetilTransaksi> detilTransaksi = new Vector<DetilTransaksi>();
	private Date tanggal;
	private Users username;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh:ss");

	public Transaksi(int noTransaksi, Vector<DetilTransaksi> detilTransaksi, Date tanggal,
			Users username) {
		this.noTransaksi = noTransaksi;
		this.detilTransaksi = detilTransaksi;
		this.tanggal = tanggal;
		this.username = username;
	}

	public Transaksi(Date tanggal, Users username) {
		this.tanggal = tanggal;
		this.username = username;
	}

	public Transaksi(int noTransaksi) {
		this.noTransaksi = noTransaksi;
	}

	public int getnoTransaksi() {
		return noTransaksi;
	}

	public Vector<DetilTransaksi> getDetilTransaksi() {
		return detilTransaksi;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public String getTanggalAsString() {
		return formatter.format(tanggal.getTime());
	}

	public Users getUser() {
		return username;
	}

	public int getTotalItem() {
		int total = 0;
		for (int i = 0; i < detilTransaksi.size(); i++) {
			total += detilTransaksi.get(i).getQuantity();
		}
		return total;
	}

	public int getTotalHrg() {
		int total = 0;
		for (int i = 0; i < detilTransaksi.size(); i++) {
			total += detilTransaksi.get(i).getBarang().getharga()
					* detilTransaksi.get(i).getQuantity();
		}
		return total;
	}

	public void addDetilTransaksi(DetilTransaksi dt) {
		detilTransaksi.add(dt);
	}
}
