package tampilan;
import javax.swing.*;

import database.Config;
import tampilan.Masuk;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ganti_pass extends JDialog {

	private JPanel panelTambahUser = new JPanel();
	private JLabel lblID = new JLabel("ID : ");
	private JLabel noID = new JLabel ("NoID");
	private JLabel lblUser = new JLabel("Username : ");
	private JLabel lblPass = new JLabel("Password Lama :");
	private JLabel lblPass2 = new JLabel("Password Baru :");
	private JTextField txtUser = new JTextField(15);
	private JTextField txtPass = new JTextField(15);
	private JTextField txtPass2 = new JTextField(15);
	private JButton btnTambah = new JButton("Simpan");
	private JButton btnClear = new JButton("Batal");
	public String cekuserx;
	private Connection koneksi;
	private String oldPass;
	
public Ganti_pass (Utama owner, String title, ModalityType model){
	super(owner, title, model);
	super.setSize(400,280);
	super.setResizable(false);
	super.setLocationRelativeTo(null);
	super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	//panelTambahUser.setBorder(BorderFactory.createTitledBorder("Tambah  User"));
	panelTambahUser.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	panelTambahUser.setBorder(BorderFactory.createTitledBorder("Tambah User"));
	c.fill=GridBagConstraints.HORIZONTAL;
	c.insets=new Insets(10, 10, 10, 10);
	c.gridx = 0;
	c.gridy = 0;
	panelTambahUser.add(lblID,c);
	
	c.gridx = 1;
	c.gridy = 0;
	panelTambahUser.add(noID, c);
	
	
	
	c.gridx = 0;
	c.gridy = 2;
	panelTambahUser.add(lblUser, c);
	
	txtUser.setEditable(false);;
	c.gridx = 1;
	c.gridy = 2;
	c.gridwidth=1;
	panelTambahUser.add(txtUser,c );
	
	
	c.gridx = 0;
	c.gridy = 3;
	panelTambahUser.add(lblPass,c);
	

	c.gridx = 1;
	c.gridy = 3;
	panelTambahUser.add(txtPass,c);

	c.gridx = 0;
	c.gridy = 4;
	panelTambahUser.add(lblPass2,c);
	
	
	c.gridx = 1;
	c.gridy = 4;
	panelTambahUser.add(txtPass2,c);
	
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 5;
	c.ipadx = 80;
	panelTambahUser.add(btnTambah,c);
	
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx =1;
	c.gridy = 5;
	panelTambahUser.add(btnClear,c);
	
	
	Container kontainer = getContentPane();
	kontainer.setLayout(new FlowLayout());
	kontainer.add(panelTambahUser);
	
	isiData();
	
	//Button Action
	btnBatal();
	btnSimpan();

	
}	
	
	
	//Function Button
	public void btnBatal(){
		btnClear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
			});
		}
	
	public void isiData(){
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		try{
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Admin where username = '"+ Masuk.user + "'");
			while (rs.next()){
				noID.setText(rs.getString(1));
				txtUser.setText(rs.getString(4));
				oldPass = rs.getString(5);
			}
		}
		catch(SQLException x){
			x.printStackTrace();
		}
	}
	
	public void btnSimpan(){
		btnTambah.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Class.forName(Config.DATABASE_DRIVER).newInstance();
					koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException z) {
					z.printStackTrace();
				}
				
				if((txtPass.getText().equals("")) || (txtPass2.getText().equals(""))){
					JOptionPane.showMessageDialog(null,
							"Field Tidak Boleh Kosong!", "Update Failed!",
							JOptionPane.ERROR_MESSAGE);
				}
				else{
				if((oldPass.equals(txtPass.getText())) && (!txtPass2.getText().equals(""))){
				
				try{
					Statement stmt = koneksi.createStatement();
					ResultSet rs = stmt.executeQuery("update Admin set password = '"+ txtPass2.getText() +"' where username= '"+ txtUser.getText() +"'");
					stmt.close();
					rs.close();
					JOptionPane.showMessageDialog(null,
							"Update Berhasil!",
							"Update Succes!",
							JOptionPane.INFORMATION_MESSAGE);
					oldPass = txtPass2.getText();
					txtPass.setText("");
					txtPass2.setText("");
				}
				catch(SQLException x){
					x.printStackTrace();
				}
				
				}
				
				else if((!oldPass.equals(txtPass)) && (!txtPass2.getText().equals("") || (txtPass2.getText().equals("") ))){
					JOptionPane.showMessageDialog(null,
							"Password Lama Anda Tidak Sama!", "Save Failed!",
							JOptionPane.ERROR_MESSAGE);
					
				}
			}
			}
			
		});
		
		

	}
	
	
	
	

}
