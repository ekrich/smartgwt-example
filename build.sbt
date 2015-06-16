name := "SmartGWT Example"

organization := "com.demo"

version := "1.0"

javacOptions ++= Seq("-source", "1.7",
	"-target", "1.7")

scalaVersion := "2.11.6"

val userHome = settingKey[String]("User home dir")
userHome := System.getProperty("user.home")

// these here not so much - j2ee7 below - (maybe specific extensions are ok)
//val serverPath = settingKey[File]("Server modules directory")
//serverPath := file(System.getProperty("server.path", Path.userHome.absolutePath + "/servers/wildfly-8.2.0.Final/modules"))


val base = settingKey[File]("Base directory value")
base := baseDirectory.value

val junit = "junit" % "junit" % "4.11" % "test"
val junitSbtInterface = "com.novocode" % "junit-interface" % "0.11" % "test"
val gwt = "com.google.gwt" % "gwt-user" % "2.7.0"
val j2ee7 = "javax" % "javaee-api" % "7.0"



libraryDependencies ++= Seq(
    j2ee7,
    junit,
    junitSbtInterface,
    gwt
)

javaSource in Compile := base.value / "src"

javaSource in Test := base.value / "testsrc"

unmanagedJars in Compile ++= {
    val base = baseDirectory.value
    val webinflib: File = file("WebContent/WEB-INF/lib")
    val baseDirectories = (base / "lib") +++ (base / "testlib") +++ webinflib //+++ serverPath.value
    val customJars = (baseDirectories ** "*.jar")
    customJars.classpath
}

//Seq(webSettings :_*)

//webappResources in Compile := Seq(baseDirectory.value / "WebContent")

// disable .jar publishing
publishArtifact in (Compile, packageBin) := false

// create an Artifact for publishing the .war file
artifact in (Compile, packageWar) := {
  val previous: Artifact = (artifact in (Compile, packageWar)).value
  previous.copy(`type` = "war", extension = "war")
}

// add the .war file to what gets published
//addArtifact(artifact in (Compile, packageWar), packageWar)
