package fungsi;

public class Products {
	private String idProd;
	private String nama;
	private String idSupp;
	private int harga;
	private int stock;
	
	public Products(String idProd, String nama, String idSupp, int harga, int stock) {
		super();
		this.idProd = idProd;
		this.nama = nama;
		this.idSupp = idSupp;
		this.harga = harga;
		this.stock = stock;
	}

	public String getIdProd() {
		return idProd;
	}
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setIdProd(String idProd) {
		this.idProd = idProd;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getIdSupp() {
		return idSupp;
	}

	public void setIdSupp(String idSupp) {
		this.idSupp = idSupp;
	}

	public int getHarga() {
		return harga;
	}

	public void setHarga(int harga) {
		this.harga = harga;
	}



}
