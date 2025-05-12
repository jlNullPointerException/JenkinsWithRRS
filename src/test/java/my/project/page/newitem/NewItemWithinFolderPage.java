package my.project.page.newitem;

import my.project.common.BasePage;
import my.project.page.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewItemWithinFolderPage extends BasePage {
    public NewItemWithinFolderPage(WebDriver driver) {
        super(driver);
    }

    public NewItemWithinFolderPage sendItemName(String name) {
        getDriver().findElement(By.id("name")).sendKeys(name);

        return this;
    }

    public HomePage selectFreestyleClickOkAndReturnToHomePage() {
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("jenkins-home-link"))).click();

        return new HomePage(getDriver());
    }

    public WebElement getInvalidItemNameError() {
        WebDriverWait wait1 = new WebDriverWait(getDriver(), Duration.ofSeconds(1));

        try {
            return wait1.until
                    (ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));
        } catch (TimeoutException e) {
            getDriver().findElement(By.id("name")).sendKeys(" ");
            return wait1.until
                    (ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));
        }
    }
}
