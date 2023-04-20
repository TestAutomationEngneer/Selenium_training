package tests;

import org.junit.jupiter.api.Assertions;
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
        BlousesPagePopUp productPopupPage = new BlousesPagePopUp(driver);
        topMenuPage.moveMouseOnWomenCategory().clickOnBlousesSubCategory();
        blousesPage.moveMouseOnProductImage().clickOnQuickView();
        productPopupPage.waitForPopup().switchToIframe();
        for (int i = 0; i < productPopupPage.getProductImages().size(); i++) {
            productPopupPage.moveMouseToImage(i);
            String miniatureSrc = productPopupPage.getProductImages().get(i).getAttribute("src").replace("-cart_default.jpg", "");
            String bigPicSrc = productPopupPage.getBigPic().getAttribute("src").replace("-large_default.jpg", "");
            Assertions.assertEquals(miniatureSrc, bigPicSrc);
        }
    }
}
