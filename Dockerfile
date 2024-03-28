FROM openjdk:21
COPY target/RepoApp-0.0.1-SNAPSHOT.jar repoapp.jar
ENTRYPOINT ["java", "-jar", "repoapp.jar"]

