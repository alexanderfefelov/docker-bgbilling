-- Сервис -> Администрирование -> Планировщик заданий
insert into scheduled_tasks(mm, dm, dw, hh, min, `prior`, date1, date2, status, class_id, class, module_id, comment, params) values
  (0, 0, 0, 0, 0, 1, null, null, 1, -1, 'ru.bitel.bgbilling.modules.npay.server.Calculator', '2', '', 'mid=2'),
  (0, 0, 0, 0, 0, 1, null, null, 1, -1, 'ru.bitel.bgbilling.modules.rscm.server.Calculator', '3', '', 'mid=3');
