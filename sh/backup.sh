echo '________________________________________'
echo ' '
echo 'Backup de MongoDB[mongodump]'
echo ' '
echo '________________________________________'
echo 'Iniciando proceso....'
echo '      mongodump [accreditation]'
mongodump --archive=accreditation.gz --gzip --db=accreditation
echo '      mongodump [configurationjmoordbdb]'
mongodump --archive=configurationjmoordbdb.gz --gzip --db=configurationjmoordbdb
echo '      mongodump [sft]' 
mongodump --archive=sft.gz --gzip --db=sft
echo 'Proceso [finalizado]'







