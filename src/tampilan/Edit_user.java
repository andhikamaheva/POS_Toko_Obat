package tampilan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import fungsi.*;
import database.*;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * @author vilian18
 *
 */
public class Edit_user extends JFrame {

	private JPanel panelCari = new JPanel();
	private JLabel lblCari = new JLabel("Username : ");
	private JTextField txtCari = new JTextField(15);
	private JButton btnCari = new JButton("Cari");
	private JButton btnEdit = new JButton("Edit");
	private JButton btnHps = new JButton("Hapus");
	private JPanel panelTable = new JPanel();
	public String username = "";
	public int rowx;
	public int columnx;
	private dbAdmin db = new dbAdmin();

	private DefaultTableModel dataModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			if (column >= 4) {
				return true;
			} else {
				return false;
			}

		}
	};
	private JTable table = new JTable(dataModel);
	private JScrollPane scroll = new JScrollPane(table);

	private Connection koneksi;

	private int id;
	private String akses;
	private String nama;
	private String user;

	public Edit_user() {
	}

	public Edit_user(String title) {
		super(title);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setSize(600, 500);
		super.setLocationRelativeTo(null);
		super.setResizable(false);

		Container kontainer = getContentPane();
		kontainer.setLayout(new FlowLayout());

		panelCari.setLayout(new GridBagLayout());
		panelTable.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		panelCari.setBorder(BorderFactory.createTitledBorder("Cari Data"));

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		panelCari.add(lblCari, c);

		c.gridx = 1;
		c.gridy = 0;
		panelCari.add(txtCari, c);

		c.gridx = 2;
		c.gridy = 0;
		panelCari.add(btnCari, c);

		scroll.setPreferredSize(new Dimension(500, 300));
		c.gridx = 0;
		c.gridy = 0;
		panelTable.add(scroll, c);

		dataModel.addColumn("ID");
		dataModel.addColumn("Hak Akses");
		dataModel.addColumn("Nama User");
		dataModel.addColumn("Username");
		dataModel.addColumn("Edit User");
		dataModel.addColumn("Hapus User");

		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);

		scroll.setVisible(true);

		// isiTable();
		createTable();
		btnCari();
		tekanEnter();

		kontainer.add(panelCari);
		kontainer.add(panelTable);
	}

	public void createTable() {

		/*
		 * for (int i = dataModel.getRowCount() - 1; i > -1; i--) {
		 * dataModel.removeRow(i); }
		 */
		// tambah tombol edit
		table.getColumnModel().getColumn(4)
				.setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(4)
				.setCellEditor(new ButtonEditor(new JCheckBox()));

		// tambah tombol hapus
		table.getColumnModel().getColumn(5)
				.setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(5)
				.setCellEditor(new ButtonEditor(new JCheckBox()));

		Vector<Admin> vAdmin = db.viewAll();
		for (int i = 0; i < vAdmin.size(); i++) {
			dataModel.addRow(new Object[] { vAdmin.elementAt(i).getId(),
					vAdmin.elementAt(i).getAkses(),
					vAdmin.elementAt(i).getNama(),
					vAdmin.elementAt(i).getUsername(), "Edit", "Hapus" });
		}
		table.setModel(dataModel);

	}

	public void updateTable() {

		Vector<Admin> vAdmin = db.viewAll();
		for (int i = 0; i < vAdmin.size(); i++) {
			dataModel.addRow(new Object[] { vAdmin.elementAt(i).getId(),
					vAdmin.elementAt(i).getAkses(),
					vAdmin.elementAt(i).getNama(),
					vAdmin.elementAt(i).getUsername(), "Edit", "Hapus" });
		}
		table.setModel(dataModel);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Edit_user edit = new Edit_user("Manage User");
		edit.setVisible(true);

	}

	public String getUsername() {
		return username;
	}

	/*
	 * public void isiTable() { try {
	 * Class.forName(Config.DATABASE_DRIVER).newInstance(); koneksi =
	 * DriverManager.getConnection(Config.URL, Config.username,
	 * Config.password); } catch (InstantiationException |
	 * IllegalAccessException | ClassNotFoundException | SQLException e) {
	 * e.printStackTrace(); }
	 * 
	 * int n = 0; int row = dataModel.getRowCount(); for (int i = 0; i < row;
	 * i++) { dataModel.removeRow(0); }
	 * 
	 * try { Statement stmt = koneksi.createStatement(); ResultSet rs =
	 * stmt.executeQuery("SELECT * FROM Admin"); while (rs.next()) { int id =
	 * rs.getInt(1); akses = rs.getString(2); nama = rs.getString(3); user =
	 * rs.getString(4); n += 1; Object[] a = { id, akses, nama, user, "Edit",
	 * "Hapus" }; dataModel.addRow(a); }
	 * 
	 * koneksi.close(); stmt.close(); rs.close(); }
	 * 
	 * catch (SQLException x) { x.printStackTrace(); }
	 * 
	 * }
	 */

	public void btnCari() {

		btnCari.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent z) {
				try {
					Class.forName(Config.DATABASE_DRIVER).newInstance();
					koneksi = DriverManager.getConnection(Config.URL,
							Config.username, Config.password);
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}

				int n = 0;
				int row = dataModel.getRowCount();
				for (int i = 0; i < row; i++) {
					dataModel.removeRow(0);
				}

				try {
					Statement stmt = koneksi.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT * FROM Admin where username = '"
									+ txtCari.getText() + "'");

					while (rs.next()) {
						int id = rs.getInt(1);
						akses = rs.getString(2);
						nama = rs.getString(3);
						user = rs.getString(4);
						n += 1;
						Object[] a = { id, akses, nama, user, "Edit", "Hapus" };
						dataModel.addRow(a);
					}
				}

				catch (SQLException x) {
					x.printStackTrace();
				}
			}
		});

	}

	public void tekanEnter() {
		if (txtCari.getText().equalsIgnoreCase(null)
				|| txtCari.getText().equalsIgnoreCase("")) {
			// isiTable();
		}
		txtCari.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent z) {

				try {
					Class.forName(Config.DATABASE_DRIVER).newInstance();
					koneksi = DriverManager.getConnection(Config.URL,
							Config.username, Config.password);
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}

				int n = 0;
				int row = dataModel.getRowCount();
				for (int i = 0; i < row; i++) {
					dataModel.removeRow(0);
				}

				try {
					Statement stmt = koneksi.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT * FROM Admin where username = '"
									+ txtCari.getText() + "'");

					while (rs.next()) {
						int id = rs.getInt(1);
						akses = rs.getString(2);
						nama = rs.getString(3);
						user = rs.getString(4);
						n += 1;
						Object[] a = { id, akses, nama, user, "Edit", "Hapus" };
						dataModel.addRow(a);
					}
				}

				catch (SQLException x) {
					x.printStackTrace();
				}
			}
		});

	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;

		private String label;
		private boolean isDeleteRow = false;
		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			// label = (value == null) ? "" : value.toString();
			username = table.getValueAt(row, 3).toString();
			rowx = row;
			columnx = column;
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;

			return button;
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				//
				//
				// JOptionPane.showMessageDialog(button, username + ": Ouch!");
				// System.out.println(label + ": Ouch!");
				showEdit(rowx, columnx);

			}
			isPushed = false;
			return new String(label);
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {

			super.fireEditingStopped();
			if (isDeleteRow) {
				DefaultTableModel tableModel = (DefaultTableModel) table
						.getModel();
				tableModel.removeRow(table.getSelectedRow());
				table.addNotify();
				// tableModel.fireTableDataChanged();
			}
			isDeleteRow = false;
		}

		public void showEdit(int row, int column) {
			int jumlah = dataModel.getRowCount() - 1;
			int tmpCek = dataModel.getRowCount();
			if (column == 4) {
				Edit_user ed = new Edit_user("");
				Final_edit edit_final = new Final_edit(ed, "Edit User "
						+ username, username, ModalityType.APPLICATION_MODAL);
				edit_final.setVisible(true);
				edit_final.setLocationRelativeTo(null);

			} else if (column == 5) {
				int pilih = JOptionPane.showConfirmDialog(null,
						"Yakin ingin menghapus " + username + " ?",
						"Delete User?", JOptionPane.YES_NO_OPTION);
				if (pilih == 0) {
					try {
						hapusData(username);
						isDeleteRow = true;
						JOptionPane.showMessageDialog(null,
								"User telah dihapus!", "User Deleted!",
								JOptionPane.INFORMATION_MESSAGE);

						username = "";
					} catch (Exception e) {
						// updateTable();
					}
				}
			}
		}

		public void hapusData(String username) {
			try {
				Class.forName(Config.DATABASE_DRIVER).newInstance();
				koneksi = DriverManager.getConnection(Config.URL,
						Config.username, Config.password);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			try {
				Statement stmt = koneksi.createStatement();
				ResultSet rs = stmt
						.executeQuery("delete from Admin where username ='"
								+ username + "'");
				// JOptionPane.showMessageDialog(null, "Data Berhasil dihapus",
				// "Delete Succes!", JOptionPane.INFORMATION_MESSAGE);
				koneksi.close();
				rs.close();
				stmt.close();
				isPushed = true;
			}

			catch (SQLException z) {
				z.printStackTrace();
			}

		}

	}

	public class Final_edit extends JDialog {
		private Connection koneksi;

		private String password;
		private JLabel lblID = new JLabel("ID");
		private JLabel lblNo = new JLabel("No");

		private JLabel lblNama = new JLabel("Nama");
		private JTextField txtNama = new JTextField(15);

		private JLabel lblUser = new JLabel("Username : ");
		private JTextField txtUser = new JTextField();

		private JLabel lblPass2 = new JLabel("Password Baru : ");
		private JTextField txtPass2 = new JTextField();

		private JLabel lblAkses = new JLabel("Hak Akses : ");
		String[] akses = { "Administrator", "Kasir" };
		private JComboBox cboAkses = new JComboBox(akses);

		private JButton btnEdit = new JButton("Edit");
		private JButton btnBatal = new JButton("Batal");

		private JPanel panelEdit = new JPanel();
		Container kontainer = getContentPane();

		public Final_edit(Edit_user owner, String title, String username,
				ModalityType modal) {
			super(owner, title, modal);
			super.setSize(400, 340);
			// super.setVisible(true);
			super.setLocationRelativeTo(null);
			super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
			panelEdit.add(lblNo, c);

			c.gridx = 0;
			c.gridy = 1;
			panelEdit.add(lblNama, c);

			c.gridx = 1;
			c.gridy = 1;
			panelEdit.add(txtNama, c);

			c.gridx = 0;
			c.gridy = 2;
			panelEdit.add(lblUser, c);

			txtUser.setEditable(false);
			c.gridx = 1;
			c.gridy = 2;
			panelEdit.add(txtUser, c);

			c.gridx = 0;
			c.gridy = 4;
			panelEdit.add(lblPass2, c);

			c.gridx = 1;
			c.gridy = 4;
			panelEdit.add(txtPass2, c);

			c.gridx = 0;
			c.gridy = 5;
			panelEdit.add(lblAkses, c);

			c.gridx = 1;
			c.gridy = 5;
			panelEdit.add(cboAkses, c);

			c.gridx = 0;
			c.gridy = 6;
			btnEdit.setPreferredSize(new Dimension(150, 25));
			panelEdit.add(btnEdit, c);

			c.gridx = 1;
			c.gridy = 6;
			panelEdit.add(btnBatal, c);

			kontainer.add(panelEdit);

			btnBatal();
			btnSimpan();
		}

		public void btnBatal() {
			btnBatal.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					dispose();
				}
			});
		}

		public void btnSimpan() {
			btnEdit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					db.editAdmin(txtUser, txtNama, txtPass2, cboAkses, table,
							rowx);
				}
			});
		}

		public void isiData(String username) {
			try {
				Class.forName(Config.DATABASE_DRIVER).newInstance();
				koneksi = DriverManager.getConnection(Config.URL,
						Config.username, Config.password);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			try {
				Statement stmt = koneksi.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from Admin where username ='"
								+ username + "'");
				while (rs.next()) {
					lblNo.setText(rs.getString(1));
					String akses = rs.getString(2);
					int index = 0;
					if (akses.equals("Administrator")) {
						index = 0;
					} else {
						index = 1;
					}
					String[] a = { "Administrator", "Kasir" };
					cboAkses.setSelectedIndex(index);
					txtNama.setText(rs.getString(3));
					txtUser.setText(rs.getString(4));
					password = rs.getString(5);

				}
				koneksi.close();
				rs.close();
			} catch (SQLException x) {
				x.printStackTrace();
			}
		}

	}

}
