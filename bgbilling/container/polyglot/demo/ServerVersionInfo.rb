=begin

  Run this demo with:

      docker exec --tty bgbilling-billing \
        polyglot --jvm --jvm.cp=/bgbilling/lib/app/kernel.jar \
          /bgbilling/polyglot/demo/ServerVersionInfo.rb

=end

VersionInfo = Java.type('bitel.billing.common.VersionInfo')
versionInfoString = VersionInfo.getVersionInfo('server').getVersionString()
print(versionInfoString)
