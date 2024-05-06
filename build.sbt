ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(
    name := "ddd-tickets-event",
    idePackagePrefix := Some("io.andrelucas")
  )

lazy val slickVersion = "3.5.1"
lazy val scalaTicVersion = "3.2.18"
lazy val pgVersion = "42.7.3"

//Core
libraryDependencies += "org.scalactic" %% "scalactic" % scalaTicVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTicVersion % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "6.0.0" % "test"
libraryDependencies +=  "com.github.sbt" % "junit-interface" % "0.13.3" % Test

//DB
libraryDependencies += "com.typesafe.slick" %% "slick" % slickVersion
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
libraryDependencies += "org.postgresql" % "postgresql" % pgVersion
libraryDependencies += "com.typesafe.slick" %% "slick-testkit" % slickVersion

//Monitoring
libraryDependencies += "ch.qos.logback"  %  "logback-classic"  % "1.5.6"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"

libraryDependencies += "org.scalameta" %% "munit" % "1.0.0-M7" % Test

Test / parallelExecution := false
testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")
