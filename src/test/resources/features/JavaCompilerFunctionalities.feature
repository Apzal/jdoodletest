Feature: As I user I should be able to do basic operations in the Java Compiler Page

  @ui
  Scenario: As a user verify I can add external libraries under External Libraries Section
    Given I open the jdoodle online "JAVA" application in browser
    And I scroll down to Compiler Footer
    And I add below jar as a external library
      | org.seleniumhq.selenium:selenium-java:4.16.1 |
      | io.cucumber.cucumber-java:7.15.0             |
    Then I should see the same jar under Library Manager

  @ui
  Scenario: As a user verify I should see a error message when clicking on Add Library without any input
    Given I open the jdoodle online "JAVA" application in browser
    And I scroll down to Compiler Footer
    When I click on Add Library without entering any input
    Then I should see an error message stating "this is a required field"

  @ui @execution
  Scenario: As a user verify captcha popup displayed if test run through automated code
    Given I open the jdoodle online "JAVA" application in browser
    When I execute below code
    """
        public class MyClass {
        public static void main(String args[]) {
        String text = "Hi,I am executing test cases";
        System.out.println(text);

    """
    Then I verify the captcha popup displayed