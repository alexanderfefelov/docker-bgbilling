"""

  Run this demo with:

      docker exec --tty bgbilling-billing \
        polyglot --jvm --jvm.cp=/bgbilling/lib/app/kernel.jar:/bgbilling/lib/ext/joda-time-2.9.9.jar --language python \
          /bgbilling/polyglot/demo/demo.py

"""

import java
from os import listdir
from os.path import isfile, join

# Java: Calling static method
#
version_info_type = java.type("bitel.billing.common.VersionInfo")
version_info_string = version_info_type.getVersionInfo("server").getVersionString()
print(version_info_string)

# Java: Using objects
#
date_time_format_type = java.type("org.joda.time.format.DateTimeFormat")
formatter = date_time_format_type.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z")
date_time_type = java.type("org.joda.time.DateTime")
dt = date_time_type(2018, 5, 31, 4, 0, 0, 0)
print(dt.toString(formatter))

# Plain old Python
#
path = "/bgbilling/polyglot/demo"
only_files = [f for f in listdir(path) if isfile(join(path, f))]
for f in only_files:
    print(f)
