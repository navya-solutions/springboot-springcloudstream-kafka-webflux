# Docker compose

The minimum version of the docker compose file define the services references which are necessary for the project i.e.

* MongoDB
* Kafka broker
* zookeeper

On the other hand the extensive version of the docker compose file define additional service which are useful for
development, like

* mongo-express
* schema-registry
* connect
* control-center
* ksqldb-server
* ksqldb-cli

### Configuration and Running docker compose locally

1. Download and install the [docker](https://docs.docker.com/get-docker/)
   and [docker-compose](https://docs.docker.com/compose/install/)
2. Go to the docker-compose folder, and use the below commands

```shell
# To Create and start minimum version containers
docker compose -f docker-compose-minimum.yaml -p px up -d
```

```shell
# To stop and remove minimum version - containers, networks
docker compose -f docker-compose-minimum.yaml -p px down
```

```shell
# To Create and start extensive version containers
docker compose -f docker-compose-extensive.yaml -p px up -d
```

```shell
# To stop and remove extensive version - containers, networks
docker compose -f docker-compose-extensive.yaml -p px down
```
