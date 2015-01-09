@echo off
rmdir data /s/q
rmdir log /s/q
java -cp ..\mckoidb.jar;..\gnu-regexp-1.0.8.jar com.mckoi.tools.JDBCScriptTool -url "jdbc:mckoi:local://./db.conf?create=true" -u test -p test -in script_in.txt -out result.txt
