package tampilan;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import tampilan.Manage_supp.ButtonEditor;
import database.Config;
import database.dbProducts;
import database.dbSupplier;
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

public class Tambah_prod extends JFrame {
	public boolean cek = false;
	private Connection koneksi;
	private JPanel panelTambah = new JPanel();
	private Container kontainer = new Container();
	private dbSupplier sp = new dbSupplier();
	private dbProducts pd = new dbProducts();
	private JLabel lblSupp = new JLabel("Nama Supplier");
	public JTextField txtSupp = new JTextField(15);
	// inser combo box
	// String[] supp = { "CV. Maju Jaya", "PT. Kimia Farma" };
	// private JComboBox cboSupp = new JComboBox(supp);

	private JLabel lblIdSupp = new JLabel("ID Supplier");
	public JLabel lblNoId = new JLabel("-");

	private JLabel lblIDp = new JLabel("ID Produk");
	private JLabel lblNoIdp = new JLabel("-");

	private JLabel lblNama = new JLabel("Nama Produk");
	public JTextField txtNama = new JTextField(10);

	private JLabel lblHarga = new JLabel("Harga");
	//Set Curency
	//NumberFormat format = NumberFormat.getCurrencyInstance();
	//NumberFormatter formatter = new NumberFormatter(format);
	
	//NumberFormat format = NumberFormat.getInstance();
    //NumberFormatter formatter = new NumberFormatter(format);
    
	private JTextField txtHarga = new JTextField(15);

	private JLabel lblStock = new JLabel("Stock");
	private JTextField txtStock = new JTextField();

	private JLabel lblNotif = new JLabel(
			"Tekan Enter pada Field Supplier untuk mencari data supplier");
	private JButton btnSimpan = new JButton("Simpan");
	private JButton btnBatal = new JButton("Batal");

	public Tambah_prod(String title) {
		super(title);
		super.setResizable(false);
		super.setLocationRelativeTo(null);
		super.setSize(500, 250);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		kontainer = getContentPane();
		panelTambah.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		kontainer.setLayout(new FlowLayout());
		panelTambah
				.setBorder(BorderFactory.createTitledBorder("Data Supplier"));

		panelTambah.setMaximumSize(new Dimension(480, 220));
/*		formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
		*/
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 0;
		c.gridy = 0;
		panelTambah.add(lblSupp, c);

		c.gridx = 1;
		c.gridy = 0;
		panelTambah.add(txtSupp, c);

		c.gridx = 2;
		c.gridy = 0;
		panelTambah.add(lblIdSupp, c);

		c.gridx = 3;
		c.gridy = 0;
		panelTambah.add(lblNoId, c);

		c.gridx = 0;
		c.gridy = 1;
		panelTambah.add(lblNama, c);

		c.gridx = 1;
		c.gridy = 1;
		panelTambah.add(txtNama, c);

		c.gridx = 2;
		c.gridy = 1;
		panelTambah.add(lblIDp, c);

		c.gridx = 3;
		c.gridy = 1;
		panelTambah.add(lblNoIdp, c);

		c.gridx = 0;
		c.gridy = 2;
		panelTambah.add(lblHarga, c);

	/*	
		format.setMaximumFractionDigits(0);
		formatter.setMinimum(0.0);
		formatter.setMaximum(1000000000.0);
		formatter.setAllowsInvalid(false);
		formatter.setOverwriteMode(false);
		formatter.setCommitsOnValidEdit(true);
		
		txtHarga.setValue( 0);
		*/
		

		c.gridx = 1;
		c.gridy = 2;
		panelTambah.add(txtHarga, c);

		c.gridx = 2;
		c.gridy = 2;
		c.ipadx = 40;
		panelTambah.add(lblStock, c);

		
		c.gridx = 3;
		c.gridy = 2;
		panelTambah.add(txtStock, c);

		c.gridx = 0;
		c.gridy = 3;
		panelTambah.add(btnBatal, c);

		c.gridx = 1;
		c.gridy = 3;
		btnSimpan.setPreferredSize(new Dimension(100, 25));
		panelTambah.add(btnSimpan, c);

		JPanel panelNotif = new JPanel();
		panelNotif.setLayout(new BorderLayout());

		panelNotif.add(lblNotif, BorderLayout.CENTER);

		kontainer.add(panelTambah);
		kontainer.add(panelNotif);

		cariSupp();
		btnBatal();
		btnSimpan();

	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		Tambah_prod tmb = new Tambah_prod("Tambah Produk");
		tmb.setVisible(true);
		tmb.setLocationRelativeTo(null);
	}

	public void btnSimpan() {
		btnSimpan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				pd.addProduk(txtSupp, lblNoId, lblNoIdp, txtNama, txtHarga, txtStock);
			}
		});
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

	public void cariSupp() {

		txtSupp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if ((txtSupp.getText().equals(null))
						|| (txtSupp.getText().equals(""))) {
					JOptionPane.showMessageDialog(null,
							"Nama Supplier Belum dimasukkan!", "Error!",
							JOptionPane.ERROR_MESSAGE);
				} else {

					cekData();

					// pc.setLocationRelativeTo(null);
				}

			}
		});
	}

	public void cekData() {

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
			ResultSet rs = stmt
					.executeQuery("select idSupp from Supplier where namaSupp like '%"
							+ txtSupp.getText() + "%'");

			while (rs.next()) {
				cek = rs.getString(1);
			}
			if (cek.equals("")) {
				JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
						"Data Not Found!", JOptionPane.ERROR_MESSAGE);

			} else {
				Tambah_prod tmb = new Tambah_prod("Tambah Supplier");
				Pencarian pc = new Pencarian(tmb, "Data Supplier",
						ModalityType.MODELESS);
				pc.setVisible(true);
			}

			/*
			 * if (!cek.equalsIgnoreCase("")){
			 * JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
			 * "Data Not Found!", JOptionPane.ERROR_MESSAGE);
			 * System.out.println(txtSupp.getText()); } else {
			 * 
			 * 
			 * }
			 */
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

		public Pencarian(Tambah_prod owner, String title, ModalityType model) {
			super(owner, title, model);
			super.setSize(500, 470);
			// super.setVisible(true);
			super.setLocationRelativeTo(null);
			super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			dataModel.addColumn("ID");
			dataModel.addColumn("Nama");
			dataModel.addColumn("Alamat");
			dataModel.addColumn("Telp");
			table.setModel(dataModel);

			kontainer = getContentPane();
			kontainer.setLayout(new FlowLayout());
			kontainer.add(scroll);
			// createTable();

			sp.viewAllByAdd(txtSupp.getText(), table, dataModel);
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

		public void createTable() {
			Vector<Supplier> vSupplier = sp.viewAll();
			for (int i = 0; i < vSupplier.size(); i++) {
				dataModel.addRow(new Object[] {
						vSupplier.elementAt(i).getIdsupp(),
						vSupplier.elementAt(i).getNama(),
						vSupplier.elementAt(i).getAlamat(),
						vSupplier.elementAt(i).getTelp(), "Edit", "Hapus" });
			}
			table.setModel(dataModel);

		}

		public void clickData() {
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						// JTable target = (JTable)e.getSource();
						table = (JTable) e.getSource();
						int row = table.getSelectedRow();
						String nama = table.getValueAt(row, 1).toString();
						String id = table.getValueAt(row, 0).toString();
						txtSupp.setText(nama);
						lblNoId.setText(id);
						isiIdProd(lblNoId);
						txtNama.grabFocus();
						dispose();
					}
				}
			});
		}
		
		public void isiIdProd(JLabel idSupp){
			try{
				
				String nol;
				int urutan=0;
				int panjang=0;
				Statement stmt = koneksi.createStatement();
				ResultSet rs = stmt.executeQuery("select count(*), length(count(*)) from Products where idSupp = '" + idSupp.getText() + "' ");
				
				while (rs.next()){
					urutan = rs.getInt(1);
					panjang = rs.getInt(2);
				}
				
				if(panjang==1){
					nol = "00";
				}
				else if(panjang==2){
					nol = "0";
				}
				else{
					nol="";
				}
				
				
				if(urutan==0){
					urutan+=1;
				}
				stmt.close();
				rs.close();
				String idProd= idSupp.getText()+nol+urutan;
				try{
					String cek="";
					rs = stmt.executeQuery("select idProd from Products where idProd = '"+idProd+"'");
					while(rs.next()){
						cek = rs.getString(1);
					}
					if (cek.equals("")){
						lblNoIdp.setText(idProd);
					}
					else{
						stmt.close();
						rs.close();
						cek="";
						boolean valid = false;
						do{
							urutan++;
							int length = (int) Math.log10(urutan) + 1;
							if(length==1){
								nol = "00";
							}
							else if(length==2){
								nol = "0";
							}
							else{
								nol="";
							}
							String idProd2= idSupp.getText()+nol+urutan;
							
							ResultSet rz = stmt.executeQuery("select idProd from Products where idProd = '"+idProd2+"'");
							while(rz.next()){
								cek = rz.getString(1);
							}
							if (cek.equals(idProd2)){
								valid = true;
							}
							else{
								valid = false;
								lblNoIdp.setText(idProd2);
							}
						}while(valid!=false);
					}
				}
				catch(SQLException x){
					x.printStackTrace();
				}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		}
		
		private class EnterAction extends AbstractAction {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	table = (JTable) e.getSource();
				int row = table.getSelectedRow();
				String nama = table.getValueAt(row, 1).toString();
				String id = table.getValueAt(row, 0).toString();
				txtSupp.setText(nama);
				lblNoId.setText(id);
				isiIdProd(lblNoId);
				txtNama.grabFocus();
				dispose();
		    }
		
		
		}

	}

}
