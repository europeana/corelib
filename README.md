# Europeana CoreLib

[![Build Status](https://travis-ci.org/europeana/corelib.svg?branch=master)](https://travis-ci.org/europeana/corelib)[![Coverage Status](https://coveralls.io/repos/europeana/corelib/badge.svg?branch=master&service=github)](https://coveralls.io/github/europeana/corelib?branch=master)

The CoreLib repository contains the libraries that provide the underlying functionality (i.e. search and ingestion)
for both the Europeana [Portal](https://github.com/europeana/portal/) (deprecated) and [API](https://github.com/europeana/api2/).

## Build requirements
### Runtime
* Java version "1.8.0_72", Oracle Java(TM) SE Runtime Environment (build 1.8.0_72-b15) or above

### Tools
* Maven v3.3.x or above

## Build
``mvn clean install`` (add ``-DskipTests``) to skip the unit tests during build

### Known issues
Note: there are a number of older/outdated libraries still being referenced as dependencies, some of which may not
be provided anymore by the central repositories. The Europeana artifactory has a copy of these dependencies; add this
repository as a mirror (to the ``<mirrors>`` section of the maven settings file, usually found in ~/.m2/settings) to
successfully build the code

* Java 8 / Maven: The combination of Java 8 and Maven has a known issue in that it does not generate the JIBX content. This manifests itself as follows:

```
[ERROR] Failed to execute goal org.jibx:maven-jibx-plugin:1.2.6:schema-codegen (generate-java-code-from-schema (EDM)) on project corelib-edm-definitions: Execution generate-java-code-from-schema (EDM) of goal org.jibx:maven-jibx-plugin:1.2.6:schema-codegen failed: Plugin org.jibx:maven-jibx-plugin:1.2.6 or one of its dependencies could not be resolved: Failure to find org.apache.bcel:bcel:jar:6.0-SNAPSHOT in https://oss.sonatype.org/content/repositories/snapshots was cached in the local repository, resolution will not be reattempted until the update interval of sonatype-nexus-snapshots has elapsed or updates are forced -> [Help 1]
```

The workaround is to add a mirror to your Maven settings.xml:

```
<mirror>
      <id>sonatype-mirror</id>
      <url>http://artifactory.eanadev.org/artifactory/ext-snapshot-local</url>
      <mirrorOf>sonatype-nexus-snapshots</mirrorOf>
</mirror>
```

## Create empty database
``mvn hibernate4:export`` in corelib-db module to create a database schema script


## Thank you JetBrains!

We've been granted an open source license to use [JetBrains'](https://www.jetbrains.com) IDE's for our work on the Europeana code base:

![](https://raw.githubusercontent.com/Luthien-in-edhil/jetbrainsicons/master/icon_IntelliJIDEA.png) [IntelliJ](https://www.jetbrains.com/idea/) Java IDE

![](https://raw.githubusercontent.com/Luthien-in-edhil/jetbrainsicons/master/icon_PyCharm.png) [PyCHarm](https://www.jetbrains.com/pycharm/) Python IDE

![](https://raw.githubusercontent.com/Luthien-in-edhil/jetbrainsicons/master/icon_RubyMine.png) [RubyMine](https://www.jetbrains.com/ruby/) Ruby IDE

