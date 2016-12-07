-- Модули -> Разовые услуги -> Конфигурация модуля
insert into module_config(mid, uid, dt, title, active, config)
  values(3, 1, now(), 'Default', 1, '
# Начисление денег сразу по добавлению услуги в договор
hot.calc=1
');
