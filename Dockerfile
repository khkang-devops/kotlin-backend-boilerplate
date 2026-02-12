FROM azul/zulu-openjdk-debian:17

RUN adduser --system --group --no-create-home appuser

COPY build/libs/*.jar /opt/app.jar

# datadog
COPY libs/dd-java-agent.jar /opt/dd-java-agent.jar

USER appuser

# ref: https://spring.io/guides/topicals/spring-boot-docker/

ARG SPRING_PROFILE
ARG dd_version

ENV SPRING_PROFILE=${SPRING_PROFILE:-default}

# base options
ENV BASE_OPTS="-Djava.security.egd=file:/dev/./urandom"
ENV BASE_OPTS="$BASE_OPTS -Duser.timezone=Asia/Seoul -Dfile.encoding=UTF-8"
ENV BASE_OPTS="$BASE_OPTS -Dfile.encoding=UTF-8"

# datadog
ENV BASE_OPTS="$BASE_OPTS -javaagent:/opt/dd-java-agent.jar"
ENV BASE_OPTS="$BASE_OPTS -Ddd.version=${dd_version}"

# JAVA_OPTS : heap size 조정이 필요할때 사용 하도록 한다.
# helm chart 에서 주입한다
# ex. -Xms1024M -Xmx1024M
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java ${BASE_OPTS} ${JAVA_OPTS} -jar /opt/app.jar --spring.profiles.active=${SPRING_PROFILE}"]