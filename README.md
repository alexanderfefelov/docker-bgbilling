    WORK IN PROGRESS!
    DO NOT USE!

# docker-bgbilling

docker-bgbilling -- это набор скриптов, позволяющий развернуть [BGBilling](https://bgbilling.ru/) в контейнерах Docker.

## Особенности

* Контейнеры

| Название | Описание | FQDN
| -------- | -------- | ----
| `bgbilling-billing`         | Сервер BGBilling | `billing.bgbilling.local`
| `bgbilling-scheduler`       | Планировщик BGBilling | `scheduler.bgbilling.local`
| `bgbilling-access`          | Сервер BGInetAccess | `access.bgbilling.local`
| `bgbilling-accounting`      | Сервер BGInetAccounting | `accounting.bgbilling.local`
| `bgbilling-mysql-master`    | Master-сервер MySQL | `master.mysql.bgbilling.local` 
| `bgbilling-mysql-backup`    | Slave-сервер MySQL для резервного копирования, read-only | `backup.mysql.bgbilling.local`
| `bgbilling-mysql-slave`     | Slave-сервер MySQL для отчётов, read-only | `slave.mysql.bgbilling.local`
| `bgbilling-activemq`        | Сервер ActiveMQ | `activemq.bgbilling.local`
| `bgbilling-graphite-statsd` | Сервер Graphite и StatsD | `graphite.bgbilling.local`, `statsd.bgbilling.local`
| `bgbilling-grafana`         | Сервер Grafana | `grafana.bgbilling.local`

Все контейнеры могут работать на одном физическом хосте. При необходимости контейнеры можно расположить
на разных физических хостах в произвольных комбинациях.

Для связи между собой контейнеры используют DNS-имена, указанные в столбце FQDN в таблице выше. Ваш DNS-сервер должен
правильно резолвить эти имена.


* JVM

В качестве Java-машины для всех приложений BGBilling используется [GraalVM](https://www.graalvm.org/). Это позволяет
выполнять код на JavaScript, Python 3, Ruby и R из BGBilling ([примеры](dyn/container/dyn/demo/)), и наоборот, использовать [API BGBilling](https://bgbilling.ru/v7.1/javadoc/index.html)
из кода на перечисленных языках ([примеры](dyn/container/polyglot/demo/)).

* База данных

Для MySQL реализована master-slave репликация. Для slave-серверов включен режим `super_read_only`.

## Как это запустить?

```bash
git clone https://github.com/alexanderfefelov/docker-bgbilling.git
cd docker-bgbilling/step-by-step
# TO BE WRITTEN

```

## Как это удалить?

```bash
utils/remove-all.sh
```
