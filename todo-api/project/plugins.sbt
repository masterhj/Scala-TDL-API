// For better dependency management
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.4.1")

// For code formatting
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")

// For static analysis
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.0.7")

// For packaging as Docker container
addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.7")