:: set classpath to current directory
set CLASSPATH = .;dropbox\APCS_H\Rupp-Coppi\Bear GUI Project - Late Submission\src; 
:: compile classes to a separate directory, compile main class in subdirectory
javac -d ..\classes bear\Main.java
PAUSE