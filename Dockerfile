# Alpine Linux with OpenJDK JRE
#FROM openjdk:8-jre-alpine
# copy WAR into image
#COPY ./build/libs/happyparking-0.0.1-SNAPSHOT.jar /app.war 
# run application with this command line 
#CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.war"]

FROM openjdk:8-jdk as builder

CMD ["gradle"]

ENV GRADLE_HOME /opt/gradle
ENV GRADLE_VERSION 5.4

ARG GRADLE_DOWNLOAD_SHA256=c8c17574245ecee9ed7fe4f6b593b696d1692d1adbfef425bef9b333e3a0e8de
RUN set -o errexit -o nounset \
    && echo "Downloading Gradle" \
    && wget --no-verbose --output-document=gradle.zip "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" \
    \
    && echo "Checking download hash" \
    && echo "${GRADLE_DOWNLOAD_SHA256} *gradle.zip" | sha256sum --check - \
    \
    && echo "Installing Gradle" \
    && unzip gradle.zip \
    && rm gradle.zip \
    && mv "gradle-${GRADLE_VERSION}" "${GRADLE_HOME}/" \
    && ln --symbolic "${GRADLE_HOME}/bin/gradle" /usr/bin/gradle \
    \
    && echo "Adding gradle user and group" \
    && groupadd --system --gid 1000 gradle \
    && useradd --system --gid gradle --uid 1000 --shell /bin/bash --create-home gradle \
    && mkdir /home/gradle/.gradle \
    && chown --recursive gradle:gradle /home/gradle \
    \
    && echo "Symlinking root Gradle cache to gradle Gradle cache" \
    && ln -s /home/gradle/.gradle /root/.gradle

# Create Gradle volume
USER gradle
VOLUME "/home/gradle/.gradle"
WORKDIR /home/gradle

RUN set -o errexit -o nounset \
    && echo "Testing Gradle installation" \
    && gradle --version

USER root
COPY . /happyparking/
RUN cd /happyparking && gradle clean && gradle build

# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
# copy WAR into image
COPY --from=builder /happyparking/build/libs/happyparking-0.0.1-SNAPSHOT.jar /app.war
# run application with this command line
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.war"]
