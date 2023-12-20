Feature: As a user I want to validate the Page components of JDoodle Java Compiler

  @ui
  Scenario: As a user verify all  menu items on the left pane of Jdoodle Java Compiler Page
    Given I open the jdoodle online "JAVA" application in browser
    And I open the left pane
    Then I should see all below options
      | New Project                            |
      | My Projects                            |
      | Execute History                        |
      | Collaborate                            |
      | Save                                   |
      | Save As                                |
      | Editable Share                         |
      | Instant Share (No Login/Save required) |
      | Copy to Clipboard                      |
      | Open (from local file)                 |
      | Save (to local file)                   |
      | Pretty Print                           |
      | How To/FAQ                             |


  @ui
  Scenario: As a user verify all footer items on the Jdoodle Java Compiler Page
    Given I open the jdoodle online "JAVA" application in browser
    And I scroll down to Compiler Footer
    Then I should see all below footer menus in the compiler page
      | External Libraries |
      | Upload Files       |
      | Input              |
      | Output             |


  @ui
  Scenario: As a user verify I should see the below list of JDK available in the Language Version dropdown
    Given I open the jdoodle online "JAVA" application in browser
    And I scroll down to Compiler Footer
    When I select the Language version for Java under Input Section
    Then I should see below list of JDK
      | JDK 1.8.0_66 |
      | JDK 9.0.1    |
      | JDK 10.0.1   |
      | JDK 11.0.4   |
      | JDK 17.0.1   |
      | JDK 21.0.0   |

      #There are 4 links which throws >=400 response which are considered as broken
  @ui @brokenlinks
  Scenario: As a user verify no broken links present in java compiler page
    Given I open the jdoodle online "JAVA" application in browser
    Then I validate all the broken links