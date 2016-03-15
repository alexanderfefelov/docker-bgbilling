FROM java:8-jdk

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update

RUN apt-get install --yes mysql-client

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
  && update-rc.d bgscheduler defaults \
  && wget -q https://bgbilling.ru/test_license/6.0/lic.properties \
  && mv lic.properties $BGBILLING_HOME/data \
  && mkdir --parents $BGBILLING_HOME/install \
  && wget -q ftp://bgbilling.ru/pub/bgbilling/7.0/npay_7.0_189.zip \
  && mv npay_7.0_189.zip $BGBILLING_HOME/install \
  && wget -q ftp://bgbilling.ru/pub/bgbilling/7.0/inet_7.0_624.zip \
  && mv inet_7.0_624.zip $BGBILLING_HOME/install \
  && wget -q ftp://bgbilling.ru/pub/bgbilling/7.0/rscm_7.0_175.zip \
  && mv rscm_7.0_175.zip $BGBILLING_HOME/install
ADD container/data.properties $BGBILLING_HOME/data/
ADD container/setenv.sh $BGBILLING_HOME/
ADD container/bgbilling.sh /
VOLUME $BGBILLING_HOME

EXPOSE 8080

CMD /bgbilling.sh && tail -F $BGBILLING_HOME/log/server.log
