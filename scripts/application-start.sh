#!/bin/bash
cd /tmp/CodeDeployExample

echo "The ApplicationStart deployment lifecycle event successfully completed." > application-start.txt

mv financial-0.0.1-SNAPSHOT.war ROOT.war
java -jar ROOT.war > logs.txt 2>&1 &

echo "----run opa------"
cd /opt/opa
aws s3 cp s3://arouri/opa/data.json /opt/opa/data.json
aws s3 cp s3://arouri/opa/policy.rego /opt/opa/policy.rego
./opa run -s  --log-level debug policy.rego data.json > opa.log 2>&1 &