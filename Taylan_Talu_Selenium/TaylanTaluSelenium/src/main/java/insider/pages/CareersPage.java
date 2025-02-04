package insider.pages;

import insider.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersPage extends BasePage {

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".ml-0")
    private WebElement locationsBlock;

    @FindBy(xpath = "//a[.='See all teams']")
    private WebElement teamsBlock;

    @FindBy(xpath = "//h2[.='Life at Insider']")
    private WebElement lifeAtInsiderBlock;



    public boolean areAllBlocksDisplayed() {
        return locationsBlock.isDisplayed() &&
                teamsBlock.isDisplayed() &&
                lifeAtInsiderBlock.isDisplayed();
    }


}
