FROM alexanderfefelov/bgbilling-base

ENV DEBIAN_FRONTEND=noninteractive

RUN sed -i 's@bin\/sh@bin\/bash\nexport BGBILLING_HOME='"$BGBILLING_HOME"'@' $BGBILLING_HOME/script/bgscheduler \
  && ln -s $BGBILLING_HOME/script/bgscheduler /etc/init.d/bgscheduler \
  && update-rc.d bgscheduler defaults \
  && rm --force --recursive /var/lib/apt/lists/* /tmp/* /var/tmp/*

ADD container/ $BGBILLING_HOME/

VOLUME $BGBILLING_HOME

EXPOSE 8080

CMD $BGBILLING_HOME/bgscheduler.sh && tail -F $BGBILLING_HOME/log/scheduler.log
