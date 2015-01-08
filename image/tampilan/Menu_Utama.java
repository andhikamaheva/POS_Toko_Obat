//Ubahen Line

package tampilan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.*;

import database.Config;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Menu_Utama extends JFrame {
	// Deklarasi Variabel
	Connection koneksi;
	private int id, harga, stock;
	private String nama, idsup;

	// T-A-B-E-L
	private DefaultTableModel dataTabelEA = new DefaultTableModel();
	private DefaultTableModel dataTabelDA = new DefaultTableModel();
	private DefaultTableModel dataTabelEP = new DefaultTableModel(){
		@Override
		public boolean isCellEditable(int row, int column) {
			if (column >= 5) {
				return true;
			} else {
				return false;
			}

		}
	};
	private DefaultTableModel dataTabelDP = new DefaultTableModel();
	private DefaultTableModel dataTabelES = new DefaultTableModel();
	private DefaultTableModel dataTabelDS = new DefaultTableModel();

	JMenuBar menuBar = new JMenuBar();

	JMenu MenuAdmin = new JMenu("Admin");
	JMenu MenuProduct = new JMenu("Product");
	JMenu MenuSupplier = new JMenu("Supplier");
	JMenu MenuStock = new JMenu("Stock");
	JMenu MenuReport = new JMenu("Report");
	JMenu MenuLogout = new JMenu("Log Out");

	JMenuItem newMenuAdmin = new JMenuItem("New Admin");
	JMenuItem editMenuAdmin = new JMenuItem("Edit Admin");
	JMenuItem deleteMenuAdmin = new JMenuItem("Delete Admin");

	JMenuItem newMenuProduct = new JMenuItem("New Product");
	JMenuItem editMenuProduct = new JMenuItem("Edit Product");
	JMenuItem deleteMenuProduct = new JMenuItem("Delete Product");

	JMenuItem newMenuSupplier = new JMenuItem("New Supplier");
	JMenuItem editMenuSupplier = new JMenuItem("Edit Supplier");
	JMenuItem deleteMenuSupplier = new JMenuItem("Delete Supplier");

	JMenuItem newMenuStock = new JMenuItem("New Stock");
	JMenuItem viewMenuStock = new JMenuItem("View Stock");

	JMenuItem viewMenuReport = new JMenuItem("View Report");

	Container kontainer = getContentPane();

	// Panel Tabel
	JPanel panelTabelEA = new JPanel();
	JPanel panelTabelDA = new JPanel();
	JPanel panelTabelEP = new JPanel();
	JPanel panelTabelDP = new JPanel();
	JPanel panelTabelES = new JPanel();
	JPanel panelTabelDS = new JPanel();

	// Panel Menu Administrator
	JPanel panelTambahAdmin = new JPanel();
	JPanel panelEditAdmin = new JPanel();
	JPanel panelDeleteAdmin = new JPanel();

	// Panel Menu Product
	JPanel panelTambahProduct = new JPanel();
	JPanel panelEditProduct = new JPanel();
	JPanel panelDeleteProduct = new JPanel();

	// Panel Menu Supplier
	JPanel panelTambahSupplier = new JPanel();
	JPanel panelEditSupplier = new JPanel();
	JPanel panelDeleteSupplier = new JPanel();

	// Panel Menu Stock
	JPanel panelTambahStock = new JPanel();
	JPanel panelLihatStock = new JPanel();

	JTable tabelEA = new JTable(dataTabelEA);
	JTable tabelDA = new JTable(dataTabelDA);
	JTable tabelEP = new JTable(dataTabelEP);
	JTable tabelDP = new JTable(dataTabelDP);
	JTable tabelES = new JTable(dataTabelES);
	JTable tabelDS = new JTable(dataTabelDS);
	JScrollPane scrollEA = new JScrollPane(tabelEA);
	JScrollPane scrollDA = new JScrollPane(tabelDA);
	JScrollPane scrollEP = new JScrollPane(tabelEP);
	JScrollPane scrollDP = new JScrollPane(tabelDP);
	JScrollPane scrollES = new JScrollPane(tabelES);
	JScrollPane scrollDS = new JScrollPane(tabelDS);

	
	JLabel editadmin_user = new JLabel("Username : ");
	JTextField txt_editadmin_user = new JTextField(20);
	JButton editadmin_cari = new JButton("Cari");
	JButton editadmin_cancel = new JButton("Cancel");
	
	JLabel newadmin_user = new JLabel("Username : ");
	JLabel newadmin_pass = new JLabel("Password : ");
	JLabel newadmin_akses = new JLabel("Hak Akses : ");
	JTextField txt_newadmin_user = new JTextField(20);
	JTextField txt_newadmin_pass = new JTextField(20);
	String[] newakses = { "Administrator", "Kasir" };
	JComboBox cbo_newadmin_akses = new JComboBox(newakses);
	JButton newadmin_save = new JButton("Save");
	JButton newadmin_cancel = new JButton("Cancel");
	
	
	JLabel newproduct_id = new JLabel("ID Product : ");
	JLabel newproduct_nama = new JLabel("Nama Product : ");
	JLabel newproduct_idsup = new JLabel("ID Supplier : ");
	JLabel newproduct_harga = new JLabel("Harga Product : ");
	JLabel newproduct_stock = new JLabel("Stock :");
	JTextField txt_newproduct_id = new JTextField(20);
	JTextField txt_newproduct_nama = new JTextField(20);
	JTextField txt_newproduct_idsup = new JTextField(20);
	JTextField txt_newproduct_harga = new JTextField(20);
	JTextField txt_newproduct_stock = new JTextField(20);
	JButton newproduct_save = new JButton("Save");
	JButton newproduct_cancel = new JButton("Cancel");
	
	
	JLabel newSupplier_id = new JLabel("ID Supplier : ");
	JLabel newSupplier_nama = new JLabel("Nama Supplier : ");
	JTextField txt_newSupplier_id = new JTextField(20);
	JTextField txt_newSupplier_nama = new JTextField(20);
	JButton newSupplier_save = new JButton("Save");
	JButton newSupplier_cancel = new JButton("Cancel");
	
	
	JLabel newStock_id = new JLabel("ID Product : ");
	JLabel newStock_nama = new JLabel("Nama Product : ");
	JLabel newStock_sisa = new JLabel("Stock sisa : ");
	JLabel newStock_tambah = new JLabel(
			"Jumlah stock yang ingin ditambahkan : ");
	String[] idStock = { " " };
	String[] namaStock = { " " };
	
	JComboBox cbo_newStock_id = new JComboBox(idStock);
	JComboBox cbo_newStock_nama = new JComboBox(namaStock);
	JTextField txt_newStock_sisa = new JTextField(20);
	JTextField txt_newStock_tambah = new JTextField(20);
	JButton newStock_save = new JButton("Save");
	JButton newStock_cancel = new JButton("Cancel");
	// Container panel1 = getContentPane();
	// Container panel2 = getContentPane();

	public Menu_Utama(String title) {
		super(title);
		super.setSize(540, 480);
		super.setLocationRelativeTo(null);
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// super.setResizable(false);
		panelTambahAdmin.setVisible(false);
		panelEditAdmin.setVisible(false);
		panelTabelEA.setVisible(false);
		panelDeleteAdmin.setVisible(false);
		panelTabelDA.setVisible(false);

		panelTambahProduct.setVisible(false);
		panelEditProduct.setVisible(false);
		panelTabelEP.setVisible(false);
		panelDeleteProduct.setVisible(false);
		panelTabelDP.setVisible(false);

		panelTambahSupplier.setVisible(false);
		panelEditSupplier.setVisible(false);
		panelTabelES.setVisible(false);
		panelDeleteSupplier.setVisible(false);
		panelTabelDS.setVisible(false);

		panelTambahStock.setVisible(false);
		panelLihatStock.setVisible(false);

		// panel1.setLayout(new FlowLayout());
		// panel2.setLayout(new FlowLayout());

		// G-R-I-D-B-A-G
		panelTambahAdmin.setLayout(new GridBagLayout());
		panelEditAdmin.setLayout(new GridBagLayout());
		panelTabelEA.setLayout(new GridBagLayout());
		panelDeleteAdmin.setLayout(new GridBagLayout());
		panelTabelDA.setLayout(new GridBagLayout());

		panelTambahProduct.setLayout(new GridBagLayout());
		panelEditProduct.setLayout(new GridBagLayout());
		panelTabelEP.setLayout(new GridBagLayout());
		panelDeleteProduct.setLayout(new GridBagLayout());
		panelTabelDP.setLayout(new GridLayout());

		panelTambahSupplier.setLayout(new GridBagLayout());
		panelEditSupplier.setLayout(new GridBagLayout());
		panelTabelES.setLayout(new GridBagLayout());
		panelDeleteSupplier.setLayout(new GridBagLayout());
		panelTabelDS.setLayout(new GridBagLayout());

		panelTambahStock.setLayout(new GridBagLayout());
		panelLihatStock.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints(); // Komponen Pasti
															// GridBag

		kontainer.setLayout(new FlowLayout());

		menuBar.add(MenuAdmin);
		menuBar.add(MenuProduct);
		menuBar.add(MenuSupplier);
		menuBar.add(MenuStock);
		menuBar.add(MenuReport);
		menuBar.add(MenuLogout);

		deleteMenuAdmin.setMnemonic(KeyEvent.VK_D);
		newMenuProduct.setMnemonic(KeyEvent.VK_N);
		editMenuProduct.setMnemonic(KeyEvent.VK_E);
		deleteMenuProduct.setMnemonic(KeyEvent.VK_D);
		newMenuSupplier.setMnemonic(KeyEvent.VK_N);
		editMenuSupplier.setMnemonic(KeyEvent.VK_E);
		deleteMenuSupplier.setMnemonic(KeyEvent.VK_D);
		newMenuStock.setMnemonic(KeyEvent.VK_N);
		viewMenuStock.setMnemonic(KeyEvent.VK_V);
		viewMenuReport.setMnemonic(KeyEvent.VK_V);
		newMenuAdmin.setMnemonic(KeyEvent.VK_N);
		editMenuAdmin.setMnemonic(KeyEvent.VK_E);

		MenuAdmin.add(newMenuAdmin);
		MenuAdmin.add(editMenuAdmin);
		MenuAdmin.add(deleteMenuAdmin);

		MenuProduct.add(newMenuProduct);
		MenuProduct.add(editMenuProduct);
		MenuProduct.add(deleteMenuProduct);

		MenuSupplier.add(newMenuSupplier);
		MenuSupplier.add(editMenuSupplier);
		MenuSupplier.add(deleteMenuSupplier);

		MenuStock.add(newMenuStock);
		MenuStock.add(viewMenuStock);
		MenuReport.add(viewMenuReport);

		super.setJMenuBar(menuBar);

		// Action
		tambahAdmin();
		editAdmin();
		deleteAdmin();
		tambahProduct();
		editProduct();
		deleteProduct();
		tambahSupplier();
		editSupplier();
		deleteSupplier();
		tambahStock();
		lihatStock();
		LogOut();

		// Panel Tambah User
		panelTambahAdmin.setBorder(BorderFactory
				.createTitledBorder("Tambah Admin")); // Menampilkan
														// Garis
														// Panel
														// dan
														// Judul
		
		cbo_newadmin_akses.setSelectedItem(2);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTambahAdmin.add(newadmin_user, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelTambahAdmin.add(txt_newadmin_user, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelTambahAdmin.add(newadmin_pass, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelTambahAdmin.add(txt_newadmin_pass, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelTambahAdmin.add(newadmin_akses, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelTambahAdmin.add(cbo_newadmin_akses, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipadx = 3;
		newadmin_save.setPreferredSize(new Dimension(170, 30));
		panelTambahAdmin.add(newadmin_save, gbc);
		newadmin_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_newadmin_user.setText("");
				txt_newadmin_pass.setText("");
				JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
			}
		});

		gbc.gridx = 1;
		gbc.gridy = 3;
		newadmin_cancel.setPreferredSize(new Dimension(170, 30));
		panelTambahAdmin.add(newadmin_cancel, gbc);
		newadmin_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_newadmin_user.setText("");
				txt_newadmin_pass.setText("");
				panelTambahAdmin.setVisible(false);
			}
		});

		// Panel Edit Admin
		panelEditAdmin
				.setBorder(BorderFactory.createTitledBorder("Edit Admin")); // Menampilkan
																			// Garis
																			// Panel
																			// dan
																			// Judul
	

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelEditAdmin.add(editadmin_user, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelEditAdmin.add(txt_editadmin_user, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		panelEditAdmin.add(editadmin_cari, gbc);

		scrollEA.setPreferredSize(new Dimension(500, 300));
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTabelEA.add(scrollEA, gbc);

		dataTabelEA.addColumn("Username");
		dataTabelEA.addColumn("Password");
		dataTabelEA.addColumn("Hak Akses");
		dataTabelEA.addColumn("Edit");

		tabelEA.setModel(dataTabelEA);
		/*
		 * tabelEA.getColumnModel().getColumn(2).setCellRenderer(new
		 * ButtonRenderer());
		 * tabelEA.getColumnModel().getColumn(2).setCellEditor(new
		 * ButtonEditor(new JCheckBox()));
		 * 
		 * tabelEA.getColumnModel().getColumn(1).setCellRenderer(new
		 * ButtonRenderer());
		 * tabelEA.getColumnModel().getColumn(1).setCellEditor(new
		 * ButtonEditor(new JCheckBox()));
		 * 
		 * tabelEA.getColumnModel().getColumn(0).setPreferredWidth(10);
		 * tabelEA.getColumnModel().getColumn(1).setPreferredWidth(50);
		 * tabelEA.getColumnModel().getColumn(2).setPreferredWidth(60);
		 */

		scrollEA.setVisible(true);

		// Panel Hapus Admin
		panelDeleteAdmin.setBorder(BorderFactory
				.createTitledBorder("Delete Admin")); // Menampilkan
														// Garis
														// Panel
														// dan
														// Judul
		JLabel deleteadmin_user = new JLabel("Username : ");
		JTextField txt_deleteadmin_user = new JTextField(20);
		JButton deleteadmin_cari = new JButton("Cari");

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelDeleteAdmin.add(deleteadmin_user, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelDeleteAdmin.add(txt_deleteadmin_user, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		panelDeleteAdmin.add(deleteadmin_cari, gbc);

		scrollDA.setPreferredSize(new Dimension(500, 300));
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTabelDA.add(scrollDA, gbc);

		dataTabelDA.addColumn("Username");
		dataTabelDA.addColumn("Password");
		dataTabelDA.addColumn("Hak Akses");
		dataTabelDA.addColumn("Hapus");

		tabelDA.setModel(dataTabelDA);
		// tabelDA.getColumnModel().getColumn(2).setCellRenderer(new
		// ButtonRenderer());
		// tabelDA.getColumnModel().getColumn(2).setCellEditor(new
		// ButtonEditor(new JCheckBox()));

		scrollDA.setVisible(true);

		// Panel Tambah Product
		panelTambahProduct.setBorder(BorderFactory
				.createTitledBorder("Tambah Product")); // Menampilkan
		// Garis
		// Panel
		// dan
		// Judul
		

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTambahProduct.add(newproduct_id, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelTambahProduct.add(txt_newproduct_id, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelTambahProduct.add(newproduct_nama, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelTambahProduct.add(txt_newproduct_nama, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelTambahProduct.add(newproduct_idsup, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelTambahProduct.add(txt_newproduct_idsup, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelTambahProduct.add(newproduct_harga, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		panelTambahProduct.add(txt_newproduct_harga, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panelTambahProduct.add(newproduct_stock, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		panelTambahProduct.add(txt_newproduct_stock, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.ipadx = 3;
		newproduct_save.setPreferredSize(new Dimension(170, 30));
		panelTambahProduct.add(newproduct_save, gbc);
		newproduct_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_newproduct_id.setText("");
				txt_newproduct_nama.setText("");
				txt_newproduct_idsup.setText("");
				txt_newproduct_harga.setText("");
				txt_newproduct_stock.setText("");
				JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
			}
		});

		gbc.gridx = 1;
		gbc.gridy = 5;
		newproduct_cancel.setPreferredSize(new Dimension(170, 30));
		panelTambahProduct.add(newproduct_cancel, gbc);
		newproduct_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_newproduct_id.setText("");
				txt_newproduct_nama.setText("");
				txt_newproduct_idsup.setText("");
				txt_newproduct_harga.setText("");
				txt_newproduct_stock.setText("");
				panelTambahProduct.setVisible(false);
			}
		});

		// Panel Edit Produk
		panelEditProduct.setBorder(BorderFactory
				.createTitledBorder("Edit Product")); // Menampilkan
		// Garis
		// Panel
		// dan
		// Judul
		JLabel editProduct_id = new JLabel("ID Product : ");
		JTextField txt_editProduct_id = new JTextField(20);
		JButton editProduct_cari = new JButton("Cari");
		

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelEditProduct.add(editProduct_id, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelEditProduct.add(txt_editProduct_id, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		panelEditProduct.add(editProduct_cari, gbc);

		scrollEP.setPreferredSize(new Dimension(500, 300));
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTabelEP.add(scrollEP, gbc);

		dataTabelEP.addColumn("ID");
		dataTabelEP.addColumn("Nama Product");
		dataTabelEP.addColumn("ID Supplier");
		dataTabelEP.addColumn("Harga");
		dataTabelEP.addColumn("Edit");

		tabelEP.setModel(dataTabelEP);
		tabelEP.getColumnModel().getColumn(4)
				.setCellRenderer(new ButtonRenderer());
		tabelEP.getColumnModel().getColumn(4)
				.setCellEditor(new ButtonEditor(new JCheckBox()));

		/*tabelEP.getColumnModel().getColumn(5)
				.setCellRenderer(new ButtonRenderer());
		tabelEP.getColumnModel().getColumn(5)
				.setCellEditor(new ButtonEditor(new JCheckBox()));*/
		// tabelEP.getColumnModel().getColumn(2).setCellRenderer(new
		// ButtonRenderer());
		// tabelEP.getColumnModel().getColumn(2).setCellEditor(new
		// ButtonEditor(new JCheckBox()));

		tabelEP.getColumnModel().getColumn(0).setPreferredWidth(35);
		tabelEP.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabelEP.getColumnModel().getColumn(2).setPreferredWidth(70);

		scrollEP.setVisible(true);
		isiTable();

		// Panel Delete Produk
		panelDeleteProduct.setBorder(BorderFactory
				.createTitledBorder("Delete Product")); // Menampilkan Garis
														// Panel dan Judul
		JLabel deleteProduct_id = new JLabel("ID Product : ");
		JTextField txt_deleteProduct_id = new JTextField(20);
		JButton deleteProduct_cari = new JButton("Cari");

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelDeleteProduct.add(deleteProduct_id, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelDeleteProduct.add(txt_deleteProduct_id, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		panelDeleteProduct.add(deleteProduct_cari, gbc);

		scrollDP.setPreferredSize(new Dimension(500, 300));
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTabelDP.add(scrollDP, gbc);

		dataTabelDP.addColumn("ID");
		dataTabelDP.addColumn("Nama Product");
		dataTabelDP.addColumn("ID Supplier");
		dataTabelDP.addColumn("Harga");
		dataTabelDP.addColumn("Stock");
		dataTabelDP.addColumn("Hapus");

		tabelDP.setModel(dataTabelDP);
		// tabelDP.getColumnModel().getColumn(2).setCellRenderer(new
		// ButtonRenderer());
		// tabelDP.getColumnModel().getColumn(2).setCellEditor(new
		// ButtonEditor(new JCheckBox()));

		tabelDP.getColumnModel().getColumn(0).setPreferredWidth(35);
		tabelDP.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabelDP.getColumnModel().getColumn(2).setPreferredWidth(70);

		scrollDP.setVisible(true);

		// Panel Tambah Supplier
		panelTambahSupplier.setBorder(BorderFactory
				.createTitledBorder("Tambah Supplier")); // Menampilkan Garis
															// Panel dan Judul
		

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTambahSupplier.add(newSupplier_id, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelTambahSupplier.add(txt_newSupplier_id, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelTambahSupplier.add(newSupplier_nama, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelTambahSupplier.add(txt_newSupplier_nama, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipadx = 3;
		panelTambahSupplier.add(newSupplier_save, gbc);
		newSupplier_save.setPreferredSize(new Dimension(170, 30));
		newSupplier_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_newSupplier_id.setText("");
				txt_newSupplier_nama.setText("");
				JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
			}
		});

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelTambahSupplier.add(newSupplier_cancel, gbc);
		newSupplier_cancel.setPreferredSize(new Dimension(170, 30));
		newSupplier_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_newSupplier_id.setText("");
				txt_newSupplier_nama.setText("");
				panelTambahSupplier.setVisible(false);
			}
		});

		// Panel Edit Supplier
		panelEditSupplier.setBorder(BorderFactory
				.createTitledBorder("Edit Supplier")); // Menampilkan Garis
														// Panel dan Judul
		JLabel editSupplier_id = new JLabel("ID Supplier : ");
		JTextField txt_editSupplier_id = new JTextField(20);
		JButton editSupplier_cari = new JButton("Cari");

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelEditSupplier.add(editSupplier_id, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelEditSupplier.add(txt_editSupplier_id, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		panelEditSupplier.add(editSupplier_cari, gbc);

		scrollES.setPreferredSize(new Dimension(500, 300));
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTabelES.add(scrollES, gbc);

		dataTabelES.addColumn("ID Supplier");
		dataTabelES.addColumn("Nama Supplier");
		dataTabelES.addColumn("Edit");

		tabelES.setModel(dataTabelES);
		// tabelES.getColumnModel().getColumn(2).setCellRenderer(new
		// ButtonRenderer());
		// tabelES.getColumnModel().getColumn(2).setCellEditor(new
		// ButtonEditor(new JCheckBox()));

		scrollES.setVisible(true);

		// Panel Delete Supplier
		panelDeleteSupplier.setBorder(BorderFactory
				.createTitledBorder("Delete Supplier")); // Menampilkan Garis
															// Panel dan Judul
		JLabel deleteSupplier_id = new JLabel("ID Supplier : ");
		JTextField txt_deleteSupplier_id = new JTextField(20);
		JButton deleteSupplier_cari = new JButton("Cari");

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelDeleteSupplier.add(deleteSupplier_id, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelDeleteSupplier.add(txt_deleteSupplier_id, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		panelDeleteSupplier.add(deleteSupplier_cari, gbc);

		scrollDS.setPreferredSize(new Dimension(500, 300));
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTabelDS.add(scrollDS, gbc);

		dataTabelDS.addColumn("ID Supplier");
		dataTabelDS.addColumn("Nama Supplier");
		dataTabelDS.addColumn("Hapus");

		tabelDS.setModel(dataTabelDS);
		// tabelES.getColumnModel().getColumn(2).setCellRenderer(new
		// ButtonRenderer());
		// tabelES.getColumnModel().getColumn(2).setCellEditor(new
		// ButtonEditor(new JCheckBox()));

		scrollDS.setVisible(true);

		// Panel New Stock
		panelTambahStock.setBorder(BorderFactory
				.createTitledBorder("Tambah Stock")); // Menampilkan Garis
														// Panel dan Judul
		

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelTambahStock.add(newStock_id, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelTambahStock.add(cbo_newStock_id, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelTambahStock.add(newStock_nama, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelTambahStock.add(cbo_newStock_nama, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelTambahStock.add(newStock_sisa, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelTambahStock.add(txt_newStock_sisa, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelTambahStock.add(newStock_tambah, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		panelTambahStock.add(txt_newStock_tambah, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.ipadx = 3;
		newStock_save.setPreferredSize(new Dimension(170, 30));
		panelTambahStock.add(newStock_save, gbc);
		newStock_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_newStock_sisa.setText("");
				txt_newStock_tambah.setText("");
				JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
			}
		});

		gbc.gridx = 1;
		gbc.gridy = 4;
		newStock_cancel.setPreferredSize(new Dimension(170, 30));
		panelTambahStock.add(newStock_cancel, gbc);
		newStock_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_newStock_sisa.setText("");
				txt_newStock_tambah.setText("");
				panelTambahStock.setVisible(false);
			}
		});

		// Panel Lihat Stock
		panelLihatStock.setBorder(BorderFactory
				.createTitledBorder("Lihat Stock")); // Menampilkan Garis
														// Panel dan Judul
		JLabel lihatStock_id = new JLabel("ID Product : ");
		JTextField txt_lihatStock_id = new JTextField(20);
		JButton lihatStock_save = new JButton("Cari");

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelLihatStock.add(lihatStock_id, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panelLihatStock.add(txt_lihatStock_id, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		panelLihatStock.add(lihatStock_save, gbc);

		kontainer.add(panelTambahStock);
		kontainer.add(panelLihatStock);

		kontainer.add(panelTambahSupplier);
		kontainer.add(panelEditSupplier);
		kontainer.add(panelTabelES);
		kontainer.add(panelDeleteSupplier);
		kontainer.add(panelTabelDS);

		kontainer.add(panelTambahProduct);
		kontainer.add(panelEditProduct);
		kontainer.add(panelTabelEP);
		kontainer.add(panelDeleteProduct);
		kontainer.add(panelTabelDP);

		kontainer.add(panelTambahAdmin);
		kontainer.add(panelEditAdmin);
		kontainer.add(panelTabelEA);
		kontainer.add(panelDeleteAdmin);
		kontainer.add(panelTabelDA);

	}

	// Tombol Tambah Admin
	public void tambahAdmin() {
		newMenuAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Tampilkan panel
				panelTambahAdmin.setVisible(true);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);
			}
		});
	}

	// Tombol Edit Admin
	public void editAdmin() {
		editMenuAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(true);
				panelTabelEA.setVisible(true);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);
			}
		});
	}

	// Tombol Delete Admin
	public void deleteAdmin() {
		deleteMenuAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(true);
				panelTabelDA.setVisible(true);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);
			}
		});
	}

	// Tombol Tambah Product
	public void tambahProduct() {
		newMenuProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(true);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);
			}
		});
	}

	// Tombol Edit Product
	public void editProduct() {
		editMenuProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(true);
				panelTabelEP.setVisible(true);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);

			}
		});
	}

	// Tombol Delete Product
	public void deleteProduct() {
		deleteMenuProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(true);
				panelTabelDP.setVisible(true);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);
			}
		});
	}

	// Tombol Tambah Supplier
	public void tambahSupplier() {
		newMenuSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(true);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);
			}
		});
	}

	// Tombol Edit Supplier
	public void editSupplier() {
		editMenuSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(true);
				panelTabelES.setVisible(true);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);
			}
		});
	}

	// Tombol Delete Supplier
	public void deleteSupplier() {
		deleteMenuSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(true);
				panelTabelDS.setVisible(true);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);
			}
		});
	}

	// Tombol Tambah Stock
	public void tambahStock() {
		newMenuStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(true);
				panelLihatStock.setVisible(false);
			}
		});
	}

	// Tombol Lihat Stock
	public void lihatStock() {
		viewMenuStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(true);
			}
		});
	}

	// Tombol Log Out
	public void LogOut() {
		MenuLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panelTambahAdmin.setVisible(false);
				panelEditAdmin.setVisible(false);
				panelTabelEA.setVisible(false);
				panelDeleteAdmin.setVisible(false);
				panelTabelDA.setVisible(false);

				panelTambahProduct.setVisible(false);
				panelEditProduct.setVisible(false);
				panelTabelEP.setVisible(false);
				panelDeleteProduct.setVisible(false);
				panelTabelDP.setVisible(false);

				panelTambahSupplier.setVisible(false);
				panelEditSupplier.setVisible(false);
				panelTabelES.setVisible(false);
				panelDeleteSupplier.setVisible(false);
				panelTabelDS.setVisible(false);

				panelTambahStock.setVisible(false);
				panelLihatStock.setVisible(false);
				//Menu_Login ml = new Menu_Login("Login");
				//ml.setVisible(true);
			}
		});
	}

	public static void main(String[] argx) {
		// JFrame menu_utama = new JFrame("Menu Utama");
		Menu_Utama menu = new Menu_Utama("Dashboard Admin");
		menu.setVisible(true);
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

		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// fireEditingStopped();
					// System.out.println("andhika");
				}
			});
		}
	}
///////////////// ----------- SEBELAH KENE PAK ------------ //////////////////////////
	public void isiTable() {
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi = DriverManager.getConnection(Config.URL, Config.username,
					Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		int n = 0;
		int row = dataTabelEP.getRowCount();
		for (int i = 0; i < row; i++) {
			dataTabelEP.removeRow(0);
		}

		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Products");
			while (rs.next()) {
				int id = rs.getInt(1);
				nama = rs.getString(2);
				idsup = rs.getString(3);
				harga = rs.getInt(4);
				//stock = rs.getInt(4);
				n += 1;
				Object[] a = { id, nama, idsup, harga, "Edit" };
				dataTabelEP.addRow(a);
			}
		}

		catch (SQLException x) {
			x.printStackTrace();
		}

	}

	public void setVisible(boolean b) {

	}

	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return null;
	}

}

