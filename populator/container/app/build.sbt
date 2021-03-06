name := "populator"

scalaVersion := "2.12.8"

lazy val apiV = "0.1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.10",
  "com.github.pathikrit" %% "better-files" % "3.5.0",
  "com.typesafe" % "config" % "1.3.3",
  "io.circe" %% "circe-core" % "0.9.3",
  "io.circe" %% "circe-generic" % "0.9.3",
  "io.circe" %% "circe-parser" % "0.9.3",
  "org.codehaus.groovy" % "groovy-all" % "2.5.5" pomOnly(),
  "com.github.alexanderfefelov" %% "bgbilling-api-action-kernel" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-bill" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-bonus" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-card" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-dispatch" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-inet" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-kernel" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-moneta" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-mps" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-oss" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-qiwi" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-rscm" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-db-subscription" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-bill" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-bonus" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-card" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-inet" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-kernel" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-moneta" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-oss" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-qiwi" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-rscm" % apiV,
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-subscription" % apiV
)
