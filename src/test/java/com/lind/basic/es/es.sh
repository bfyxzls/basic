# 拉镜像
docker pull elasticsearch:6.7.2
# docker启动
 docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9200:9200 -p 9300:9300 -v d:/es/config/es1.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v d:/es/data1:/usr/share/elasticsearch/data --name ES01 elasticsearch:6.7.2 &
 docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9201:9201 -p 9301:9301 -v d:/es/config/es2.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v d:/es/data2:/usr/share/elasticsearch/data --name ES02 elasticsearch:6.7.2 &
 docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9202:9202 -p 9302:9302 -v d:/es/config/es3.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v d:/es/data3:/usr/share/elasticsearch/data --name ES03 elasticsearch:6.7.2 &

# 验证是否搭建成功
在浏览器地址栏访问http://192.168.9.219:9200/_cat/nodes?pretty 查看节点状态

# 使用elasticsearch-head前端框架
docker pull  yanliangzhong/elasticsearch-head:latest
docker run -d -p 9100:9100 --name es-manager  yanliangzhong/elasticsearch-head:latest
浏览器访问http://192.168.9.219:9100/
