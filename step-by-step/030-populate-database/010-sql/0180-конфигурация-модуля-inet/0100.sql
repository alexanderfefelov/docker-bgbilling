-- Модули -> Интернет -> Конфигурация
insert into module_config(mid, uid, dt, title, active, config)
  values(1, 1, now(), 'Default', 1, '
# ID типа устройства, являющегося (корневым) InetAccounting-сервером. Параметр обязателен!
accounting.deviceTypeIds=1
');
