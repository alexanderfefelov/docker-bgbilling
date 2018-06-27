FROM alexanderfefelov/bgbilling-base

RUN sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgbilling \
  && ln -s $BGBILLING_HOME/script/bgbilling /etc/init.d/bgbilling \
  && update-rc.d bgbilling defaults \
  && mv dump.sql $BGBILLING_BOOTSTRAP \
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
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/subscription_$SUBSCRIPTION_VERSION.zip

ADD container/ $BGBILLING_HOME/

VOLUME $BGBILLING_HOME

EXPOSE 8080

CMD $BGBILLING_HOME/bgbilling.sh && tail -F $BGBILLING_HOME/log/server.log
