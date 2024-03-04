package tests.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class BaseTest {

    protected WebDriver driver;
    protected static Logger logger = LoggerFactory.getLogger("tests.base.BaseTest.class");
    private final String browserName = "chrome";
    private final boolean headlessBrowser = false;
    private final String APP_URL = "https://cosmocode.io/automation-practice-webtable/";

    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(NoSuchElementException.class);

    @BeforeEach
     void setUp() throws IOException {
        driver = getDriver();
        driver.get(APP_URL);
        logger.info("Driver is opened");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        logger.info("Driver is closed");
    }

    private WebDriver getDriver() {
        switch (this.browserName) {
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                WebDriverManager.edgedriver().setup();
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                if (this.headlessBrowser) {
                    options.addArguments("--headless");
                }
                driver = new EdgeDriver(options);
            }

            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                WebDriverManager.chromedriver().setup();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--remote-allow-origins=*");
                if (this.headlessBrowser) {
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(chromeOptions);
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                WebDriverManager.firefoxdriver().setup();
                firefoxOptions.addArguments("start-maximized");
                if (this.headlessBrowser) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
            }
            case "internet explorer" -> {
                InternetExplorerOptions defaultOptions = new InternetExplorerOptions();
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver(defaultOptions);
            }
            default -> throw new UnsupportedOperationException("Unsupported browser selected.");
        }
        return driver;
    }

    protected void scrollToElement(WebElement element) {
        new Actions(driver)
                .scrollToElement(element)
                .perform();
    }
    protected void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        logger.info("Element " + element.getText() + " is clicked");

    }

    protected WebElement findCheckbox(List<WebElement> list, int index) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(list.get(index)));
        return checkbox;
    }

    protected String getCapitolForCountry(String country) {
        int row = 0;
        List<WebElement> table = driver.findElements(By.cssSelector("#countries > tbody > tr"));
        for (WebElement e : table) {
            if (e.getText().contains(country)) {
                break;
            }
            row++;
        }
        WebElement capital = driver.findElement(By.cssSelector("#countries > tbody > tr:nth-child(" + (row + 1) + ") > td:nth-child(3)"));
        return capital.getText();
    }
}
