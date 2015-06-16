This project is not complete enough at the time to use - just wanted a good distributed backup.
Installing Wildfly - TODO

Installing Egit to download code into Eclipse.
Help->Install New Software...
http://download.eclipse.org/egit/updates

Before deploying this application you must compile the GWT code. If the menu item 
Project-> Build Automatically is selected the java code will build inside Eclipse sufficiently
to deploy the application. The main GWT site for more information is here. http://www.gwtproject.org/

To install GWT "Google Plugin for Eclipse", use the following instructions for Eclipse 4.4 (Luna):
1) Select Help > Install New Software... In the dialog that appears, 
   enter the update site URL into the Work with text box: https://dl.google.com/eclipse/plugin/4.4
2) Select Google Plugin for Eclipse 4.4 - Note: we need to install the latest SDK manually.
3) Download the SDK from here, currently 2.7.0. http://www.gwtproject.org/download.html
4) The GWT SDK can be unzipped anywhere but I prefer my $HOME/eclipse-plugins
5)  In Eclipse, got to Window -> Preferences -> Google -> Web Toolkit and push Add...
    Navigate to the Installation directory and select gwt-2.7.0.
6) Look at Markers Tab in Eclipse and fix any class path issues you may have.
7) Now right click on demo and select Google -> GWT Compile (or use the toolbar icon menu)
8) The following options may help under Advanced
   Additional compiler arguments: -localWorkers 2 -strict -war WebContent
   VM arguments: -Xmx1025m
9) To deploy right click on the Wildfly server and select Add and Remove ...
   Add "demo" and cleick finish.

To view the application use the following URL:
http://localhost:8080/demo/

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
