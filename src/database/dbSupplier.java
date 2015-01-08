package database;

import fungsi.Admin;
import fungsi.Products;
import fungsi.Supplier;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.sql.Connection;

import tampilan.Tambah_prod;
import tampilan.Tambah_prod.Pencarian;
import tampilan.Tambah_supp;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class dbSupplier {
	private Connection koneksi;

	public dbSupplier() {
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

	public void addSupplier(Supplier a, JTextField w, JTextField x,
			JTextField y, JTextField z) {
		if ((a.getIdsupp().equalsIgnoreCase(""))
				|| (a.getNama().equalsIgnoreCase(""))
				|| (a.getAlamat().equalsIgnoreCase(""))
				|| (a.getTelp().equalsIgnoreCase(""))) {
			JOptionPane.showMessageDialog(null, "Semua Field Harus Terisi!",
					"Add Failed!", JOptionPane.ERROR_MESSAGE);
		} else {

			try {
				Statement stmt = koneksi.createStatement();
				String idSupp = "";
				String namaSupp = "";
				ResultSet rs = stmt
						.executeQuery("select idSupp, namaSupp from Supplier where idSupp ='"
								+ a.getIdsupp()
								+ "' or namaSupp = '"
								+ a.getNama() + "'");
				while (rs.next()) {
					idSupp = rs.getString(1);
					namaSupp = rs.getString(2);
				}

				stmt.close();
				rs.close();
				if ((a.getIdsupp().equalsIgnoreCase(idSupp))
						|| (a.getNama().equals(namaSupp))) {
					JOptionPane.showMessageDialog(null,
							"Supplier Telah Terdaftar!", "Add Failed!",
							JOptionPane.ERROR_MESSAGE);
					idSupp = "";
					namaSupp = "";
				}

				else {
					rs = stmt.executeQuery("insert into Supplier values('"
							+ a.getIdsupp() + "','" + a.getNama() + "','"
							+ a.getAlamat() + "','" + a.getTelp() + "')");
					JOptionPane.showMessageDialog(null,
							"Supplier Berhasil Ditambahkan!", "Add Succes!",
							JOptionPane.INFORMATION_MESSAGE);
					w.grabFocus();
					w.setText("");
					x.setText("");
					y.setText("");
					z.setText("");

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public Vector<Supplier> viewAll() {
		Vector<Supplier> vHasil = new Vector<Supplier>();
		Supplier aHasil = null;
		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Supplier");
			while (rs.next()) {
				String id = rs.getString(1);
				String nama = rs.getString(2);
				String alamat = rs.getString(3);
				String telp = rs.getString(4);
				aHasil = new Supplier(id, nama, alamat, telp);
				vHasil.add(aHasil);
			}
			stmt.close();
		} catch (SQLException x) {
			x.printStackTrace();
		}
		return vHasil;
	}

	public void viewAllBy(String data, String kategori, JTextField text,
			JTable table, DefaultTableModel model) {
		Vector<Supplier> vHasil = new Vector<Supplier>();
		String id = "";
		Supplier aHasil = null;
		if ((text.getText().equalsIgnoreCase(""))
				|| (text.getText().equalsIgnoreCase(null))) {
			JOptionPane.showMessageDialog(null, "Data Belum Dimasukkan!",
					"Search Failed!", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				Statement stmt = koneksi.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM Supplier where "
								+ kategori + " like '%" + data + "%' or "
								+ kategori + " like '%" + data + "%'");
				while (rs.next()) {
					id = rs.getString(1);
					String nama = rs.getString(2);
					String alamat = rs.getString(3);
					String telp = rs.getString(4);
					aHasil = new Supplier(id, nama, alamat, telp);
					vHasil.add(aHasil);
				}
				stmt.close();
				if (id.equals("")) {
					JOptionPane.showMessageDialog(null,
							"Data Tidak Ditemukan!", "Data Not Found!",
							JOptionPane.ERROR_MESSAGE);
				} else {
					for (int i = table.getRowCount() - 1; i > -1; i--) {
						model.removeRow(i);
					}

					for (int i = 0; i < vHasil.size(); i++) {
						model.addRow(new Object[] {
								vHasil.elementAt(i).getIdsupp(),
								vHasil.elementAt(i).getNama(),
								vHasil.elementAt(i).getAlamat(),
								vHasil.elementAt(i).getTelp(), "Edit", "Hapus" });
					}
					table.setModel(model);
				}

			} catch (SQLException x) {
				x.printStackTrace();
				JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
						"Data Not Found!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void viewAllByAdd(String data, JTable table, DefaultTableModel model) {
		Vector<Supplier> vHasil = new Vector<Supplier>();
		String id = "";
		Supplier aHasil = null;
		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Supplier where namaSupp like '%"
							+ data + "%'");
			while (rs.next()) {
				id = rs.getString(1);
				String nama = rs.getString(2);
				String alamat = rs.getString(3);
				String telp = rs.getString(4);
				aHasil = new Supplier(id, nama, alamat, telp);
				vHasil.add(aHasil);
			}
			stmt.close();
			/*
			 * else { for(int i = table.getRowCount()-1; i>-1; i--){
			 * model.removeRow(i); }
			 */

			for (int i = 0; i < vHasil.size(); i++) {
				model.addRow(new Object[] { vHasil.elementAt(i).getIdsupp(),
						vHasil.elementAt(i).getNama(),
						vHasil.elementAt(i).getAlamat(),
						vHasil.elementAt(i).getTelp(), "Edit", "Hapus" });
			}
			table.setModel(model);

		} catch (SQLException x) {
			x.printStackTrace();
			// JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
			// "Data Not Found!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void editSupp(JLabel lblID,JTextField namaSupp, JTextField alamat, JTextField telp, JTable table, int row){
		if((namaSupp.getText().equals("")) || (alamat.getText().equals("")) || (telp.getText().equals(""))){
			JOptionPane.showMessageDialog(null, "Field Harus Terisi Semua!", "Edit Failed!", JOptionPane.ERROR_MESSAGE);
		}
		else {
		try{
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("update Supplier set namaSupp = '"+namaSupp.getText()+"', alamat = '"+alamat.getText()+"', telp = '"+telp.getText()+"' where idSupp = '"+lblID.getText()+"'");
			JOptionPane.showMessageDialog(null, "Update Berhasil disimpan!", "Edit Succes!", JOptionPane.INFORMATION_MESSAGE);
			table.setValueAt(namaSupp.getText(), row, 1);
			table.setValueAt(alamat.getText(), row, 2);
			table.setValueAt(telp.getText(), row, 3);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		}
	}



}
