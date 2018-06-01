FROM alexanderfefelov/graalvm-polyglot

ENV BGBILLING_VERSION=7.1_971 \
\
  BONUS_VERSION=7.1_96 \
  DISPATCH_VERSION=7.1_29 \
  NPAY_VERSION=7.1_167 \
  RSCM_VERSION=7.1_157

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
  && rm --force BGBillingServer_$BGBILLING_VERSION.zip \
  && chmod +x $BGBILLING_HOME/*.sh \
  && chmod +x $BGBILLING_HOME/script/* \
  && sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgscheduler \
  && sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgcommonrc \
  && ln -s $BGBILLING_HOME/script/bgcommonrc /etc/init.d/bgcommonrc \
  && ln -s $BGBILLING_HOME/script/bgscheduler /etc/init.d/bgscheduler \
  && update-rc.d bgscheduler defaults \
  && mkdir --parents $BGBILLING_BOOTSTRAP \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/bonus_$BONUS_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/dispatch_$DISPATCH_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/npay_$NPAY_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/rscm_$RSCM_VERSION.zip \
  && rm --recursive --force /var/lib/apt/lists/* /tmp/* /var/tmp/*

ADD container/ $BGBILLING_HOME/
ADD ../dyn/container/ $BGBILLING_HOME/

VOLUME $BGBILLING_HOME

EXPOSE 8080

CMD $BGBILLING_HOME/bgscheduler.sh && tail -F $BGBILLING_HOME/log/scheduler.log
