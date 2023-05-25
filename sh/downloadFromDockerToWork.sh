echo '________________________________________'
echo ' '
echo 'Download from Docker to Work'
echo ' '
echo '________________________________________'
echo 'Iniciando proceso....'
echo '      download [accreditation.gz]'
  docker cp e321ee10e65e:/home/avbravo/accreditation.gz  /home/avbravo/Descargas/accreditation.gz

echo '      download [configurationjmoordbdb.gz]'
  docker cp e321ee10e65e:/home/avbravo/configurationjmoordbdb.gz  /home/avbravo/Descargas/configurationjmoordbdb.gz

echo '      download [sft.gz]'
docker cp e321ee10e65e:/home/avbravo/sft.gz  /home/avbravo/Descargas/sft.gz


echo 'Proceso [finalizado]'


