/*

  Для выполнения этого файла:
      1. Запустите BGBillingClient.
      2. Сервис -> Автоматизация -> Управление динамическим кодом.
      3. Найдите и откройте этот файл.
      4. Нажмите клавишу [F9].

*/

package demo.polyglot;

import com.github.alexanderfefelov.bgbilling.dyn.framework.PolyglotRunner;
import org.graalvm.polyglot.Value;

public class PolyglotRubyDemo {

    public static void main(String[] args) {
        String text = String.join("\n",
                "# Ruby",
                "puts('Hello from Ruby')",
                "VersionInfoType = Java.type('bitel.billing.common.VersionInfo')",
                "versionInfoString = VersionInfoType.getVersionInfo('server').getVersionString()",
                "versionInfoString");
        System.out.println(text);

        PolyglotRunner runner = new PolyglotRunner();
        Value result = runner.runLiteralText(PolyglotRunner.Language.RUBY, text, 50000,
                true, true, true, true);

        System.out.println(result.toString());
    }

}
