package my.project.page.search;

import my.project.common.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public SearchPage sendSearchText(String text) {
        getWait10().until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("command-bar")))
                .sendKeys(text);

        return new SearchPage(getDriver());
    }


}
