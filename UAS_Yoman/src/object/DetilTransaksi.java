package object;

public class DetilTransaksi {
	private Products barang;
	private int quantity;
	private Transaksi transaksi;

	public DetilTransaksi(Transaksi transaksi, Products barang, int quantity) {
		this.transaksi = transaksi;
		this.barang = barang;
		this.quantity = quantity;
	}

	public DetilTransaksi(Products barang, int quantity) {
		this.barang = barang;
		this.quantity = quantity;
	}

	public Products getBarang() {
		return barang;
	}

	public int getQuantity() {
		return quantity;
	}

	public Transaksi getTransaksi() {
		return transaksi;
	}

}
