# TODO
* Spring Configuration file descriptions
* Spring security configuration

# Spring-MVC-React-Demo

This Demo project was designed for the 'Hogeschool Arnhem en Nijmegen' (HAN) school in The Netherlands, Arnhem to teach
students about the inner workings of some of the most-used tech in Web App development. The intent is therefore to 
provide examples and hooks to study material for teachers to use in class and thus is not an appropriate reflection of
best practices in Web App development per se. For example, this project uses Spring Core (e.g. MVC) for educational 
purposes instead of the more popular Spring Boot framework to bootstrap the Web Server and its services.

The Web App includes all necessary build tools and dependencies in a single application to ease deployment, testing and
debugging.

## Apache 2.0 License
[![License](https://img.shields.io/badge/License-Apache_2.0-yellowgreen.svg)](https://opensource.org/licenses/Apache-2.0)

## Features

The featured elements of this Demo project include at least:
* Spring Core framework
  * Spring MVC
  * Spring Data
  * Spring Security
* Hibernate / JPA
* Thymeleaf
* JUnit
* Maven
* Node / npm
* React

## About this repository

This repository is provided as a largely independent Spring Web App, but assumes at least a database connection. An
example [application.properties](https://github.com/wieberinsma/my-rest-app/blob/master/src/main/resources/application.properties)
is provided using PostgreSQL. In addition, to succesfully build the project, the following local dependencies are
required:
* Java 12
* TomEE Webprofile 8/9
* Maven

Of course, your favourite IDE may provide these for you while developing. Methods for deployment on a remote server are
not described, as it depends highly on additional startup parameters. Still, this project is intended to run on any 
number of different architectures and contexts.

The basic process of building the Web App, whether locally or remote, is to delegate the build process to Maven. For 
command line usage this is straightforward through use of its binaries. For others, such as the IntelliJ IDE, it
requires you to denote this delegation manually (`Settings > Build, Execution, Deployment > Build Tools > Maven > Runner > Delegate IDE build/run actions to Maven`).
The Maven build process includes the [Maven Frontend Plugin](https://github.com/eirslett/frontend-maven-plugin) to build
the static `HTML`, `CSS` and `JavaScript` files using automatically installed Node, npm and Webpack services and 
dependencies and bundling all static files into a single `bundle.js` file. Therefore, HTML files should reference this 
bundle instead of individual JS files. To provide a smooth build process, the internal configuration for these build 
tools through the `package.json` and `webpack.config.js` files assumes a certain package structure and so it is 
important to keep track of any changes made between them.

The Java files are published as a `WAR` archive at `/target/` and the static interface is published at
`/resources/static/built`. The `React`-based output of the front-end uses internal routing to find Java endpoints and
dynamically update the requested HTML. If you want to build individual JS files for each HTML file, refer to Webpack's
capabilities of [Code Splitting](https://webpack.js.org/guides/code-splitting/).

If you want to help and _improve_ this project, please contact me directly as the current project is:
* A bootstrapped demo
* Intended for educational purposes

It is not:
* An enterprise app
* A commercial product

Please feel free to take this repository at face value, fork it and expand upon it under your own expertise. Any
development on this root will be discussed and implemented both through `.git` version control as well as physical
meetings at the HAN. Not all changes will therefore reflect a typical software development process.

## Quick Start

Either:
* Build on commandline using `mvn install` followed by a `startup` command from Tomcat's install directory
* Build and start on commandline using `mvn install tomee:run` by including the [tomee-maven-plugin](https://mvnrepository.com/artifact/org.apache.tomee.maven/tomee-maven-plugin)
in your pom.xml
* Build and start using your favourite IDE by creating a `Run Configuration` that builds a WAR archive in the `/target/`
folder using a specific TomEE instance


## Documentation

The codebase provides Javadoc documentation for certain dependencies used and functions that require additional context.
Again, this is done for educational purposes and does not reflect commercial best-practices.