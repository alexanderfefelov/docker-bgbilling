FROM openjdk:8-jdk

ENV BGINETACCESS_VERSION=7.1_35 \
\
  BGINETACCESS_HOME=/bginetaccess \
\
  DEBIAN_FRONTEND=noninteractive

RUN apt-get -qq update \
  && apt-get -qq install --yes unzip \
  && apt-get -qq clean \
  && wget --quiet ftp://bgbilling.ru/pub/bgbilling/7.1/data/BGInetAccess_$BGINETACCESS_VERSION.zip \
  && unzip -qq BGInetAccess_$BGINETACCESS_VERSION.zip \
  && mv BGInetAccess $BGINETACCESS_HOME \
  && rm --force BGInetAccess_$BGINETACCESS_VERSION.zip \
  && chmod +x $BGINETACCESS_HOME/*.sh \
  && chmod +x $BGINETACCESS_HOME/script/* \
  && sed -i 's@\/usr\/local\/BGInetAccess@'"$BGINETACCESS_HOME"'@' $BGINETACCESS_HOME/script/bginet_access \
  && ln -s $BGINETACCESS_HOME/script/bginet_access /etc/init.d/bginet_access \
  && update-rc.d bginet_access defaults \
  && rm --recursive --force /var/lib/apt/lists/* /tmp/* /var/tmp/*

ADD container/ $BGINETACCESS_HOME/

VOLUME $BGINETACCESS_HOME

EXPOSE 1812/udp
EXPOSE 67/udp

CMD $BGINETACCESS_HOME/bginetaccess.sh && tail -f /dev/null
