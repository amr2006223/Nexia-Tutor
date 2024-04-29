@echo off

cd kafka

start cmd /k call .\bin\windows\zookeeper-server-stop.bat 

start cmd /k call .\bin\windows\kafka-server-stop.sh
