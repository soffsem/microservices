FROM openjdk:17-oracle
RUN mkdir /app
COPY ./build/libs/users-0.0.1.jar /app
EXPOSE 8080
ENV JAVA_OPTS=""
WORKDIR /app
CMD java $JAVA_OPTS -jar users-0.0.1.jar