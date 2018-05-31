=begin

  Run this demo with:

      docker exec --tty bgbilling-billing \
        polyglot --jvm --jvm.cp=/bgbilling/lib/app/kernel.jar:/bgbilling/lib/ext/joda-time-2.9.9.jar \
          /bgbilling/polyglot/demo/demo.rb

=end

# Java: Calling static method
#
VersionInfoType = Java.type('bitel.billing.common.VersionInfo')
versionInfoString = VersionInfoType.getVersionInfo('server').getVersionString()
puts(versionInfoString)

# Java: Using objects
#
DateTimeFormatType = Java.type('org.joda.time.format.DateTimeFormat')
formatter = DateTimeFormatType.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z")
DateTimeType = Java.type('org.joda.time.DateTime')
dt = DateTimeType.new(2018, 5, 31, 4, 0, 0, 0)
puts(dt.toString(formatter))

# Plain old Ruby
#
path = '/bgbilling/polyglot/demo/*'
onlyFiles = Dir[path].select { |f| File.file?(f) }
onlyFiles.each { |f| puts(File.basename(f)) }
