package tests.noweTesty;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AdvanceSeleniumTableTest {
    private WebDriver driver;
    private JavascriptExecutor js;

    @BeforeEach
    void setEach() {
        WebDriverManager.chromedriver().setup();
        // Inicjalizacja WebDriver dla Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        //options.addArguments("--headless");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    public void getAdvanceSimple() {
        String url = "https://demo.aspnetawesome.com/GridClientDataDemo";
        driver.get(url);

        //liczba wierszy - na start 10
        checkDefaultRowsCount();

        changeTableSize();

        //liczba wierszy - po zmianie 100
        checkDefaultRowsCount();

        //table walidation - get map - Person - Food
        WebElement table = driver.findElement(By.cssSelector("#GridClientData .awe-mcontent .awe-content .awe-tablw table"));
        List<Map<String, String>> results = getFoods(table);
        System.out.println(results);

        //table walidation - get map - Person - Food
        String person = "Alan";
        String foodForLuke = getFoodForPerson(person, results);
        System.out.println(person + " prefers " + foodForLuke);
    }

    public String getFoodForPerson(String person, List<Map<String, String>> foods) {
        return foods.stream()
                .filter(currencyMap -> currencyMap.containsKey(person))
                .map(currencyMap -> currencyMap.get(person))
                .findFirst()
                .orElse("Location not found for: " + person);
    }

    public List<Map<String, String>> getFoods(WebElement tableElement) {
        List<Map<String, String>> table = new ArrayList<>();
        String source = "<table>" + getElementSource(tableElement) + "</table>";

        Jsoup.parseBodyFragment(source).select("tr").forEach(row -> {
            List<String> columns = row.select("td").eachText();
            table.add(Collections.singletonMap(columns.get(1), columns.get(2)));
        });
        return table;
    }

    public String getElementSource(WebElement element) {
        js = (JavascriptExecutor) driver;
        return (String) this.js.executeScript("return arguments[0].innerHTML;", element);
    }

    private void changeTableSize() {
        driver.findElement(By.cssSelector("#GridClientDataPageSize-awed")).click();


        WebElement numList = driver.findElement(By.xpath("//*[@id='GridClientDataPageSize-dropmenu']/div[2]/ul"));

        // Znajdź wszystkie elementy <li> w tym <ul>
        List<WebElement> options = numList.findElements(By.tagName("li"));

        // Przejdź przez opcje i kliknij tę z tekstem '100'
        for (WebElement option : options) {
            if (option.getText().trim().equals("100")) {
                option.click();
                break;
            }
        }
    }

    private void checkDefaultRowsCount() {
        String pageSource = driver.getPageSource();
        Document doc = Jsoup.parse(pageSource);

        //liczba wierszy - na start 10
        Elements rows = doc.select("#GridClientData .awe-mcontent .awe-content .awe-tablw table tbody tr");
        System.out.printf("Liczba wierszy %s \n", rows.size());
    }

}
