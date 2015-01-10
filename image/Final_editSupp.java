
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import tampilan.Final_edit;
import tampilan.Manage_supp;
import database.Config;
import database.dbSupplier;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Final_editSupp extends JFrame {
	private Connection koneksi;
	private dbSupplier sp = new dbSupplier();
	Manage_supp mg = new Manage_supp("");
	private int row = mg.rowx;
	private String password;
	private JLabel lblID = new JLabel("ID Supplier :");
	private JLabel lblNo = new JLabel("No");
	
	private JLabel lblNama = new JLabel("Nama :");
	private JTextField txtNama = new JTextField(15);
	
	private JLabel lblAlamat = new JLabel("Alamat :");
	private JTextField txtAlamat = new JTextField();
	
	private JLabel lblTelp = new JLabel("No. Telp");
	private JTextField txtTelp = new JTextField();
	
	private JButton btnEdit = new JButton("Edit");
	private JButton btnBatal = new JButton("Batal");
	
	private JPanel panelEdit = new JPanel();
	Container kontainer = getContentPane();
	
	
	
	public Final_editSupp(String title, String namaSupp){
		super(title);
		super.setResizable(false);
		//super.setLocationRelativeTo(null);
		super.setSize(400,270);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		panelEdit.setVisible(true);
		panelEdit.setLayout(new GridBagLayout());
		kontainer.setLayout(new FlowLayout());
		GridBagConstraints c = new GridBagConstraints();
		panelEdit.setBorder(BorderFactory.createTitledBorder("Edit"));
		
		
		isiData(namaSupp);
		
		
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		panelEdit.add(lblID, c);
		
		c.gridx = 1;
		c.gridy = 0;
		panelEdit.add(lblNo,c);
		
		c.gridx = 0;
		c.gridy = 1;
		panelEdit.add(lblNama,c);
		
		c.gridx = 1;
		c.gridy = 1;
		panelEdit.add(txtNama,c);
		
		c.gridx = 0;
		c.gridy = 2;
		panelEdit.add(lblAlamat,c);
		
		
		c.gridx = 1;
		c.gridy = 2;
		panelEdit.add(txtAlamat,c);
		
		c.gridx = 0;
		c.gridy = 3;
		panelEdit.add(lblTelp,c);
		
		c.gridx = 1;
		c.gridy = 3;
		panelEdit.add(txtTelp,c);
		

		
		c.gridx = 0;
		c.gridy = 4;
		btnEdit.setPreferredSize(new Dimension(150,25));
		panelEdit.add(btnEdit,c);
		
		c.gridx = 1;
		c.gridy = 4;
		panelEdit.add(btnBatal,c);
		
		kontainer.add(panelEdit);
		
		btnBatal();
		btnSimpan();
	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Final_edit f_edit = new Final_edit("Edit Supplier","");
		f_edit.setVisible(true);
		f_edit.setLocationRelativeTo(null);
		
	}
	
	public void btnBatal(){
		btnBatal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
	
	public void btnSimpan(){
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sp.editSupp(lblNo, txtNama, txtAlamat, txtTelp, mg.table, row);
			}
		});
	}
	
	public void isiData(String namaSupp){
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		try{
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Supplier where namaSupp ='"+ namaSupp +"'");
			while(rs.next()){
				lblNo.setText(rs.getString(1));
				txtNama.setText(rs.getString(2));
				txtAlamat.setText(rs.getString(3));
				txtTelp.setText(rs.getString(4));
			}
			koneksi.close();
			rs.close();
		}
		catch(SQLException x){
			x.printStackTrace();
		}
	}

}
