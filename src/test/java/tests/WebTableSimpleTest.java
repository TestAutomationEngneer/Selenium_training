package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class WebTableSimpleTest {

    private static WebDriver driver;
    private static Logger logger = LoggerFactory.getLogger("tests.WebTableTest.class");
    private final String browserName = "chrome";
    private final boolean headlessBrowser = false;
    private final String APP_URL = "https://cosmocode.io/automation-practice-webtable/";


    @Test
    @DisplayName("Check countries and capitals")
    void WebTableVerification() {

        logger.info("Start test");
        driver = getDriver();
        driver.get(APP_URL);

        //set implicit wait
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //fluent wait
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);


        //1. count how many rows are in the table
        //expected result: 197
        List<WebElement> table = driver.findElements(By.cssSelector("#countries > tbody > tr"));
        int rows = table.size();
        logger.info("Number of rows: " + rows);
        assertThat(rows).isEqualTo(197);

        //3. Check Poland Visited checkboxes #139
        List<WebElement> elements = driver.findElements(By.cssSelector(".hasVisited[type='checkbox']"));
        //wait for element to be clickable
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(elements.get(139)));

        logger.info("Checkbox - before click -> Selected: " + checkbox.isSelected());
        logger.info("Checkbox - before click -> Displayed: " + checkbox.isDisplayed());
        logger.info("Checkbox - before click -> Enabled: " + checkbox.isEnabled());

        //scroll to element
        new Actions(driver)
                .scrollToElement(elements.get(145))
                .perform();


        //click on checkbox
        checkbox.click();
        logger.info(">>>Checkbox clicked");

        logger.info("Checkbox - after click -> Selected: " + checkbox.isSelected());
        logger.info("Checkbox - after click -> Displayed: " + checkbox.isDisplayed());
        logger.info("Checkbox - after click -> Enabled: " + checkbox.isEnabled());
        assertThat(checkbox.isSelected()).isTrue();

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
}
