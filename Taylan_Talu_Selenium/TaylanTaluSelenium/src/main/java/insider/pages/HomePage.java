package insider.pages;

import insider.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[contains(.,'Company')]")
    private WebElement companyMenu;

    @FindBy(xpath = "//a[.='Careers']")
    private WebElement careersLink;

    public void navigateToHomePage() {
        driver.get("https://useinsider.com/");
    }


    public CareersPage navigateToCareers() {
        waitForElementClickable(companyMenu);
        companyMenu.click();
        waitForElementClickable(careersLink);
        careersLink.click();
        return new CareersPage(driver);
    }
}
