FROM alexanderfefelov/bgbilling-base

ENV DEBIAN_FRONTEND=noninteractive

RUN sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgscheduler \
  && ln -s $BGBILLING_HOME/script/bgscheduler /etc/init.d/bgscheduler \
  && update-rc.d bgscheduler defaults \
  && mkdir --parents $BGBILLING_BOOTSTRAP \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/bonus_$BONUS_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/dispatch_$DISPATCH_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/npay_$NPAY_VERSION.zip \
  && wget --quiet --directory $BGBILLING_BOOTSTRAP $FTP_DATA_DIR/rscm_$RSCM_VERSION.zip \
  && rm --recursive --force /var/lib/apt/lists/* /tmp/* /var/tmp/*

ADD container/ $BGBILLING_HOME/

VOLUME $BGBILLING_HOME

EXPOSE 8080

CMD $BGBILLING_HOME/bgscheduler.sh && tail -F $BGBILLING_HOME/log/scheduler.log
