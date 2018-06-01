/*

  Для выполнения этого файла:
      1. Запустите BGBillingClient.
      2. Сервис -> Автоматизация -> Управление динамическим кодом.
      3. Найдите и откройте этот файл.
      4. Нажмите кнопку [F9].

*/

package demo;

import com.github.alexanderfefelov.bgbilling.dyn.graalvm.*;
import org.graalvm.polyglot.Value;

public class PolyglotJavaScriptDemo {

    public static void main(String[] args) {
        String text = String.join("\n",
                "// JavaScript",
                "console.log('Hello from JavaScript');",
                "const VersionInfoType = Java.type('bitel.billing.common.VersionInfo');",
                "const versionInfoString = VersionInfoType.getVersionInfo('server').getVersionString();",
                "versionInfoString;");
        System.out.println(text);

        PolyglotRunner runner = new PolyglotRunner();
        Value result = runner.runLiteralText(PolyglotLanguage.JS, text, 50000,
                true, true, true, true);

        System.out.println(result.toString());
    }

}
