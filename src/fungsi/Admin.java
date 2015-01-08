package fungsi;

public class Admin {
	private int id;
	private String nama;
	private String username;
	private String password;
	private String akses;
	
	public Admin(int id, String nama, String username, String password, String akses){
		this.id = id;
		this.nama = nama;
		this.username = username;
		this.password = password;
		this.akses = akses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAkses() {
		return akses;
	}

	public void setAkses(String akses) {
		this.akses = akses;
	}
	
	
}
