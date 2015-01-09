
DATABASE TESTS
--------------

This demonstrates how to run test scripts against a database.  See
'script_in.txt' for the layout of an SQL script file.

To run the test SQL script in a stand-alone database application, use
the 'runLocalTest' batch file/shell script.  This will create a new
database and send each SQL command in the script to the database.
The results of each command are output to 'result.txt'.

To run the test script in a client/server database application, first
use the 'testServerBoot' command to start the database server.  After
the database has booted, run the 'runServerTest' command.  This will
read the SQL commands from 'script_in.txt', send them to the database
server for processing, and output the results to 'result.txt'.  When
the test has completed, the 'testServerShutdown' command can be used
to shutdown the database server.


The current test script is quite simplistic.  Feel free to add/remove
your own commands to test database features, or generate your own test
scripts.