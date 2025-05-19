package my.project.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseSaveProjectPage extends BasePage {

    @FindBy(id = "description-link")
    private WebElement descriptionButton;

    @FindBy(css = "textarea[name='description']")
    private WebElement descriptionTextarea;

    @FindBy(css = "#description > div")
    private WebElement descriptionText;

    @FindBy(xpath = "//*[contains(@class, 'page-headline')]")
    private WebElement projectName;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public BaseSaveProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return getWait5().until(ExpectedConditions.visibilityOf(projectName)).getText();
    }

    public String getDescription() {
        return descriptionText.getText();
    }

    public BaseSaveProjectPage clickDescriptionButton() {
        descriptionButton.click();

        return this;
    }

    public BaseSaveProjectPage sendDescription(String text) {
        descriptionTextarea.sendKeys(text);

        return this;
    }

    public BaseSaveProjectPage deleteDescription() {
        descriptionTextarea.clear();

        return this;
    }

    public BaseSaveProjectPage clickSaveButton() {
        saveButton.click();

        return this;
    }
}
