#!/bin/bash

# Перекомпилируем весь динамический код
curl --header "Content-Type: text/xml" --request POST --silent --output /dev/null --write-out "%{http_code}\n" \
  -d '<?xml version="1.0" ?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Header><auth xmlns="http://ws.base.kernel.bgbilling.bitel.ru/" pswd="admin" user="admin"></auth></S:Header><S:Body><ns2:recompileAll xmlns:ns2="http://common.dynamic.kernel.bgbilling.bitel.ru/"></ns2:recompileAll></S:Body></S:Envelope>' \
  http://localhost:8080/bgbilling/executer/ru.bitel.bgbilling.kernel.dynamic/DynamicCodeService

# Перечитываем конфигурацию на серверах
curl --header "Content-Type: text/xml" --request POST --silent --output /dev/null --write-out "%{http_code}\n" \
  -d '<?xml version="1.0" ?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><S:Header><auth xmlns="http://ws.base.kernel.bgbilling.bitel.ru/" pswd="admin" user="admin"></auth></S:Header><S:Body><ns5:deviceReload xmlns:common="http://common.bitel.ru" xmlns:ns5="http://service.common.api.inet.modules.bgbilling.bitel.ru/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"></ns5:deviceReload></S:Body></S:Envelope>' \
  http://localhost:8080/bgbilling/executer/ru.bitel.bgbilling.modules.inet.api/1/InetDeviceService
