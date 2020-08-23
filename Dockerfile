FROM gradle:6.5.0-jre14 AS build

WORKDIR /build/customer-service

COPY --chown=gradle:gradle . /build/customer-service

RUN gradle build --no-daemon --console verbose
RUN ls -la /build/customer-service/build/libs

FROM openjdk:14.0.1-jdk-oraclelinux7

COPY --from=build /build/customer-service/build/libs/customer-service-*.jar ./customer-service.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar","customer-service.jar" ]