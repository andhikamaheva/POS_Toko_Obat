package tampilan;

import javax.swing.*;

import database.Config;
import database.dbSupplier;
import fungsi.Supplier;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tambah_supp extends JFrame {
	
	private JPanel panelTambah = new JPanel();
	private Container kontainer = getContentPane();
	
	private JLabel lblID = new JLabel("ID Supplier");
	public JTextField txtID = new JTextField(10);
	
	private JLabel lblNama = new  JLabel("Nama Supplier");
	private JTextField txtNama = new JTextField(15);
	
	private JLabel lblAlamat = new JLabel("Alamat");
	private JTextField txtAlamat = new JTextField(15);
	
	private JLabel lblTelp = new JLabel("No. Telp");
	private JTextField txtTelp = new JTextField(15);
	
	private JButton btnSimpan = new JButton("Simpan");
	private JButton btnBatal = new JButton("Batal");
	
	public dbSupplier supp = new dbSupplier();
	
	private Connection koneksi;
	public Tambah_supp(String title){
		super(title);
		super.setResizable(false);
		super.setSize(500,270);
		super.setVisible(true);
		
		panelTambah.setLayout(new GridBagLayout());
		panelTambah.setBorder(BorderFactory.createTitledBorder("Data Supplier"));
		GridBagConstraints c = new GridBagConstraints();
		kontainer.setLayout(new FlowLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		c.gridx =0;
		c.gridy =0;
		panelTambah.add(lblID,c);
		
		c.gridx = 1;
		c.gridy = 0;
		panelTambah.add(txtID,c);
		
		c.gridx=0;
		c.gridy=1;
		panelTambah.add(lblNama,c);
		
		c.gridx=1;
		c.gridy=1;
		panelTambah.add(txtNama,c);
		
		c.gridx=0;
		c.gridy=2;
		panelTambah.add(lblAlamat,c);
		
		c.gridx=1;
		c.gridy=2;
		txtTelp.setPreferredSize(new Dimension(15,20));
		panelTambah.add(txtAlamat,c);
		
		c.gridx = 0;
		c.gridy = 3;
		panelTambah.add(lblTelp,c);
		
		c.gridx = 1;
		c.gridy = 3;
		panelTambah.add(txtTelp,c);
		
		c.gridx=0;
		c.gridy=4;
		btnSimpan.setPreferredSize(new Dimension(200,25));
		panelTambah.add(btnSimpan,c);
		
		c.gridx=1;
		c.gridy=4;
		panelTambah.add(btnBatal,c);
		
		kontainer.add(panelTambah);
		
		//Button Function
		btnBtal();
		btnSimpan();
	}
	
	public static void main(String[] args) {
		Tambah_supp tmbh = new Tambah_supp("Tambah Supplier");
		tmbh.setVisible(true);
		tmbh.setLocationRelativeTo(null);

	}
	
	public void btnBtal(){
		btnBatal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
	}
	
	public void btnSimpan(){

		
		btnSimpan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String id = txtID.getText();
				String nama = txtNama.getText();
				String alamat = txtAlamat.getText();
				String telp = txtTelp.getText();
				Supplier sps = new Supplier(id,nama,alamat,telp);
				supp.addSupplier(sps, txtID, txtNama, txtAlamat, txtTelp);
			}
		});
	}
	
	

}
