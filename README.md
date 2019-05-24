    WORK IN PROGRESS!
    DO NOT USE!

# docker-bgbilling

docker-bgbilling -- это набор скриптов, позволяющий развернуть [BGBilling](https://bgbilling.ru/) в контейнерах Docker.

## Особенности

* Контейнеры

    | Название | Описание | FQDN
    |:---------|:---------|:----
    | `bgbilling-billing`         | Сервер BGBilling | `billing.bgbilling.local`
    | `bgbilling-scheduler`       | Планировщик BGBilling | `scheduler.bgbilling.local`
    | `bgbilling-access`          | Сервер BGInetAccess | `access.bgbilling.local`
    | `bgbilling-accounting`      | Сервер BGInetAccounting | `accounting.bgbilling.local`
    | `bgbilling-mysql-master`    | Master-сервер MySQL | `master.mysql.bgbilling.local` 
    | `bgbilling-mysql-backup`    | Slave-сервер MySQL для резервного копирования, read-only | `backup.mysql.bgbilling.local`
    | `bgbilling-mysql-slave`     | Slave-сервер MySQL для отчётов, read-only | `slave.mysql.bgbilling.local`
    | `bgbilling-activemq-1`<br>`bgbilling-activemq-2`<br>`bgbilling-activemq-3` | [Кластер ActiveMQ](activemq/README.md) | `1.activemq.bgbilling.local`<br>`2.activemq.bgbilling.local`<br>`3.activemq.bgbilling.local`
    | `bgbilling-graphite-statsd` | Сервер Graphite и StatsD | `graphite.bgbilling.local`, `statsd.bgbilling.local`
    | `bgbilling-grafana`         | Сервер Grafana | `grafana.bgbilling.local`
    | `bgbilling-telegraf`        | Сенсор Telegraf | `telegraf.bgbilling.local`
    | `bgbilling-influxdb`        | Сервер InfluxDB | `influxdb.bgbilling.local`
    | `bgbilling-kapacitor`       | Сервер Kapacitor | `kapacitor.bgbilling.local`
    | `bgbilling-chronograf`      | Сервер Chronograf | `chronograf.bgbilling.local`
    | `bgbilling-ofelia`          | Планировщик заданий Ofelia | `ofelia.bgbilling.local`
    | `bgbilling-redis`           | Сервер Redis | `redis.bgbilling.local`
    | `bgbilling-vault`           | Сервер Vault | `vault.bgbilling.local`
    
    Все контейнеры могут работать на одном физическом хосте. При необходимости контейнеры можно расположить
    на разных физических хостах в произвольных комбинациях.
    
    Для связи между собой контейнеры используют DNS-имена, указанные в столбце FQDN в таблице выше. Ваш DNS-сервер должен
    правильно резолвить эти имена.

* JVM

    В качестве Java-машины для BGBilling используется [GraalVM](https://www.graalvm.org/). Это позволяет выполнять код
    на JavaScript, Python 3, Ruby и R из BGBilling ([примеры](dyn/container/dyn/demo/polyglot)), и наоборот, использовать
    [API BGBilling](https://bgbilling.ru/v7.1/javadoc/index.html) из кода на перечисленных языках ([примеры](dyn/container/polyglot/demo/)).
    Кроме того, на этих языках можно программировать dynaction ([пример](dyn/container/dyn/demo/dynaction/Python.java)).

* Скриптинг JVM

    Для скриптинга Java-приложений на стороне BGBilling доступны следующие движки:

    | Название | Язык
    |:---------|:----
    | BeanShell Engine        | BeanShell 2.0b5
    | Graal.js                | ECMAScript ECMA - 262 Edition 9
    | Groovy Scripting Engine | Groovy 2.4.11
    | JEXL Engine             | JEXL 2.0
    | Oracle Nashorn          | ECMAScript ECMA - 262 Edition 5.1
    | TruffleRuby             | ruby 2.4.4

* База данных

    Для MySQL реализована master-slave репликация. Для slave-серверов включен режим `super_read_only`.

* Мониторинг

    * Сбор метрик -- [Telegraf](https://www.influxdata.com/time-series-platform/telegraf/), [Graphite](https://graphiteapp.org/), [StatsD](https://github.com/statsd/statsd)
    * Хранилище метрик -- [InfluxDB](https://www.influxdata.com/products/influxdb-overview/), Graphite
    * Визуализация -- [Grafana](https://grafana.com/), [Chronograf](https://www.influxdata.com/time-series-platform/chronograf/)
    * Выявление аномалий, алертинг -- [Kapacitor](https://www.influxdata.com/time-series-platform/kapacitor/), Grafana

## Как это запустить?

```bash
git clone https://github.com/alexanderfefelov/docker-bgbilling.git
cd docker-bgbilling/step-by-step
# TO BE WRITTEN

```

## Как это удалить?

```bash
utils/remove-all-containers-and-volumes.sh
utils/remove-all-images.sh
```
