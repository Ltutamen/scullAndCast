ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val mygame =
  (project in file("."))
    .enablePlugins(ScalaJSPlugin, SbtIndigo) // Enable the Scala.js and Indigo plugins
    .settings( // Standard SBT settings
      name := "game-sandbox",
      version := "0.0.1",
      scalaVersion := "3.2.0",
      organization := "org.mygame"
    )
    .settings( // Indigo specific settings
      showCursor := true,
      title := "Scull&Cast",
      gameAssetsDirectory := "assets",
      windowStartWidth := 720, // Width of Electron window, used with `indigoRun`.
      windowStartHeight := 480, // Height of Electron window, used with `indigoRun`.
      libraryDependencies ++= Seq(
        "io.indigoengine" %%% "indigo" % "0.14.0",
        "io.indigoengine" %%% "indigo-extras" % "0.14.0",
        "io.indigoengine" %%% "indigo-json-circe" % "0.14.0",
      )
    )

addCommandAlias("buildGame", ";compile;fastOptJS;indigoBuild")
addCommandAlias("runGame", ";compile;fastOptJS;indigoRun")
addCommandAlias("buildGameFull", ";compile;fullOptJS;indigoBuildFull")
addCommandAlias("runGameFull", ";compile;fullOptJS;indigoRunFull")