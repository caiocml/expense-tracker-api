# expense-tracker-api
Project to track transactions

First download docker from https://www.docker.com/products/docker-desktop

> This project is using postgresql with docker, another databases is suported but I can't garantee that. 

> If you dont want use docker, skip this and just create a local database, run the .sql file local and add the conection properties on the file application.properties

after install docker, creat a image for postgresdb.

 - download the postgress image
```
docker pull postgres
```

 - run the line command to run postgress, the -p is the port to port, the first one is the host port the second one is the container port
```
docker run -p 5432:5432 -e POSTGRES_PASSWORD="YOUR_PASSWORD" postgres
```

 - copy the .sql file to the postgres image you just created.
```
cd C:/where/your/project/is/expense-tracker-api/

docker cp expense-tracker_db.sql postgresdb:/
```
- run the bash on image:
```
docker container exec -it postgresdb bash
```
 -   then run the command line :
```
psql -U postgres --file expense-tracker_db.sql
```




