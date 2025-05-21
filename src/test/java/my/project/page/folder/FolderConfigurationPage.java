package my.project.page.folder;

import my.project.page.base.BaseConfigurationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FolderConfigurationPage extends BaseConfigurationPage {

    @FindBy(name = "_.description")
    private WebElement fieldDescription;

    public FolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public FolderConfigurationPage addDescription(String text) {
        fieldDescription.sendKeys(text);

        return this;
    }
}
