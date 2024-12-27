package tests.noweTesty;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

public class AdvanceSeleniumTest {
    private WebDriver driver;
    private JavascriptExecutor js;

    @BeforeEach
    void setEach() {
        WebDriverManager.chromedriver().setup();
        // Inicjalizacja WebDriver dla Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    public void getAdvanceSimple() {
        String url = "https://cosmocode.io/automation-practice-webtable/";
        driver.get(url);

        //wykorzystanie JSoup
        String pageSource = driver.getPageSource();
        Document doc = Jsoup.parse(pageSource);

        //body
        Element body = doc.body();
        //System.out.println(doc);

        //webElement for the table
        WebElement table = driver.findElement(By.id("countries"));
        List<Map<String, String>> capitols = getCapitols(table);
        List<Map<String, String>> currencies = getCurrencies(table);
        System.out.println(capitols);
       // System.out.println(currencies);

        //get currency for POland
        String polandCurrency = getCurrencyForTheCountry("Poland", currencies);
       // System.out.println(polandCurrency);
       // System.out.println("-".repeat(20));

        //get first for h3
        //  Element experiment = experiment(table);

        //get text from h3
        // String text = experiment(table).text();

        //  System.out.println(experiment);
        // System.out.println(text);

    }

    public Element experiment(WebElement tableElement) {
        List<Map<String, String>> table = new ArrayList<>();
        String source = getElementSource(tableElement);

        //Element body = Jsoup.parseBodyFragment(source);
        Element body = Jsoup.parseBodyFragment(source).select("h3").first();

        return body;
    }


    public List<Map<String, String>> getCapitols(WebElement tableElement) {
        List<Map<String, String>> table = new ArrayList<>();
        String source = "<table>" + getElementSource(tableElement) + "</table>";

        Jsoup.parseBodyFragment(source).select("tr").forEach(row -> {
            List<String> columns = row.select("td").eachText();
            table.add(Collections.singletonMap(columns.get(0), columns.get(1)));
        });

        return table;
    }

    public List<Map<String, String>> getCurrencies(WebElement tableElement) {
        List<Map<String, String>> table = new ArrayList<>();
        String source = "<table>" + getElementSource(tableElement) + "</table>";

        Jsoup.parseBodyFragment(source).select("tr").forEach(row -> {
                    List<String> columns = row.select("td").eachText();
                    table.add(Collections.singletonMap(columns.get(0), columns.get(2)));
                });
        return table;
    }

    public String getElementSource(WebElement element) {
        js = (JavascriptExecutor) driver;
        return (String) this.js.executeScript("return arguments[0].innerHTML;", element);
    }

//    public String getCurrencyForTheCountry(String country, List<Map<String, String>> currencies) {
//        for (Map<String, String> currencyMap : currencies) {
//            if (currencyMap.containsKey(country)) {
//                return currencyMap.get(country);
//            }
//        }
//        return "Currency not found for: " + country;
//    }

    public String getCurrencyForTheCountry(String country, List<Map<String, String>> currencies) {
        return currencies.stream()
                .filter(currencyMap -> currencyMap.containsKey(country))
                .map(currencyMap -> currencyMap.get(country))
                .findFirst()
                .orElse("Currency not found for: " + country);
    }
}
