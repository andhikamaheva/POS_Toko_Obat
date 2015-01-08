package tampilan;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import tampilan.Manage_supp.ButtonEditor;
import tampilan.Tambah_prod.Pencarian;
import database.Config;
import database.dbProducts;
import database.dbSupplier;
import fungsi.Products;
import fungsi.Supplier;
import database.dbSupplier;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Vector;

public class Restock_prod extends JDialog {
	private Connection koneksi;
	private dbProducts dp = new dbProducts();
	private JPanel panelCari = new JPanel();
	private JPanel panelHasil = new JPanel();
	private Container kontainer = getContentPane();
	public String nKategori = "";
	private JLabel lblCari = new JLabel("Cari Berdasar : ");
	String[] akses = { "ID Produk", "Nama Produk" };
	private JComboBox cboKategori = new JComboBox(akses);
	private JTextField txtCari = new JTextField(13);
	private JButton btnCari = new JButton("Cari");
	
	private JLabel lblIDp = new JLabel("ID Produk");
	private JLabel lblNoIdp = new JLabel("-");
	
	private JLabel lblNama = new JLabel("Nama Produk");
	private JLabel lblNamaP = new JLabel("-");
	
	private JLabel lblIdSupp = new JLabel("ID Supplier");
	private JLabel lblIdSuppNo = new JLabel("-");
	
	private JLabel lblHarga = new JLabel("Harga");
	private JLabel lblHargaA = new JLabel("-");
	
	private JLabel lblStock = new JLabel("Stock");
	private JLabel lblStockA = new JLabel("-");
	
	private JLabel lblRestock = new JLabel("Restock");
	NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format);
	private JFormattedTextField txtRestock = new JFormattedTextField(formatter);
	
	private JButton btnTambah = new JButton("+");
	private JButton btnKurang = new JButton("-");
	
	private JButton btnSimpan = new JButton("Simpan");
	
	
	public Restock_prod (Utama owner,String title, ModalityType modal){
		super(owner, title, modal);
		super.setSize(550, 450);
		super.setResizable(false);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		btnSimpan.setEnabled(false);
		cboKategori.setSelectedItem(2);
		
		panelCari.setLayout(new GridBagLayout());
		panelHasil.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		kontainer.setLayout(new FlowLayout());
		
		 	formatter.setValueClass(Integer.class);
		    formatter.setMinimum(0);
		    formatter.setMaximum(Integer.MAX_VALUE);
		    // If you want the value to be committed on each keystroke instead of focus lost
		    formatter.setCommitsOnValidEdit(true);
		
		panelCari.setBorder(BorderFactory.createTitledBorder("Cari Data"));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		panelCari.add(lblCari, c);
		
		c.gridx = 1;
		c.gridy = 0;
		panelCari.add(cboKategori, c);
		
		c.gridx = 2;
		c.gridy = 0;
		panelCari.add(txtCari,c);
		
		c.gridx = 3;
		c.gridy = 0;
		panelCari.add(btnCari,c);
		
		
		panelHasil.setBorder(BorderFactory.createTitledBorder("Restock Produk"));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		panelHasil.add(lblIDp, c);
		
		c.gridx = 1;
		c.gridy = 0;
		panelHasil.add(lblNoIdp,c);
		
		c.gridx = 0;
		c.gridy = 1;
		panelHasil.add(lblNama,c);
		
		c.gridx = 1;
		c.gridy = 1;
		panelHasil.add(lblNamaP,c);
		
		c.gridx = 0;
		c.gridy = 2;
		panelHasil.add(lblIdSupp,c);
		
		c.gridx = 1;
		c.gridy = 2;
		panelHasil.add(lblIdSuppNo,c);
		
		c.gridx = 0;
		c.gridy = 3;
		panelHasil.add(lblHarga,c);
		
		c.gridx = 1;
		c.gridy = 3;
		panelHasil.add(lblHargaA,c);
		
		c.gridx = 0;
		c.gridy = 4;
		panelHasil.add(lblStock,c);
		
		c.gridx = 1;
		c.gridy = 4;
		panelHasil.add(lblStockA,c);
		
		c.gridx = 0;
		c.gridy = 5;
		panelHasil.add(lblRestock,c);
		
		c.gridx = 1;
		c.gridy = 5;
		
		panelHasil.add(txtRestock,c);
		
		c.gridx = 2;
		c.gridy = 5;
		panelHasil.add(btnTambah,c);
		
		c.gridx = 3;
		c.gridy = 5;
		panelHasil.add(btnKurang,c);
		
		c.gridx = 1;
		c.gridy = 7;
		panelHasil.add(btnSimpan,c);
		
		
		kontainer.add(panelCari);
		kontainer.add(panelHasil);
		cariData();
		cariDataEnter();
		btnTambah();
		btnKurang();
		btnSimpan();
		
		

	}
	
	public void btnSimpan(){
		btnSimpan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(lblNoIdp.getText().equals("-") || (txtRestock.getText().equals(""))   ){
					JOptionPane.showMessageDialog(null, "Field Tidak Boleh Kosong ", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				
				else{
					dp.reStock(lblNoIdp, lblStockA);
				}
			}
		});
	}
	
	public void btnTambah(){
		btnTambah.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if((txtRestock.getText().equals("")) || (txtRestock.getText().equals(null))){
					JOptionPane.showMessageDialog(null, "Field Restock Tidak Boleh Kosong & Harus Angka!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else{
				int awal = Integer.parseInt(lblStockA.getText());
				int akhir = Integer.parseInt(txtRestock.getText());
				int total = awal+akhir;
				lblStockA.setText(""+total);
				}
			}
		});
	}
	
	public void btnKurang(){
		btnKurang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(txtRestock.getText().equals("") || (txtRestock.getText().equals(null)) ){
					JOptionPane.showMessageDialog(null, "Field Restock Tidak Boleh Kosong & Harus Angka!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else{
				int awal = Integer.parseInt(lblStockA.getText());
				int akhir = Integer.parseInt(txtRestock.getText());
				int total = awal-akhir;
				lblStockA.setText(""+total);
				}
			}
		});
	}
	public void cariData() {
		btnCari.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if ((txtCari.getText().equals(null))
						|| (txtCari.getText().equals(""))) {
					JOptionPane.showMessageDialog(null,
							"Data Belum dimasukkan!", "Error!",
							JOptionPane.ERROR_MESSAGE);
				} else {
				int kategori = cboKategori.getSelectedIndex();
				
				if (kategori == 0) {
					nKategori = "idProd";
				} else if (kategori == 1) {
					nKategori = "nama";
				}
				String by = txtCari.getText();
				//dp.viewAllByRe(by, nKategori, txtCari, table, dataModel);
				cekData(nKategori, by);
				}
			}
		});
	}
	
	public void cariDataEnter() {
		txtCari.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if ((txtCari.getText().equals(null))
						|| (txtCari.getText().equals(""))) {
					JOptionPane.showMessageDialog(null,
							"Data Belum dimasukkan!", "Error!",
							JOptionPane.ERROR_MESSAGE);
				} else {
				int kategori = cboKategori.getSelectedIndex();
				
				if (kategori == 0) {
					nKategori = "idProd";
				} else if (kategori == 1) {
					nKategori = "nama";
				}
				String by = txtCari.getText();
				//dp.viewAllByRe(by, nKategori, txtCari, table, dataModel);
				cekData(nKategori, by);
				}
			}
		});
	}
	
	
	public void cekData(String kategori, String by) {

		String cek = "";
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi = DriverManager.getConnection(Config.URL, Config.username,
					Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		try {
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select idProd from Products where "+kategori+" like '%"+ by +"%' ");
			while (rs.next()) {
				cek = rs.getString(1);
			}
			if (cek.equals("")) {
				JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
						"Data Not Found!", JOptionPane.ERROR_MESSAGE);
			} else {
				Utama owner = new Utama("");
				Restock_prod re = new Restock_prod(owner,"", ModalityType.APPLICATION_MODAL);
				Pencarian pc = new Pencarian(owner, "Data Products",
						ModalityType.APPLICATION_MODAL);
				pc.setVisible(true);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public class Pencarian extends JDialog {
		private JPanel panelDialog = new JPanel();
		private Container kontainer = new Container();

		private DefaultTableModel dataModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		private JTable table = new JTable(dataModel);
		private JScrollPane scroll = new JScrollPane(table);

	
	public Pencarian(Utama owner, String title, ModalityType model) {
		super(owner, title, model);
		super.setSize(500, 470);
		// super.setVisible(true);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		dataModel.addColumn("ID");
		dataModel.addColumn("Nama");
		dataModel.addColumn("ID Suppier");
		dataModel.addColumn("Harga");
		dataModel.addColumn("Stock");
		table.setModel(dataModel);

		kontainer = getContentPane();
		kontainer.setLayout(new FlowLayout());
		kontainer.add(scroll);
		// createTable();

		dp.viewAllByAdd(nKategori, txtCari.getText(), table, dataModel);
		if (table.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
					"Data Not Found!", JOptionPane.ERROR_MESSAGE);
			setVisible(false);
			dispose();
			setVisible(false);
			dispose();

		}
		String solve = "Solve";
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, solve);
		table.getActionMap().put(solve, new EnterAction());
		clickData();
		// createTable();

	}



	public void clickData() {
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// JTable target = (JTable)e.getSource();
					table = (JTable) e.getSource();
					int row = table.getSelectedRow();
					String id = table.getValueAt(row, 0).toString();
					String nama = table.getValueAt(row, 1).toString();
					String idSupp = table.getValueAt(row, 2).toString();
					String harga = table.getValueAt(row, 3).toString();
					String stock = table.getValueAt(row, 4).toString();
					
					lblNoIdp.setText(id);
					lblNamaP.setText(nama);
					lblIdSuppNo.setText(idSupp);
					lblHargaA.setText(harga);
					lblStockA.setText(stock);
					btnSimpan.setEnabled(true);
					dispose();
				}
			}
		});
	}

	
	private class EnterAction extends AbstractAction {

	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	table = (JTable) e.getSource();
			int row = table.getSelectedRow();
			String id = table.getValueAt(row, 0).toString();
			String nama = table.getValueAt(row, 1).toString();
			String idSupp = table.getValueAt(row, 2).toString();
			String harga = table.getValueAt(row, 3).toString();
			String stock = table.getValueAt(row, 4).toString();
			
			lblNoIdp.setText(id);
			lblNamaP.setText(nama);
			lblIdSuppNo.setText(idSupp);
			lblHargaA.setText(harga);
			lblStockA.setText(stock);
			btnSimpan.setEnabled(true);
			dispose();
	    }
	
	
	}

}
	
}
