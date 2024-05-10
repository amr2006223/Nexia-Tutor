@echo off

REM Change directory to the directory containing this batch file
cd /d "%~dp0"

REM Change directory to "kafka", Open new Terminal, start zookeeper
cd kafka
start cmd /k call .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
timeout /t 25

REM Open new Terminal, start kafka
start cmd /k call .\bin\windows\kafka-server-start.bat .\config\server.properties
timeout /t 5
cd ..

cd configserver
start cmd /k call mvn spring-boot:run
timeout /t 10
cd ..

REM Change directory to "eurekaserver", Open new Terminal, start spring boot application
cd eurekaserver
start cmd /k call mvn spring-boot:run
timeout /t 10
cd ..

REM Change directory to "gateway", Open new Terminal, start spring boot application
cd gateway
start cmd /k call mvn spring-boot:run
timeout /t 10
cd ..

REM Change directory to "Nexia-Tutor", Open new Terminal, start spring boot application
cd Nexia-Tutor
cd backend
start cmd /k call mvn spring-boot:run
timeout /t 10
cd ..
cd ..

REM Change directory to "reportGeneration", Open new Terminal, start spring boot application
cd identity-service
start cmd /k call mvn spring-boot:run
timeout /t 10
cd ..

REM Change directory to "reportGeneration", Open new Terminal, start spring boot application
cd reportGeneration
start cmd /k call mvn spring-boot:run
timeout /t 10
cd ..

REM Change directory back to parent, Open new Terminal, start python KeywordExtractionAPI\app.py
cd KeywordExtractionAPI
start cmd /k call python app.py
timeout /t 10
cd ..

REM Change directory back to parent, Open new Terminal, start python ScreeningServiceAPI\app.py
cd ScreeningServiceAPI
start cmd /k call python main.py
timeout /t 10

REM Change directory back to parent, Open new Terminal, start python ScreeningServiceAPI\app.py
start cmd /k call pytest tests/
timeout /t 10
cd ..

REM Change directory back to parent, Open new Terminal, start python WebScraping\games_controller.py
cd WebScraping
start cmd /k call python games_controller.py
timeout /t 10
cd ..

REM Change directory to "nexia-ai-tutor-frontend", Open new Terminal, start npm run dev
cd nexia-ai-tutor-frontend
start cmd /k call npm run dev
cd ..