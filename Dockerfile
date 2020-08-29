FROM openjdk:8
EXPOSE 8080
ADD target/covid19-impact-analysis-api.jar covid19-impact-analysis-api.jar
ENTRYPOINT ["java", "-jar", "/covid19-impact-analysis-api.jar"]
