echo '________________________________________'
echo ' '
echo 'Sube archivos desde Work a Docker'
echo ' '
echo '________________________________________'
echo 'Iniciando proceso....'
echo '      subiendo [accreditation.gz]'
  docker cp /home/avbravo/Descargas/accreditation.gz e321ee10e65e:/home/avbravo/accreditation.gz

echo '      subiendo [configurationjmoordbdb.gz]'
  docker cp /home/avbravo/Descargas/configurationjmoordbdb.gz e321ee10e65e:/home/avbravo/configurationjmoordbdb.gz

echo '      subiendo [sft.gz]'
 docker cp /home/avbravo/Descargas/sft.gz e321ee10e65e:/home/avbravo/sft.gz


echo 'Proceso [finalizado]'


 