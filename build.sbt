lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "scalabyrinth",
    organization := "wrthfl",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "com.h2database" % "h2" % "1.4.199",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
    ),
    scalacOptions ++= List("-encoding", "utf8", "-deprecation", "-feature", "-unchecked", "-Xfatal-warnings"),
    javacOptions ++= List("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")
  )


/*
name := """scalabyrinth"""
organization := "wrthfl"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  guice, jdbc, "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)
*/


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "wrthfl.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "wrthfl.binders._"
