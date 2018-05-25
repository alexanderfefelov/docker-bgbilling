#!/bin/bash

export BGBILLING_HOME=/bgbilling
BGBILLING_BOOTSTRAP=$BGBILLING_HOME/bootstrap

# Если в каталоге bootstrap существуют sql-файлы, выполняем их в MySQL и удаляем
#
SQLS=$BGBILLING_BOOTSTRAP/*.sql
for s in $SQLS
do
  if [ -f $s ]
  then
    echo Executing $s
    mysql --host=master.mysql.bgbilling.local --user=root --password=password --default-character-set=utf8 < $s
    rm --force $s
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
