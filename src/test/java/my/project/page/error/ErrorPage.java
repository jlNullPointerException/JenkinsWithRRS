package my.project.page.error;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import my.project.common.BasePage;

public class ErrorPage extends BasePage {

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();
    }

    public String getErrorText() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/p")).getText();
    }
}
