"

  Run this demo with:

      docker exec --tty bgbilling-billing \
        polyglot --jvm --jvm.cp=/bgbilling/lib/app/kernel.jar:/bgbilling/lib/ext/joda-time-2.10.jar \
          /bgbilling/polyglot/demo/demo.R

"

cat("Hello from R", sep = "\n")

# Java: Calling static method
#
VersionInfoType <- java.type("bitel.billing.common.VersionInfo")
versionInfoString <- VersionInfoType$getVersionInfo("server")$getVersionString()
cat(versionInfoString, sep = "\n")

# Java: Using objects
#
DateTimeFormatType <- java.type("org.joda.time.format.DateTimeFormat")
formatter <- DateTimeFormatType$forPattern("yyyy-MM-dd'T'HH:mm:ss'Z")
DateTimeType <- java.type("org.joda.time.DateTime")
dt <- new(DateTimeType, 2018, 5, 31, 4, 0, 0, 0)
cat(dt$toString(formatter), sep = "\n")

# Plain old R
#
onlyFiles <- list.files("/bgbilling/polyglot/demo", no.. = TRUE)
cat(onlyFiles, sep = "\n")
