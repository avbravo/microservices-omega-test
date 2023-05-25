echo '________________________________________'
echo ' '
echo 'Download from Docker to House'
echo ' '
echo '________________________________________'
echo 'Iniciando proceso....'
echo '      download [accreditation.gz]'
  docker cp c27edf2f87f4:/home/avbravo/accreditation.gz  /home/avbravo/Descargas/accreditation.gz

echo '      download [configurationjmoordbdb.gz]'
docker cp c27edf2f87f4:/home/avbravo/configurationjmoordbdb.gz  /home/avbravo/Descargas/configurationjmoordbdb.gz

echo '      download [sft.gz]'
docker cp c27edf2f87f4:/home/avbravo/sft.gz  /home/avbravo/Descargas/sft.gz


echo 'Proceso [finalizado]'


