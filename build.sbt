ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(
    name := "ddd-tickets-event",
    idePackagePrefix := Some("io.andrelucas")
  )

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.5.1"