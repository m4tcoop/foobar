import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "foobar"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
    )


	    def publishArtifact(task: TaskKey[File]) = addArtifact(artifact in (Compile, task), task in Compile)

	    val buildSettings: Seq[Setting[_]] = Defaults.defaultSettings ++ Seq(
	      name := appName,
	      version := appVersion,
	      organization := "com.automatedlabs.demo",

	      scalaVersion := "2.9.1",

	      publishTo <<= version { (v: String) =>
	        val nexus = "http://ec2-174-129-75-167.compute-1.amazonaws.com:8080/nexus/"
	        if (v.trim.endsWith("SNAPSHOT")) 
	          Some("snapshots" at nexus + "content/repositories/snapshots") 
	        else
	          Some("releases"  at nexus + "content/repositories/releases")
	      },
	      credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

	    ) ++ 
	    addArtifact(Artifact(appName, "bundle", "zip"), dist)

	    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA, settings = buildSettings)
}
