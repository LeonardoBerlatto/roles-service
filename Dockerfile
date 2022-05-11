FROM openjdk:17-alpine
MAINTAINER "Leonardo Berlatto"

ENV IMAGE_VERSION=1.0.0
ENV IMAGE_NAME=roles


WORKDIR /app

COPY target/$IMAGE_NAME-$IMAGE_VERSION.jar /app/$IMAGE_NAME.jar

ENTRYPOINT java -jar /app/$IMAGE_NAME.jar