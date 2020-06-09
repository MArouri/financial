#!/bin/bash
cd /tmp/CodeDeployExample

echo "The ApplicationStop deployment lifecycle event successfully completed." > application-stop.txt

sudo killall -9 java
sudo killall -9 opa