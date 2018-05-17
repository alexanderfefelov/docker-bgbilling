#!/bin/bash

export BGBILLING_HOME=/bgbilling

# Если существует файл dump.sql, выполняем его в MySQL и удаляем
#
if [ -f $BGBILLING_HOME/dump.sql ]
then
  echo -ne Seeding database...
  mysql --host=mysql --default-character-set=utf8 < $BGBILLING_HOME/dump.sql
  rm -f $BGBILLING_HOME/dump.sql
  echo done
fi

service bgbilling start
service bgscheduler start

# Если в каталоге install существуют zip-файлы, устанавливаем их как модули (плагины) и удаляем
#
MODULES=$BGBILLING_HOME/install/*.zip
for m in $MODULES
do
  if [ -f $m ]
  then
    echo Installing $m
    $BGBILLING_HOME/bg_installer.sh $m
    rm -f $m
  fi
done
