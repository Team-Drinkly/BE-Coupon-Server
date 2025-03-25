FROM openjdk:21
COPY ./build/libs/coupon-service.jar coupon-service.jar
#ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "member-service.jar"]
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "-jar", "coupon-service.jar"]