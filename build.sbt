import com.typesafe.sbt.SbtStartScript

name := "CSC376 Assignments"

version := "1.0"

scalaVersion := "2.11.2"

scalacOptions ++= 
  Seq("-deprecation",
      "-feature",
      "-target:jvm-1.7",
      "-unchecked")

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "spray repo" at "http://repo.spray.io",
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "org.scalatest"  %% "scalatest"  % "2.2.1"  % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.5" % "test",
  "org.pegdown"    %  "pegdown"    % "1.4.2"  % "test",
  "io.netty"               %  "netty-all"       % "4.0.23.Final",
  "com.typesafe.akka"      %% "akka-actor"      % "2.3.6",
  "io.spray"               %% "spray-can"       % "1.3.2",  // fetches spray-{io,http,util}
  "com.wandoulabs.akka"    %% "spray-websocket" % "0.1.3",
  "org.scala-lang.modules" %% "scala-xml"       % "1.0.2"
)

seq(SbtStartScript.startScriptForClassesSettings: _*)
