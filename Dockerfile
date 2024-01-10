FROM docker.io/library/openjdk:11
COPY target/news-portal-0.0.1.jar backend.jar
CMD ["java", "-jar", "backend.jar"]