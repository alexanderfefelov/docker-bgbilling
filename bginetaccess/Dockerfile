FROM java:8-jdk

ENV DEBIAN_FRONTEND noninteractive

ENV BGINETACCESS_VERSION 7.0_30
ENV BGINETACCESS_HOME /bginetaccess
RUN wget -q ftp://bgbilling.ru/pub/bgbilling/7.0/BGInetAccess_$BGINETACCESS_VERSION.zip \
  && unzip -qq BGInetAccess_$BGINETACCESS_VERSION.zip \
  && mv BGInetAccess $BGINETACCESS_HOME \
  && rm -f BGInetAccess_$BGINETACCESS_VERSION.zip \
  && chmod +x $BGINETACCESS_HOME/*.sh \
  && chmod +x $BGINETACCESS_HOME/script/* \
  && sed -i 's@\/usr\/local\/BGInetAccess@'"$BGINETACCESS_HOME"'@' $BGINETACCESS_HOME/script/bginet_access \
  && ln -s $BGINETACCESS_HOME/script/bginet_access /etc/init.d/bginet_access \
  && update-rc.d bginet_access defaults
ADD container/setenv.sh $BGINETACCESS_HOME/
ADD container/inet-access.xml.template /
ADD container/bginetaccess.sh /
VOLUME $BGINETACCESS_HOME

EXPOSE 1812/udp
EXPOSE 10067/udp

CMD /bginetaccess.sh && tail -F $BGINETACCESS_HOME/log/access.log
