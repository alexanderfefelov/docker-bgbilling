name := "populator"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.9.9",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-bill" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-bonus" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-card" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-dispatch" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-inet" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-kernel" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-moneta" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-mps" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-oss" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-qiwi" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-rscm" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-db-subscription" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-bill" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-card" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-inet" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-kernel" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-moneta" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-oss" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-qiwi" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-rscm" % "0.1.0-SNAPSHOT",
  "com.github.alexanderfefelov" %% "bgbilling-api-soap-subscription" % "0.1.0-SNAPSHOT"
)
