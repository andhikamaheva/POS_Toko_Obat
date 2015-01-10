package database;

import fungsi.Admin;
import fungsi.Products;
import fungsi.Supplier;
import fungsi.Transaksi;
import fungsi.DTransaksi;

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

public class dbTransaksi {
	private Connection koneksi;
	
	public dbTransaksi() {
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
	
	public void addTransaksi(JLabel id, JLabel nama, JLabel tgl, JLabel jam){
		try{
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("insert into Transaksi values('"+id.getText()+"', '"+nama.getText()+"', '"+tgl.getText()+"', '"+jam.getText()+"')  ");
		}
		catch(SQLException e){
			
		}
	}
	
	public void cariBy(String kategori, JTable table, DefaultTableModel dataModel, JTextField txtCari){
		try{
			Statement stmt = koneksi.createStatement();
			ResultSet rs;
			String idTrans="";
			String nama;
			String tgl;
			String jam;
			if(txtCari.equals("")){
				JOptionPane.showMessageDialog(null, "Field tidak boleh kosong!", "Error!", JOptionPane.ERROR_MESSAGE);
			}
			else{
				if (kategori.equals("idTrans")){
					rs = stmt.executeQuery("select * from Transaksi where "+kategori+" like '%"+txtCari.getText()+"%'");
					while(rs.next()){
						idTrans = rs.getString(1);
						nama = rs.getString(2);
						tgl = rs.getString(3);
						jam = rs.getString(4);
						Object [] a = {idTrans, nama, tgl, jam, "View"};
						dataModel.addRow(a);
					}
					if (idTrans.equals("")){
						JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
					rs.close();
				}
				else if(kategori.equals("namaKasir")){
					rs = stmt.executeQuery("select * from Transaksi where "+kategori+" like '%"+txtCari.getText()+"%' ");
					while(rs.next()){
						idTrans = rs.getString(1);
						nama = rs.getString(2);
						tgl = rs.getString(3);
						jam = rs.getString(4);
						Object [] a = {idTrans, nama, tgl, jam, "View"};
						dataModel.addRow(a);
					}
					if (idTrans.equals("")){
						JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
					rs.close();
				}
				else{
					rs = stmt.executeQuery("select * from Transaksi where tgl like '%"+txtCari.getText()+"%' ");
					while(rs.next()){
						idTrans = rs.getString(1);
						nama = rs.getString(2);
						tgl = rs.getString(3);
						jam = rs.getString(4);
						Object [] a = {idTrans, nama, tgl, jam, "View"};
						dataModel.addRow(a);
					}
					if (idTrans.equals("")){
						JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
					rs.close();
				}
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

}
