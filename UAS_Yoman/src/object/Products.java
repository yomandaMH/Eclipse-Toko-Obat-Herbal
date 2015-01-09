package object;

public class Products {
	private String idProducts, nama, idSuppliers;
	private int harga, stock;
	
	public Products(String idProducts, String nama, String idSuppliers, int harga,  int stock){
		this.idProducts=idProducts;
		this.nama=nama;
		this.idSuppliers=idSuppliers;
		this.harga=harga;
		this.stock=stock;
	}
	
	public Products(String nama, String idSuppliers, int harga, int stock){
		this.nama=nama;
		this.idSuppliers=idSuppliers;
		this.harga=harga;
		this.stock=stock;
	}

	public String getIdProducts() {
		return idProducts;
	}

	public String getnama() {
		return nama;
	}

	public String getidSuppliers() {
		return idSuppliers;
	}

	public int getharga() {
		return harga;
	}

	public int getstock() {
		return stock;
	}
}
