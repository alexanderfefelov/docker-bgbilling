/*

  Run this demo with:

      docker exec --tty bgbilling-billing \
        node --jvm --jvm.cp=/bgbilling/lib/app/kernel.jar:/bgbilling/lib/ext/joda-time-2.9.9.jar \
          /bgbilling/polyglot/demo/demo.js

*/

console.log('Hello from JavaScript');

// Java: Calling static method
//
const VersionInfoType = Java.type('bitel.billing.common.VersionInfo');
const versionInfoString = VersionInfoType.getVersionInfo('server').getVersionString();
console.log(versionInfoString);

// Java: Using objects
//
const DateTimeFormatType = Java.type('org.joda.time.format.DateTimeFormat');
const formatter = DateTimeFormatType.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z");
const DateTimeType = Java.type('org.joda.time.DateTime');
const dt = new DateTimeType(2018, 5, 31, 4, 0, 0, 0);
console.log(dt.toString(formatter));

// Plain old JavaScript
//
const fs = require('fs');
const path = '/bgbilling/polyglot/demo';
const onlyFiles = fs.readdir(path);
onlyFiles.forEach(f => {
    console.log(f);
});
