package database;

import fungsi.Admin;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.sql.Connection;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class dbAdmin {
	private Connection koneksi;

	public dbAdmin() {
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi = DriverManager.getConnection(Config.URL, Config.username,
					Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addAdmin(Admin a) {
		try {
			Statement stmt = koneksi.createStatement();
			stmt.executeUpdate("INSERT INTO Admin VALUES('UNIQUEKEY ('Admin')','"
					+ a.getAkses()
					+ "','"
					+ a.getNama()
					+ "','"
					+ a.getUsername() + "','" + a.getPassword() + "')");
			stmt.close();
		} catch (SQLException x) {
			x.printStackTrace();
		}
	}

	public void deleteAdmin(String username) {
		try {
			Statement stmt = koneksi.createStatement();
			stmt.executeUpdate("delete from Admin where username = '"
					+ username + " ')");
			stmt.close();
		} catch (SQLException x) {
			x.printStackTrace();
		}
	}

	public void updateAdmin(String username, Admin a) {
		try {
			Statement stmt = koneksi.createStatement();
			stmt.executeUpdate("UPDATE Admin SET nama = '" + a.getNama()
					+ "','" + a.getUsername() + "','" + a.getPassword() + "','"
					+ a.getAkses() + "')");
			stmt.close();
		} catch (SQLException x) {
			x.printStackTrace();
		}
	}

	public Admin cariByUsername(String username) {
		Admin aHasil = null;
		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Admin WHERE username='"
							+ username + "'");
			while (rs.next()) {
				int id = rs.getInt(1);
				String akses = rs.getString(2);
				String nama = rs.getString(3);
				String userx = rs.getString(4);
				String password = rs.getString(5);
				aHasil = new Admin(id, nama, userx, password, akses);
			}
			stmt.close();
		} catch (SQLException x) {
			x.printStackTrace();
		}
		return aHasil;
	}

	public Vector<Admin> viewAll() {
		Vector<Admin> vHasil = new Vector<Admin>();
		Admin aHasil = null;
		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Admin");
			while (rs.next()) {
				int id = rs.getInt(1);
				String akses = rs.getString(2);
				String nama = rs.getString(3);
				String username = rs.getString(4);
				String password = rs.getString(5);
				aHasil = new Admin(id, nama, username, password, akses);
				vHasil.add(aHasil);
			}
			stmt.close();
		} catch (SQLException x) {
			x.printStackTrace();
		}
		return vHasil;
	}

	public Vector<Admin> viewAll(String username) {
		Vector<Admin> vHasil = new Vector<Admin>();
		Admin aHasil = null;
		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Admin WHERE username like '"
							+ username + "'");
			while (rs.next()) {
				int id = rs.getInt(1);
				String akses = rs.getString(2);
				String nama = rs.getString(3);
				String usrx = rs.getString(4);
				String password = rs.getString(5);
				aHasil = new Admin(id, nama, usrx, password, akses);
				vHasil.add(aHasil);
			}
			stmt.close();
		} catch (SQLException x) {
			x.printStackTrace();
		}
		return vHasil;
	}
	
	public void editAdmin(JTextField user,JTextField nama, JTextField pass2, JComboBox cboAkses, JTable table, int row){
		if((nama.getText().equals(""))){
			JOptionPane.showMessageDialog(null, "Field Harus Terisi Semua!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		else{
			try{
				if(pass2.getText().equals("")){
					Statement stmt = koneksi.createStatement();
					ResultSet rs = stmt.executeQuery("update Admin set akses = '"+cboAkses.getSelectedItem().toString()+"', nama = '"+ nama.getText()+"' where username = '"+user.getText()+"' ");
					JOptionPane.showMessageDialog(null, "User Berhasil diupdate!", "Update Succes", JOptionPane.INFORMATION_MESSAGE);
					table.setValueAt(cboAkses.getSelectedItem().toString(), row, 1);
					table.setValueAt(nama.getText(), row, 2);
					
				}
				
				else{
					Statement stmt = koneksi.createStatement();
					ResultSet rs = stmt.executeQuery("update Admin set akses = '"+cboAkses.getSelectedItem().toString()+"', nama = '"+ nama.getText()+"', password = '"+ pass2.getText() +"' where username = '"+user.getText()+"' ");
					JOptionPane.showMessageDialog(null, "User Berhasil diupdate!", "Update Succes", JOptionPane.INFORMATION_MESSAGE);
					table.setValueAt(cboAkses.getSelectedItem().toString(), row, 1);
					table.setValueAt(nama.getText(), row, 2);
				}
				
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

}
