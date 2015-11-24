# Europeana CoreLib

[![Build Status](https://travis-ci.org/europeana/corelib.svg?branch=master)](https://travis-ci.org/europeana/corelib)[![Coverage Status](https://coveralls.io/repos/europeana/corelib/badge.svg?branch=master&service=github)](https://coveralls.io/github/europeana/corelib?branch=master)

The CoreLib repository contains the libraries that provide the underlying functionality (i.e. search and ingestion) 
for both the [Portal](https://github.com/europeana/portal/) and [API](https://github.com/europeana/api2/).

## Build requirements
### Runtime
* Java version "1.7.0_55", OpenJDK Runtime Environment (IcedTea 2.4.7)

or

* Java version "1.7.0_67", Oracle Java(TM) SE Runtime Environment (build 1.7.0_67-b01)

### Tools
* Maven v3.x

## Build
``mvn clean install`` (add ``-DskipTests``) to skip the unit tests during build

### Known issues
Note: there are a number of older/outdated libraries still being referenced as dependencies, some of which may not 
be provided anymore by the central repositories. The Europeana artifactory has a copy of these dependencies; add this 
repository as a mirror (to the ``<mirrors>`` section of the maven settings file, usually found in ~/.m2/settings) to 
successfully build the code

* Maven 2.2.1: The 2.2.1 version of Maven has a [known issue](http://jira.codehaus.org/browse/WAGON-314) that it does 
not follow redirects (HTTP 301). This manifests itself currently in trying to download the net.java.jvnet-parent:1 
pom. A workaround is to add the following mirror settings:
```bash
</mirror>
    <mirror>
    <id>europeana-ext-release-local</id>
    <url>http://artifactory.eanadev.org/artifactory/ext-release-local</url>
    <mirrorOf></mirrorOf>
</mirror>
``` 
* Java 8 / Maven: The combination of Java 8 and Maven has a [known issue](http://jira.codehaus.org/browse/JIBX-515) 
in that it does not generate the JIBX content. A workaround is posted in the link
