# MySQL

```
docker pull mysql/mysql-server:latest
```

```bash
docker run -d -p 3306:3306 --name mysql-docker-container -e MYSQL_ROOT_PASSWORD=rootlibrary -e MYSQL_DATABASE=library -e MYSQL_USER=library -e MYSQL_PASSWORD=library mysql/mysql-server:latest
```