
Mckoi SQL Database
------------------

This is a release of the Mckoi SQL Database engine.  The homepage for
this project is at 'http://www.mckoi.com/database'.


IMPORTANT INFORMATION FOR CONVERTING DATA BEFORE VERSION 1.00
=============================================================

Before upgrading your applications to a higher version, make sure to back
up all critical database data before making the switch.  Some versions
of the Mckoi engine may need to alter the structure of your database
files to add new features.  The data conversion may make it impossible
to 'downgrade' to an older version, therefore we recommend that
data is backed up before conversion so if you need to fall back to an
older version you will not lose any data.

Note that, as with any data storage product, it is good practice to keep
regular backups of all critical data.

To convert your data files, use the following command from the
distribution directory (all one line);

java -cp mckoidb.jar com.mckoi.tools.DataFileConvertTool \
     -path [database directory] -u [admin username] -p [admin password]

Substitute [database directory] with the path to your database files and
[admin username] and [admin password] with the username and password of
the administrator user that was set when the database was created.
After the files are converted you will be able to use your data files with
version 1.00.

If you have problems upgrading a database, contact toby@mckoi.com.



Installing the software
-----------------------

To use this software, you need a Java runtime environment version 1.2 or
greater.  See the FAQ for details on where to download a Java runtime.

Unpack the zip file to a directory in your file system.  Most Java
developer kits come with a utility called 'jar' which can be used to
unpack the distribution file.  Type 'jar xvf [zip file]' at a prompt
to unpack the distribution into the current directory or you can
use another unzip package.  A popular zip package on the  Microsoft
Windows platform is WinZIP.  Most modern Unix systems ship with an
'unzip' utility.


What's in this distribution?
----------------------------

This distribution includes the source code as well as the compiled binary
class files.  It also includes tools/documentation and demonstrations of
a simple application using the database.  The binaries were compiled
using the Sun javac compiler.

Below is a description of the files/directories in this release;

contrib/     - Developer contributions.
demo/        - Demonstrations of a simple database application.
docs/        - HTML documentation.
test/        - Test script demonstration.
db.conf      - A typical database configuration file.
docpackages.txt
             - Used when generating the JavaDocs.
mckoidb.jar  - The complete compiled database class files.
mkjdbc.jar   - Only the JDBC Driver class files.
src.zip      - The database source code.
LICENSE.txt  - The GPL license the software is distributed under.
README.txt   - This file.


Building the binaries
---------------------

You will need at least Java 1.7 standard edition developers version to
compile the source.

First unpack the source files that are archived in the src.zip file.  You
can use the command 'jar xvf src.zip' to unpack the source.  To build the
binaries into the 'lib' sub-directory, use the following commands (from
the distribution directory);

  mkdir lib
  javac -target 1.3 -classpath src;lib -d lib/ \
    src/main/java/com/mckoi/runtime/McKoiDBMain.java \
    src/main/java/com/mckoi/JDBCDriver.java \
    src/main/java/com/mckoi/database/jdbcserver/DefaultLocalBootable.java \
    src/main/java/com/mckoi/database/interpret/*.java \
    src/main/java/com/mckoi/database/control/*.java \
    src/main/java/com/mckoi/tools/*.java


You will receive some deprecation warnings but they can be safely
ignored.

Note that Unix and Windows use different path separators.  Windows uses
the semi-colon (;) character and Unix uses colon (:).  The build command
under Unix will need ';' replaced with ':'.

JavaCC, the Java compiler compiler, was used to build the SQL parser.
You can still compile the source without JavaCC however you will not be
able to change the grammar.

JavaCC is available at 'http://www.metamata.com/JavaCC/'.

To compile the grammar with JavaCC, use the following commands;

  cd src/main/java/com/mckoi/database/sql
  javacc SQL.jj

Another way to build Mckoi from source is to use Maven. A pom.xml for
Maven is included in the src.zip archive.


Building the JavaDoc API documentation
--------------------------------------

As with building the binaries, you will first need to unzip the source
files in src.zip.  To build the JavaDoc API documentation into the
'javadoc' sub-directory, use the following commands;

  mkdir javadoc
  javadoc -classpath src;gnu-regexp-1.1.4.jar -protected \
      -overview src/main/java/com/mckoi/overview.html \
      -sourcepath src/main/java -d javadoc @docpackages.txt

Note that '-protected' can be changed to '-public' or '-private' to change
the grade of documentation provided.  See Sun's javadoc tool documentation
for further command line options.

Overview and package API documentation was provided by Jim McBeath of
Globtrotter.


Contacting the Author
---------------------

My email is 'toby@mckoi.com'. If you have any general comments or
questions about the software please contact me. I appreciate the feedback.
