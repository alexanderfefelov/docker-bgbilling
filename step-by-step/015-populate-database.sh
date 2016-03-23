DATA_DIR=015-populate-database
FILE=$DATA_DIR/0000.sql

echo $FILE
mysql --host=127.0.0.1 bgbilling < $FILE
