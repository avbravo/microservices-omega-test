

---
## House

### Docker

- Ver las imagenes
```
docker ps -a 
```

- House

```
docker exec -it c27edf2f87f4  bash

```

## Subir script

```shell
docker cp /home/avbravo/Descargas/backup.sh c27edf2f87f4:/home/avbravo/backup.sh
docker cp /home/avbravo/Descargas/restoredb.sh c27edf2f87f4:/home/avbravo/restoredb.sh

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

-House
```
docker cp c27edf2f87f4:/home/avbravo/accreditation.gz  /home/avbravo/Descargas/accreditation.gz

docker cp c27edf2f87f4:/home/avbravo/configurationjmoordbdb.gz  /home/avbravo/Descargas/configurationjmoordbdb.gz

docker cp c27edf2f87f4:/home/avbravo/sft.gz  /home/avbravo/Descargas/sft.gz

docker cp c27edf2f87f4:/home/avbravo/historydb.gz  /home/avbravo/Descargas/historydb.gz

docker cp c27edf2f87f4:/home/avbravo/sfthistory.gz  /home/avbravo/Descargas/sfthistory.gz

docker cp c27edf2f87f4:/home/avbravo/accreditationhistory.gz  /home/avbravo/Descargas/accreditationhistory.gz

```
## Restauración

---
### Copiar desde Disco a Docker
```
docker cp /home/avbravo/Descargas/accreditation.gz c27edf2f87f4:/home/avbravo/accreditation.gz

docker cp /home/avbravo/Descargas/configurationjmoordbdb.gz c27edf2f87f4:/home/avbravo/configurationjmoordbdb.gz
  
docker cp /home/avbravo/Descargas/sft.gz c27edf2f87f4:/home/avbravo/sft.gz

docker cp /home/avbravo/Descargas/historydb.gz c27edf2f87f4:/home/avbravo/historydb.gz

docker cp /home/avbravo/Descargas/sfthistory.gz c27edf2f87f4:/home/avbravo/sfthistory.gz

docker cp /home/avbravo/Descargas/accreditationhistory.gz c27edf2f87f4:/home/avbravo/accreditationhistory.gz


```

### Ingresar a Docker

- Ver las imagenes
```
docker ps -a 
```

- House

```
docker exec -it c27edf2f87f4  bash

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


# Front End

## Iconos
### favicon
[https://iconmonstr.com/school-30-png/](https://iconmonstr.com/school-30-png/)

### icon-primeblocks.svg
[https://iconmonstr.com/rocket-17-svg/](https://iconmonstr.com/rocket-17-svg/)