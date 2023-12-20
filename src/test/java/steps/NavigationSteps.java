package steps;

import base.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.JavaCompilerPage;

import java.util.List;

public class NavigationSteps {

    private final DriverContext driverContext;
    private final DataContext dataContext;

    public NavigationSteps(DriverContext driverContext, DataContext dataContext) {
        this.driverContext = driverContext;
        this.driverContext.currentPage = new PageInstance(driverContext);
        this.dataContext = dataContext;
    }

    @Given("I open the jdoodle online {string} application in browser")
    public void iOpenTheJdoodleOnlineJavaCompilerApplication(String language) {
        driverContext.currentPage.As(BasePage.class).launchBrowserAndNavigate(CommonEnum.PATH.valueOf(language).getValue());
    }

    @And("I open the left pane")
    public void iOpenTheLeftPane() {
        driverContext.currentPage.As(JavaCompilerPage.class).hoverOnLeftPane();
    }

    @And("I scroll down to Compiler Footer")
    public void iScrollDownToCompilerFooter() {
        driverContext.currentPage.As(JavaCompilerPage.class).scrollToCompilerFooter();
    }

    @And("I add below jar as a external library")
    public void iAddAsAExternalLibrary(List<String> jar) {
        dataContext.addToDictionary("jars",jar);
        driverContext.currentPage.As(JavaCompilerPage.class,dataContext).addExternalLibrary(jar);
    }

    @When("I click on Add Library without entering any input")
    public void iClickOnAddLibraryWithoutEnteringAnyInput() {
        driverContext.currentPage.As(JavaCompilerPage.class).clickAddLibrary();
    }

    @When("I select the Language version for Java under Input Section")
    public void iSelectTheLanguageVersionForJavaUnderInputSection() {
        driverContext.currentPage.As(JavaCompilerPage.class).selectLanguageVersionsDropDown();
    }

    @When("I execute below code")
    public void iExecuteBelowCode(String code) {
        driverContext.currentPage.As(JavaCompilerPage.class).enterCode(code);
        driverContext.currentPage.As(JavaCompilerPage.class).executeCode();
    }
}
