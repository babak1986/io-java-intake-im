FROM openjdk:17-jdk

RUN mkdir "/io-java-intake"

WORKDIR "/io-java-intake"

COPY target/io-java-intake-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8525

CMD ["java", "-jar", "app.jar"]