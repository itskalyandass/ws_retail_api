FROM openjdk:11
EXPOSE 8080

ADD target/ws_retail_api-1.0.0.jar ws_retail_api.jar
ENTRYPOINT exec java -jar ws_retail_api.jar