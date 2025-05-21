package my.project.page.folder;

import my.project.page.base.BaseSaveProjectPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FolderPage extends BaseSaveProjectPage {

    @FindBy(xpath = "//*[@id='main-panel']/h1")
    private WebElement folderName;

    @FindBy(id = "view-message")
    private WebElement folderDescription;

    public FolderPage(WebDriver driver) {
        super(driver);
    }

    public String getFolderName() {
        return getWait5().until(ExpectedConditions.visibilityOf(folderName)).getText();
    }

    public String getFolderDescription() {
        return folderDescription.getText();
    }
}
