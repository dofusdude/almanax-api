# Almanax API

A multilingual API for the Krosmoz Dofus Almanax.

Depends on the [Dofus API](https://github.com/dofusdude/dofus-api) for the items.

## Usage
See the OpenAPI or SwaggerUI endpoint for types and examples:
- https://alm.dofusdu.de/swagger
- https://alm.dofusdu.de/openapi

## Running on your machine
```shell script
docker-compose up -d
./mvnw compile quarkus:dev
```
If that does not work (maybe because you are on Windows), download Maven for yourself and enter `mvn quarkus:dev` in the
project directory.

Note: The API is build on Quarkus. https://quarkus.io for more.

## License
Author: Christopher Sieh <stelzo@steado.de>

This project is licensed under the Apache-2.0 License.
