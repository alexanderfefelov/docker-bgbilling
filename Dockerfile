FROM java:8-jdk

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update

RUN apt-get install --yes mysql-server mysql-client

ENV ACTIVEMQ_VERSION 5.13.1
ENV ACTIVEMQ_HOME /activemq
RUN wget -q http://www.eu.apache.org/dist/activemq/$ACTIVEMQ_VERSION/apache-activemq-$ACTIVEMQ_VERSION-bin.tar.gz -O - | tar xfz - \
  && mv apache-activemq-$ACTIVEMQ_VERSION $ACTIVEMQ_HOME \
  && rm -f apache-activemq-$ACTIVEMQ_VERSION-bin.tar.gz \
  && ln -s $ACTIVEMQ_HOME/bin/activemq /etc/init.d/activemq \
  && update-rc.d activemq defaults
VOLUME $ACTIVEMQ_HOME

ENV BGBILLING_VERSION 7.0_1007
ENV BGBILLING_HOME /bgbilling
RUN wget -q ftp://bgbilling.ru/pub/bgbilling/7.0/BGBillingServer_$BGBILLING_VERSION.zip \
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
  && update-rc.d bgscheduler defaults
ADD container/setenv.sh $BGBILLING_HOME/
ADD container/bgbilling.sh /
VOLUME $BGBILLING_HOME

CMD /bgbilling.sh && tail -F $BGBILLING_HOME/log/server.log
