'''

  Run this demo with:

      docker exec --tty bgbilling-billing \
        polyglot --jvm --jvm.cp=/bgbilling/lib/app/kernel.jar --language python \
          /bgbilling/polyglot/demo/ServerVersionInfo.py

'''

import java

version_info = java.type("bitel.billing.common.VersionInfo")
version_info_string = version_info.getVersionInfo("server").getVersionString()
print(version_info_string)
