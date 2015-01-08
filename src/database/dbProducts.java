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

public class dbProducts {
	private Connection koneksi;
	
	public dbProducts() {
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
	
	public void addProduk(JTextField namaSupp, JLabel lblIdSupp, JLabel lblNoIdp,
			JTextField namaProd, JTextField harga,
			JTextField stock) {
		if ((namaSupp.getText().equals("")) || (lblIdSupp.getText().equals(""))
				|| (namaProd.getText().equals(""))
				|| (harga.getText().equals("")) || (stock.getText().equals("")) || (lblIdSupp.getText().equals("-")) || (harga.getText().equals(null)) || (stock.getText().equals(null)) || (stock.getText().equals("0")) || (harga.getText().equals("0")) ){
			JOptionPane.showMessageDialog(null, "Field Tidak Boleh Kosong atau Masukan Salah!",
					"Add Failed!", JOptionPane.ERROR_MESSAGE);
		} else {
			

				
				
				try {
					Statement stmt = koneksi.createStatement();
					ResultSet rs = stmt.executeQuery("insert into Products values ('"
							+ lblNoIdp.getText() + "','" + namaProd.getText()
							+ "', '" + lblIdSupp.getText() + "', '"
							+ harga.getText() + "', '" + stock.getText() + "')");
					JOptionPane.showMessageDialog(null,
							"Produk Berhasil Disimpan!", "Added Succes!",
							JOptionPane.INFORMATION_MESSAGE);
					namaSupp.setText("");
					lblIdSupp.setText("");
					lblNoIdp.setText("");
					namaProd.setText("");
					harga.setText("");
					stock.setText("");
					stmt.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				
				}	
		}
	}
	
	public Vector<Products> viewAll() {
		Vector<Products> vHasil = new Vector<Products>();
		Products aHasil = null;
		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Products");
			while (rs.next()) {
				String id = rs.getString(1);
				String nama = rs.getString(2);
				String idSupp = rs.getString(3);
				int harga = rs.getInt(4);
				int stock = rs.getInt(5);
				aHasil = new Products(id, nama, idSupp, harga, stock);
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
		Vector<Products> vHasil = new Vector<Products>();
		String id = "";
		Products aHasil = null;
		
		if ((text.getText().equalsIgnoreCase(""))
				|| (text.getText().equalsIgnoreCase(null))) {
			JOptionPane.showMessageDialog(null, "Data Belum Dimasukkan!",
					"Search Failed!", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				
				
				Statement stmt = koneksi.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM Products where "
								+ kategori + " like '%" + data + "%' or "
								+ kategori + " like '%" + data + "%'");
				while (rs.next()) {
					id = rs.getString(1);
					String nama = rs.getString(2);
					String idSupp = rs.getString(3);
					int harga = rs.getInt(4);
					int stock = rs.getInt(5);
					aHasil = new Products(id, nama, idSupp, harga, stock);
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
								vHasil.elementAt(i).getIdProd(),
								vHasil.elementAt(i).getNama(),
								vHasil.elementAt(i).getIdSupp(),
								vHasil.elementAt(i).getHarga(),
								vHasil.elementAt(i).getStock(),"Edit", "Hapus" });
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
	
	
	public void editProd(JLabel lblID,JTextField nama, JTextField harga, JTable table, int row){
		if((nama.getText().equals("")) || (harga.getText().equals(""))){
			JOptionPane.showMessageDialog(null, "Field Harus Terisi Semua!", "Edit Failed!", JOptionPane.ERROR_MESSAGE);
		}
		else {
		try{
			Statement stmt = koneksi.createStatement();
			int hargax = Integer.parseInt(harga.getText());
			ResultSet rs = stmt.executeQuery("update Products set nama = '"+nama.getText()+"', harga = "+hargax+" where idProd = '"+lblID.getText()+"' ");
			JOptionPane.showMessageDialog(null, "Update Berhasil disimpan!", "Edit Succes!", JOptionPane.INFORMATION_MESSAGE);
			table.setValueAt(nama.getText(), row, 1);
			table.setValueAt(harga.getText(), row, 3);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		}
	}
	
	
	public void viewAllByAdd(String nKategori, String data, JTable table, DefaultTableModel model) {
		Vector<Products> vHasil = new Vector<Products>();
		String id = "";
		Products aHasil = null;
		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Products where "+nKategori+"  like '%"+data+"%'");
			while (rs.next()) {
				id = rs.getString(1);
				String nama = rs.getString(2);
				String idSupp = rs.getString(3);
				int harga = rs.getInt(4);
				int stock = rs.getInt(5);
				aHasil = new Products(id, nama, idSupp, harga, stock);
				vHasil.add(aHasil);
			}
			stmt.close();
			/*
			 * else { for(int i = table.getRowCount()-1; i>-1; i--){
			 * model.removeRow(i); }
			 */

			for (int i = 0; i < vHasil.size(); i++) {
				model.addRow(new Object[] { vHasil.elementAt(i).getIdProd(), vHasil.elementAt(i).getNama(), vHasil.elementAt(i).getIdSupp(),
						vHasil.elementAt(i).getHarga(), vHasil.elementAt(i).getStock()});
			}
			table.setModel(model);

		} catch (SQLException x) {
			x.printStackTrace();
			// JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
			// "Data Not Found!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void reStock(JLabel id, JLabel stock){
		try{
		Statement stmt = koneksi.createStatement();
		ResultSet rs = stmt.executeQuery("update Products set stock = "+ Integer.parseInt(stock.getText()) +" where idProd = '"+id.getText()+"' ");
		JOptionPane.showMessageDialog(null, "Stock Berhasil diupdate!", "Update Succes" , JOptionPane.INFORMATION_MESSAGE);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public void viewAllByTrans(JTextField text, JTable table, DefaultTableModel model) {
		Vector<Products> vHasil = new Vector<Products>();
		String id = "";
		Products aHasil = null;
		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Products where idProd  like '%"+text.getText().toUpperCase()+"%'");
			while (rs.next()) {
				id = rs.getString(1);
				String nama = rs.getString(2);
				String idSupp = rs.getString(3);
				int harga = rs.getInt(4);
				int stock = rs.getInt(5);
				aHasil = new Products(id, nama, idSupp, harga, stock);
				vHasil.add(aHasil);
			}
			stmt.close();
			/*
			 * else { for(int i = table.getRowCount()-1; i>-1; i--){
			 * model.removeRow(i); }
			 */

			for (int i = 0; i < vHasil.size(); i++) {
				model.addRow(new Object[] { vHasil.elementAt(i).getIdProd(), vHasil.elementAt(i).getNama(), vHasil.elementAt(i).getIdSupp(),
						vHasil.elementAt(i).getHarga(), vHasil.elementAt(i).getStock()});
			}
			table.setModel(model);

		} catch (SQLException x) {
			x.printStackTrace();
			// JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
			// "Data Not Found!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void viewAllByTrans2(JTextField text, JTable table, DefaultTableModel model) {
		Vector<Products> vHasil = new Vector<Products>();
		String id = "";
		Products aHasil = null;
		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Products where nama  like '%"+text.getText()+"%'");
			while (rs.next()) {
				id = rs.getString(1);
				String nama = rs.getString(2);
				String idSupp = rs.getString(3);
				int harga = rs.getInt(4);
				int stock = rs.getInt(5);
				aHasil = new Products(id, nama, idSupp, harga, stock);
				vHasil.add(aHasil);
			}
			stmt.close();
			/*
			 * else { for(int i = table.getRowCount()-1; i>-1; i--){
			 * model.removeRow(i); }
			 */

			for (int i = 0; i < vHasil.size(); i++) {
				model.addRow(new Object[] { vHasil.elementAt(i).getIdProd(), vHasil.elementAt(i).getNama(), vHasil.elementAt(i).getIdSupp(),
						vHasil.elementAt(i).getHarga(), vHasil.elementAt(i).getStock()});
			}
			table.setModel(model);

		} catch (SQLException x) {
			x.printStackTrace();
			// JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
			// "Data Not Found!", JOptionPane.ERROR_MESSAGE);
		}
	}
	

}
