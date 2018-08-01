FROM openjdk:8-jdk

ENV SBT_VERSION=0.13.16
RUN curl --location --silent --show-error --output sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb \
  && dpkg -i sbt-$SBT_VERSION.deb \
  && rm sbt-$SBT_VERSION.deb \
  && git clone https://github.com/alexanderfefelov/bgbilling-api.git \
  && cd bgbilling-api \
  && sbt publishLocal \
  && cd .. \
  && rm --force --recursive bgbilling-api

WORKDIR /app

ADD container/* /app/

CMD ["sbt", "run"]
