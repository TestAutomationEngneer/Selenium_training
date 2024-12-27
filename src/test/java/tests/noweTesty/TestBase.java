package tests.noweTesty;


import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.glytching.junit.extension.watcher.WatcherExtension;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

@ExtendWith({WatcherExtension.class})
public class TestBase {

    @RegisterExtension
    static SeleniumJupiter seleniumJupiter = new SeleniumJupiter();

    protected WebDriver driver;
    public static final File REPORT_FILE = new File("target/reports/extent-report/index.html");

    @BeforeAll
    static void setUp() {
        seleniumJupiter.getConfig().setScreenshot(true);
        seleniumJupiter.getConfig().setRecording(true);
        seleniumJupiter.getConfig().setOutputFolder(REPORT_FILE.getParent());
    }

    @BeforeEach
    void setEach(FirefoxDriver driver) {
        this.driver = driver;
    }

    protected void registerUserBasedAPI(String username) {
        // Rejestracja użytkownika za pomocą API RestAssured
        String baseUrl = "https://api.demoblaze.com/signup";

        String payload = String.format("""
                {"username": "%s", "password": "dG9tY3J1emU="}
                """, username);


        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post(baseUrl);

        if (response.getStatusCode() == 200) {
            System.out.println("Rejestracja użytkownika powiodła się: " + username);
        } else {
            System.err.println("Rejestracja użytkownika nie powiodła się. Status: " + response.getStatusCode());
        }
    }

}
