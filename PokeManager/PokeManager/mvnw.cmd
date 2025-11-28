@echo off
REM Lightweight Maven bootstrap wrapper for Windows
REM This script downloads Apache Maven binary into .mvn\apache-maven-3.9.6 if needed,
REM then executes Maven with the forwarded arguments.

setlocal
set MAVEN_VERSION=3.9.6
set MAVEN_DIR=.mvn\apache-maven-%MAVEN_VERSION%
set MAVEN_BIN=%MAVEN_DIR%\bin\mvn.cmd
set MAVEN_DIR=.mvn\apache-maven-%MAVEN_VERSION%
set MAVEN_BIN=%MAVEN_DIR%\bin\mvn.cmd

if exist "%MAVEN_BIN%" goto :run_mvn

echo Maven not found locally. Downloading Apache Maven %MAVEN_VERSION% (this may take a while)...
set MAVEN_URL=https://downloads.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip
set MAVEN_URL1=https://downloads.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip
set MAVEN_URL2=https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip
powershell -NoProfile -Command "if (-not (Test-Path '.mvn')) { New-Item -ItemType Directory -Path '.mvn' | Out-Null }; $zip='.mvn\maven.zip'; if (-not (Test-Path $zip)) { Try { Invoke-WebRequest -Uri '%MAVEN_URL1%' -OutFile $zip -UseBasicParsing -ErrorAction Stop } Catch { Write-Output 'Primary download failed, trying archive mirror...'; Invoke-WebRequest -Uri '%MAVEN_URL2%' -OutFile $zip -UseBasicParsing -ErrorAction Stop } }; Expand-Archive -Path $zip -DestinationPath '.mvn'; Remove-Item $zip -ErrorAction SilentlyContinue"
if errorlevel 1 (
    echo Failed to download Maven. Check your internet connection.
    exit /b 1
)

:run_mvn
REM Execute the downloaded Maven with all arguments
"%MAVEN_BIN%" %*

endlocal
