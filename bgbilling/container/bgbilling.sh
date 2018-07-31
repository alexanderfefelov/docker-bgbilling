#!/bin/bash

# Если в каталоге bootstrap существуют sql-скрипты, выполняем их в MySQL и удаляем при отсутствии ошибок.
# Если в скриптах производится обращение к таблицам, которые ещё не существуют (например, таблицы модулей),
# скрипты не будут удалены, и, следовательно, будут выполнены при следующем запуске
#
SQLS=$BGBILLING_BOOTSTRAP/*.sql
for s in $SQLS
do
  if [ -f $s ]
  then
    echo Executing $s
    mysql --host=master.mysql.bgbilling.local --user=root --password=password --default-character-set=utf8 < $s \
    && rm --force $s
  fi
done

service bgbilling start

# Если в каталоге bootstrap существуют zip-файлы, устанавливаем их как модули (плагины) и удаляем
#
MODULES=$BGBILLING_BOOTSTRAP/*.zip
for m in $MODULES
do
  if [ -f $m ]
  then
    echo Installing $m
    $BGBILLING_HOME/bg_installer.sh $m
    rm --force $m
  fi
done
