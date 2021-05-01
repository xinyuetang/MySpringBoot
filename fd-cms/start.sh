#!/bin/bash

CUR_DIR=$(cd "$(dirname "$0")";pwd)
APPLICATION_PATH=$CUR_DIR/fd-cms-1.0.0.jar
nohup -Dspring.profiles.active=dev -Denv=dev -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m  -Xms256m -Xmx512m -XX:+PrintGCDetails -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -jar $APPLICATION_PATH >/dev/null 2>&1 &
