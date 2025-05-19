package my.project.page.saveItem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import my.project.common.BasePage;


import java.util.List;

public class AllCreateProjectPage extends BasePage {

    @FindBy(id = "description-link")
    private WebElement descriptionButton;

    @FindBy(css = "textarea[name='description']")
    private WebElement descriptionTextarea;

    @FindBy(css = "#description > div")
    private WebElement descriptionText;

    @FindBy(xpath = "//*[contains(@class, 'page-headline')]")
    private WebElement projectName;

    @FindBy(xpath = "//div[@id='tasks']/div/span/a/span[2]")
    private List<WebElement> leftMenuElementsList;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    public AllCreateProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return getWait5().until(ExpectedConditions.visibilityOf(projectName)).getText();
    }

    public String getDescription() {
        return descriptionText.getText();
    }

    public AllCreateProjectPage clickDescriptionButton() {
        descriptionButton.click();

        return this;
    }

    public AllCreateProjectPage sendDescription(String text) {
        descriptionTextarea.sendKeys(text);

        return this;
    }

    public AllCreateProjectPage deleteDescription() {
        descriptionTextarea.clear();

        return this;
    }

    public AllCreateProjectPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public List<String> getLeftSideMenuNameList() {
        getWait10().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("side-panel")));

        return leftMenuElementsList.stream()
                .map(WebElement::getText).toList();
    }
}
