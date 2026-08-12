FROM bellsoft/liberica-openjdk-alpine:21

RUN apk add --no-cache curl jq

WORKDIR /home/selenium-docker

ADD target/docker-resources ./
ADD runner.sh ./runner.sh

RUN chmod +x ./runner.sh

ENTRYPOINT ["./runner.sh"]