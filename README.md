Steps to run the application:

mvn clean install 
 
mvn exec:java -Dexec.mainClass="com.zuhlke.App" -Dexec.args="sales.csv" -Dexec.cleanupDaemonThreads=false
