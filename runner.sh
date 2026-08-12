#!/bin/sh

echo "-------------------------------------------"
echo "HUB_HOST      : ${HUB_HOST:-hub}"
echo "BROWSER       : ${BROWSER:-chrome}"
echo "THREAD_COUNT  : ${THREAD_COUNT:-1}"
echo "TEST_SUITE    : ${TEST_SUITE}"
echo "-------------------------------------------"

echo "Checking if hub is ready..!"

count=0

while [ "$(curl -s http://${HUB_HOST:-hub}:4444/status | jq -r .value.ready)" != "true" ]
do
    count=$((count+1))
    echo "Attempt: ${count}"

    if [ "$count" -ge 30 ]
    then
        echo "**** HUB IS NOT READY WITHIN 30 SECONDS ****"
        exit 1
    fi

    sleep 1
done

echo "Selenium Grid is up and running. Running the test...."

java \
  -cp 'libs/*' \
  -Dselenium.grid.enabled=true \
  -Dselenium.grid.hubHost="${HUB_HOST:-hub}" \
  -Dbrowser="${BROWSER:-chrome}" \
  org.testng.TestNG \
  -threadcount "${THREAD_COUNT:-1}" \
  "test-suites/${TEST_SUITE}"