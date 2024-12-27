package tests.noweTesty;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleSeleniumTest extends TestBase {

    @Test
    public void getTitleSimple() throws InterruptedException {
        String url = "https://www.demoblaze.com/";
        checkTitleOfthePage(url);

        // Rejestracja użytkownika za pomocą API RestAssured

        String username = "toomaszek21";
        String password = "tomcruze";


        registerUserBasedAPI(username);
        //registerUserGUI(username, password);


        loginToApplicationGUI(username, password);
    }

    private void checkTitleOfthePage(String url) {
        driver.get(url);
        String actulTitle = driver.getTitle();
        assertThat(actulTitle).isEqualTo("STORE");
    }

    private void loginToApplicationGUI(String username, String password) throws InterruptedException {
        // Przejście do logowania
        driver.findElement(By.id("login2")).click();
        Thread.sleep(2000); // Czekanie na załadowanie modalu

        // Logowanie użytkownika
        driver.findElement(By.id("loginusername")).sendKeys(username);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        // Sprawdzanie, czy użytkownik został zalogowany (np. sprawdzenie widoczności przycisku "Welcome")
        Thread.sleep(2000);
        boolean isLoggedIn = driver.findElement(By.id("nameofuser")).isDisplayed();

        if (isLoggedIn) {
            System.out.println("Użytkownik został pomyślnie zalogowany = " + username);
        } else {
            System.err.println("Logowanie nie powiodło się.");
        }
    }

    private void registerUserGUI(String username, String password) throws InterruptedException {
        // Kliknięcie przycisku SignUp
        WebElement signUpButton = driver.findElement(By.id("signin2"));
        signUpButton.click();

        // Poczekanie na załadowanie modalu
        Thread.sleep(2000);

        // Wypełnienie formularza rejestracji
        WebElement usernameField = driver.findElement(By.id("sign-username"));
        WebElement passwordField = driver.findElement(By.id("sign-password"));
        WebElement signUpSubmitButton = driver.findElement(By.xpath("//button[text()='Sign up']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        signUpSubmitButton.click();
        Thread.sleep(2000);
        //alert
        var alert = driver.switchTo().alert();
        //Store the alert text in a variable and verify it
        var text = alert.getText();
        assertThat(text).isEqualTo("Sign up successful.");
        alert.accept();

        // Poczekanie na odpowiedź serwera
        Thread.sleep(3000);

        System.out.println("Rejestracja użytkownika angelina zakończona.");
    }
}
