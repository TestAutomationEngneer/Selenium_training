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
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BlousesPage;
import pages.BlousesPagePopUp;
import pages.TopMenuPage;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class FillFormTest {

    private static WebDriver driver;
    private static Logger logger = LoggerFactory.getLogger("tests.FillFormTest.class");
    private final String browserName = "chrome";
    private final boolean headlessBrowser = false;
    private final String APP_URL = "http://www.automationpractice.pl/index.php";


    @Test
    @DisplayName("Fill form")
    void TopMenuVerification() {
        //selektory css
        https://devqa.io/selenium-css-selectors/


        logger.info("Start test");
        driver = getDriver();
        driver.get(APP_URL);

        //set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //1 - click on contact us
        WebElement contactUs = driver.findElement(By.cssSelector("#contact-link > a"));
        contactUs.click();

        //2 - fill form: Select one option eg webmaster
        //https://www.selenium.dev/documentation/webdriver/support_features/select_lists/#select-option
        WebElement subjectHeading = driver.findElement(By.cssSelector("#id_contact"));
        Select select = new Select(subjectHeading);
        select.selectByVisibleText("Webmaster");

        //3 - fill form: email
        WebElement email = driver.findElement(By.cssSelector("#email"));
        email.sendKeys("myemail@op.pl");

        //  4 - fill form: order reference
        WebElement orderReference = driver.findElement(By.cssSelector("#id_order"));
        orderReference.sendKeys("13-15-17");

        //  5 - fill form: message
        WebElement message = driver.findElement(By.cssSelector("#message"));
        message.sendKeys("I have a problem with my order");

        //6 - attach file
        WebElement fileUpload = driver.findElement(By.cssSelector("#fileUpload"));
        fileUpload.sendKeys("C:\\Users\\dhryciuk\\Desktop\\backup\\sii dokumenty\\szkolenie SII - Selenium\\projekt_automationPractice\\mouseOperations\\test.txt");

        //7 - click on send button
        WebElement sendButton = driver.findElement(By.cssSelector("#submitMessage"));
        sendButton.click();

        //8 - verify if message was sent
        WebElement successMessage = driver.findElement(By.cssSelector(".alert-success"));
        String messageText = successMessage.getText();
        assertThat(messageText).contains("Your message has been successfully sent to our team.");


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
