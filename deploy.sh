#!/bin/bash

gradle war && \
scp ./build/libs/door-0.1.0.war door:/tmp/door.war && \
ssh door "mv /tmp/door.war /home/pi/jetty-distribution-9.1.5.v20140505/webapps/ROOT.war"