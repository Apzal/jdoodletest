# 🥒 Cucumber Selenium Framework Assignment

This is an assignment project created to test the [JDoodle Application](#https://www.jdoodle.com/online-java-compiler) using Cucumber Java and Selenium

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Execution](#execution)
- [Test Cases](#test-cases)
- [Continuous Integration](#continuous-integration)


## Introduction

This Cucumber Selenium project is created with a simple and scalable framework architecture that provides flexibility to the user to accommodate new changes easily.

## Features

- 🌐 Support for multiple browsers. Currently supports Chrome and Firefox; more browsers can be added.
- ⚡ Support for Parallel Execution with TestNG.
- 📊 Extent Reports with Screenshot Facility.
- 📝 Log4j Support.
- 🔍 Strategic element locators technique to reduce object identification failures.
- 🏗️ Page Object Model Design Pattern.
- 💉 Dependency Injection for better maintainability and flexibility.
- 🔄 CI enabled to run test cases for every PR raised and push made to the main branch.

## Execution

### To execute in command prompt

```bash
# Example installation and execution steps
git clone https://github.com/Apzal/jdoodletest
cd your-repo
# Ensure Maven is installed
mvn clean test


## Execution

### To execute in command prompt

```bash
# Example installation and execution steps
git clone https://github.com/Apzal/jdoodletest
cd your-repo
# Ensure maven is installed
mvn clean test
```
### To execute in IDE
- After cloning the project open it in your IDE
- Once the dependencies are downloaded
- Execute the file [RunCucumberTest](src/test/java/runner/RunCucumberTest.java)

## Test Cases

There are 7 sample test cases written which are split across the files [JavaCompilerComponents.feature](src/test/resources/features/JavaCompilerComponents.feature)
and [JavaCompilerFunctionalities.feature](src/test/resources/features/JavaCompilerFunctionalities.feature)

These test cases covers functionalities in the java compile page it covers
- The list of options available in the left Pane
- The footer section components names
- The Language versions available in input
- Broken links available in the compiler page
- Validation of addition of external libraries
- Execution of a sample java code which validates the captcha

## Continuous Integration

Workflow file [maven.yml](#.github/workflows/maven.yml) is configured with ability to run all test cases for every PR and push to main branch.
Also after every run the html report and logs are published under the [Git Action Run](https://github.com/Apzal/jdoodletest/actions/runs/7279475549)

Sample Report for reference attached [here](test-output/SparkReport/testReport.html)



