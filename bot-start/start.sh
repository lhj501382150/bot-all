echo 'starting......'
kill -9 `ps -ef|grep java | grep mall-mg-1.0.0.jar | awk '{print $2}'` > /dev/null 2>&1
jav -jar jar/mall-mg-1.0.0.jar --spring.config.location=config/application.yml --server.port=8001 > logs/catalina.out 2>&1 &

