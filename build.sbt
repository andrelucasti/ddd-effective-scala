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

lazy val zio = Seq(
  "dev.zio"       %% "zio"            % "2.0.22",
  "dev.zio"       %% "zio-json"       % "0.6.2",
  "dev.zio"       %% "zio-http"       % "3.0.0-RC6",
)

lazy val javalin = Seq(
  "io.javalin" % "javalin" % "6.1.4",
  "io.javalin" % "javalin-bundle" % "6.1.3"
)

lazy val slick = Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.typesafe.slick" %% "slick-testkit" % slickVersion
)

//Core
libraryDependencies += "org.scalactic" %% "scalactic" % scalaTicVersion
libraryDependencies += "com.softwaremill.ox" %% "core" % "0.1.0"
libraryDependencies += "com.google.code.gson" % "gson" % "2.10.1"

libraryDependencies ++= javalin
libraryDependencies ++= zio

//DB
libraryDependencies ++= slick
libraryDependencies += "org.postgresql" % "postgresql" % pgVersion

//Monitoring
libraryDependencies += "ch.qos.logback"  %  "logback-classic"  % "1.5.6"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"

//Tests
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTicVersion % Test
libraryDependencies += "org.scalameta" %% "munit" % "1.0.0-M7" % Test
libraryDependencies +=  "com.github.sbt" % "junit-interface" % "0.13.3" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "6.0.0" % Test

Test / parallelExecution := false
testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")

