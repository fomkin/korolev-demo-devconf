enablePlugins(JavaServerAppPackaging)
enablePlugins(LinuxPlugin)
enablePlugins(DebianPlugin)
enablePlugins(JDebPackaging)
enablePlugins(SystemVPlugin)
  
name := "TODO tracker"
scalaVersion := "2.12.2"
version := "0.1.0"
maintainer := "Aleksey Fomkin <aleksey.fomkin@gmail.com>"
packageSummary := "Simple GTD web application"
packageDescription := "Korolev based demo application for my speak at DevConf Moscow 2017"

libraryDependencies ++= Seq(
  "com.github.fomkin" %% "korolev-server-blaze" % "0.3.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)
