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
import tests.base.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class WebTableAdvanceTest extends BaseTest {

    @Test
    @DisplayName("Check countries and capitals - rows")
    void WebTableVerification() {

        //1. count how many rows are in the table
        //expected result: 197
        List<WebElement> table = driver.findElements(By.cssSelector("#countries > tbody > tr"));
        int rows = table.size();
        logger.info("Number of rows: " + rows);
        assertThat(rows).isEqualTo(197);

        //3. Check Poland Visited checkboxes #139
        List<WebElement> elements = driver.findElements(By.cssSelector(".hasVisited[type='checkbox']"));
        //find checkbox
        WebElement checkbox = findCheckbox(elements, 139);

        //scroll to element 145
        scrollToElement(elements.get(145));

        //click on checkbox with Poland
        clickElement(checkbox);
        assertThat(checkbox.isSelected()).isTrue();
    }

    @Test
    @DisplayName("Check countries and capitals simple")
    void WebTablePolandVerificationSimple() {

        //1. Check capital of Poland
        //$$("#countries > tbody > tr:nth-child(141) > td:nth-child(3)")
        int rowPoland = 0;
        List<WebElement> table = driver.findElements(By.cssSelector("#countries > tbody > tr"));
        for (WebElement e : table) {
            if (e.getText().contains("Poland")) {
                break;
            }
            rowPoland++;
        }
        WebElement capital = driver.findElement(By.cssSelector("#countries > tbody > tr:nth-child(" + (rowPoland + 1) + ") > td:nth-child(3)"));
        logger.info("Capital of Poland: " + capital.getText());
        assertThat(capital.getText()).isEqualTo("Warsaw");
    }

    @Test
    @DisplayName("Check countries and capitals Poland")
    void WebTablePolandVerificationAdvance() {
        //1. Check capital of Poland
        String capital = getCapitolForCountry("Poland");
        logger.info("Capital of Poland: " + capital);
        assertThat(capital).isEqualTo("Warsaw");
    }
    @Test
    @DisplayName("Check countries and capitals Burkina Faso")
    void WebTableBurkinaFasoVerificationAdvance() {
        //1. Check capital of Burkina Faso
        String capital = getCapitolForCountry("Burkina Faso");
        logger.info("Capital of Burkina Faso: " + capital);
        assertThat(capital).isEqualTo("Ouagadougou");
    }
}
