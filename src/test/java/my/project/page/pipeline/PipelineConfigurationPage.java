package my.project.page.pipeline;

import my.project.common.BasePage;
import my.project.page.saveItem.AllCreateProjectPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PipelineConfigurationPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement buttonSave;

    @FindBy(name = "description")
    private WebElement fieldDescription;

    public PipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public AllCreateProjectPage clickSaveButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return new AllCreateProjectPage(getDriver());
    }

    public PipelineConfigurationPage addDescription(String text) {
        fieldDescription.sendKeys(text);

        return this;
    }
}
