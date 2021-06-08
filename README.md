# Europeana CoreLib

The CoreLib repository contains the libraries that provide the underlying functionality for the 
Search & Record API and our ingestion system Metis

## Build requirements
### Runtime
* Java version 11 or above

### Tools
* Maven v3.3.x or above

## Build
``mvn clean install`` (add ``-DskipTests``) to skip the unit tests during build

## Known issues

### Cannot resolve dependencies
Note: there are a number of older/outdated libraries still being referenced as dependencies, some of which may not
be provided anymore by the central repositories. The Europeana artifactory has a copy of these dependencies; add this
repository as a mirror (to the ``<mirrors>`` section of the maven settings file, usually found in ~/.m2/settings) to
successfully build the code




