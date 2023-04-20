package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

import java.util.List;

public class BlousesPagePopUp extends BasePage {
    public BlousesPagePopUp(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#thumbs_list_frame a[title='Blouse'] img")
    private List<WebElement> productImages;

    @FindBy(css = "#bigpic")
    private WebElement bigPic;

    @FindBy(css = ".fancybox-close")
    private WebElement popUpCloseButton;

    @FindBy(css = ".fancybox-iframe")
    private WebElement iframe;

    public List<WebElement> getProductImages() {
        return productImages;
    }

    public BlousesPagePopUp moveMouseToImage(int index) {
        mouseMover(productImages.get(index));
        return this;
    }

    public WebElement getBigPic() {
        return bigPic;
    }

    public BlousesPagePopUp waitForPopup() {
        waitToBeVisible(popUpCloseButton);
        return this;
    }

    public BlousesPagePopUp switchToIframe() {
        driver.switchTo().frame(iframe);
        return this;
    }
}
