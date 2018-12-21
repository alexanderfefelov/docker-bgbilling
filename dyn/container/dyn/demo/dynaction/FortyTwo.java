package demo.dynaction;

import bitel.billing.server.ActionBase;

public class FortyTwo extends ActionBase {

    @Override
    public void doAction() throws Exception {
        setMsg("Forty-Two");

        // Response:
        //   <?xml version="1.0" encoding="UTF-8"?>
        //   <data status="message">Forty-Two</data>
    }

}
