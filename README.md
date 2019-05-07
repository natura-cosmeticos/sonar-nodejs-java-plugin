# Sonar NodeJS
SonarNodeJS is a code analyzer for NodeJS projects.

## Features
* [Metrics](https://github.com/Murilovisque/sonar-nodejs/blob/master/docs/metrics.md)

## Get Started

To build the plugin and run its unit tests, execute this command from the project's root directory to generate the jar file. After this put the jar file in $SONARQUBE_HOME/extensions/plugins, removing any previous versions of the same plugins:
```
mvn clean package
```

Building using docker, execute this command from the project's root directory:
```
docker run -v ~/.m2:/var/maven/.m2 -ti --name sonar-nodejs --rm -u `id -u`:`id -g` -v "$(pwd)":/usr/src/sonar-nodejs -w /usr/src/sonar-nodejs -e MAVEN_CONFIG=/var/maven/.m2 maven:3.6.1-jdk-8-alpine mvn clean package -Duser.home=/var/maven
```