FROM tomcat:jre11-temurin

ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} /usr/local/tomcat/webapp/ROOT.war

