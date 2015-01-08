package tampilan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.PlainDocument;

import database.Config;
import database.dbProducts;
import database.dbSupplier;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;

import tampilan.Manage_supp.ButtonEditor;
import tampilan.Manage_supp.ButtonRenderer;
import tampilan.Manage_supp.Final_editSupp;
import tampilan.Restock_prod.Pencarian;

public class Utama extends JFrame {
	
	public Masuk ms = new Masuk("");
	public dbProducts dp = new dbProducts();
	private JPanel panelTitle = new JPanel();
	private JPanel panelTengah = new JPanel();
	private JPanel panelReport = new JPanel();
	private JPanel panelDetail = new JPanel();
	private JPanel panelDetail2 = new JPanel();
	private JMenuBar menu = new JMenuBar();
	private Connection koneksi;
	public boolean cekKlik = false; 
	// Master Administrator
	public JMenu Administrator = new JMenu("Administrator");
	public JMenuItem tmbUser = new JMenuItem("Tambah User");
	public JMenuItem gantiUser = new JMenuItem("Ganti Password");
	public JMenuItem editUser = new JMenuItem("Manage User");
	public JMenuItem logout = new JMenuItem("Log out");

	// Master Supplier
	public JMenu mSupp = new JMenu("Master Supplier");
	public JMenuItem tmbSupp = new JMenuItem("Tambah Supplier");
	public JMenuItem mngSupp = new JMenuItem("Manage Supplier");
	// public JMenuItem editSupp = new JMenuItem("Edit Supplier");
	// public JMenuItem hpsSupp = new JMenuItem("Hapus User");
	// public JMenuItem lhtSupp = new JMenuItem("Lihat User");

	// Master Produk
	public JMenu mProduk = new JMenu("Master Produk");
	public JMenuItem tmbProduk = new JMenuItem("Tambah Produk");
	public JMenuItem mngProduk = new JMenuItem("Manage Produk");
	public JMenuItem restockProduk = new JMenuItem("Restock Produk");

	// Master Laporan
	public JMenu mReport = new JMenu("Report");
	public JMenuItem rSupp = new JMenuItem("Report Supplier");
	public JMenuItem rProd = new JMenuItem("Report Produk");
	public JMenuItem rSales = new JMenuItem("Report Penjualan");

	// Report
	public JMenu laporan = new JMenu("Laporan");

	// About

	// GUI
	private JLabel lblTitle = new JLabel(
			"<HTML><H1><Center>Apotik K24 Sidoarjo</Center></H1></HTML>");

	// GUI Penjualan
	private JLabel lblIdTransaksi = new JLabel("Kode Transasksi");
	private JLabel lblNoTransaksi = new JLabel("");
	
	private JLabel lblWaktu = new JLabel("Taanggal / Jam ");
	private JLabelTime lblTime = new JLabelTime();
	
	
	private JLabel lblNama = new JLabel("Nama Petugas : ");
	
	private JLabel lblNama2 = new JLabel("");

	private JLabel lblKode = new JLabel("Kode Barang");
	private JTextField txtKode = new JTextField();
	private JLabel lblNotif = new JLabel("Enter untuk cari");

	private JLabel lblNamaB = new JLabel("Nama Barang");
	private JTextField txtNamaB = new JTextField();
	
	
	private JLabel lblHarga = new JLabel("Harga Barang");
	private JTextField txtHarga = new JTextField();
	private JLabel lblStock = new JLabel ("Stock");
	private JLabel lblStockA = new JLabel("-");
	
	private JLabel lblJumlah = new JLabel("Jumlah Beli");
	private JTextField txtJumlah = new JTextField();

	private JButton btnTambah = new JButton("Tambah");

	private JPanel panelTotal = new JPanel();
	private JLabel lblRp = new JLabel("<html><h1>Rp.</h1></html>");
	private JLabel lblTotal = new JLabel("<HTML><H1>0</H1></HTML>");

	// GUI Table
	private DefaultTableModel dataModel = new DefaultTableModel(){
		@Override
		public boolean isCellEditable(int row, int column) {
			if (column >= 5) {
				return true;
			} else {
				return false;
			}
		}

	};
	private JTable table = new JTable(dataModel);
	private JScrollPane scroll = new JScrollPane(table);
	private JButton btnBayar = new JButton("Bayar");
	

	public JMenu help = new JMenu("Help");
	public JMenuItem about = new JMenuItem("About");
	
	//Update Data
	public String idProd;
	public String namaProd;
	public String hargaProd;
	public String jumlahProd;
	
	public int rowx;
	public int columnx;
	
	
	public int cekStock;
	

	public Utama(String title) {
		super(title);
		// super.setResizable(false);
		super.setSize(800, 600);
		super.setLocationRelativeTo(null);
		
		super.setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		panelTitle.setLayout(new FlowLayout());
		panelTengah.setLayout(new BoxLayout(panelTengah, BoxLayout.PAGE_AXIS));
		panelTotal.setLayout(new GridBagLayout());
		panelDetail.setLayout(new FlowLayout());
		panelDetail2.setLayout(new FlowLayout());
		panelReport.setLayout(new GridBagLayout());

		scroll.setPreferredSize(new Dimension(700, 200));
		GridBagConstraints c = new GridBagConstraints();
		Container kontainer = getContentPane();

		panelReport.setMaximumSize(new Dimension(700, 340));
		panelDetail.setMaximumSize(new Dimension(800, 270));
		panelDetail2.setMaximumSize(new Dimension(800, 280));

		kontainer.setLayout(new BorderLayout());

		panelReport.setBorder(BorderFactory
				.createTitledBorder("Data Penjualan"));
		panelDetail.setBorder(BorderFactory
				.createTitledBorder("Detail Penjualan"));

		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi = DriverManager.getConnection(Config.URL, Config.username,
					Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		JLabel lblJudul = new JLabel(
				"<HTML><H!>Program Maintenance Data Product</H1></HTML>");
		lblJudul.setForeground(Color.WHITE);

		// panelAtas.setBackground(Color.BLACK);
		// panelAtas.add(lblJudul);

		// Administrator
		menu.add(Administrator);
		Administrator.add(tmbUser);
		Administrator.add(gantiUser);
		Administrator.add(editUser);
		Administrator.add(logout);

		// Master Supplier
		menu.add(mSupp);
		mSupp.add(tmbSupp);
		mSupp.add(mngSupp);
		// mSupp.add(editSupp);
		// mSupp.add(hpsSupp);
		// mSupp.add(lhtSupp);

		// Master Produk
		menu.add(mProduk);
		mProduk.add(tmbProduk);
		mProduk.add(mngProduk);
		mProduk.add(restockProduk);
		setJMenuBar(menu);

		// Master Report
		menu.add(mReport);
		mReport.add(rSupp);
		mReport.add(rProd);
		mReport.add(rSales);

		// Function Tombol Administrator
		btnTambah();
		btnGanti();
		btnLogout();
		btnManage();

		// Function Tombol Supplier
		btnTambahSupp();
		btnManageSupp();

		// Function Tombol Produk
		btnTambahProd();
		btnManageProd();
		btnReStock();
		
		// Function Cari
		cariBarangKode();
		cariBarangNama();

		//Transaksi
		btnTambahB();
		isiKode();
		isiPetugas();
		btnBayar();
		
		// GUI
		lblTitle.setForeground(Color.WHITE);
		panelTitle.setBackground(Color.BLACK);
		panelTitle.add(lblTitle);

		// GUI Penjualan
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 0;
		c.gridy = 0;
		panelReport.add(lblIdTransaksi, c);

		c.gridx = 1;
		c.gridy = 0;
		panelReport.add(lblNoTransaksi, c);

		c.gridx = 2;
		c.gridy = 0;
		panelReport.add(lblNama, c);

		c.gridx = 3;
		c.gridy = 0;
		lblNama2.setForeground(Color.RED);
		panelReport.add(lblNama2, c);
		
		
		
		// Total
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 1;
		lblRp.setForeground(Color.GREEN);
		panelTotal.add(lblRp, c);

		c.gridx = 1;
		c.gridy = 1;
		lblTotal.setForeground(Color.GREEN);
		panelTotal.add(lblTotal, c);
		// panelTotal.add(lblTotal, BorderLayout.NORTH);

		c.gridx = 4;
		c.gridy = 1;
		panelTotal.setPreferredSize(new Dimension(200, 50));
		panelTotal.setBackground(Color.BLACK);
		panelReport.add(panelTotal, c);
		
		c.gridx = 0;
		c.gridy = 1;
		panelReport.add(lblWaktu,c);
		
		c.gridx = 2;
		c.gridy = 1;
		lblTime.start();
		panelReport.add(lblTime,c);
		
		c.gridx = 0;
		c.gridy = 2;
		panelReport.add(lblKode, c);

		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		
		panelReport.add(txtKode, c);

		c.gridx = 3;
		c.gridy = 2;
		panelReport.add(lblNotif, c);

		c.gridx = 0;
		c.gridy = 3;
		panelReport.add(lblNamaB, c);

		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 2;
		panelReport.add(txtNamaB, c);

		c.gridx = 0;
		c.gridy = 4;
		panelReport.add(lblHarga, c);

		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 1;
		txtHarga.setEnabled(false);
		panelReport.add(txtHarga, c);
		
		c.gridx = 0;
		c.gridy = 5;
		panelReport.add(lblJumlah, c);

		c.gridx = 2;
		c.gridy = 5;
		c.gridwidth = 1;
		panelReport.add(txtJumlah, c);

		c.gridx = 2;
		c.gridy = 6;
		panelReport.add(btnTambah, c);

		// Table
		dataModel.addColumn("ID Produk");
		dataModel.addColumn("Nama Produk");
		dataModel.addColumn("Harga");
		dataModel.addColumn("Jumlah");
		dataModel.addColumn("Sub Total");
		dataModel.addColumn("Edit");
		dataModel.addColumn("Hapus");

		/*Date now= new Date();
		SimpleDateFormat fYear=new SimpleDateFormat("yy");
		SimpleDateFormat fDate=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fTime=new SimpleDateFormat("HH:mm:ss");

		String thn=fYear.format(now);
		String tanggal=fDate.format(now);
		String waktu=fTime.format(now);*/
		
		panelDetail.add(scroll);

		btnBayar.setPreferredSize(new Dimension(100,30));
		panelDetail.add(btnBayar);
		

		panelDetail.add(panelDetail2);
		panelTengah.add(panelReport);
		panelTengah.add(panelDetail);
		
		
		
		// panelTengah.setPreferredSize(new Dimension(700,400));
		kontainer.add(panelTitle, BorderLayout.PAGE_START);

		kontainer.add(panelTengah, BorderLayout.CENTER);
		
		
		
		btnBayar.setEnabled(false);
		
		
		
		
		
	}

	public static void main(String[] argx) {
		Utama tst = new Utama("Dahsboard Admin");
		tst.setVisible(true);
	}

	public void isiPetugas() {
		try {
			Class.forName(Config.DATABASE_DRIVER).newInstance();
			koneksi = DriverManager.getConnection(Config.URL, Config.username,
					Config.password);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		try{
			String nama="";
			Statement stmt = koneksi.createStatement();
			ResultSet rs = stmt.executeQuery("select nama from Admin where username = '"+ms.user+"'");
			while(rs.next()){
				nama = rs.getString(1);
			}
			
			lblNama2.setText(nama);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void isiKode(){
		int urut = 1;
		String idCek = lblNoTransaksi.getText();
		String idtTrans = "TRX" + urut;
		String cekId = "";
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
					.executeQuery("SELECT idTrans FROM Transaksi WHERE idTrans = '"
							+ idtTrans + "'");
			while (rs.next()) {
				cekId = rs.getString(1);
			}

			if (cekId.equals("")) {
				lblNoTransaksi.setText(idtTrans);
			}

			else {
				rs = stmt
						.executeQuery("SELECT idTrans FROM Transaksi WHERE idTrans = '"
								+ idCek + "'");
				while (rs.next()) {
					cekId = rs.getString(1);
				}
				if (!cekId.equals("")) {
					boolean benar = false;
					do {
						urut += 1;
						idtTrans = "TRX" + urut;
						rs = stmt
								.executeQuery("SELECT idTrans FROM Transaksi WHERE idTrans = '"
										+ idtTrans + "'");
						while (rs.next()) {
							cekId = rs.getString(1);
						}
						if (cekId.equals(idtTrans)) {
							benar = true;
						} else {
							benar = false;
							lblNoTransaksi.setText(idtTrans);
						}
					} while (benar != false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	// function button Administrator

	public void btnTambah() {
		tmbUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Utama ut = new Utama("Utama");
				TambahUser tmb = new TambahUser(ut, "Tambah User",
						ModalityType.APPLICATION_MODAL);
				tmb.setVisible(true);
				
			}
		});
	}

	public void btnLogout() {

		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Masuk msk = new Masuk(".:: Login ::.");
				msk.setVisible(true);
			}
		});
	}

	public void btnGanti() {
		gantiUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Utama ut = new Utama("");
				Ganti_pass ganti = new Ganti_pass(ut, "Ganti Password",
						ModalityType.APPLICATION_MODAL);
				ganti.setVisible(true);
			}
		});

	}

	public void btnManage() {
		editUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Edit_user edit = new Edit_user("Manage User");
				edit.setVisible(true);

			}
		});

	}

	// function button Supplier
	public void btnTambahSupp() {
		tmbSupp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Tambah_supp tambah = new Tambah_supp("Tambah Supplier");
				tambah.setVisible(true);
				tambah.setLocationRelativeTo(null);
			}
		});
	}

	public void btnManageSupp() {
		mngSupp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Manage_supp mng = new Manage_supp("Manage Supplier");
				mng.setVisible(true);
				mng.setLocationRelativeTo(null);

			}
		});
	}

	// function button Produk
	public void btnTambahProd() {
		tmbProduk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Tambah_prod tmb = new Tambah_prod("Tambah Produk");
				tmb.setVisible(true);
				tmb.setLocationRelativeTo(null);
				
			}
		});
	}

	public void btnManageProd() {
		mngProduk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Manage_prod mg = new Manage_prod("Manage Produk");
				mg.setVisible(true);
				mg.setLocationRelativeTo(null);

			}
		});
	}
	
	public void btnReStock(){
		restockProduk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Utama owner = new Utama("");
				Restock_prod re = new Restock_prod(owner, "Restock Produk", ModalityType.APPLICATION_MODAL);
				re.setVisible(true);
				re.setLocationRelativeTo(null);
			}
		});
	}

	// function pencarian
	public void cariBarangKode() {

		txtKode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(txtKode.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Field Tidak Boleh Kosong", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else{
					cekData(txtKode);
					
				}
				
			}
		});
	}

	public void cariBarangNama() {

		txtNamaB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(txtNamaB.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Field Tidak Boleh Kosong", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else{
				cekData2(txtNamaB);
				}
			}
		});
	}
	
	public void cekData(JTextField text) {

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
			ResultSet rs = stmt.executeQuery("select idProd from Products where idProd like '%"+ text.getText() +"%' ");
			while (rs.next()) {
				cek = rs.getString(1);
			}
			if (cek.equals("")) {
				JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
						"Data Not Found!", JOptionPane.ERROR_MESSAGE);
			} else {
				Utama u = new Utama("Utama");
				Pencarian pc = new Pencarian(u, "Cari Produk");
				pc.setVisible(true);
				pc.setLocationRelativeTo(null);
				pc.setResizable(false);
				cekKlik = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void setTotal(){
		int total=0;
		int a = 0;
		for(int i = table.getRowCount()-1; i > -1 ; i --){
			a = Integer.parseInt(table.getValueAt(i,4).toString());
			total +=a;
			
		}
		lblTotal.setText(""+total);
		
	}
	
	public void cekData2(JTextField text) {

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
			ResultSet rs = stmt.executeQuery("select idProd from Products where nama like '%"+ text.getText() +"%' ");
			while (rs.next()) {
				cek = rs.getString(1);
			}
			if (cek.equals("")) {
				JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!",
						"Data Not Found!", JOptionPane.ERROR_MESSAGE);
			} else {
				Utama u = new Utama("Utama");
				Pencarian pc = new Pencarian(u, "Cari Produk");
				pc.setVisible(true);
				pc.setLocationRelativeTo(null);
				pc.setResizable(false);
				cekKlik = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//Tansaksi

	public void btnBayar(){
		btnBayar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Utama ut = new Utama("");
				Bayar byr = new Bayar(ut, "Bayar Transaksi", ModalityType.APPLICATION_MODAL);
				byr.setVisible(true);
			}
		});
	}
	
	
	public void btnTambahB(){
		
		btnTambah.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if((txtKode.getText().equals("")) && (txtNamaB.getText().equals(""))){
					JOptionPane.showMessageDialog(null, "Field Tidak Boleh Kosong!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				
				else{
					if (txtJumlah.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Field Jumlah Belum Diisi!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
					else{
					int stockA = Integer.parseInt(lblStockA.getText());
					int stockB = Integer.parseInt(txtJumlah.getText());
					
					if (stockB > stockA){
						JOptionPane.showMessageDialog(null, "Stock tidak cukup!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
					else{
						String cek="" ;
						Boolean benar = false;
						for(int i = table.getRowCount()-1; i > -1 ; i--){
							cek = table.getValueAt(i, 0).toString();
							if(txtKode.getText().equals(cek)){
								JOptionPane.showMessageDialog(null, "Gagal Tambah Produk Yang Sama! Silahkan Edit Detail Penjualan", "Error!", JOptionPane.ERROR_MESSAGE);
								i =0;
								benar = false;
								cek ="1";
							}
							else{
							benar = true;
							cek="";
							}
						}
						
						if((benar=true) && (cek.equals(""))){
							
							int subTotal;
							int harga = Integer.parseInt(txtHarga.getText());
							int jumlah = Integer.parseInt(txtJumlah.getText());
							subTotal = harga * jumlah;
							table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
							table.getColumnModel().getColumn(5)
							.setCellEditor(new ButtonEditor(new JCheckBox()));

							// tambah tombol hapus
							table.getColumnModel().getColumn(6)
							.setCellRenderer(new ButtonRenderer());
							table.getColumnModel().getColumn(6)
							.setCellEditor(new ButtonEditor(new JCheckBox()));

							Object [] x = {txtKode.getText(),txtNamaB.getText(), txtHarga.getText(), txtJumlah.getText(), subTotal, "Edit", "Hapus"};
							dataModel.addRow(x);
							setTotal();
							if(table.getRowCount()==0){
								btnBayar.setEnabled(false);
							}
							else{
								btnBayar.setEnabled(true);
							}

						}
						
						else {
							benar = false;
							cek ="";
						}
						
					}
					}
				}
					
		
				
			}
		});
			}

	// Dialog Pencarian
	class Pencarian extends JDialog {
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

		

		public Pencarian(Utama owner, String title) {

			super(owner, title);
			super.setLocationRelativeTo(null);
			super.setSize(500, 470);
			super.setVisible(true);

			dataModel.addColumn("ID");
			dataModel.addColumn("Nama Produk");
			dataModel.addColumn("ID Supplier");
			dataModel.addColumn("Harga");
			dataModel.addColumn("Stock");
			table.setModel(dataModel);

			kontainer = getContentPane();
			kontainer.setLayout(new FlowLayout());
			kontainer.add(scroll);
			
			if(cekKlik=true){
				dp.viewAllByTrans(txtKode, table, dataModel);
			}
			
			else if(cekKlik=false){
				dp.viewAllByTrans2(txtNamaB, table, dataModel);
			}
			
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
						String harga = table.getValueAt(row, 3).toString();
						String stock = table.getValueAt(row, 4).toString();
						
						
						txtKode.setText(id);
						txtNamaB.setText(nama);
						txtHarga.setText(harga);
						lblStockA.setText(stock);
						txtJumlah.grabFocus();
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
				String harga = table.getValueAt(row, 3).toString();
				String stock = table.getValueAt(row, 4).toString();
				
				txtKode.setText(id);
				txtNamaB.setText(nama);
				txtHarga.setText(harga);
				lblStockA.setText(stock);
				txtJumlah.grabFocus();
				
				dispose();
		    }
		
		
		}

	}
	
	//Button Render
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
			idProd = table.getValueAt(row, 0).toString();
			namaProd = table.getValueAt(row, 1).toString();
			hargaProd = table.getValueAt(row, 2).toString();
			jumlahProd = table.getValueAt(row, 3).toString();
			
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
				if(table.getRowCount()==0){
					btnBayar.setEnabled(false);
				}
				else{
					btnBayar.setEnabled(true);
				}
			}
			isDeleteRow = false;
		}

		public void showEdit(int row, int column) {

			if (column == 5) {

				int cekX=0;
				try{
				Statement stmt = koneksi.createStatement();
				ResultSet rs = stmt.executeQuery("select stock from Products where idProd = '"+ table.getValueAt(row, 0) +"'");
				while (rs.next()){
					cekX = rs.getInt(1);
				}
				}
				catch(SQLException e){
					e.printStackTrace();
				}
				Utama ut = new Utama("");
				Final_editProd edit_final = new Final_editProd(ut,
						"Edit Produk " + namaProd, 
						ModalityType.APPLICATION_MODAL, cekX);
				
				
				edit_final.setVisible(true);
				// edit_final.setLocationRelativeTo(null);

			} else if (column == 6) {
				int pilih = JOptionPane.showConfirmDialog(null,
						"Yakin ingin menghapus " + namaProd + " ?",
						"Delete Supplier?", JOptionPane.YES_NO_OPTION);
				if (pilih == 0) {
					try {
						
						isDeleteRow = true;
						int total = Integer.parseInt(lblTotal.getText());
						int min = Integer.parseInt(table.getValueAt(row, 4).toString());
						
						lblTotal.setText(""+ (total-min));
						JOptionPane.showMessageDialog(null,
								"Produk telah dihapus!", "Product Deleted!",
								JOptionPane.INFORMATION_MESSAGE);

						
					} catch (Exception e) {
						// updateTable();
					}
				}
			}
		}



	}
	
	public class Final_editProd extends JDialog {
		private Connection koneksi;
		private dbSupplier sp = new dbSupplier();
		Manage_supp mg = new Manage_supp("");
		private int row = mg.rowx;
		private String password;
		private JLabel lblID = new JLabel("ID Produk :");
		private JLabel lblNo = new JLabel("No");

		private JLabel lblNama = new JLabel("Nama :");
		private JTextField txtNama = new JTextField(15);

		private JLabel lblharga = new JLabel("Harga :");
		private JTextField txtHarga = new JTextField();

		private JLabel lblStock = new JLabel("Stock :");
		private JTextField txtStock = new JTextField();
		
		private JLabel lblJumlah = new JLabel("Jumlah :");
		private JTextField txtJumlah = new JTextField();

		private JButton btnEdit = new JButton("Edit");
		private JButton btnBatal = new JButton("Batal");

		private JPanel panelEdit = new JPanel();
		Container kontainer = getContentPane();
		
		public Final_editProd(Utama mg, String title,
				ModalityType modal, int cekZ) {
			super(mg, title, modal);
			super.setSize(400, 320);
			// super.setVisible(true);
			super.setLocationRelativeTo(null);
			super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			panelEdit.setVisible(true);
			panelEdit.setLayout(new GridBagLayout());
			kontainer.setLayout(new FlowLayout());
			GridBagConstraints c = new GridBagConstraints();
			panelEdit.setBorder(BorderFactory.createTitledBorder("Edit"));

		

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
			txtNama.setEnabled(false);
			panelEdit.add(txtNama, c);

			c.gridx = 0;
			c.gridy = 2;
			panelEdit.add(lblHarga, c);

			c.gridx = 1;
			c.gridy = 2;
			txtHarga.setEnabled(false);
			panelEdit.add(txtHarga, c);

			c.gridx = 0;
			c.gridy = 3;
			panelEdit.add(lblStock, c);

			c.gridx = 1;
			c.gridy = 3;
			txtStock.setEnabled(false);
			panelEdit.add(txtStock, c);
			
			c.gridx = 0;
			c.gridy = 4;
			panelEdit.add(lblJumlah, c);

			c.gridx = 1;
			c.gridy = 4;
			panelEdit.add(txtJumlah, c);

			c.gridx = 0;
			c.gridy = 5;
			btnEdit.setPreferredSize(new Dimension(150, 25));
			panelEdit.add(btnEdit, c);

			c.gridx = 1;
			c.gridy = 5;
			panelEdit.add(btnBatal, c);
			
			kontainer.add(panelEdit);
			
				
				lblNo.setText(table.getValueAt(row, 0).toString());
				txtNama.setText(table.getValueAt(row, 1).toString());
				txtHarga.setText(table.getValueAt(row, 2).toString());
				txtStock.setText(""+ cekZ);
				
		
			
			isiData();
			btnBatal();
			btnSimpan();

		}

		/*
		 * public static void main(String[] args) { // TODO Auto-generated
		 * method stub Final_edit f_edit = new Final_edit("Edit Supplier","");
		 * f_edit.setVisible(true); f_edit.setLocationRelativeTo(null);
		 * 
		 * }
		 */

		public void btnBatal() {
			btnBatal.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					dispose();
				}
			});
		}
		
		public void isiData(){
		
			
		}

		public void btnSimpan() {
			btnEdit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//dp.editSupp(lblNo, txtNama, txtAlamat, txtTelp, table, rowx);
					if(txtJumlah.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Jumlah Tidak Boleh Kosong!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else{
						try {
							Class.forName(Config.DATABASE_DRIVER).newInstance();
							koneksi = DriverManager.getConnection(Config.URL, Config.username,
									Config.password);
						} catch (InstantiationException | IllegalAccessException
								| ClassNotFoundException | SQLException z) {
							// TODO Auto-generated catch block
							z.printStackTrace();
						}
						
						try{
							int cek=0;
							Statement stmt = koneksi.createStatement();
							ResultSet rs = stmt.executeQuery("select stock from Products where idProd = '"+ lblNo.getText() +"'");
							while (rs.next()){
								cek = rs.getInt(1);
							}
							
							int cekB = Integer.parseInt(txtJumlah.getText());
							
							if(cekB > cek){
								JOptionPane.showMessageDialog(null, "Stock Tidak Cukup!", "Error", JOptionPane.ERROR_MESSAGE);
							}
							
							else{
								table.setValueAt(txtJumlah.getText(), rowx, 3);
								int Harga = Integer.parseInt(table.getValueAt(rowx, 2).toString());
								int jumlah = Integer.parseInt(txtJumlah.getText());
								int total = Harga * jumlah;
								table.setValueAt(total, rowx, 4);
								setTotal();
								JOptionPane.showMessageDialog(null, "Berhasil Diupdate!", "Update Succes!", JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}
							
						}
						catch(SQLException x){
							x.printStackTrace();
						}
						
					}
			
					
				}
			});
		}
	}

	// Dialog Barang
	class Bayar extends JDialog {
		private JPanel panelDialog = new JPanel();
		private Container kontainer = new Container();
		
		private JLabel lblIdTrans = new JLabel("ID Transaksi");
		private JLabel lblIdTrans2 = new JLabel("");
		
		private JLabel lblNama = new JLabel("Nama Petugas");
		private JLabel lblNama2 = new JLabel("");
		
		private JLabel lblTgl = new JLabel("Tanggal");
		private JLabel lblTgl2 = new JLabel("");
		
		private JLabel lblJam = new JLabel("Jam");
		private JLabel lblJam2 = new JLabel("");
		
		private JLabel lblTotal = new JLabel("Grand Total");
		private JLabel lblTotal2 = new JLabel("");
		
		private JLabel lblBayar = new JLabel("Masukkan Uang");
		private JTextField txtBayar = new JTextField(15);
		
		
		private DefaultTableModel dataModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		private JTable table = new JTable(dataModel);
		private JScrollPane scroll = new JScrollPane(table);

		private JPanel panelTransaksi = new JPanel();
		private JPanel panelDetailT = new JPanel();
		private Container kontainer2;
		

		public Bayar(Utama owner, String title, ModalityType modal) {
			
			super(owner, title, modal);
			super.setSize(500, 550);
			super.setLocationRelativeTo(null);
			super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			super.setResizable(false);
			//super.setVisible(true);

			
			panelTransaksi.setLayout(new GridBagLayout());
			panelDialog.setLayout(new GridBagLayout());
			panelDetailT.setLayout(new FlowLayout());
			kontainer2= getContentPane();
			kontainer2.setLayout(new FlowLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			panelTransaksi.setBorder(BorderFactory.createTitledBorder("Transaksi"));
			
			dataModel.addColumn("ID");
			dataModel.addColumn("Nama Produk");
			dataModel.addColumn("Harga");
			dataModel.addColumn("Jumlah");
			dataModel.addColumn("Subtotal");
			table.setModel(dataModel);

			Date now= new Date();
			SimpleDateFormat fYear=new SimpleDateFormat("yy");
			SimpleDateFormat fDate=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat fTime=new SimpleDateFormat("HH:mm:ss");

			String tanggal=fDate.format(now);
			lblTgl2.setText(tanggal);
			
			String waktu=fTime.format(now);
			lblJam2.setText(waktu);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(10,10,10,10);
			c.gridx = 0;
			c.gridy = 0;
			panelTransaksi.add(lblIdTrans,c);
			
			c.gridx = 1;
			c.gridy = 0;
			panelTransaksi.add(lblIdTrans2,c);
			
			c.gridx = 0;
			c.gridy = 1;
			panelTransaksi.add(lblNama,c);
			
			c.gridx = 2;
			c.gridy = 1;
			panelTransaksi.add(lblNama2,c);
			
			c.gridx = 0;
			c.gridy = 2;
			panelTransaksi.add(lblTgl,c);
			
			c.gridx = 1;
			c.gridy = 2;
			panelTransaksi.add(lblTgl2,c);
			
			c.gridx = 2;
			c.gridy = 2;
			panelTransaksi.add(lblJam2,c);
			
			scroll.setMaximumSize(new Dimension(470,100));
			scroll.setPreferredSize(new Dimension(470,200));
			
			panelDetailT.add(scroll);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(10,10,10,10);
			c.gridx = 0;
			c.gridy = 0;
			panelDialog.add(lblTotal,c);
			
			c.gridx = 1;
			c.gridy = 0;
			panelDialog.add(lblTotal2,c);
			
			c.gridx = 0;
			c.gridy = 1;
			panelDialog.add(lblBayar,c);
			
			c.gridx = 1;
			c.gridy = 1;
			panelDialog.add(txtBayar,c);
			
			
			kontainer2.add(panelTransaksi);
			kontainer2.add(panelDetailT);
			kontainer2.add(panelDialog);
			
			
			
			
			
		}

	}
	
	//Time
	
	public class JLabelTime extends JLabel implements ActionListener {
		 
	    private String pattern = "dd MMMM yyyy, HH:m:ss";
	    private SimpleDateFormat format;
	    private Timer timer;
	    private Date date;
	 
	    public JLabelTime() {
	        timer = new Timer(1000, this);
	        format = new SimpleDateFormat(pattern);
	        date = new Date();
	    }
	 
	    public String getPattern() {
	        return pattern;
	    }
	    
	    public Date getDate(){
	    	return date;
	    }
	 
	    public void setPattern(String pattern) {
	        this.pattern = pattern;
	        format = new SimpleDateFormat(pattern);
	    }
	 
	    public void start() {
	        timer.start();
	    }
	 
	    public void stop() {
	        timer.stop();
	    }
	 
	    public void actionPerformed(ActionEvent e) {
	        date.setTime(System.currentTimeMillis());
	        setText(format.format(date));
	    }
	}

}
