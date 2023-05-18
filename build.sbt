ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "final-assignment"
  )
libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "ch.qos.logback" % "logback-classic" % "1.3.5"
)
val AkkaVersion = "2.6.8"
//val AkkaHttpVersion = "10.2.4"
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
//  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
//
////  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
//)
//val AkkaHttpVersion = "10.5.2"
//libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.6.14",
  "com.typesafe.akka" % "akka-stream-typed_2.13" % "2.6.14",
  "com.typesafe.akka" % "akka-http_2.13" % "10.2.4",
  "com.typesafe.akka" % "akka-http-spray-json_2.13" % "10.2.4"
)
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC7"