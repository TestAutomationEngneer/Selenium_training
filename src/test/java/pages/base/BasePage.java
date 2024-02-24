package pages.base;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


@Slf4j
public abstract class BasePage {
    protected WebDriver driver;

    protected WebDriverWait wait;

    private Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int timeout = Integer.parseInt(System.getProperty("timeout", "10"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        PageFactory.initElements(driver,this);
    }

    public void clickOnButton(WebElement element) {
        String webElementText = element.getText();
        waitForElementToBeClickable(element);

        actions = new Actions(driver);
        actions.click(element).perform();

        log.info("Element: " + webElementText + " has been clicked");
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        if (element.getTagName().equals("img")) {
            log.info("Waiting for image " + element.getAttribute("src"));
        } else {
            log.info("Waiting for " + element.getText());
        }
    }

    public void mouseMover(WebElement element){
        actions = new Actions(driver);
        actions.moveToElement(element).perform();
        if (element.getTagName().equals("img")) {
            log.info("Waiting for image " + element.getAttribute("src"));
        } else {
            log.info("Waiting for " + element.getText());
        }
    }

    protected void waitToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        if (element.getTagName().equals("img")) {
            log.info("Waiting for image " + element.getAttribute("src"));
        } else {
            log.info("Waiting for " + element.getText());
        }
    }
    }

