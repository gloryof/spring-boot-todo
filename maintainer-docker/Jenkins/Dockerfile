FROM jenkins:alpine

ENV MAVEN_HOME=/usr/share/jenkins/ref/maven
ENV MAVEN_VERSION=3.3.9
ENV INSTALLED_DIR=${MAVEN_HOME}/apache-maven-${MAVEN_VERSION}

RUN mkdir -p ${MAVEN_HOME} \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
      | tar -xzC ${MAVEN_HOME}

COPY conf/custom.groovy /usr/share/jenkins/ref/init.groovy.d/custom.groovy
