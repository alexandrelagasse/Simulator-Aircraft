@echo off
echo --------------------------------------------------
echo Initialisation du simulateur ENAC avec Maven local     
echo --------------------------------------------------

:: Détection de Maven
set MAVEN_PATH=apache-maven-3.9.9\bin\mvn.cmd
if not exist "%MAVEN_PATH%" (
    echo ❌ Maven introuvable à %MAVEN_PATH%
    pause
    exit /b 1
)

echo ✅ Maven trouvé : %MAVEN_PATH%

:: Détection de JAVA_HOME (optionnel, mais utile)
if not defined JAVA_HOME (
    echo ❗ JAVA_HOME non défini, on essaie avec la version de java dans le PATH...
) else (
    echo ✅ JAVA_HOME=%JAVA_HOME%
)

echo 🚀 Compilation et lancement...

:: TRES IMPORTANT – mettre tout entre guillemets s'il y a des espaces dans le chemin
call "%MAVEN_PATH%" clean javafx:run

echo ----------------------------------------
pause
