echo 'shutdown......'
kill -9 `ps -ef|grep java | grep jar/mall-mg-1.0.0.jar | awk '{print $2}'` > /dev/null 2>&1
