/**
 * com.mckoi.tools.JDBCQueryTool  18 Aug 2000
 *
 * Mckoi SQL Database ( http://www.mckoi.com/database )
 * Copyright (C) 2000, 2001  Diehl and Associates, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Change Log:
 *
 * Tree control by Christophe NIGAUD amendments in place.
 *
 * Mike Calder-Smith October 2001.  Added command history and Table,
 * column query/paste dialogs.
 *
 */
package com.mckoi.tools;


import com.mckoi.jfccontrols.ResultSetTableModel;
import com.mckoi.jfccontrols.QueryAgent;
import com.mckoi.jfccontrols.Query;
import com.mckoi.util.CommandLine;
import java.sql.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.DefaultListModel;

/**
 * An SQL query tool that allows for queries to be executed to a JDBC driver.
 *
 * @author Tobias Downer
 * @author Christophe Nigaud (Treeview - atrap@club-internet.fr)
 *
 */

public class JDBCQueryTool extends JComponent {

  /**
   * The agent used to make queries on the JDBC connection.
   */
  private QueryAgent query_agent;

  /**
   * The JTextArea where the query is entered.
   */
  private JTextArea query_text_area;

  /**
   * The JTable where the query result is printed.
   */
  private JTable result_table;

  /**
   * The ResultSetTableModel for the table model that contains our result set.
   */
  private ResultSetTableModel table_model;

  /**
   * The JLabel status bar at the bottom of the window.
   */
  private JLabel status_text;

  /**
   * Set to true if the table is auto resize (default).
   */
  JCheckBoxMenuItem auto_resize_result_table;

  /**
   * Set autocommit database option.
   */
  JCheckBoxMenuItem auto_commit;

  /**
   * Total number of rows in the result.
   */
  private int total_row_count;

  /**
   * The time it took to execute the query in milliseconds.
   */
  private int query_time = -1;

  /**
   * The tree view to display db structure
   */
  private JTree dbTree = null;

  /**
   * Hashtable to get association between treenode & dbItem.
   */
  private Hashtable dataHTree = new Hashtable();


  // -- Start Command Recall modification
  // -- Author Mike Calder October 2001

  /**
  Command stack
  */
  DefaultListModel cmdstack;

  /**
  Command List
  */
  JList commandlist;

  /**
  Current query text
  */
  String qtext;

  /**
  Last query text
  */
  String lastqtext;

  // Various entities made global from their routines so they can be
  // referenced from inner classes and such.

  // The execute and cancel query button
  final JButton execute = new JButton("Run Query");
  final JButton stop = new JButton("Stop Query");
  static final JFrame frame = new JFrame("Mckoi JDBC Query Tool");
  static JDesktopPane desktop;

  static JMenuItem paste_table;

  JList tablelist;
  JList columnlist;

  // -- End Command Recall modification

  /**
   * Inner class for Tables
   */
  public class DBItem{
    int typeOfItem;
    String item_name;
    String schema;
    String sql;

    public DBItem(int typeOfItem, String schema, String name){
      this.typeOfItem = typeOfItem;
      this.schema = schema; this.item_name = name;
      switch(typeOfItem){
        case 0:
          sql = "show "+schema;
          break;
        case 1:
          sql = "select * from "+schema + "." + item_name;
      }
    }
  }

  /**
   * Constructs the JComponent.
   */
  public JDBCQueryTool(QueryAgent in_query_agent) {
    this.query_agent = in_query_agent;

    // --- Layout ---
    // Toggle auto result columns.
    auto_resize_result_table = new JCheckBoxMenuItem("Auto Resize Columns");
    auto_resize_result_table.setSelected(true);
    auto_resize_result_table.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        if (auto_resize_result_table.isSelected()) {
          result_table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);}
        else {
          result_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
      }
    });

    auto_commit = new JCheckBoxMenuItem("Autocommit");
    auto_commit.setSelected(true);
    auto_commit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        try{
          if (auto_commit.isSelected()){
            query_agent.executeQuery(new Query("SET AUTO COMMIT ON"));
          } else {
            query_agent.executeQuery(new Query("SET AUTO COMMIT OFF"));
          }
        } catch (Exception e){e.printStackTrace();}
      }
    });

    paste_table = new JMenuItem("Table Names");
    paste_table.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        tablenameBox();
      }
    });

    // Main window
    setLayout(new BorderLayout());
    setBorder(new EmptyBorder(2, 2, 2, 2));
    JPanel query_area = new JPanel();
    query_area.setLayout(new BorderLayout());

    // Mono-space font.
    Font mono_font = new Font("MonoSpaced", Font.PLAIN, 12);

    // The query text area
    query_text_area = new JTextArea(7, 80);
    query_text_area.setFont(mono_font);

    // The tree view
    DefaultMutableTreeNode topnode =  new DefaultMutableTreeNode("Database");
    dbTree = new JTree(topnode);
    dbTree.setEditable(false);
    dbTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    dbTree.setShowsRootHandles(true);
    JScrollPane scroll_dbTree = new JScrollPane(dbTree);
    scroll_dbTree.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scroll_dbTree.setViewportBorder(BorderFactory.createEtchedBorder());
    dbTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        evndbTreeSelectionChanged(e);
      }
    });

    // Fill the treeview
    DefaultMutableTreeNode schemanode;
    DefaultMutableTreeNode tablenode;
    try{
      ResultSet listofSchema = query_agent.executeQuery(new Query("show SCHEMA"));

      ResultSet listofTables;
      String schemaname;
      String tablename;

      while(listofSchema.next()){
        // for each schema...

        // schemaname = listofSchema.getString("schema_name");
        schemaname = listofSchema.getString(1);
        schemanode = new DefaultMutableTreeNode( schemaname );

        topnode.add(schemanode);
        dataHTree.put(schemanode, new DBItem(0, schemaname, null));
        query_agent.executeQuery(new Query("SET SCHEMA " + schemaname ));
        listofTables = query_agent.executeQuery(new Query("show tables"));
        while(listofTables.next()){
          // for each table of schema...

          // tablename = listofTables.getString("table_name");
          tablename = listofTables.getString(1);
          tablenode = new DefaultMutableTreeNode(listofTables.getString("table_name"));

          schemanode.add(tablenode);
          dataHTree.put(tablenode, new DBItem(1, schemaname, tablename));
        }
        listofTables.close();
      }
      listofSchema.close();
    } catch (Exception e){
      // System.out.println( "Exception " + e );
    }

    //
    JSplitPane northsplit_pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    northsplit_pane.setLeftComponent(scroll_dbTree);
    // replaced immediately below        northsplit_pane.setRightComponent(query_area);



    // -- Start Command Recall modification
    // -- Author Mike Calder October 2001

    JPanel stack_area = new JPanel();
    stack_area.setLayout(new BorderLayout());

    JSplitPane leftsplit_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    leftsplit_pane.setBottomComponent(query_area);
    leftsplit_pane.setTopComponent(stack_area);

    northsplit_pane.setRightComponent(leftsplit_pane);

    cmdstack = new DefaultListModel();
    commandlist = new JList( cmdstack );
    JScrollPane cmdpane = new JScrollPane( commandlist );
    stack_area.add( cmdpane, BorderLayout.CENTER );

    commandlist.addListSelectionListener( new ListSelectionListener() {
      public void valueChanged( ListSelectionEvent lse ) {
        if (lse.getValueIsAdjusting()) {
          String selectedcmd;
          selectedcmd = (String)commandlist.getSelectedValue();
          qtext = selectedcmd;
          lastqtext = selectedcmd;
          query_text_area.setText( qtext );
        }
      }
    });


    // -- End Command Recall modification



    stop.setEnabled(false);
    JPanel button_bar = new JPanel();
    button_bar.setLayout(new FlowLayout());
    button_bar.add(execute);
    button_bar.add(stop);

    // The query area
    query_area.add(new JScrollPane(query_text_area), BorderLayout.CENTER);
    query_area.add(button_bar, BorderLayout.SOUTH);

    table_model = new ResultSetTableModel();
    result_table = new JTable(table_model);
    JScrollPane scrolly_result_table = new JScrollPane(result_table);
    scrolly_result_table.setPreferredSize(new Dimension(650, 550));

    // The status bar.
    status_text = new JLabel("  ");
    status_text.setFont(mono_font);
    status_text.setBorder(new BevelBorder(BevelBorder.LOWERED));

    // Make the split pane
    JSplitPane split_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    split_pane.setTopComponent(northsplit_pane);
    split_pane.setBottomComponent(scrolly_result_table);
    add(split_pane, BorderLayout.CENTER);
    add(status_text, BorderLayout.SOUTH);

    // --- Actions ---
    execute.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        qtext = query_text_area.getText();
        performcommand();
      }
    });

    stop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        query_agent.cancelQuery();
      }
    });

  }

  /**
  */
  private void performcommand() {

    try {

      //  repaint();
      stop.setEnabled(true);
      execute.setEnabled(false);

      table_model.clear();

      // replaced immediately below   Query query = new Query(query_text_area.getText());

      // -- Start Command Recall modification
      // -- Author Mike Calder October 2001

      Query query = new Query( qtext );

      if ( !qtext.equals( lastqtext ) ) {
        cmdstack.addElement( qtext );
      }
      lastqtext = qtext;

      // -- End Command Recall modification

      long time_start = System.currentTimeMillis();
      ResultSet result_set = query_agent.executeQuery(query);
      query_time = (int) (System.currentTimeMillis() - time_start);

      table_model.updateResultSet(result_set);

      total_row_count = 0;
      if (result_set.last()) {
        total_row_count = result_set.getRow();
      }
      updateStatus();

    }
    catch (SQLException e) {
      JOptionPane.showMessageDialog(JDBCQueryTool.this,
                  e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
      e.printStackTrace();
    }
    catch (InterruptedException e) {
      System.out.println("Query cancelled.");
    }
    stop.setEnabled(false);
    execute.setEnabled(true);
  }


  /**
   *
   */
  private void evndbTreeSelectionChanged(TreeSelectionEvent e) {
    DefaultMutableTreeNode selectednode = (DefaultMutableTreeNode)dbTree.getLastSelectedPathComponent();
    if (!selectednode.equals(null) && dataHTree.containsKey(selectednode))
      query_text_area.setText(((DBItem)dataHTree.get(selectednode)).sql);
  }


  /**
   * Updates the status bar.
   */
  private void updateStatus() {
    StringBuffer buf = new StringBuffer();
    buf.append("Query Time: ");
    buf.append((double) query_time / 1000.0);
    buf.append(" seconds.  Row Count: ");
    buf.append(total_row_count);
    status_text.setText(new String(buf));
  }

  // ----- Static methods -----

  /**
   * The JDBC Connection we have established to the server.
   */
  private static Connection connection;

  /**
   * The number of query windows we have open.
   */
  private static int query_window_count = 0;

  /**
   * Creates a new JDBC Query window.
   */
  private static void createNewWindow() {
    // Increment the count of windows open.
    ++query_window_count;

    // The QueryAgent for this frame.
    final QueryAgent query_agent = new QueryAgent(connection);
    // Make the window,
    try{
      DatabaseMetaData dbInfo = connection.getMetaData();
      frame.setTitle(frame.getTitle() + " [" + dbInfo.getUserName() + "@" + dbInfo.getDatabaseProductName() + "]");
    } catch (Exception e){}

    // The action to close this window,
    final Action close_action = new AbstractAction("Exit") {
      public void actionPerformed(ActionEvent evt) {
        frame.dispose();
        System.gc();
        // Decrement the count of windows open.
        --query_window_count;
        //
        if (query_window_count == 0) {
          try {
            connection.close();
          } catch (SQLException e) {System.err.println("SQL Exception on close: " + e.getMessage());}
          System.exit(0);
        }
      }
    };

    // --- The layout ---

    desktop = new JDesktopPane();
    frame.getContentPane().add(desktop);

    // Container c = frame.getContentPane();
    // c.setLayout(new BorderLayout());
    desktop.setLayout(new BorderLayout());
    JDBCQueryTool query_tool = new JDBCQueryTool(query_agent);
    // c.add(query_tool, BorderLayout.CENTER);
    desktop.add(query_tool, BorderLayout.CENTER);

    // Set the menu bar for the window.
    JMenuBar menu_bar = new JMenuBar();
    JMenu file = new JMenu("File");
    file.add(clone_window);
    file.addSeparator();
    file.add(close_action);
    menu_bar.add(file);

    JMenu paste = new JMenu("List");
    paste.add( paste_table );
    menu_bar.add( paste );

    JMenu options = new JMenu("Options");
    options.add(query_tool.auto_resize_result_table);
    options.add(query_tool.auto_commit);
    menu_bar.add(options);
    frame.setJMenuBar(menu_bar);

    // Pack and show the window.
    frame.pack();
    frame.show();

    // If frame is closed then perform the close action.
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        close_action.actionPerformed(null);
      }
    });
  }


  /**
   * An Action that clones a new query window.
   */
  private static Action clone_window = new AbstractAction("New Window") {
    public void actionPerformed(ActionEvent evt) {
      createNewWindow();
    }
  };


  /**
   * Prints the syntax to System.out.
   */
  private static void printSyntax() {
    System.out.println("JDBCQueryTool [-jdbc JDBC_Driver_Class] [-url JDBC_URL] -u username -p password");
  }


  /**
   * Application start point.
   */
  public static void main(String[] args) {
    CommandLine cl = new CommandLine(args);

    String driver = cl.switchArgument("-jdbc", "com.mckoi.JDBCDriver");
    String url = cl.switchArgument("-url", ":jdbc:mckoi:");
    String username = cl.switchArgument("-u");
    String password = cl.switchArgument("-p");

    if (username == null) {
      System.out.println("Please provide a username");
      System.out.println();
      printSyntax();

    } else if (password == null) {
      System.out.println("Please provide a password");
      System.out.println();
      printSyntax();

    } else {
      try {
        System.out.println("Using JDBC Driver: " + driver);

        // Register the driver.
        Class.forName(driver).newInstance();

        // Make a connection to the server.
        connection = DriverManager.getConnection(url, username, password);

        System.out.println("Connection established to: " + url);

//        // Register the connection with the QueryAgent
//        QueryAgent.initAgent(connection);

        // Invoke the tool on the swing event dispatch thread.
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            createNewWindow();
          }
        });
      } catch (ClassNotFoundException e) {System.out.println("JDBC Driver not found.");}
        catch (Exception e) {e.printStackTrace();}
    } // en of '} else {'
  }

  /**
  Box to list Tablenames
  */
  private void tablenameBox() {
    String schemaname;
    DefaultListModel tablenames;
    JPanel jp;

  try {

    schemaname = "APP";         // default
    tablenames = getTableNames(schemaname);

    jp = new JPanel();
    jp.setLayout( new BorderLayout() );
    tablelist = new JList( tablenames );
    JScrollPane js = new JScrollPane( tablelist );
    js.setPreferredSize( new Dimension(150, 300) );
    jp.add( js, BorderLayout.CENTER );

    JPanel jp2 = new JPanel();
    jp2.setLayout( new BorderLayout() );
    JButton paste = new JButton( "Paste");
    paste.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent e ) {

        qtext = (String)tablelist.getSelectedValue();
        query_text_area.replaceSelection( qtext );
      }
    });

    JButton cols = new JButton( "Columns");
    cols.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent e ) {
        columnnameBox( (String)tablelist.getSelectedValue() );
      }
    });

    jp2.add( paste, BorderLayout.EAST );
    jp2.add( cols, BorderLayout.WEST );

    jp.add( jp2, BorderLayout.SOUTH );

    JOptionPane.showInternalMessageDialog( frame.getContentPane(), jp, "Table List for " + schemaname,
                     JOptionPane.PLAIN_MESSAGE);

  } catch(Exception e) {
    try {
      PrintWriter ps = new PrintWriter( new FileOutputStream( "test.log", true ), true );
      e.printStackTrace( ps );
      ps.close();
    } catch(Exception e2) {}
  }

  }

  /**
  Get list of table names for schema
  */
  private DefaultListModel getTableNames( String schemaname ) {
    ResultSet listofTables;
    DefaultListModel tnames;

    tnames = new DefaultListModel();

    try {
      query_agent.executeQuery(new Query("SET SCHEMA " + schemaname ));
      listofTables = query_agent.executeQuery(new Query("show tables"));
      while(listofTables.next()){

        tnames.addElement( listofTables.getString(1) );
      }
      listofTables.close();
    } catch(Exception e) {
    }

    return tnames;
  }

  /**
  Box to list Column names
  */
  private void columnnameBox(String tablename) {
    DefaultListModel columnnames;
    JPanel jp;

    columnnames = getColumnNames(tablename);

    jp = new JPanel();
    jp.setLayout( new BorderLayout() );
    columnlist = new JList( columnnames );
    JScrollPane js = new JScrollPane( columnlist );
    js.setPreferredSize( new Dimension(150, 300) );
    jp.add( js, BorderLayout.CENTER );

    JPanel jp2 = new JPanel();
    jp2.setLayout( new BorderLayout() );
    JButton paste = new JButton( "Paste");
    paste.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent e ) {

        qtext = (String)columnlist.getSelectedValue();
        qtext = qtext.substring(0, qtext.indexOf( '(' ));
        query_text_area.replaceSelection( qtext );

      }
    });
    jp.add( paste, BorderLayout.SOUTH );


    JOptionPane.showInternalMessageDialog( frame.getContentPane(), jp, "Column List for Table " + tablename,
                     JOptionPane.PLAIN_MESSAGE);
  }

  /**
  Get list of column names for table
  */
  private DefaultListModel getColumnNames( String tablename ) {
    ResultSet lcols;
    DefaultListModel tcols;

    tcols = new DefaultListModel();

    try {
      Query qry = new Query("select \"column\", type_desc from SYS_INFO.sUSRTableColumns where \"table\"='" + tablename + "'" );
      lcols = query_agent.executeQuery( qry );
      while(lcols.next()){

        tcols.addElement( lcols.getString(1) + " (" + lcols.getString(2) + ")" );
      }
      lcols.close();
    } catch(Exception e) {
    }

    return tcols;
  }

}
