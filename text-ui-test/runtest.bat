@ECHO OFF

REM Change directory to where the script is located (optional, depending on CI setup)
cd %~dp0

REM Delete the data directory to remove old task data
if exist ..\data (
    rd /s /q ..\data
)

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\commands\*.java ..\src\main\java\common\*.java ..\src\main\java\components\*.java ..\src\main\java\exceptions\*.java ..\src\main\java\gui\*.java ..\src\main\java\parser\*.java ..\src\main\java\storage\*.java ..\src\main\java\tasks\*.java ..\src\main\java\ui\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin ui.Friday < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
