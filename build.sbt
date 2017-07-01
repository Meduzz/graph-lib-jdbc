name := "Graph-JDBC"

version := "20170701"

scalaVersion := "2.11.7"

organization := "se.kodiak.tools"

credentials += Credentials(Path.userHome / ".ivy2" / ".tools")

publishTo := Some("se.kodiak.tools" at "http://yamr.kodiak.se/maven")

publishArtifact in (Compile, packageDoc) := false

resolvers += "se.kodiak.tools" at "http://yamr.kodiak.se/maven"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

libraryDependencies += "se.kodiak.tools" %% "graph" % "20170701"

libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1208"

libraryDependencies += "io.getquill" %% "quill-jdbc" % "1.0.1"