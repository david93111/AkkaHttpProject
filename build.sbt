name := "AkkaHttpProject"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1",
  "com.typesafe.akka" %% "akka-http" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.0" % "test",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
  "junit" % "junit" % "4.11",
  "com.github.tomakehurst" % "wiremock" % "2.4.1",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.14",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
  "com.typesafe.play" %% "play-json" % "2.5.7",
  "org.postgresql" % "postgresql" % "42.0.0"
)

mainClass in Compile := Some("com.co.boot.Startup")

enablePlugins(JavaServerAppPackaging)

packageName in Universal := name.value

parallelExecution in Test := false
fork in Test := false
    