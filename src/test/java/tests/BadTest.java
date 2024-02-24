package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BlousesPage;
import pages.BlousesPagePopUp;
import pages.TopMenuPage;

public class BadTest {

    private static WebDriver driver;
    private static Logger logger = LoggerFactory.getLogger("tests.BadTest.class");
    private final String browserName = "chrome";
    private final boolean headlessBrowser = false;
    private final String APP_URL = "http://www.automationpractice.pl/index.php";


    @Test
    @DisplayName("Verification")
    void TopMenuVerification() {
        logger.info("Start test");
        driver = getDriver();
        driver.get(APP_URL);

        TopMenuPage topMenuPage = new TopMenuPage(driver);
        BlousesPage blousesPage = new BlousesPage(driver);
        BlousesPagePopUp blousesPagePopUp = new BlousesPagePopUp(driver);

        topMenuPage.moveMouseOnWomenCategory().clickOnBlousesSubCategory();
        blousesPage.moveMouseOnProductImage().clickOnQuickView();
        blousesPagePopUp.waitForPopup().switchToIframe();
        blousesPagePopUp.verifyProductImages(blousesPagePopUp.getProductImages());

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
