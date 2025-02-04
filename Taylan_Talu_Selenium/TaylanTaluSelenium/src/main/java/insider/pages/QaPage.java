package insider.pages;

import insider.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

public class QaPage extends BasePage {
    public QaPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[.='See all QA jobs']")
    private WebElement seeAllQaJobsButton;

    @FindBy(css = "span[aria-labelledby='select2-filter-by-location-container']")
    private WebElement locationFilter;

    @FindBy(css = "#select2-filter-by-department-container")
    private WebElement departmentFilter;

    @FindBy(css = ".job-item")
    private List<WebElement> jobList;

    private By viewRoleButton = By.cssSelector("a.btn.btn-navy");

    public void navigateToQaPage() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
    }

    public void clickSeeAllQaJobs() {
        waitForElementClickable(seeAllQaJobsButton);
        seeAllQaJobsButton.click();
    }

    public void filterJobs() throws InterruptedException {
        Thread.sleep(5000);
        locationFilter.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[contains(text(),'Istanbul, Turkey')]")));
        option.click();
        departmentFilter.click();
       WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"top-filter-form\"]/div[2]/span/span[1]/span")));
        option2.click();
    }

    public boolean validateJobListings(String position, String department, String location) {
        for (WebElement job : jobList) {
            String jobPosition = job.findElement(By.cssSelector(".position")).getText();
            String jobDepartment = job.findElement(By.cssSelector(".department")).getText();
            String jobLocation = job.findElement(By.cssSelector(".location")).getText();

            if (!jobPosition.contains(position) ||
                    !jobDepartment.contains(department) ||
                    !jobLocation.contains(location)) {
                return false;
            }
        }
        return true;
    }

    public boolean clickViewRole()  {

       List<WebElement> buttons = driver.findElements(viewRoleButton);
        if (!buttons.isEmpty())
        {
            waitForElementClickable(buttons.get(0));
            buttons.get(0).click();
            return true;
        }
        return false;
    }


    public boolean verifyApplicationPage()  {
        return driver.getCurrentUrl().contains("jobs.lever.co");
    }
}
