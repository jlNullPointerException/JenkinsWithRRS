package my.project.page.multiconfiguration;

import my.project.common.BasePage;
import my.project.page.saveItem.AllCreateProjectPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MultiConfigurationConfigurationPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement buttonSave;

    @FindBy(name = "description")
    private WebElement fieldDescription;

    public MultiConfigurationConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public AllCreateProjectPage clickSaveButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return new AllCreateProjectPage(getDriver());
    }

    public MultiConfigurationConfigurationPage addDescription(String text) {
        fieldDescription.sendKeys(text);

        return this;
    }
}
