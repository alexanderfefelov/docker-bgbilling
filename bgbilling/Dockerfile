FROM alexanderfefelov/graalvm-polyglot

ENV BGBILLING_VERSION=7.1_985 \
\
  BILL_VERSION=7.1_257 \
  BONUS_VERSION=7.1_96 \
  CARD_VERSION=7.1_189 \
  DISPATCH_VERSION=7.1_31 \
  INET_VERSION=7.1_599 \
  MONETA_VERSION=7.1_8 \
  MPS_VERSION=7.1_175 \
  NPAY_VERSION=7.1_169 \
  QIWI_VERSION=7.1_15 \
  RSCM_VERSION=7.1_158 \
  SUBSCRIPTION_VERSION=7.1_14

ENV BGBILLING_HOME=/bgbilling
ENV BGBILLING_DATA=$BGBILLING_HOME/data \
  BGBILLING_BOOTSTRAP=$BGBILLING_HOME/bootstrap \
  FTP_DATA_DIR=ftp://bgbilling.ru/pub/bgbilling/7.1/data \
\
  DEBIAN_FRONTEND=noninteractive

RUN apt-get -qq update \
  && apt-get -qq install --yes mysql-client unzip \
  && apt-get -qq clean \
  && wget --quiet $FTP_DATA_DIR/BGBillingServer_$BGBILLING_VERSION.zip \
  && unzip -qq BGBillingServer_$BGBILLING_VERSION.zip \
  && mv BGBillingServer $BGBILLING_HOME \
  && mkdir --parents $BGBILLING_BOOTSTRAP \
  && mv dump.sql $BGBILLING_BOOTSTRAP \
  && rm --force BGBillingServer_$BGBILLING_VERSION.zip \
  && chmod +x $BGBILLING_HOME/*.sh \
  && chmod +x $BGBILLING_HOME/script/* \
  && sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgbilling \
  && sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgcommonrc \
  && ln -s $BGBILLING_HOME/script/bgcommonrc /etc/init.d/bgcommonrc \
  && ln -s $BGBILLING_HOME/script/bgbilling /etc/init.d/bgbilling \
  && update-rc.d bgbilling defaults \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/bill_$BILL_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/bonus_$BONUS_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/card_$CARD_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/dispatch_$DISPATCH_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/inet_$INET_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/moneta_$MONETA_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/mps_$MPS_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/npay_$NPAY_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/qiwi_$QIWI_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/rscm_$RSCM_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/subscription_$SUBSCRIPTION_VERSION.zip \
  && rm --recursive --force /var/lib/apt/lists/* /tmp/* /var/tmp/*

ADD container/ $BGBILLING_HOME/

VOLUME $BGBILLING_HOME

EXPOSE 8080

CMD $BGBILLING_HOME/bgbilling.sh && tail -F $BGBILLING_HOME/log/server.log
