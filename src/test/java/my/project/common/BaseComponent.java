package my.project.common;

import org.openqa.selenium.WebDriver;

public abstract class BaseComponent extends BaseModel {

    public BaseComponent(WebDriver driver) {
        super(driver);
    }
}
