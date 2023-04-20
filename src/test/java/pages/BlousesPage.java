package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

public class BlousesPage extends BasePage {
    public BlousesPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".product_list .product_img_link")
    private WebElement productImage;

    @FindBy(css = ".quick-view")
    private WebElement quickView;


    public BlousesPage moveMouseOnProductImage() {
        waitToBeVisible(productImage);
        mouseMover(productImage);
        return this;
    }


    public BlousesPage clickOnQuickView() {
        waitForElementToBeClickable(quickView);
        clickOnButton(quickView);
        return this;
    }

}


