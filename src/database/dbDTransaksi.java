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

public class dbDTransaksi {

	private Connection koneksi;
	
	public dbDTransaksi() {
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
	
	public void addDTransaksi(JLabel id, JTable table){
		
		try{
			Statement stmt = koneksi.createStatement();
			ResultSet rs;
			String idProd;
			int hargax;
			int jumlahx;
			int subtotalx;
			
			for(int i = table.getRowCount()-1; i > -1 ; i --){
				
				idProd = table.getValueAt(i, 0).toString();
				hargax = Integer.parseInt(table.getValueAt(i, 2).toString());
				jumlahx = Integer.parseInt(table.getValueAt(i, 3).toString());
				subtotalx = Integer.parseInt(table.getValueAt(i, 4).toString());
				rs = stmt.executeQuery("insert into DTransaksi values('"+id.getText()+"', '"+idProd+"', "+hargax+", "+jumlahx+", "+subtotalx+" )");
				rs.close();
				
				rs = stmt.executeQuery("update Products set stock = (stock-"+jumlahx+") where idProd = '"+idProd+"'") ;
			}
			JOptionPane.showMessageDialog(null, "Transaksi Berhasil Disimpan! \n Terima Kasih :)", "Add Succes!", JOptionPane.INFORMATION_MESSAGE);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void tampilDetail(String idTrans, DefaultTableModel dataModel, JLabel totalx, JTable tablex){
		try{
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select * from DTransaksi where idTrans = '"+idTrans+"'");
			int total = 0;
			while(rs.next()){
				String idTransx = rs.getString(1);
				String nama = rs.getString(2);
				int harga = rs.getInt(3);
				int jumlah = rs.getInt(4);
				int subtotal = rs.getInt(5);
				Object [] x = {idTransx, nama, harga, jumlah, subtotal};
				dataModel.addRow(x);
				total+= subtotal;
			}
			totalx.setText(""+total);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
