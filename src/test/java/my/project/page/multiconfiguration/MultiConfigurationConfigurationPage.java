package my.project.page.multiconfiguration;

import my.project.page.base.BaseConfigurationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultiConfigurationConfigurationPage extends BaseConfigurationPage {

    @FindBy(name = "description")
    private WebElement fieldDescription;

    public MultiConfigurationConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationConfigurationPage addDescription(String text) {
        fieldDescription.sendKeys(text);

        return this;
    }
}
