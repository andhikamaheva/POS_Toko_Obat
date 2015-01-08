package fungsi;

public class Supplier {
	private String idsupp;
	private String nama;
	private String Alamat;
	private String telp;

	public Supplier(String idsupp, String nama, String alamat, String telp) {
		this.idsupp = idsupp;
		this.nama = nama;
		Alamat = alamat;
		this.telp = telp;

	}

	public String getIdsupp() {
		return idsupp;
	}

	public void setIdsupp(String idsupp) {
		this.idsupp = idsupp;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getAlamat() {
		return Alamat;
	}

	public void setAlamat(String alamat) {
		Alamat = alamat;
	}

	public String getTelp() {
		return telp;
	}

	public void setTelp(String telp) {
		this.telp = telp;
	}

}
