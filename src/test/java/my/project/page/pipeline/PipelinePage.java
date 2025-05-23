package my.project.page.pipeline;

import my.project.page.base.BaseSaveProjectPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PipelinePage extends BaseSaveProjectPage {

//    @FindBy(id = "main-panel")
//    private WebElement nameJobInFolder;

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

//    public String getJobNameInFolder() {
//        return getWait5().until(ExpectedConditions.visibilityOf(nameJobInFolder)).getText();
//    }
}
