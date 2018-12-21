package demo.dynaction;

import bitel.billing.server.ActionBase;

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

        // Response:
        //   <data result="42.0" status="ok"/>
        // or
        //   <data errorMessage="Обратитесь к хрустальному шару" status="error"/>
    }

}
