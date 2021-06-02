# :calendar: Almanax API
[![Build](https://github.com/dofusdude/almanax-api/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/dofusdude/almanax-api/actions/workflows/maven.yml)

A multilingual API for the Krosmoz Dofus Almanax with a few extras.

The main features are
- get by date :date:
- combining all info about the needed offering item :bookmark: (with higher res images)
- getting date spans up to a month 
- filter a date span by bonus
- getting the next date for a specific bonus
- all time relative APIs support timezones :clock1: (default Europe/Paris)
- everything above (including language agnostic links) in english :uk:, french :fr:, italian :it:, spanish :es: and german :de:

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

## Awesome projects using the API
- [AlmanaxApp](https://almanaxapp.netlify.app) by Lystina
- [KaellyBot](https://github.com/Kaysoro/KaellyBot) by Kaysoro

## License
Author: Christopher Sieh <stelzo@steado.de>

This project is licensed under the Apache-2.0 License.
