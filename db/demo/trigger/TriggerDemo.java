
/**
 * A demonstation of using triggers in a database application.
 */

import java.sql.*;
import com.mckoi.database.jdbc.MckoiConnection;
import com.mckoi.database.jdbc.TriggerListener;

public class TriggerDemo {

  public static void main(String[] args) {

    System.out.println();

    // Register the Mckoi JDBC Driver
    try {
      Class.forName("com.mckoi.JDBCDriver").newInstance();
    }
    catch (Exception e) {
      System.out.println(
	 "Unable to register the JDBC Driver.\n" +
	 "Make sure the classpath is correct.\n" +
	 "For example on Win32;  java -cp ../../mkoidb.jar;. TriggerDemo\n" +
	 "On Unix;  java -cp ../../mckoidb.jar:. TriggerDemo");
      return;
    }

    // This URL specifies we are creating a local database.  The
    // configuration file for the database is found at './ExampleDB.conf'
    // The 'create=true' argument means we want to create the database.  If
    // the database already exists, it can not be created.
    String url = "jdbc:mckoi:local://ExampleDB.conf?create=true";

    // The username/password for the database.  This will be the username/
    // password for the user that has full control over the database.
    // ( Don't use this demo username/password in your application! )
    String username = "user";
    String password = "pass1212";

    // Make a connection with the database.  This will create the database
    // and log into the newly created database.
    Connection connection;
    try {
      connection = DriverManager.getConnection(url, username, password);
    }
    catch (SQLException e) {
      System.out.println(
	 "Unable to create the database.\n" +
	 "The reason: " + e.getMessage());
      return;
    }

    // --- Wrap the java.sql.Connection object around the Mckoi connection
    //     extention.  ---
    MckoiConnection mckoi_connection = new MckoiConnection(connection);

    try {
      // Create a Statement object to execute the queries on,
      Statement statement = connection.createStatement();
      ResultSet result;

      System.out.println("-- Creating Tables --");

      // Create two tables to test triggers,
      statement.executeQuery(
	  "    CREATE TABLE TriggerTable1 ( " +
	  "     trig_column INTEGER NOT NULL ) ");

      statement.executeQuery(
	  "    CREATE TABLE TriggerTable2 ( " +
	  "     trig_column INTEGER NOT NULL ) ");

      System.out.println("-- Setting Triggers --");

      // Set the triggers on the database,
      statement.executeQuery("CREATE CALLBACK TRIGGER trig_1 INSERT ON TriggerTable1");
      statement.executeQuery("CREATE CALLBACK TRIGGER trig_2 DELETE ON TriggerTable1");
      statement.executeQuery("CREATE CALLBACK TRIGGER trig_3 UPDATE ON TriggerTable2");

      // Set up listeners to listen for trigger events on the JDBC client,
      mckoi_connection.addTriggerListener("trig_1", new TriggerListener() {
	public void triggerFired(String trigger_name) {
	  System.out.println("Trigger fired: " + trigger_name);
	}
      });
      mckoi_connection.addTriggerListener("trig_2", new TriggerListener() {
	public void triggerFired(String trigger_name) {
	  System.out.println("Trigger fired: " + trigger_name);
	}
      });
      mckoi_connection.addTriggerListener("trig_3", new TriggerListener() {
	public void triggerFired(String trigger_name) {
	  System.out.println("Trigger fired: " + trigger_name);
	}
      });

      System.out.println("-- Performing queries to cause triggers --");

      // Perform some queries to fire the triggers.

      statement.executeQuery(
	  " INSERT INTO TriggerTable1 ( trig_column ) VALUES " +
	  "    ( 10 ), ( 15 ), ( 20 ) ");
      statement.executeQuery(
	  " INSERT INTO TriggerTable2 ( trig_column ) VALUES " +
	  "    ( 10 ), ( 15 ), ( 20 ) ");
      statement.executeQuery(
	  " DELETE FROM TriggerTable1 WHERE trig_column = 10 ");
      statement.executeQuery(
	  " DELETE FROM TriggerTable2 WHERE trig_column = 20 ");
      statement.executeQuery(
	  " INSERT INTO TriggerTable1 ( trig_column ) " +
	  "   SELECT trig_column FROM TriggerTable1 ");
      statement.executeQuery(
	  " INSERT INTO TriggerTable2 ( trig_column ) " +
	  "   SELECT trig_column FROM TriggerTable2 ");
      statement.executeQuery(
	  " UPDATE TriggerTable1 SET trig_column = trig_column * 12.3 ");
      statement.executeQuery(
	  " UPDATE TriggerTable2 SET trig_column = trig_column * 12.3 ");

      // Wait 2 seconds for the triggers to fire....

      System.out.println("--- Waiting for triggers ---");

      try {
	Thread.sleep(2000);
      }
      catch (InterruptedException e) { /* ignore */ }

      System.out.println("--- Complete ---");

      // Close the statement and the connection.
      statement.close();
      connection.close();

    }
    catch (SQLException e) {
      System.out.println(
	"An error occured\n" +
	"The SQLException message is: " + e.getMessage());

    }

    // Close the the connection.
    try {
      connection.close();
    }
    catch (SQLException e2) {
      e2.printStackTrace(System.err);
    }

  }

}
