FROM maven:3.8.4-openjdk-17 as build
RUN mkdir topjava
WORKDIR topjava
COPY . .
RUN mvn clean package

FROM tomcat:9.0.71
COPY --from=build topjava/config /usr/local/topjava/config
COPY --from=build topjava/target/topjava.war /usr/local/tomcat/webapps/ROOT.war
ENV TOPJAVA_ROOT="/usr/local/topjava"
EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]

