@echo off
rmdir data /s/q
rmdir log /s/q
java -cp ..\mckoidb.jar;..\gnu-regexp-1.0.8.jar com.mckoi.runtime.McKoiDBMain -conf .\db.conf -create test test
java -cp ..\mckoidb.jar;..\gnu-regexp-1.0.8.jar com.mckoi.runtime.McKoiDBMain -conf .\db.conf
