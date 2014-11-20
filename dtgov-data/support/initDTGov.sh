#!/bin/bash
JBOSS_SERVER_BIN=@@serverDirectory@@

if [ ! -f ./support/sramp-dtgovwf-upload.txt ]; then
  echo "Please execute this script only via './initDTGov.sh' in the FSW-Target-directory!"
  echo "I checked if there is a './support/sramp-dtgovwf-upload'-File present and found nothing."
  exit
fi
$JBOSS_SERVER_BIN/s-ramp.sh -f support/sramp-dtgovwf-upload.txt
