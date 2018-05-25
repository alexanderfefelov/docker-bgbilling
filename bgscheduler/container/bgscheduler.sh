#!/bin/bash

export BGBILLING_HOME=/bgbilling

# Если в каталоге bootstrap существуют zip-файлы, считаем их модулями (плагинами),
# извлекаем из них server.jar, переносим server.jar в lib/app, и удаляем zip-файлы
#
MODULES=$BGBILLING_HOME/bootstrap/*.zip
for m in $MODULES
do
  if [ -f $m ]
  then
    echo Installing $m
    filename=`basename $m .zip`
    IFS=_ read name version <<EOF
$filename
EOF
    unzip -p $m server.zip | funzip > $name.jar
    mv --force $name.jar $BGBILLING_HOME/lib/app
    rm --force $m
  fi
done

service bgscheduler start
