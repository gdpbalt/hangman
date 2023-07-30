FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY . /app

RUN ./gradlew clean build
RUN mv /app/build/libs/* /

CMD ["sh", "-c", "java -jar /hangman-0.0.1-SNAPSHOT.jar"]
