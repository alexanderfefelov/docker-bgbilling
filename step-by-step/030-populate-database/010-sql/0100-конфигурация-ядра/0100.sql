-- Сервис -> Настройка -> Конфигурация
insert into module_config(mid, uid, dt, title, active, config)
  values(0, 1, now(), 'Default', 1, '
# Форматы адресов
addrs.format=(${city})(, ${street}),(д. ${house})(${frac})(, кв. ${flat})(, п. ${pod})(, э. ${floor})
addrs.format.pattern.Обычный=(${city})(, ${street})(, д. ${house})(${frac})(, кв. ${flat})(, п. ${pod})(, э. ${floor})(, ${comment})
addrs.format.list=Обычный
');
