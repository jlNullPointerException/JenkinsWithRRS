package my.project.page.folder;

import my.project.page.base.BaseSaveProjectPage;
import my.project.page.newitem.NewItemPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FolderPage extends BaseSaveProjectPage {

    @FindBy(xpath = "//*[@id='main-panel']/h1")
    private WebElement folderName;

    @FindBy(id = "view-message")
    private WebElement folderDescription;

    @FindBy(className = "trailing-icon")
    private WebElement addJob;

    public FolderPage(WebDriver driver) {
        super(driver);
    }

    public String getFolderName() {
        return getWait5().until(ExpectedConditions.visibilityOf(folderName)).getText();
    }

    public String getFolderDescription() {
        return folderDescription.getText();
    }

    public NewItemPage createJob() {
        addJob.click();
        return new NewItemPage(getDriver());
    }

    public boolean isJobListEmpty() {
        return getWait5().until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(
                By.id("main-panel")))).getText().contains("Welcome to Jenkins!");
    }

    public List<String> getJobNameList() {
        if (isJobListEmpty()) {
            return List.of();
        }
        return getDriver().findElements(By.cssSelector(".jenkins-table__link > span:nth-child(1)")).stream()
                .map(WebElement::getText).toList();
    }
}
