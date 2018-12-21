package demo.dynaction;

import bitel.billing.server.ActionBase;

/*

Вызов:

GET/POST /bgbilling/executer?user=admin&pswd=admin&module=demo&action=Calculator&a=2&b=40&op=add

*/

public class Calculator extends ActionBase {

    @Override
    public void doAction() throws Exception {
        Float a = getFloatParameter("a", 0);
        Float b = getFloatParameter("b", 0);
        String op = getParameter("op", "/");

        switch (op) {
            case "add":
                rootNode.setAttribute("result", Float.toString(a + b));
                break;
            case "divide":
                rootNode.setAttribute("result", Float.toString(a / b));
                break;
            case "multiply":
                rootNode.setAttribute("result", Float.toString(a * b));
                break;
            case "subtract":
                rootNode.setAttribute("result", Float.toString(a - b));
                break;
            default:
                rootNode.setAttribute("status", "error");
                rootNode.setAttribute("errorMessage", "Обратитесь к хрустальному шару");
                break;
        }

        // Ответ:
        //   <?xml version="1.0" encoding="UTF-8"?>
        //   <data result="42.0" status="ok"/>
        // или
        //   <?xml version="1.0" encoding="UTF-8"?>
        //   <data errorMessage="Обратитесь к хрустальному шару" status="error"/>
    }

}
