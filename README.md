Steps to run the application:

mvn clean install 
 
mvn exec:java -Dexec.mainClass="com.zuhlke.App" -Dexec.args="sales.csv" -Dexec.cleanupDaemonThreads=false

Comments:
- Since last year I consider Sandro Mancuso's approach (https://codurance.com/2017/10/23/outside-in-design/) to build APIs based on atomic actions.
In our case, we can see that we have two: ImportSales and StoreSales.
Both can be unit tested, and in the case of the second one, with a repository in memory. Taking into account that there is no logic added in this action and it's only "a bridge" to connect with the DB, I chose to skip the unit test and just implement Integration tests. In case it grows and we find ourselves adding logic to this action that should be unit tested independently from the connection with MySQL, we can easily do it injecting an implementation of the SalesRepository that handles everything in memory.

- The getSales method is added even when is not used by anyone as we can assume in this context that data is stored to be retrieved later. In real world probably we don't need this method and we can design our tests in a different way.

- The Main class is not easy to test, so I apply the Humble Object pattern: I remove logic from there and I move it to a class called StoreSalesApplication, which can be mocked to verify that all the needed steps of the application are called.

- I assume that mandatory fields are those who are "NOT NULL" in the DB, although it would be a question to the product team, as it should be defined in the requirements.

- Another business decision is what to do when one row import fails, and when one insert query to the DB fails as well. Should we rollback everything or just move forward? I chose to interrupt the process but needs to be defined with the product team in a real world environment.

- In this direction, with this example we don't need to think about bulk operations. In case the requirement includes handling thousands of records, then a loop with inserts could be definitely improved.



