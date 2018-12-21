package demo.dynaction;

import bitel.billing.server.ActionBase;

/*

В конфигурации ядра:

         module       action           class
            |            |               |
          /--\       /------\ /---------------------\
dynaction:demo.ActionFortyTwo=demo.dynaction.FortyTwo

Вызов:

GET/POST /bgbilling/executer?user=admin&pswd=admin&module=demo&action=FortyTwo

*/

public class FortyTwo extends ActionBase {

    @Override
    public void doAction() throws Exception {
        setMsg("Forty-Two");

        // Ответ:
        //   <?xml version="1.0" encoding="UTF-8"?>
        //   <data status="message">Forty-Two</data>
    }

}
