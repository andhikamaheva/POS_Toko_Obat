@echo off
echo.
echo Uninstall Mckoi NT Service with JavaService
echo http://www.alexandriasc.com/software/JavaService/index.html
echo Usage:
echo %0 javaservice_home
echo.

if "%1" == "" goto eof

%1\bin\JavaService.exe -uninstall Mckoi
echo.

:eof
