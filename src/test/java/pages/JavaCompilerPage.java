package pages;

import base.BasePage;
import base.DataContext;
import base.DriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JavaCompilerPage extends BasePage {

    Logger logger = LogManager.getLogger(JavaCompilerPage.class);
    private DataContext dataContext = null;
    private final By divLeftPane = By.xpath("//div[contains(@class,'transition-all print:hidden')]");
    private final By btnExecute = By.xpath("//button[contains(text(),'Execute')]");
    private final By tabExternalLibraries = By.xpath("//div[@id='splitTabsHeader']//button[contains(text(),'External Libraries')]");
    private final By tabInput = By.xpath("//div[@id='splitTabsHeader']//button[contains(text(),'Input')]");
    private final By inputExternalLibrary = By.xpath("//div[@id='splitExternalLibrariesComp']//input[contains(@placeholder,'org.apache.commons:commons-lang3:jar:3.9')]");
    private final By btnAddLibrary = By.xpath("//div[@id='splitExternalLibrariesComp']//button[contains(text(),'Add Library')]");
    private final By addedLibrary = By.xpath("//div[@id='splitLibraryManagerComp']//li/p");
    private final By jdkVersionSelect = By.xpath("(//select[@id='versionSelect'])[2]");
    private final By txtAreaEditor = By.className("ace_text-input");
    private final By divCodeEditor = By.xpath("//div[@class='ace_content']");
    private final By btnStopExecuting = By.xpath("//button[contains(text(),'Stop Executing')]");
    private final By captchaPopUp = By.id("recaptcha-token");
    private final By captchaIFrame = By.xpath("//iframe[contains(@title,'recaptcha')]");

    public JavaCompilerPage(DriverContext driverContext) {
        super(driverContext);
    }

    public JavaCompilerPage(DriverContext driverContext,DataContext dataContext) {
        super(driverContext);
        this.dataContext = dataContext;
    }

    public void hoverOnLeftPane() {
        mouseHover(divLeftPane);
    }

    public void validateAllText(List<String> texts) {
        scrollDown(60);
        for (String txt : texts) {
            mouseHover(divLeftPane);
            By txtElement = By.xpath("//div[contains(@class,'sticky')][2]//span[text()='" + txt + "']");
            Assert.assertTrue(isElementDisplayed(txtElement), txt + " not present");
            logger.info("Validated text:'"+txt+"' successfully");

        }
    }

    public void scrollToCompilerFooter() {
        scrollIntoView(btnExecute);
    }

    public void validateCompilerFooterMenus(List<String> texts) {
        for (String txt : texts) {
            Assert.assertTrue(isElementDisplayed(By.xpath("//div[@id='splitTabsHeader']//button[contains(text(),'" + txt + "')]")), txt + " not present");
            logger.info("Validated Compiler Footer Menus:'"+txt+"' successfully");
        }
    }

    public void addExternalLibrary(List<String> jars) {
        click(tabExternalLibraries);
        for (String jar : jars) {
            setText(inputExternalLibrary, jar);
            click(btnAddLibrary);
            logger.info("Added External library,"+jar);
        }

    }

    public void clickAddLibrary() {
        click(tabExternalLibraries);
        click(btnAddLibrary);
    }

    public void validateLibrariesAdded() {
        List<WebElement> libraryManagerElements = waitForElements(addedLibrary, 20);
        List<String> addedJars = libraryManagerElements.stream()
                .map(WebElement::getText)
                .toList();
        List<String> expectedJars = (List<String>) dataContext.readFromDictionary("jars");
        Assert.assertEquals(addedJars, expectedJars, "Added jars not matching," +
                "Expected:" + expectedJars + ",Found:" + addedJars);
        logger.info("Validated Libraries '"+addedJars+"' added successfully");
    }

    public void validateErrorMessage(String errorMessage) {
        Assert.assertTrue(isElementDisplayed(By.xpath("//div[@id='splitExternalLibrariesComp']//p[text()='" + errorMessage + "']"))
                , errorMessage + " not displayed");
        logger.info("Validated error message '"+errorMessage+"' displayed");

    }

    public void selectLanguageVersionsDropDown() {
        click(tabInput);
        click(jdkVersionSelect);
    }

    public void validateLanguageVersions(List<String> expectedVersions) {
        List<String> actualVersions = getDropDownElement(jdkVersionSelect).getOptions().stream()
                .map(WebElement::getText)
                .toList();
        Assert.assertEquals(actualVersions, expectedVersions, "List of Version not matching," +
                "Expected:" + expectedVersions + ",Found:" + actualVersions);
        logger.info("Validated language versions '"+actualVersions+"' successfully");
    }


    public void enterCode(String code) {
        click(divCodeEditor);
        clearCodeEditor();
        setText(txtAreaEditor, code);
    }

    public void executeCode() {
        scrollIntoView(btnExecute);
        click(btnExecute);
        Assert.assertTrue(waitForElementToDisAppear(btnStopExecuting), "Execution not completed");
        logger.info("Execution button clicked");
    }

    public void verifyCaptchaDisplayed() {
        switchToIFrame(captchaIFrame);
        Assert.assertFalse(waitForElements(captchaPopUp, 20).isEmpty(), "Captcha not prompted");
        switchToDefaultContent();
        logger.info("Validated Captcha popup display");
    }

    public void validateBrokenLinks() {
        List<String> brokenUrl = new ArrayList<>();
        List<WebElement> linkElements = waitForElements(By.tagName("a"), 30);
        for (WebElement linkElement : linkElements) {
            String link = linkElement.getAttribute("href");
            int responseCode = httpRequestCall(link);
            if(responseCode>=400) {
                brokenUrl.add(link);
            }
        }
        Assert.assertTrue(brokenUrl.isEmpty(),"Broken URL Found,"+brokenUrl);
        logger.info("Validated broken links successfully");

    }

    public int httpRequestCall(String link) {
        int responseCode = 0;
        if (link != null && !link.isEmpty()) {
            try {
                if (!link.contains("mailto:")) { //skip email links
                    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    responseCode = connection.getResponseCode();
                    logger.info("Request Made for link:'"+link+"',Response Code:"+responseCode);
                    connection.disconnect();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return responseCode;

    }
}
