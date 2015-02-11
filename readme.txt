This project is not complete enough at the time to use - just wanted a good distributed backup.

To use the H2 Console started when the application started navigate to the following URL:
http://localhost:8082/

Use of H2 Console available via the following dependency. Note that this is the name of the JBoss
module, not the package name. Use of the manifest is the J2EE portable way to include dependencies
although I don't know how since this only works using module name not the package name.

This is the content of demo/WebContent/META-INF/MANIFEST.MF
Manifest-Version: 1.0
Dependencies: com.h2database.h2 
Class-Path: 

TODO: directions to use persistent DB ~/h2testdb
To use the H2 Console
Create a Run Configuration -> Java Application

On the Main tab, fill out the following
Name: H2 Console
Project: demo
Main Class: org.h2.tools.Console

Select the Classpath tab.
Select User Entries.
Click the Add External JARs... button

Navigate to your WildFly server and the path to the jar that contains the main class above.
/path/to/wildfly-8.2.0.Final/modules/system/layers/base/com/h2database/h2/main/h2-1.3.173.jar
Select the JAR.

Click apply then run. It will open the console in your default browser.

Use the following:
Url: jdbc:h2:mem:test
Password: sa
Note: You can save the password in the browser if you wish.
