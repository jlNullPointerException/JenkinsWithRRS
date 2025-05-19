package my.project.page.freestyle;

import my.project.page.base.BaseConfigurationPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class FreestyleConfigurationPage extends BaseConfigurationPage {

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement fieldDescription;

    @FindBy(xpath = "//*[@id='main-panel']/form/div[1]/section[1]/div[12]/div[1]/div/span/label")
    private WebElement throttleBuilds;

    @FindBy(name = "_.durationName")
    private WebElement sTimePeriod;

    @FindBy(name = "description")
    private WebElement description;

    @FindBy(name = "_.throttle")
    private WebElement throttle;

    public FreestyleConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleConfigurationPage addDescription(String text) {
        fieldDescription.sendKeys(text);

        return this;
    }

    public FreestyleConfigurationPage clickThrottleBuilds() {
        throttleBuilds.click();

        return this;
    }

    public FreestyleConfigurationPage selectTimePeriod(String period) {
        WebElement timePeriod = getWait5().until(ExpectedConditions.elementToBeClickable(sTimePeriod));
        timePeriod.click();
        new Select(timePeriod).selectByVisibleText(period);

        return this;
    }

    public String getDescriptionText() {
        return getWait10().until(ExpectedConditions.visibilityOf(description)).getText();
    }

    public boolean getSelectThrottleBuilds() {
        return throttle.isSelected();
    }

    public String getTimePeriod() {
        return new Select(sTimePeriod).getFirstSelectedOption().getText();
    }


}