package fungsi;

public class Transaksi {
	/*create table Transaksi(
			idTrans varchar,
			namaKasir varchar,
			tgl varchar,
			jam varchar,
			constraint PK_Transaksi_idTrans primary key (idTrans)
			);*/
	private String idTrans;
	private String namaKasir;
	private String tgl;
	private String jam;
	public String getNamaKasir() {
		return namaKasir;
	}
	public void setNamaKasir(String namaKasir) {
		this.namaKasir = namaKasir;
	}
	public String getTgl() {
		return tgl;
	}
	public void setTgl(String tgl) {
		this.tgl = tgl;
	}
	public String getJam() {
		return jam;
	}
	public void setJam(String jam) {
		this.jam = jam;
	}
	public String getIdTrans() {
		return idTrans;
	}
	
	
}
