FROM openjdk:8-jre
ADD target/manage-console-module-system-2.2.5.RELEASE.jar /usr/jar/manage-console-module-system-2.2.5.RELEASE.jar
CMD ["java", "-jar","/usr/jar/manage-console-module-system-2.2.5.RELEASE.jar","--spring.profiles.active=test"]
