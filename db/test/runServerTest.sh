#!/bin/sh

java -cp ../mckoidb.jar:../gnu-regexp-1.0.8.jar com.mckoi.tools.JDBCScriptTool -url "jdbc:mckoi://localhost/" -u test -p test -in script_in.txt -out result.txt
