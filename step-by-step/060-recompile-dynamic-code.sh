curl --header "Content-Type: text/xml" --request POST --silent --output /dev/null --write-out "%{http_code}\n" \
  -d '<?xml version="1.0" ?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Header><auth xmlns="http://ws.base.kernel.bgbilling.bitel.ru/" pswd="admin" user="admin"></auth></S:Header><S:Body><ns2:recompileAll xmlns:ns2="http://common.dynamic.kernel.bgbilling.bitel.ru/"></ns2:recompileAll></S:Body></S:Envelope>' \
  http://localhost:8080/bgbilling/executer/ru.bitel.bgbilling.kernel.dynamic/DynamicCodeService
