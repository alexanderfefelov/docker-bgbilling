-- Модули -> Периодические услуги -> Конфигурация
insert into module_config(mid, uid, dt, title, active, config)
  values(2, 1, now(), 'Default', 1, '
recalculate.on.service.change=1
');
