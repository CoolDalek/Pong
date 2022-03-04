ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.1"

lazy val root = (project in file("."))
  .settings(
    name := "Pong",
    libraryDependencies ++= Dependencies.Gdx(),
    javacOptions ++= Seq(
      "-Xlint",
      "-encoding", "UTF-8",
      "-source", "17",
      "-target", "17"
    ),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature",
      "-encoding", "UTF-8",
    ),
    assembly / assemblyJarName := "pong.jar"
  )

ThisBuild / assemblyMergeStrategy := {
  case PathList(path @ _*) if path.last == "module-info.class" =>
    MergeStrategy.discard
  case x =>
    val strategy = (ThisBuild / assemblyMergeStrategy).value
    strategy(x)
}