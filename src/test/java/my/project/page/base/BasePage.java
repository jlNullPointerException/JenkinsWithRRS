package my.project.page.base;

import my.project.common.BaseModel;
import org.openqa.selenium.WebDriver;
import my.project.component.HeaderComponent;

public abstract class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HeaderComponent getHeader() {
        return new HeaderComponent(getDriver());
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
}
