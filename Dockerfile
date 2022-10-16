FROM openjdk:11

COPY target/ecommerce-0.0.1-SNAPSHOT.jar  /usr/app/

WORKDIR /usr/app/

ENTRYPOINT ["java", "-jar", "ecommerce-0.0.1-SNAPSHOT.jar"]