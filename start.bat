@echo off

REM Change directory to the directory containing this batch file
cd /d "%~dp0"

REM Change directory to configserver, Open new Terminal, start spring boot application
cd configserver
start cmd /k call mvn spring-boot:run

@REM must wait for configserver to start before starting other services
timeout /t 10

REM Change directory to "eurekaserver", Open new Terminal, start spring boot application
cd ..
cd eurekaserver
start cmd /k call mvn spring-boot:run

REM must wait for eureka server to start before starting other services
timeout /t 10

REM Change directory to "gateway", Open new Terminal, start spring boot application
cd ..
cd gateway
start cmd /k call mvn spring-boot:run

@REM wait for kafka to start before starting other services
timeout /t 5

REM Change directory to "Nexia-Tutor", Open new Terminal, start spring boot application
cd ..
cd Nexia-Tutor
cd back
start cmd /k call mvn spring-boot:run

@REM wait for kafka to start before starting other services
timeout /t 5

REM Change directory to "reportGeneration", Open new Terminal, start spring boot application
cd ..
cd reportGeneration
start cmd /k call mvn spring-boot:run

@REM wait for kafka to start before starting other services
timeout /t 5

REM Change directory back to parent, Open new Terminal, start python KeywordExtractionAPI\app.py
cd ..
cd KeywordExtractionAPI
start cmd /k call python app.py

@REM wait for kafka to start before starting other services
timeout /t 5

REM Change directory back to parent, Open new Terminal, start python ScreeningServiceAPI\app.py
cd ..
cd ScreeningServiceAPI
start cmd /k call python app.py

@REM wait for kafka to start before starting other services
timeout /t 5

REM Change directory back to parent, Open new Terminal, start python WebScraping\games_controller.py
cd ..
cd WebScraping
start cmd /k call python games_controller.py

@REM wait for kafka to start before starting other services
timeout /t 5

REM Change directory to "nexia-ai-tutor-frontend", Open new Terminal, start npm run dev
cd ..
cd nexia-ai-tutor-frontend
start cmd /k call npm run dev