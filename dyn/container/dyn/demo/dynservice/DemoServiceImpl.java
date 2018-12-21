package demo.dynservice;

import ru.bitel.bgbilling.kernel.container.service.server.AbstractService;

import javax.jws.WebService;

/*

В конфигурации ядра:

  dynservice:demo.DemoService=demo.dynservice.DemoServiceImpl

*/

@WebService(targetNamespace="demoService")
public class DemoServiceImpl extends AbstractService implements DemoService {

    /*

    Вызов:

      POST /bgbilling/executer/json/demo/DemoService

      {
        "method": "fortyTwo",
        "user": {
            "user": "admin",
            "pswd": "admin"
        },
        "params": {}
      }


    */
    @Override
    public String fortyTwo() {
        return "Forty-Two";
    }

}
