echo "> docker image pull "

docker pull breadgood/dockerhub:latest

DOCKER_APP_NAME=springproject

EXIST_BLUE=$(/usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml ps | grep Up)

if [ -z "$EXIST_BLUE" ]; then
    echo "> blue up"

    /usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d
    echo "set \$service_url http://127.0.0.1:8082;" | sudo tee /etc/nginx/conf.d/service-url.inc
    # --build

    echo "> 엔진엑스 Reload"
    sudo service nginx reload

#    sleep 10

    /usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml down
    echo "green down"

else
    echo "> green up"

    /usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d
    echo "set \$service_url http://127.0.0.1:8081;" | sudo tee /etc/nginx/conf.d/service-url.inc
    # --build

    echo "> 엔진엑스 Reload"
    sudo service nginx reload

#    sleep 10

    /usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml down
    echo "> blue down"
fi