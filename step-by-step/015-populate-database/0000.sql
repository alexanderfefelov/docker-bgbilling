/*

Создаем конфигурацию

*/

delete from module_config;

insert into module_config(uid, title, active, config)
  values(1, 'Default', 1, '
# Форматы адресов
addrs.format.pattern.Обычный=(${city}), (${street}), (д. ${house})(${frac}) (, кв. ${flat})(, п. ${pod})(, э. ${floor})(, ${comment})
addrs.format.list=Обычный
');
