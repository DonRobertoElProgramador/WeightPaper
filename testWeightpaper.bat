@echo off
REM Change directory to your project's root
cd /d "%~dp0"

REM Perform Maven clean and install
call mvn clean install

REM Check if configuration directory exists and create it if it doesn't
if not exist "target\conf" mkdir target\conf

REM Copy the specified configuration file to the target directory
copy configuration.yaml target\conf\

REM Check if img directory exists and create it if it doesn't
if not exist "target\img" mkdir target\img

REM Copy the specified image files to the img directory
copy test1.jpg target\img\
copy test2.png target\img\
copy test3.bmp target\img\
copy test4.jpg target\img\
copy mejorfotodelmundo.jpg target\img\

REM Change directory to the target folder
cd target

REM Run the Java application from the JAR file
java -jar Weightpaper-1.0-SNAPSHOT.jar

cd..

REM Pause the script to view any output before closing
pause