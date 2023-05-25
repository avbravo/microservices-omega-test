# Developer Book

Este libro muestra la arquitectura conque fue construido el proyecto.

## Acreditation
Gestiona las acreditaciones del sistema

## POA
El sistema esta asociado a un POA basico con la finalidad de que los jefes de departamento creen anualmente las actividades y asi los colaboradores cuando registren sus tareas lo asignen a estas actividades.
Las autoridades pueden ingresar al POA y ver los trabajos que se van realizando en base a los objetivos estipulados.

### Bases de Datos
Colecciones que se usan
- Area
- Objetivo
- Actividad
- Subactividad

----






## Work
### Docker

- Ver las imagenes
```
docker ps -a 
```
- Entrar al bash
```
docker exec -it e321ee10e65e bash

```


## Subir script

```shell
docker cp /home/avbravo/Descargas/backup.sh e321ee10e65e:/home/avbravo/backup.sh

docker cp /home/avbravo/Descargas/restoredb.sh e321ee10e65e:/home/avbravo/restoredb.sh



```




## Backup MongoDB


Se puede hacer mediante el script backup.sh

o
mediante

```
cd /home/avbravo

mongodump --archive=accreditation.gz --gzip --db=accreditation

mongodump --archive=configurationjmoordbdb.gz --gzip --db=configurationjmoordbdb


mongodump --archive=sft.gz --gzip --db=sft


mongodump --archive=historydb.gz --gzip --db=historydb


mongodump --archive=sfthistory.gz --gzip --db=sfthistory

mongodump --archive=accreditationhistory.gz --gzip --db=accreditationhistory




```

### Copiarlos desde Docker al disco

```
docker cp e321ee10e65e:/home/avbravo/accreditation.gz  /home/avbravo/Descargas/accreditation.gz

docker cp e321ee10e65e:/home/avbravo/configurationjmoordbdb.gz  /home/avbravo/Descargas/configurationjmoordbdb.gz

docker cp e321ee10e65e:/home/avbravo/sft.gz  /home/avbravo/Descargas/sft.gz

docker cp e321ee10e65e:/home/avbravo/historydb.gz  /home/avbravo/Descargas/historydb.gz


docker cp e321ee10e65e:/home/avbravo/sfthistory.gz  /home/avbravo/Descargas/sfthistory.gz

docker cp e321ee10e65e:/home/avbravo/accreditationhistory.gz  /home/avbravo/Descargas/accreditationhistory.gz


```


## Restauración

### Copiar desde Disco a Docker
```
  docker cp /home/avbravo/Descargas/accreditation.gz e321ee10e65e:/home/avbravo/accreditation.gz

  docker cp /home/avbravo/Descargas/configurationjmoordbdb.gz e321ee10e65e:/home/avbravo/configurationjmoordbdb.gz

  docker cp /home/avbravo/Descargas/sft.gz e321ee10e65e:/home/avbravo/sft.gz

  docker cp /home/avbravo/Descargas/historydb.gz e321ee10e65e:/home/avbravo/historydb.gz

  docker cp /home/avbravo/Descargas/sfthistory.gz e321ee10e65e:/home/avbravo/sfthistory.gz

  docker cp /home/avbravo/Descargas/accreditationhistory.gz e321ee10e65e:/home/avbravo/accreditationhistory.gz


```

### Restaurar un gzip
```
mongorestore --gzip --archive=accreditation.gz
 
mongorestore --gzip --archive=configurationjmoordbdb.gz


mongorestore --gzip --archive=sft.gz

mongorestore --gzip --archive=historydb.gz

mongorestore --gzip --archive=sfthistory.gz.gz

mongorestore --gzip --archive=accreditationhistory.gz
```


