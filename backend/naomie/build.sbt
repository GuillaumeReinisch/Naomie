import org.fusesource.scalate.ScalatePlugin.ScalateKeys._
import org.scalatra.sbt._

val ScalatraVersion = "2.5.0"

ScalatraPlugin.scalatraSettings

scalateSettings

organization := "com.github.GuillaumeReinisch"

name := "Naomie"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.1"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-jackson" % "3.5.0",
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-swagger"  % ScalatraVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "com.google.cloud" % "google-cloud" % "0.9.2-alpha",
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

scalateTemplateConfig in Compile := {
  val base = (sourceDirectory in Compile).value
  Seq(
    TemplateConfig(
      base / "webapp" / "WEB-INF" / "templates",
      Seq.empty,  /* default imports should be added here */
      Seq(
        Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
      ),  /* add extra bindings here */
      Some("templates")
    )
  )
}

enablePlugins(JettyPlugin)
