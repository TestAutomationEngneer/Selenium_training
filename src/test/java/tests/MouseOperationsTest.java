package tests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BlousesPage;
import pages.BlousesPagePopUp;
import pages.TopMenuPage;
public class MouseOperationsTest extends BaseTest {
    @Test
    @DisplayName("Verification")
    void TopMenuVerification() {
        TopMenuPage topMenuPage = new TopMenuPage(driver);
        BlousesPage blousesPage = new BlousesPage(driver);
        BlousesPagePopUp blousesPagePopUp = new BlousesPagePopUp(driver);
        topMenuPage.moveMouseOnWomenCategory().clickOnBlousesSubCategory();
        blousesPage.moveMouseOnProductImage().clickOnQuickView();
        blousesPagePopUp.waitForPopup().switchToIframe();
        blousesPagePopUp.verifyProductImages(blousesPagePopUp.getProductImages());
    }
}

