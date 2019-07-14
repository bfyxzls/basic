[返回目录](../README.md)

### mysql部署
```
# mac linux
docker run -p 3306:3306 --name mymysql -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql

# windows
cd c: && mkdir mysql && cd mysql && mkdir conf && mkdir logs && mkdir data
docker run -p 3306:3306 --name mymysql -v c:/mysql/conf:/etc/mysql/conf.d -v c:/mysql/logs:/logs -v c:/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql
```
### redis & mysql
```
# 集成开始环境部署包括了mongodb,redis,rabbitmq,mysql等
version: "3.3"
services:

 Redis:
  image: sameersbn/redis:latest
  ports:
    - "6379:6379"
  volumes:
    - redis_data:/var/lib/redis

 mysql:
    image: mysql:latest
    restart: always
    command: --default-authentication-plugin=mysql_native_password
    networks:
      - dev
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: root
      MYSQL_USER: root
      MYSQL_PASSWORD: root
    volumes:
      - mysql_data:/var/lib/mysql

networks:
  dev:
    driver: bridge

volumes:
  redis_data:
  mysql_data:

```
### redis & postsql
```$xslt
# 集成开始环境部署包括了mongodb,redis,rabbitmq,mysql等
version: "3.3"
services:

 Redis:
  image: sameersbn/redis:latest
  ports:
    - "6379:6379"
  volumes:
    - redis_data:/var/lib/redis:Z
  restart: always

 PostgreSQL:
  restart: always
  image: sameersbn/postgresql:9.6-2
  ports:
    - "5432:5432"
  environment:
    - DEBUG=false
    - DB_USER=wang
    - DB_PASS=yunfei
    - DB_NAME=order
  volumes:
    - postgresql_data:/var/lib/postgresql:Z 

networks:
  dev:
    driver: bridge

volumes:
  redis_data:
  postgresql_data:

```
### redis & mongodb & rabbitmq
```$xslt
# 集成开始环境部署包括了mongodb,redis,rabbitmq,mysql等
version: "3.3"
services:

  mongo:
    image: mongo:3.4.10
    ports:
      - "27017:27017"
    networks:
      - dev
    volumes:
      - mongo_data:/data/db
 
  redis:
    image: redis:3.2-alpine
    networks:
      - dev
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
 
  rabbit:
    image: rabbitmq:3.6.10-management-alpine
    hostname: rabbit
    ports:
      - "5672:5672"
      - "15672:15672"
      - "61613:61613"
    networks:
      - dev
    environment:
      RABBITMQ_DEFAULT_VHOST: lind
    volumes:
      - rabbitmq_data:/var/lib/rabbitmq
  
  
networks:
  dev:
    driver: bridge

volumes:
  redis_data:
  mongo_data:
  rabbitmq_data:

```