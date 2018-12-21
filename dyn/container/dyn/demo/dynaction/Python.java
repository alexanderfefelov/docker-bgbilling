package demo.dynaction;

import bitel.billing.server.ActionBase;
import com.github.alexanderfefelov.bgbilling.dyn.framework.PolyglotLanguage;
import com.github.alexanderfefelov.bgbilling.dyn.framework.PolyglotRunner;
import org.graalvm.polyglot.Value;

/*

В конфигурации ядра:

           module       action          class
              |            |              |
            /--\       /------\ /-------------------\
  dynaction:demo.ActionPolyglot=demo.dynaction.Python

Вызов:

                                                           module      action
                                                              |           |
                                                            /--\        /----\
  GET/POST /bgbilling/executer?user=admin&pswd=admin&module=demo&action=Python

Ответ:

  <?xml version="1.0" encoding="UTF-8"?>
  <data status="message">None</data>

*/

public class Python extends ActionBase {

    @Override
    public void doAction() throws Exception {
        String script = "demo/demo.py";
        PolyglotRunner runner = new PolyglotRunner();
        Value result = runner.runFile(PolyglotLanguage.PYTHON, script, 50000,
                true, true, true, true);
        setMsg(result.toString());
    }

}
