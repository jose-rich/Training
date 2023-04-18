FROM openjdk:15
EXPOSE 8888
ADD target/cloud-config-server.jar cloud-config-server.jar
ENTRYPOINT ["java", "-jar", "/cloud-config-server.jar"]