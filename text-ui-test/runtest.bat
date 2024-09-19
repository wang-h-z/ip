@ECHO OFF

REM Print the current working directory for debugging
echo Current Directory: %cd%

REM Delete the data directory to remove old task data
if exist ..\data (
    echo Deleting data directory...
    REM Use rmdir with /s /q flags to forcefully remove directory and its contents
    rmdir /s /q ..\data
    REM Wait for a moment to ensure deletion is complete
    timeout /t 2 /nobreak > nul
)

REM Ensure the directory is deleted
if exist ..\data (
    echo Error: Data directory still exists after deletion!
    echo Attempting to force delete...
    REM Try to force delete using del /f /s /q
    del /f /s /q ..\data\*.*
    rmdir /s /q ..\data
    timeout /t 2 /nobreak > nul
    if exist ..\data (
        echo Error: Unable to delete data directory. Exiting.
        exit /b 1
    )
)

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
javac -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\commands\*.java ..\src\main\java\common\*.java ..\src\main\java\exceptions\*.java ..\src\main\java\gui\*.java ..\src\main\java\parser\*.java ..\src\main\java\storage\*.java ..\src\main\java\tasks\*.java ..\src\main\java\ui\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin ui.Friday < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
