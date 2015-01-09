#!/bin/sh

rm -fr data
rm -fr log
java -cp ../mckoidb.jar:../gnu-regexp-1.0.8.jar com.mckoi.tools.JDBCScriptTool -url "jdbc:mckoi:local://./db.conf?create=true" -u test -p test -in script_in.txt -out result.txt
