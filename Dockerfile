FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY run.sh .
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
RUN chmod 777 run.sh
ENTRYPOINT ["./run.sh"]