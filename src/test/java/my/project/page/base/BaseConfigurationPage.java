package my.project.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseConfigurationPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement buttonSave;

    public BaseConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public <T> T clickSaveButton(T resultPage) {
        getWait5().until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return resultPage;
    }
}