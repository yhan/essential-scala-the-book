name := "essential-scala"

version := "0.1"

scalaVersion := "2.13.1"


libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"

lazy val akkaVersion = "2.6.0"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.1" % "test",
    "org.typelevel" %% "cats-core" % "2.0.0"
)

// https://mvnrepository.com/artifact/org.scala-lang/scala-actors
libraryDependencies += "org.scala-lang" % "scala-actors" % "2.11.7"