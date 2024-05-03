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

libraryDependencies += "org.scalactic" %% "scalactic" % scalaTicVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTicVersion % "test"
libraryDependencies += "org.scalamock" %% "scalamock" % "6.0.0" % "test"

libraryDependencies += "com.typesafe.slick" %% "slick" % slickVersion
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % slickVersion

libraryDependencies += "ch.qos.logback"  %  "logback-classic"  % "1.5.6"

libraryDependencies += "org.postgresql" % "postgresql" % pgVersion
