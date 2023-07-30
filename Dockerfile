#FROM registry.access.redhat.com/openjdk/openjdk-11-rhel7
FROM openjdk:17

# App
CMD mkdir /deployments
COPY build/libs/*.jar /deployments/app.jar

# Ports exposing
EXPOSE 9080

# Set entrypoint command
ENTRYPOINT java $JAVA_OPTS -jar /deployments/app.jar