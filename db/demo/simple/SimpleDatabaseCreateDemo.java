
/**
 * Demonstrates how to create a database through the JDBC driver.  This
 * will create a database in the local directory, create three tables and
 * fill them with sample data.
 * <p>
 * This demo distribution should contain a created database in the local
 * directory.  If the 'data' directory doesn't exist or was deleted then it
 * can be recreated by running this.
 */

import java.sql.*;

public class SimpleDatabaseCreateDemo {

  /**
   * Application start method.
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

    // --- Set up the database ---

    try {
      // Create a Statement object to execute the queries on,
      Statement statement = connection.createStatement();
      ResultSet result;

      System.out.println("-- Creating Tables --");

      // Create a Person table,
      statement.executeQuery(
	  "    CREATE TABLE Person ( " +
	  "       name      VARCHAR(100) NOT NULL, " +
	  "       age       INTEGER, " +
	  "       lives_in  VARCHAR(100) ) " );

      // Create a ListensTo table (which person listens to what music)
      statement.executeQuery(
	  "    CREATE TABLE ListensTo ( " +
	  "       person_name      VARCHAR(100) NOT NULL, " +
	  "       music_group_name VARCHAR(250) NOT NULL ) ");

      // Create a MusicGroup table
      statement.executeQuery(
	  "    CREATE TABLE MusicGroup ( " +
	  "       name              VARCHAR(250) NOT NULL, " +
	  "       country_of_origin VARCHAR(100) ) ");

      // Insert records into the tables,

      System.out.println("-- Inserting Data --");

      System.out.println("-- Adding to Person Table --");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Robert Bellamy', 24, 'England' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Grayham Downer', 59, 'Africa' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Timothy French', 24, 'Africa' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Butch Fad', 53, 'USA' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Judith Brown', 34, 'Africa' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Elizabeth Kramer', 24, 'USA' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Yamnik Wordsworth', 14, 'Australia' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Domonic Smith', 25, 'England' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Ivan Wilson', 23, 'England' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Lisa Williams', 24, 'England' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'Xenia, Warrior Princess', 32, 'Rome' ) ");
      statement.executeQuery(
	  "    INSERT INTO Person ( name, age, lives_in ) VALUES " +
	  "      ( 'David Powell', 25, 'New Zealand' ) ");

      System.out.println("-- Adding to MusicGroup Table --");
      statement.executeQuery(
	  "    INSERT INTO MusicGroup " +
	  "      ( name, country_of_origin ) VALUES " +
	  "      ( 'Oasis',       'England' ), " +
	  "      ( 'Fatboy Slim', 'England' ), " +
	  "      ( 'Metallica',   'USA' ), " +
	  "      ( 'Nirvana',     'USA' ), " +
	  "      ( 'Beatles',     'England' ), " +
	  "      ( 'Fela Kuti',   'Africa' ), " +
	  "      ( 'Blur',        'England' ), " +
	  "      ( 'Muddy Ibe',   'Africa' ), " +
	  "      ( 'Abba',        'Sweden' ), " +
	  "      ( 'Madonna',     'USA' ), " +
	  "      ( 'Cure',        'England' ) " );

      // Who listens to what music?
      System.out.println("-- Adding to ListensTo Table --");
      statement.executeQuery(
	  "    INSERT INTO ListensTo " +
	  "      ( person_name, music_group_name ) VALUES " +
	  "      ( 'David Powell',             'Metallica' ), " +
	  "      ( 'David Powell',             'Cure' ), " +
	  "      ( 'Xenia, Warrior Princess',  'Madonna' ), " +
	  "      ( 'Lisa Williams',            'Blur' ), " +
	  "      ( 'Lisa Williams',            'Cure' ), " +
	  "      ( 'Lisa Williams',            'Beatles' ), " +
	  "      ( 'Ivan Wilson',              'Cure' ), " +
	  "      ( 'Ivan Wilson',              'Beatles' ), " +
	  "      ( 'Yamnik Wordsworth',        'Abba' ), " +
	  "      ( 'Yamnik Wordsworth',        'Fatboy Slim' ), " +
	  "      ( 'Yamnik Wordsworth',        'Fela Kuti' ), " +
	  "      ( 'Elizabeth Kramer',         'Nirvana' ), " +
	  "      ( 'Judith Brown',             'Fela Kuti' ), " +
	  "      ( 'Judith Brown',             'Muddy Ibe' ), " +
	  "      ( 'Butch Fad',                'Metallica' ), " +
	  "      ( 'Timothy French',           'Blur' ), " +
	  "      ( 'Timothy French',           'Oasis' ), " +
	  "      ( 'Timothy French',           'Nirvana' ), " +
	  "      ( 'Grayham Downer',           'Fela Kuti' ), " +
	  "      ( 'Grayham Downer',           'Beatles' ), " +
	  "      ( 'Robert Bellamy',           'Oasis' ), " +
	  "      ( 'Robert Bellamy',           'Beatles' ), " +
	  "      ( 'Robert Bellamy',           'Abba' ), " +
	  "      ( 'Robert Bellamy',           'Blur' ) " );

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
