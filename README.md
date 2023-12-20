# Cucumber Selenium Framework Assignment

This is a assignment project created to test the [JDoodle Application](#https://www.jdoodle.com/online-java-compiler) using 
Cucumber Java and Selenium

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Execution](#execution)
- [TestCases](#test-cases)
- [CI](#contributing)


## How to execute

This Cucumber Selenium project is created with a simple and Scalable framework architecture provides flexibility to user to accomodate new changes easily

## Features

- Support multiple browsers. Currently chrome and firefox are supported , more browsers can be added
- Support Parallel execution with testng
- Extent Reports with Screenshot Facility
- Log4j Support
- Strategic element locators technique to reduce object identification failures
- Page Object Model Design Pattern
- Dependency Injection for better maintainability and flexibility
- CI enabled to run test case for every PR raise and push made to main branch

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



