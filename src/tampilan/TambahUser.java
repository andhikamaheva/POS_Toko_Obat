package tampilan;

import javax.swing.*;

import database.Config;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TambahUser extends JDialog {

	private JPanel panelTambahUser = new JPanel();
	private JLabel lblID = new JLabel("ID : ");
	private JLabel noID = new JLabel("NoID");
	private JLabel lblUser = new JLabel("Username : ");
	private JLabel lblPass = new JLabel("Password :");
	private JLabel lblNama = new JLabel("Nama User : ");
	private JTextField txtNama = new JTextField(15);
	private JLabel lblAkses = new JLabel("Hak Akses :");
	private JTextField txtUser = new JTextField(15);
	private JTextField txtPass = new JTextField(15);
	String[] akses = { "Administrator", "Kasir" };
	private JComboBox cboAkses = new JComboBox(akses);
	private JButton btnTambah = new JButton("Simpan");
	private JButton btnClear = new JButton("Batal");

	private Connection koneksi;

	public TambahUser(Utama owner, String title, ModalityType modal) {
		super(owner, title, modal);
		super.setSize(400, 290);
		super.setResizable(false);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// panelTambahUser.setBorder(BorderFactory.createTitledBorder("Tambah  User"));
		panelTambahUser.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		panelTambahUser.setBorder(BorderFactory
				.createTitledBorder("Tambah User"));

		cboAkses.setSelectedItem(2);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 1;
		panelTambahUser.add(lblNama, c);

		c.gridx = 1;
		c.gridy = 1;
		panelTambahUser.add(txtNama, c);

		c.gridx = 0;
		c.gridy = 2;
		panelTambahUser.add(lblUser, c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		panelTambahUser.add(txtUser, c);

		c.gridx = 0;
		c.gridy = 3;
		panelTambahUser.add(lblPass, c);

		c.gridx = 1;
		c.gridy = 3;
		panelTambahUser.add(txtPass, c);

		c.gridx = 0;
		c.gridy = 4;
		panelTambahUser.add(lblAkses, c);

		c.gridx = 1;
		c.gridy = 4;
		panelTambahUser.add(cboAkses, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.ipadx = 80;
		panelTambahUser.add(btnTambah, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		panelTambahUser.add(btnClear, c);

		Container kontainer = getContentPane();
		kontainer.setLayout(new FlowLayout());
		kontainer.add(panelTambahUser);
	

		// Button Action
		btnBatal();
		btnSimpan();

	}

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub TambahUser tmbh = new TambahUser("Tambah User");
	 * tmbh.setVisible(true); }
	 */
	// Function Button
	public void btnBatal() {
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	public void btnSimpan() {
		btnTambah.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				try {
					Class.forName(Config.DATABASE_DRIVER).newInstance();
					koneksi = DriverManager.getConnection(Config.URL,
							Config.username, Config.password);
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}

				if ((txtNama.getText().equals(""))
						|| (txtPass.getText().equals(""))
						|| (txtUser.getText().equals(""))) {
					JOptionPane.showMessageDialog(null,
							"Semua Field Harus Terisi!", "Add Failed!",
							JOptionPane.ERROR_MESSAGE);
				}

				else {
					try {
						Statement stmt = koneksi.createStatement();
						String username = "";
						ResultSet rs = stmt
								.executeQuery("select username from Admin where username ='"
										+ txtUser.getText() + "'");
						while (rs.next()) {
							username = rs.getString(1);
						}

						stmt.close();
						rs.close();
						if (txtUser.getText().equalsIgnoreCase(username)) {
							JOptionPane.showMessageDialog(null,
									"Username Telah Terdaftar!", "Add Failed!",
									JOptionPane.ERROR_MESSAGE);
						}

						else {
							rs = stmt.executeQuery("insert into Admin values(UNIQUEKEY ('Admin'),'"
									+ cboAkses.getSelectedItem().toString()
									+ "', '"
									+ txtNama.getText()
									+ "','"
									+ txtUser.getText()
									+ "','"
									+ txtPass.getText() + "')");
							JOptionPane.showMessageDialog(null,
									"User Berhasil Ditambahkan!",
									"Add Succes!",
									JOptionPane.INFORMATION_MESSAGE);
							txtNama.grabFocus();
							txtNama.setText("");
							txtUser.setText("");
							txtPass.setText("");
						}
					}

					catch (SQLException x) {
						x.printStackTrace();
						// JOptionPane.showMessageDialog(null,
						// "Username Telah Terdaftar!", "Add Failed!",
						// JOptionPane.ERROR_MESSAGE);
					}
				}

			}

		});

	}

}
