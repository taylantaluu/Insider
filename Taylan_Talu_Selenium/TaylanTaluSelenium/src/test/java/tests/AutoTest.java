package tests;

import insider.pages.CareersPage;
import insider.pages.HomePage;
import insider.pages.QaPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class AutoTest {

    private WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;
    private QaPage qaPage;

    @Parameters("browser")
    @BeforeClass
    public void setup(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testHomePage() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        Assert.assertEquals(driver.getCurrentUrl(), "https://useinsider.com/");
    }

    @Test(priority = 2)
    public void testCareersPage() {
        careersPage = homePage.navigateToCareers();
        Assert.assertTrue(careersPage.areAllBlocksDisplayed());
    }


    @Test(priority = 3)
    public void testQaPage() {
        qaPage = new QaPage(driver);
        qaPage.navigateToQaPage();
        Assert.assertEquals(driver.getCurrentUrl(), "https://useinsider.com/careers/quality-assurance/");
    }

    @Test(priority = 4)
    public void testQAJobs() throws InterruptedException {
        qaPage.clickSeeAllQaJobs();
        qaPage.filterJobs();
        Assert.assertTrue(qaPage.validateJobListings(
                "Quality Assurance",
                "Quality Assurance",
                "Istanbul, Turkey"
        ));
    }

    @Test(priority = 5)
    public void testJobApplication() {
        Assert.assertTrue(qaPage.clickViewRole());
        Assert.assertTrue(qaPage.verifyApplicationPage(), "Application page verification failed!");

    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(screenshot, new File("screenshots/" + result.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
