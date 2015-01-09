@echo off

if not "%9" == "" goto install

echo.
echo Install Mckoi NT Service with JavaService
echo http://www.alexandriasc.com/software/JavaService/
echo USAGE:
echo %0 javaservice_path jvm_dll jvm_option mckoi_path mckoi_config_file mckoi_data_path mckoi_log_path username password
echo EXAMPLE:
echo %0 c:\javaservice c:\j2sdk1.4.1\jre\bin\server\jvm.dll -Xmx128m c:\mckoi0.94e c:\mckoi_data\db.conf c:\mckoi_data\data c:\mckoi_data\log toto secret
echo NOTE: You MAY NOT use spaces in the path names.
echo.

goto :eof

:install

copy %1\bin\JavaService.exe %4\Mckoi.exe > nul
%4\Mckoi.exe -install Mckoi %2 -Djava.class.path=%4\mckoidb.jar %3 -start com.mckoi.runtime.McKoiDBMain -params -conf %5 -dbpath %6 -logpath %7 -stop com.mckoi.runtime.McKoiDBMain -params -shutdown %8 %9 -out %4\serv_out.log -err %4\serv_err.log -current %4
echo.

:eof
