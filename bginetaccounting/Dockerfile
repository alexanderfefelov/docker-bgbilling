FROM openjdk:8-jdk

ENV DEBIAN_FRONTEND noninteractive

ENV BGINETACCOUNTING_VERSION 7.1_31
ENV BGINETACCOUNTING_HOME /bginetaccounting

RUN wget -q ftp://bgbilling.ru/pub/bgbilling/7.1/data/BGInetAccounting_$BGINETACCOUNTING_VERSION.zip \
  && unzip -qq BGInetAccounting_$BGINETACCOUNTING_VERSION.zip \
  && mv BGInetAccounting $BGINETACCOUNTING_HOME \
  && rm -f BGInetAccounting_$BGINETACCOUNTING_VERSION.zip \
  && chmod +x $BGINETACCOUNTING_HOME/*.sh \
  && chmod +x $BGINETACCOUNTING_HOME/script/* \
  && sed -i 's@\/usr\/local\/BGInetAccounting@'"$BGINETACCOUNTING_HOME"'@' $BGINETACCOUNTING_HOME/script/bginet_accounting \
  && ln -s $BGINETACCOUNTING_HOME/script/bginet_accounting /etc/init.d/bginet_accounting \
  && update-rc.d bginet_accounting defaults

ADD container/setenv.sh $BGINETACCOUNTING_HOME/
ADD container/inet-accounting.xml.template /
ADD container/bginetaccounting.sh /

VOLUME $BGINETACCOUNTING_HOME

EXPOSE 1813/udp
EXPOSE 2001/udp

CMD /bginetaccounting.sh && tail -F $BGINETACCOUNTING_HOME/log/accounting.log
