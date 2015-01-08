package tampilan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import database.Config;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Final_edit extends JFrame {
	private Connection koneksi;
	
	
	private String password;
	private JLabel lblID = new JLabel("ID");
	private JLabel lblNo = new JLabel("No");
	
	private JLabel lblNama = new JLabel("Nama");
	private JTextField txtNama = new JTextField(15);
	
	private JLabel lblUser = new JLabel("Username : ");
	private JTextField txtUser = new JTextField();
	
	private JLabel lblPass = new JLabel("Password Lama : ");
	private JTextField txtPass = new JTextField();
	
	private JLabel lblPass2 = new JLabel("Password Baru : ");
	private JTextField txtPass2 = new JTextField();
	
	private JLabel lblAkses = new JLabel("Hak Akses : ");
	String [] akses = {"Administrator", "Kasir"};
	private JComboBox cboAkses = new JComboBox(akses);
	
	private JButton btnEdit = new JButton("Edit");
	private JButton btnBatal = new JButton("Batal");
	
	private JPanel panelEdit = new JPanel();
	Container kontainer = getContentPane();
	
	
	
	public Final_edit(String title, String username){
		super(title);
		super.setResizable(false);
		//super.setLocationRelativeTo(null);
		super.setSize(400,400);
		
		
		panelEdit.setVisible(true);
		panelEdit.setLayout(new GridBagLayout());
		kontainer.setLayout(new FlowLayout());
		GridBagConstraints c = new GridBagConstraints();
		panelEdit.setBorder(BorderFactory.createTitledBorder("Edit"));
		
		cboAkses.setSelectedItem(2);
		isiData(username);
		
		
		
		
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
		panelEdit.add(lblUser,c);
		
		txtUser.setEditable(false);
		c.gridx = 1;
		c.gridy = 2;
		panelEdit.add(txtUser,c);
		
		c.gridx = 0;
		c.gridy = 3;
		panelEdit.add(lblPass,c);
		
		c.gridx = 1;
		c.gridy = 3;
		panelEdit.add(txtPass,c);
		
		c.gridx = 0;
		c.gridy = 4;
		panelEdit.add(lblPass2,c);
		
		c.gridx = 1;
		c.gridy = 4;
		panelEdit.add(txtPass2,c);
		
		c.gridx = 0;
		c.gridy = 5;
		panelEdit.add(lblAkses,c);
		
		c.gridx = 1;
		c.gridy = 5;
		panelEdit.add(cboAkses,c);
		
		c.gridx = 0;
		c.gridy = 6;
		btnEdit.setPreferredSize(new Dimension(150,25));
		panelEdit.add(btnEdit,c);
		
		c.gridx = 1;
		c.gridy = 6;
		panelEdit.add(btnBatal,c);
		
		kontainer.add(panelEdit);
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Final_edit f_edit = new Final_edit("Edit User","");
		f_edit.setVisible(true);
		f_edit.setLocationRelativeTo(null);
		
	}
	
	public void isiData(String username){
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi=DriverManager.getConnection(Config.URL,Config.username,Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		try{
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Admin where username ='"+ username +"'");
			while(rs.next()){
				lblNo.setText(rs.getString(1));
				String akses = rs.getString(2);
				int index=0;
				if (akses.equals("Administrator")){
					index =0;
				}
				else{
					index=1;
				}
				String [] a = {"Administrator", "Kasir"};
				cboAkses.setSelectedIndex(index);
				txtNama.setText(rs.getString(3));
				txtUser.setText(rs.getString(4));
				password = rs.getString(5);
				
			}
			koneksi.close();
			rs.close();
		}
		catch(SQLException x){
			x.printStackTrace();
		}
	}

}
