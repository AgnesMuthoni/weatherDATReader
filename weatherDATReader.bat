@ECHO ON
set mypath=%cd%
@echo %mypath%
java -jar %mypath%\dist\weatherDATReader.jar
PAUSE