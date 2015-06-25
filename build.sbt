// Basic build stuff
name := "demo"

organization := "com.demo"

version := "1.0"

javacOptions ++= Seq("-source", "1.7",
	"-target", "1.7")

// no scala versions in target	
crossPaths := false

// Pure java project
//scalaVersion := "2.11.6"

// Compile stuff
val base = settingKey[File]("Base directory value")
base := baseDirectory.value

val junit = "junit" % "junit" % "4.11" % "test"
val junitSbtInterface = "com.novocode" % "junit-interface" % "0.11" % "test"
val gwt = "com.google.gwt" % "gwt-user" % "2.7.0"
val j2ee7 = "javax" % "javaee-api" % "7.0"
val smartgwtnightly = "2015-06-07/"
val smartgwtbaseurl = "http://www.smartclient.com/builds/SmartGWT/5.0p/LGPL/" + smartgwtnightly
val smartgwt = "com.smartgwt" % "smartgwt" % "5.0p"
val smartgwtskins = "com.smartgwt" % "smartgwt-skins" % "5.0p"
val smartgwtloc = smartgwtbaseurl + "smartgwt.jar"
val smartgwtskinsloc = smartgwtbaseurl + "smartgwt-skins.jar"


// managed jars
libraryDependencies ++= Seq(
    j2ee7,
    junit,
    junitSbtInterface,
    gwt,
    smartgwt from smartgwtloc,
    smartgwtskins from smartgwtskinsloc
)

javaSource in Compile := base.value / "src"

javaSource in Test := base.value / "testsrc"

// Not needed now as we have j2ee7 above - (maybe needed for wildfly extensions)
//val serverPath = settingKey[File]("Server modules directory")
//serverPath := file(System.getProperty("server.path", Path.userHome.absolutePath + "/servers/wildfly-8.2.0.Final/modules"))

unmanagedJars in Compile ++= {
    val base = baseDirectory.value
    val webinflib: File = file("WebContent/WEB-INF/lib")
    val baseDirectories = (base / "lib") +++ (base / "testlib") +++ webinflib //+++ serverPath.value
    val customJars = (baseDirectories ** "*.jar")
    customJars.classpath
}

// test options
javaOptions in Test += "-Djunit.output.file=" + (target.value / "generated/junit.html").getAbsolutePath
fork in Test := true

// package setup for war
//jetty()

// set <project>/WebContent as the webapp resources directory
//webappSrc in webapp <<= baseDirectory map  { _ / "WebContent" }

// put stuff in classes dir
//webInfClasses in webapp := true

// disable project jar
//publishArtifact in (Compile, packageBin) := false

// alternative
//val packageWar = taskKey[File]("package-war")

packageWar := {
    IO.copyDirectory(file(base + "/WebContent"), file(base + "/target/webapp")) 
    val warFile = name.value + "-" + version.value + ".war"
    (baseDirectory in Compile).value / "target" / warFile
}

// create an Artifact for publishing the .war file
artifact in (Compile, packageWar) := {
    val previous: Artifact = (artifact in (Compile, packageWar)).value
    previous.copy(`type` = "war", extension = "war", classifier = Some("webapp"))
}

// add the .war file to what gets published
addArtifact(artifact in (Compile, packageWar), packageWar)

