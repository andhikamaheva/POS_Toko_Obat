package tampilan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import tampilan.Manage_supp.ButtonEditor;
import tampilan.Manage_supp.ButtonRenderer;
import tampilan.Manage_supp.Final_editSupp;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Report extends JDialog {

	private dbTransaksi trans = new dbTransaksi();
	private dbDTransaksi dTrans = new dbDTransaksi();
	private dbAdmin da = new dbAdmin();
	private dbProducts dp = new dbProducts();
	
	private JPanel panelCari = new JPanel();
	private JPanel panelKeluaran = new JPanel();
	private Container kontainer;
	
	private JLabel lblCari = new JLabel("Cari Berdasar");
	private String [] x = {"ID Transaksi", "Nama Petugas", "Tanggal"};
	private JComboBox cboKategori = new JComboBox(x);
	private JTextField txtCari = new JTextField(10);
	private JButton btnCari = new JButton("Cari");
	
	private JLabel lblNotif = new JLabel("Format tanggal masukan : yyyy - mm - dd");
	
	public String idTrans="";
	public int rowx;
	public int columnx;

	private DefaultTableModel dataModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			if(column >=4){
				return true;
			}
			else{
			return false;
			}
		}
	};
	private JTable table = new JTable(dataModel);
	private JScrollPane scroll = new JScrollPane(table);
	
		public Report(Utama owner, String title, ModalityType modal) {
		
		super(owner, title, modal);
		super.setSize(500, 370);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setResizable(false);
		//super.setVisible(true);

		
		panelCari.setLayout(new GridBagLayout());
		panelKeluaran.setLayout(new FlowLayout());
		
		kontainer= getContentPane();
		kontainer.setLayout(new FlowLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		panelCari.setBorder(BorderFactory.createTitledBorder("Cari Transaksi"));
		
		dataModel.addColumn("ID Transaksi");
		dataModel.addColumn("Nama Petugas");
		dataModel.addColumn("Tanggal");
		dataModel.addColumn("Jam");
		dataModel.addColumn("View");
		table.setModel(dataModel);

		Date now= new Date();
		SimpleDateFormat fYear=new SimpleDateFormat("yy");
		SimpleDateFormat fDate=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fTime=new SimpleDateFormat("HH:mm:ss");

		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		panelCari.add(lblCari,c);
		
		c.gridx = 1;
		c.gridy = 0;
		//lblIdTrans2.setText(id.getText());
		panelCari.add(cboKategori,c);
		
		c.gridx = 2;
		c.gridy = 0;
		panelCari.add(txtCari,c);
		
		c.gridx = 3;
		c.gridy = 0;
		panelCari.add(btnCari,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		lblNotif.setVisible(false);
		panelCari.add(lblNotif,c);
		
		
		scroll.setMaximumSize(new Dimension(470,100));
		scroll.setPreferredSize(new Dimension(470,200));
		
		table.getColumnModel().getColumn(4)
		.setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(4)
		.setCellEditor(new ButtonEditor(new JCheckBox()));
		
		panelKeluaran.add(scroll);
		
		kontainer.add(panelCari);
		kontainer.add(panelKeluaran);
		
		selectKategori();
		btnCari();
	}
		
		public void selectKategori(){
			cboKategori.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					// TODO Auto-generated method stub
					if(cboKategori.getSelectedIndex()==0){
						lblNotif.setVisible(false);
						
					}
					else if (cboKategori.getSelectedIndex()==1){
						lblNotif.setVisible(false);
					}
					else{
						lblNotif.setVisible(true);
						
					}
					
				}
			});
		
		}
		
		public void btnCari(){
			btnCari.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					String kategori ="";
					if(cboKategori.getSelectedIndex()==0){
						kategori = "idTrans";
					}
					else if (cboKategori.getSelectedIndex()==1){
						kategori = "namaKasir";
					}
					else{
						kategori = "tgl";
					}
					trans.cariBy(kategori, table, dataModel, txtCari);
					kategori="";
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
				idTrans = table.getValueAt(row, 0).toString();
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

				if (column == 4) {
					Utama ut = new Utama("");
					Detail dt = new Detail(ut, "Detail Transaksi", ModalityType.APPLICATION_MODAL, table, dataModel);
					dt.setVisible(true);
			

				}
			}
		}
		
		class Detail extends JDialog {
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
			public JLabel lblTotal2 = new JLabel("");

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
			
			private JButton btnClose = new JButton("Close");
			

			public Detail(Utama owner, String title, ModalityType modal, JTable table1, DefaultTableModel dataModelx) {
				
				super(owner, title, modal);
				super.setSize(500, 420);
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
				
				c.gridx = 1;
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
				panelTransaksi.add(lblJam,c);
				
				c.gridx = 3;
				c.gridy = 2;
				panelTransaksi.add(lblJam2,c);
				
				scroll.setMaximumSize(new Dimension(470,100));
				scroll.setPreferredSize(new Dimension(470,200));
				
				panelDetailT.add(scroll);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(10,10,10,10);
				c.gridx = 2;
				c.gridy = 0;
				panelDialog.add(lblTotal,c);
				
				c.gridx = 3;
				c.gridy = 0;
				panelDialog.add(lblTotal2,c);
				
			
				
				
				kontainer2.add(panelTransaksi);
				kontainer2.add(panelDetailT);
				kontainer2.add(panelDialog);
				
				setLabel(table1);
				//setTable(table1, dataModel);
				btnClose();
				setTable(table1, dataModel);
				
			}
			
			public void setLabel(JTable table){
				lblIdTrans2.setText(table.getValueAt(rowx, 0).toString());
				lblNama2.setText(table.getValueAt(rowx, 1).toString());
				lblTgl2.setText(table.getValueAt(rowx, 2).toString());
				lblJam.setText(table.getValueAt(rowx, 3).toString());
			}
			
		public void setTable(JTable table1,  DefaultTableModel dataModelz){
				dTrans.tampilDetail( idTrans, dataModelz, lblTotal2, table1);
				}
				

		
			
			public void btnClose(){
				btnClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
					dispose();
					}
				});
			}
			
			
		}
		
	
}
