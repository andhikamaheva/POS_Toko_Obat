package tampilan;
import javax.swing.*;

import database.Config;


import database.dbAdmin;
import tampilan.Ganti_pass;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Masuk extends JFrame {

	
	private JPanel panelLogin = new JPanel();
	private JLabel lblUser = new JLabel("Username");
	private JLabel lblPass = new JLabel("Password");
	private JTextField txtUser = new JTextField();
	private JPasswordField txtPass = new JPasswordField();
	private  JButton btnLogin = new JButton("Login");
	private JButton btnBatal = new JButton("Cancel");
	private Connection koneksi;
	public static String user="";
	
	public String getUser(){
		return user;
	}
	
	
	
	public Masuk (String title)
	{
		super(title);
		super.setSize(300,200);
		super.setResizable(false);
		super.setLocationRelativeTo(null);
	//	super.setVisible(true);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.user=user;
		
		Container kontainer = getContentPane();
		kontainer.setLayout(null);
		
		lblUser.setBounds(10, 30, 90, 30);
		txtUser.setBounds(100,30, 150, 30);
		txtUser.grabFocus();
		
		lblPass.setBounds(10, 80, 90, 17);
		txtPass.setBounds(100,80, 150, 30);
		txtPass.setEchoChar('*');
		
		btnLogin.setBounds(20, 130, 100, 20);
		btnBatal.setBounds(160, 130, 100, 20);
		
	
	
		kontainer.add(lblUser);
		kontainer.add(txtUser);
		kontainer.add(lblPass);
		kontainer.add(txtPass);
		kontainer.add(btnLogin);
		kontainer.add(btnBatal);
		
		btnLogin.addActionListener( new ActionListener()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		        cekLogin();
		    }
		});
		
		txtPass.addActionListener( new ActionListener()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		        cekLogin();
		    }
		});
		
		btnBatal.addActionListener( new ActionListener()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		        System.exit(0);
		    }
		});
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Masuk msk = new Masuk(".:: Login ::.");
		msk.setVisible(true);
	}
	
	@SuppressWarnings("deprecation")
	public void cekLogin(){
		 int id;
		 
		 String usrx = "";
		 String pass="";
		 String akses = "";
		 
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		try{
			Statement stmt=koneksi.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Admin where username = '" +txtUser.getText() + "' and password ='" + txtPass.getText() + "'" );
			while(rs.next()){
				
				 user=rs.getString(4);
				 pass=rs.getString(5);
				 akses=rs.getString(2);
				
			}
			
			if ( (user.equals("")) && (pass.equals("")) ) {
				JOptionPane.showMessageDialog(null, "Username atau Password Salah", "Login Failed!", JOptionPane.ERROR_MESSAGE);
				txtUser.setText("");
				txtPass.setText("");
				txtUser.grabFocus();
			}
			
			else{
				if( akses.equals("Administrator") ){
					this.setVisible(false);
					Utama utama = new Utama ("Dashboard Admin");
					utama.setVisible(true);
					user = txtUser.getText();
				}
				
				else if( akses.equals("Kasir")){
					this.setVisible(false);
					Utama utama = new Utama ("Dashboard Kasir");
					utama.setVisible(true);
					utama.tmbUser.setVisible(false);
					utama.editUser.setVisible(false);
					user = txtUser.getText();
				}
			}
			
			
			stmt.close();
		}catch(SQLException x){
			x.printStackTrace();
		}
			
	}
		
	
	}


