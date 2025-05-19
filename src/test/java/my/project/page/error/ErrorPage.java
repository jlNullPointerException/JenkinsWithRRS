package my.project.page.error;

import org.openqa.selenium.WebDriver;
import my.project.page.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ErrorPage extends BasePage {

    @FindBy(xpath = "//*[@id='main-panel']/h1")
    private WebElement titleText;

    @FindBy(xpath = "//*[@id='main-panel']/p")
    private WebElement errorText;

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return titleText.getText();
    }

    public String getErrorText() {
        return errorText.getText();
    }
}
