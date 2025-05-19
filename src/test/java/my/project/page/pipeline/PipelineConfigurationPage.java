package my.project.page.pipeline;

import my.project.page.base.BaseConfigurationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PipelineConfigurationPage extends BaseConfigurationPage {

    @FindBy(name = "description")
    private WebElement fieldDescription;

    public PipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public PipelineConfigurationPage addDescription(String text) {
        fieldDescription.sendKeys(text);

        return this;
    }
}
