package demo.dynservice;

import ru.bitel.bgbilling.kernel.container.service.server.AbstractService;

/*

В конфигурации ядра:

  dynservice:demo.DemoService=demo.dynservice.DemoServiceImpl

*/

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

    Ответ:

      {
        "status": "ok",
        "exception": null,
        "message": "",
        "tag": null,
        "data": {
          "return": "Forty-Two"
        }
      }

    */
    @Override
    public String fortyTwo() {
        return "Forty-Two";
    }

}
