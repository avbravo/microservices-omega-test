echo '________________________________________'
echo ' '
echo 'Sube archivos desde House a Docker'
echo ' '
echo '________________________________________'
echo 'Iniciando proceso....'
echo '      subiendo [accreditation.gz]'
docker cp /home/avbravo/Descargas/accreditation.gz c27edf2f87f4:/home/avbravo/accreditation.gz
echo '      subiendo [configurationjmoordbdb.gz]'
docker cp /home/avbravo/Descargas/configurationjmoordbdb.gz c27edf2f87f4:/home/avbravo/configurationjmoordbdb.gz
echo '      subiendo [sft.gz]' 
docker cp /home/avbravo/Descargas/sft.gz c27edf2f87f4:/home/avbravo/sft.gz
echo 'Proceso [finalizado]'



