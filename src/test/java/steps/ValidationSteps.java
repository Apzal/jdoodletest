package steps;

import base.DataContext;
import base.DriverContext;
import io.cucumber.java.en.Then;
import pages.JavaCompilerPage;

import java.util.List;

public class ValidationSteps {

    private final DriverContext driverContext;
    private final DataContext dataContext;
    public ValidationSteps(DriverContext driverContext,DataContext dataContext) {
        this.driverContext = driverContext;
        this.dataContext = dataContext;
    }

    @Then("I should see all below options")
    public void iShouldSeeAllBelowOptions(List<String> text) {
        driverContext.currentPage.As(JavaCompilerPage.class).validateAllText(text);
    }

    @Then("I should see all below footer menus in the compiler page")
    public void iShouldSeeAllBelowFooterMenusInTheCompilerPage(List<String> text) {
        driverContext.currentPage.As(JavaCompilerPage.class).validateCompilerFooterMenus(text);
    }

    @Then("I should see the same jar under Library Manager")
    public void iShouldSeeTheSameUnderLibraryManager() {
        driverContext.currentPage.As(JavaCompilerPage.class,dataContext).validateLibrariesAdded();
    }

    @Then("I should see an error message stating {string}")
    public void iShouldSeeAnErrorMessageStating(String errorMessage) {
        driverContext.currentPage.As(JavaCompilerPage.class).validateErrorMessage(errorMessage);
    }

    @Then("I should see below list of JDK")
    public void iShouldSeeBelowListOfJDK(List<String> languageVersions) {
        driverContext.currentPage.As(JavaCompilerPage.class).validateLanguageVersions(languageVersions);
    }

    @Then("I verify the captcha popup displayed")
    public void iVerifyTheCaptchaPopupDisplayed() {
        driverContext.currentPage.As(JavaCompilerPage.class).verifyCaptchaDisplayed();
    }

    @Then("I validate all the broken links")
    public void iValidateAllTheBrokenLinks() {
        driverContext.currentPage.As(JavaCompilerPage.class).validateBrokenLinks();
    }




}
