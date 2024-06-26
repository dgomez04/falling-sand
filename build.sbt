val scala3Version = "3.4.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "falling-sand",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalafx" %% "scalafx" % "16.0.0-R24",
      "org.scalameta" %% "munit" % "0.7.29" % Test 
    )
  )
