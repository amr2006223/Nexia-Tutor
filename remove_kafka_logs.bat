@echo off

REM delete kafka logs 
cd /d "kafka\logs\metadata"
del /q *.*
for /d %%i in (*) do rmdir /s /q "%%i"