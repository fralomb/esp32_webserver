FROM docker.io/mongo:latest

MAINTAINER Francesco Lombardo <fra.lombardo92@gmail.com>

ENV MONGO_INITDB_DATABASE="probesreqdb"

COPY mysetup.js /docker-entrypoint-initdb.d/
