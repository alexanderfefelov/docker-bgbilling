DATA_DIR=030-populate-database

for file in $DATA_DIR/sql/*.sql; do
  echo $file
  mysql --host=127.0.0.1 bgbilling < $file
done

SOAP_BASE_URL=http://localhost:8080/bgbilling/executer
while read -r line
do
  IFS=' ' read -a query <<< $line
  echo ${query[0]} '->' ${query[1]}
  curl --header "Content-Type: text/xml" --request POST --silent --output /dev/null --write-out "%{http_code}\n" --data-binary @$DATA_DIR/soap/${query[0]} $SOAP_BASE_URL/${query[1]}
done < $DATA_DIR/soap/queries
