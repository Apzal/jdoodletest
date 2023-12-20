package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage extends PageInstance {

    private final DriverContext driverContext;
    public static final String BASE_URL = "https://www.jdoodle.com/";
    public static final int DEFAULT_TIMEOUT_SECONDS = 5;

    Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(DriverContext driverContext) {
        super(driverContext);
        this.driverContext = driverContext;
    }


    public void launchBrowser() {
        String browser = System.getenv("browser") != null ? System.getenv("browser") : "chrome";

        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            this.driverContext.driver = new FirefoxDriver();
        }
        else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("--headless");
            this.driverContext.driver = new ChromeDriver(chromeOptions);
            logger.info("Launched browser:" + browser);
        }
    }

    public void navigateToUrl(String url) {
        this.driverContext.driver.navigate().to(url);
        logger.info("Navigated to URL:" + url);
    }

    public void launchBrowserAndNavigate(String url) {
        launchBrowser();
        navigateToUrl(url);
    }

    private WebDriverWait loadWaitTimer(int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(this.driverContext.driver, Duration.ofSeconds(timeOutInSeconds));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(ElementClickInterceptedException.class);
        wait.ignoring(ElementNotInteractableException.class);
        return wait;
    }

    private void highlightElement(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driverContext.driver;
        jsExecutor.executeScript("arguments[0].style.border='2px solid red'", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        jsExecutor.executeScript("arguments[0].style.border='0px solid red'", element);
    }

    protected void scrollIntoView(By by) {
        WebElement element = waitForElement(by, BasePage.DEFAULT_TIMEOUT_SECONDS);
        ((JavascriptExecutor) this.driverContext.driver).executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled into view for element:" + element);
    }

    protected void scrollDown(int pixel) {
        ((JavascriptExecutor) this.driverContext.driver).executeScript("window.scrollBy(0," + pixel + ");");
        logger.info("Scrolled down by " + pixel + " pixel");
    }

    protected WebElement waitForElement(By locator) {
        return waitForElement(locator, DEFAULT_TIMEOUT_SECONDS);
    }

    protected WebElement waitForElement(By locator, int timeOutInSeconds) {
        WebDriverWait wait = loadWaitTimer(timeOutInSeconds);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.info("Located Element, Locator Used : " + element);
        try {
            highlightElement(element);
        } catch (Exception e) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            highlightElement(element);
        }
        return element;
    }

    protected boolean waitForElementToDisAppear(By locator) {
        WebDriverWait wait = loadWaitTimer(DEFAULT_TIMEOUT_SECONDS);
        logger.info("Waited for Element to Disappear, Locator Used : " + locator);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public List<WebElement> waitForElements(By locator, int timeOutInSeconds) {
        WebDriverWait wait = loadWaitTimer(timeOutInSeconds);
        logger.info("Wait for Multiple Elements, Locator Used : " + locator);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected void click(By locator, int timeOutInSeconds) {
        int counter = 3;
        while (counter > 0)
            try {
                waitForElement(locator, timeOutInSeconds).click();
                logger.info("Clicked Element, Locator Used : "+locator);
                break;
            } catch (WebDriverException e) {
                counter--;
                if (counter == 0) {
                    logger.info("Click element failed, Locator Used : "+locator+",Exception :"+e.getMessage());
                    throw new WebDriverException(e.getMessage());
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                logger.info("Retrying click, Attempt left:" + counter);
            }

    }

    protected void click(By locator) {
        click(locator, DEFAULT_TIMEOUT_SECONDS);
    }

    protected void mouseHover(By locator) {
        Actions actions = new Actions(driverContext.driver);
        actions.moveToElement(waitForElement(locator))
                .build()
                .perform();
        logger.info("Mouse Hover Action Performed on locator:"+locator);
    }

    protected boolean isElementDisplayed(By locator) {
        return waitForElement(locator).isDisplayed();
    }

    protected void setText(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
        logger.info("For Element "+element+" Set text as "+text);
    }

    protected Select getDropDownElement(By locator) {
        return new Select(waitForElement(locator));
    }

    public void clearCodeEditor() {
        Actions actions = new Actions(driverContext.driver);
        actions.keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).keyUp(Keys.CONTROL).build().perform();
        logger.info("Cleared Contents from Code Editor");
    }

    public void switchToIFrame(By locator) {
        driverContext.driver.switchTo().frame(waitForElement(locator));
        logger.info("Switched to frame using locator:"+locator);
    }

    public void switchToDefaultContent() {
        driverContext.driver.switchTo().defaultContent();
        logger.info("Switched back to default content");
    }


}
