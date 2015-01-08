package fungsi;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class Pencarian extends JDialog {
	private JPanel panelDialog = new JPanel();
	private Container kontainer = new Container();
	private static JFrame a;
	
	private DefaultTableModel dataModel = new DefaultTableModel(){
		@Override
		public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		private JTable table = new JTable(dataModel);
		private JScrollPane scroll = new JScrollPane(table);
		
		private String k;
		
		public Pencarian(JFrame frame){
			super(frame, "Data Supplier",true);
			super.setResizable(false);
			super.setLocationRelativeTo(null);
			
			dataModel.addColumn("ID");
			dataModel.addColumn("Nama");
			dataModel.addColumn("Alamat");
			dataModel.addColumn("Telp");
			table.setModel(dataModel);
			
			kontainer = getContentPane();
			kontainer.setLayout(new FlowLayout());
			
			kontainer.add(scroll);
		}
		
		public static void main(String [] argx){
			Pencarian pc = new Pencarian(a);
			pc.setVisible(true);
			
		}
}
