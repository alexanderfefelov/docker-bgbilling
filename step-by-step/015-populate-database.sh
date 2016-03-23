DATA_DIR=015-populate-database

for file in $DATA_DIR/*.sql; do
  echo $file
  mysql --host=127.0.0.1 bgbilling < $file
done
