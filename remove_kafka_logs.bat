@echo off

REM delete kafka logs 
cd /d "D:\Nexia-Tutor\kafka\logs\metadata"
del /q *.*
for /d %%i in (*) do rmdir /s /q "%%i"