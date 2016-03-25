DATA_DIR=030-populate-database

for dir in $DATA_DIR/010-sql/*; do
  echo $dir
  for file in $dir/*.sql; do
    echo $file
    mysql --host=127.0.0.1 bgbilling < $file
  done
done

SOAP_BASE_URL=http://localhost:8080/bgbilling/executer
while read -r line
do
  if [ -n "$line" ]; then
    IFS=' ' read -a query <<< $line
    echo -n ${query[0]}...
    curl --header "Content-Type: text/xml" --request POST --silent --output /dev/null --write-out "%{http_code}\n" --data-binary @$DATA_DIR/020-soap/${query[0]} $SOAP_BASE_URL/${query[1]}
  fi
done < $DATA_DIR/020-soap/queries
