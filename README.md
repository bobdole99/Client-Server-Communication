Client-Server-Communication
===========================
To compile and generate needed classes: 

> make all

To run server:

> make runs

To run client:

> make runc

To run test cases on client:

> make test

I've included some test cases for client, in order to run <font color = "blue"> <i>testValidCommandLineArgs()</i></font> and <i><font color = "blue"> testInvalidCommandLineArgs()</i></font>, <i><font color = "blue"> testNumberOfCommandLineArgs()</i></font> needs to be commented out. In order to run <i><font color = "blue"> testNumberOfCommandLineArgs()</i></font>,  <i><font color = "blue"> testValidCommandLineArgs()</font></i> and <i><font color = "blue">testInvalidCommandLineArgs()</font></i>, need to be commented out. I think the problem has to do with Client.java being a static class. Also JUnit version 4.8.2 was used for testing.

<h3> Known issue </h3>
At the last minute I noticed that if the client quits the server will crash.
 
