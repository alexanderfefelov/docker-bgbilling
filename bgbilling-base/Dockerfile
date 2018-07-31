FROM alexanderfefelov/graalvm-polyglot

ENV BGBILLING_VERSION=7.1_1001 \
  UPDATE_SET=20180730205357 \
\
  BILL_VERSION=7.1_260 \
  BONUS_VERSION=7.1_98 \
  CARD_VERSION=7.1_189 \
  DISPATCH_VERSION=7.1_34 \
  INET_VERSION=7.1_609 \
  MONETA_VERSION=7.1_8 \
  MPS_VERSION=7.1_177 \
  NPAY_VERSION=7.1_169 \
  QIWI_VERSION=7.1_15 \
  RSCM_VERSION=7.1_158 \
  SUBSCRIPTION_VERSION=7.1_14

ENV BGBILLING_HOME=/bgbilling
ENV BGBILLING_DATA=$BGBILLING_HOME/data \
  BGBILLING_BOOTSTRAP=$BGBILLING_HOME/bootstrap \
  FTP_DATA_DIR=ftp://bgbilling.ru/pub/bgbilling/7.1/data \
\
  DEBIAN_FRONTEND=noninteractive \
\
  LANG=C.UTF-8

RUN apt-get -qq update \
  && apt-get -qq install --yes mysql-client unzip \
  && apt-get -qq clean \
  && wget --quiet $FTP_DATA_DIR/BGBillingServer_$BGBILLING_VERSION.zip \
  && unzip -qq BGBillingServer_$BGBILLING_VERSION.zip \
  && mv BGBillingServer $BGBILLING_HOME \
  && mkdir --parents $BGBILLING_BOOTSTRAP \
  && rm --force BGBillingServer_$BGBILLING_VERSION.zip \
  && chmod +x $BGBILLING_HOME/*.sh \
  && chmod +x $BGBILLING_HOME/script/* \
  && sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgcommonrc \
  && ln -s $BGBILLING_HOME/script/bgcommonrc /etc/init.d/bgcommonrc \
  && rm --recursive --force /var/lib/apt/lists/* /tmp/* /var/tmp/*

ADD container/ $BGBILLING_HOME/
