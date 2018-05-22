FROM alexanderfefelov/graalvm-polyglot

ENV BGBILLING_VERSION=7.1_971 \
\
  BILL_VERSION=7.1_257 \
  BONUS_VERSION=7.1_96 \
  CARD_VERSION=7.1_186 \
  DISPATCH_VERSION=7.1_29 \
  INET_VERSION=7.1_587 \
  MONETA_VERSION=7.1_8 \
  MPS_VERSION=7.1_171 \
  NPAY_VERSION=7.1_167 \
  QIWI_VERSION=7.1_15 \
  RSCM_VERSION=7.1_157 \
  SUBSCRIPTION_VERSION=7.1_14

ENV BGBILLING_HOME=/bgbilling
ENV BGBILLING_DATA=$BGBILLING_HOME/data \
  BGBILLING_INSTALL=$BGBILLING_HOME/install \
\
  DEBIAN_FRONTEND=noninteractive

RUN apt-get -qq update \
  && apt-get -qq install --yes mysql-client unzip \
  && apt-get -qq clean \
  && wget --quiet ftp://bgbilling.ru/pub/bgbilling/7.1/data/BGBillingServer_$BGBILLING_VERSION.zip \
  && unzip -qq BGBillingServer_$BGBILLING_VERSION.zip \
  && mv BGBillingServer $BGBILLING_HOME \
  && mv dump.sql $BGBILLING_HOME \
  && rm -f BGBillingServer_$BGBILLING_VERSION.zip \
  && chmod +x $BGBILLING_HOME/*.sh \
  && chmod +x $BGBILLING_HOME/script/* \
  && sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgbilling \
  && sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgscheduler \
  && sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgcommonrc \
  && ln -s $BGBILLING_HOME/script/bgcommonrc /etc/init.d/bgcommonrc \
  && ln -s $BGBILLING_HOME/script/bgbilling /etc/init.d/bgbilling \
  && ln -s $BGBILLING_HOME/script/bgscheduler /etc/init.d/bgscheduler \
  && update-rc.d bgbilling defaults \
  && update-rc.d bgscheduler defaults \
  && mkdir --parents $BGBILLING_INSTALL \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/bill_$BILL_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/bonus_$BONUS_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/card_$CARD_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/dispatch_$DISPATCH_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/inet_$INET_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/moneta_$MONETA_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/mps_$MPS_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/npay_$NPAY_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/qiwi_$QIWI_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/rscm_$RSCM_VERSION.zip \
  && wget --quiet --directory $BGBILLING_INSTALL ftp://bgbilling.ru/pub/bgbilling/7.1/data/subscription_$SUBSCRIPTION_VERSION.zip \
  && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

ADD container/ $BGBILLING_HOME/

VOLUME $BGBILLING_HOME

EXPOSE 8080

CMD $BGBILLING_HOME/bgbilling.sh && tail -F $BGBILLING_HOME/log/server.log
