# docker-bgbilling

BGBilling in a container - work in progress

docker-bgbilling -- это набор скриптов, позволяющий развернуть [BGBilling](https://bgbilling.ru/) в контейнерах Docker.
Контейнеры могут работать как на одном физическом хосте, так и на нескольких.

| Контейнер | Описание | FQDN
| --------- | -------- | ----
| `bgbilling-mysql-master` | Master-сервер MySQL | `master.mysql.bgbilling.local` 
| `bgbilling-mysql-backup` | Slave-сервер MySQL для резервного копирования, read-only | `backup.mysql.bgbilling.local`
| `bgbilling-mysql-slave`  | Slave-сервер MySQL для отчётов, read-only | `slave.mysql.bgbilling.local`
| `bgbilling-activemq`     | Сервер ActiveMQ | `activemq.bgbilling.local`
| `bgbilling-billing`      | Сервер BGBilling | `billing.bgbilling.local`
| `bgbilling-scheduler`    | Планировщик BGBilling | `scheduler.bgbilling.local`
| `bgbilling-access`       | Сервер BGInetAccess | `access.bgbilling.local`
| `bgbilling-accounting`   | Сервер BGInetAccounting | `accounting.bgbilling.local`

Контейнеры для связи между собой используют DNS-имена, указанные в столбце FQDN в таблице выше. Ваш DNS-сервер должен
правильно резолвить эти имена.
