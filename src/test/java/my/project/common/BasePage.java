package my.project.common;

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
