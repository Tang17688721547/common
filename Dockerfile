FROM openjdk:8-jre
ADD target/manage-console-module-system-2.2.5.RELEASE.jar /data/jar/manage-console-module-system-2.2.5.RELEASE.jar
EXPOSE 8084
CMD ["java", "-jar","/data/jar/manage-console-module-system-2.2.5.RELEASE.jar","--spring.profiles.active=aliyun"]
