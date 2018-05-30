/*

  Run this demo with:

      docker exec --tty bgbilling-billing \
        polyglot --jvm --jvm.cp=/bgbilling/lib/app/kernel.jar \
          /bgbilling/polyglot/demo/ServerVersionInfo.js

 */

const VersionInfo = Java.type('bitel.billing.common.VersionInfo');
const versionInfoString = VersionInfo.getVersionInfo('server').getVersionString();
console.log(versionInfoString);
