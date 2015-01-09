
/**
 * This demonstrates a simple stand-alone database application.  This will
 * start up the database that's found in this directory and perform some
 * queries on the data.
 * <p>
 * The demo distribution should contain the database data files already
 * prepared.  However, if the 'data' directory was removed then run
 * 'SimpleDatabaseCreateDemo' to remake it.
 */

import java.sql.*;

public class SimpleApplicationDemo {

  /**
   * The demonstation 'main' method.
   */
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
	 "For example on Win32;  java -cp ../../mckoidb.jar;. SimpleApplicationDemo\n" +
	 "On Unix;  java -cp ../../mckoidb.jar:. SimpleApplicationDemo");
      return;
    }

    // This URL specifies we are connecting with a local database.  The
    // configuration file for the database is found at './ExampleDB.conf'
    String url = "jdbc:mckoi:local://ExampleDB.conf";

    // The username/password for the database.  This is set when the database
    // is created (see SimpleDatabaseCreateDemo).
    String username = "user";
    String password = "pass1212";

    // Make a connection with the database.
    Connection connection;
    try {
      connection = DriverManager.getConnection(url, username, password);
    }
    catch (SQLException e) {
      System.out.println(
	 "Unable to make a connection to the database.\n" +
	 "The reason: " + e.getMessage());
      return;
    }

    // --- Do some queries ---
    System.out.println();

    try {
      // Create a Statement object to execute the queries on,
      Statement statement = connection.createStatement();
      ResultSet result;

      // How many rows are in the 'Person' table?
      result = statement.executeQuery("SELECT COUNT(*) FROM Person");
      if (result.next()) {
	System.out.println("Rows in 'Person' table: " + result.getInt(1));
      }
      System.out.println();

      // What's the average age of the people stored?
      result = statement.executeQuery("SELECT AVG(age) FROM Person");
      if (result.next()) {
	System.out.println("Average age of people:  " + result.getDouble(1));
      }
      System.out.println();

      // List the names of all the people that live in Africa ordered by name
      result = statement.executeQuery(
	  "SELECT name FROM Person WHERE lives_in = 'Africa' ORDER BY name");
      System.out.println("All people that live in Africa:");
      while (result.next()) {
	System.out.println("  " + result.getString(1));
      }
      System.out.println();

      // List the name and music group of all the people that listen to
      // either 'Oasis' or 'Beatles'
      result = statement.executeQuery(
	  "   SELECT Person.name, MusicGroup.name " +
	  "     FROM Person, ListensTo, MusicGroup " +
	  "    WHERE MusicGroup.name IN ( 'Oasis', 'Beatles' ) " +
	  "      AND Person.name = ListensTo.person_name " +
	  "      AND ListensTo.music_group_name = MusicGroup.name " +
	  " ORDER BY MusicGroup.name, Person.name ");
      System.out.println("All people that listen to either Beatles or Oasis:");
      while (result.next()) {
	System.out.print("  " + result.getString(1));
	System.out.print(" listens to ");
	System.out.println(result.getString(2));
      }
      System.out.println();

      // Close the statement and the connection to end,
      statement.close();
      connection.close();

    }
    catch (SQLException e) {
      System.out.println(
	"An error occured\n" +
	"The SQLException message is: " + e.getMessage());
      return;
    }

  }

}
