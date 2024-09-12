@ECHO OFF

REM Print the current working directory for debugging
echo Current Directory: %cd%

REM Print the current working directory for debugging
echo Current Directory: %cd%

REM Delete the data directory to remove old task data
if exist ..\data (
    echo Deleting data directory...
    rd /s /q ..\data
)
echo Recreating data directory...
mkdir ..\data

REM Ensure the directory is deleted and check for locks
if exist ..\data (
    echo Error: Data directory still exists after deletion!
    exit /b 1
) else (
    echo Data directory successfully deleted and recreated.
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
