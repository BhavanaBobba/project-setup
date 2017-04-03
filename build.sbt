name := "project-setup"
organization := "com.myorg"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  filters,
  ws,
  "io.swagger" %% "swagger-play2" % "1.5.2",
  "io.swagger" % "swagger-annotations" % "1.5.10",
  "io.swagger" % "swagger-core" % "1.5.10",
  "io.swagger" % "swagger-models" % "1.5.10",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "org.scalatestplus" %% "play" % "1.4.0-M4" % "test",
  "org.mockito" % "mockito-all" % "1.10.19" % "test",
  "com.mohaine" % "mohaine-utils" % "1.0.2",
  "org.jbehave" % "jbehave-scala" % "3.9.5" % "test"
)

//resolvers := Seq(
//  Resolver.mavenLocal,
//  Resolver sonatypeRepo "public"
//)

resolvers += "TypeSafe Ivy releases" at "http://dl.bintray.com/typesafe/ivy-releases/"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

includeFilter in (Assets, LessKeys.less) := "*.less"
excludeFilter in (Assets, LessKeys.less) := "_*.less"


