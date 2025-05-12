package my.project.page.newitem;

import my.project.common.BasePage;
import my.project.page.error.ErrorPage;

import my.project.page.freestyle.FreestyleConfigurationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class NewItemPage extends BasePage {
    public static List<String> itemType = List.of(
            "Freestyle project",
            "Pipeline",
            "Multi-configuration project",
            "Folder",
            "Multibranch Pipeline",
            "Organization Folder"
    );

    @FindBy(id = "name")
    private WebElement itemName;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(className = "hudson_model_FreeStyleProject")
    public WebElement freestyleProject;

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage sendItemName(String name) {
        itemName.sendKeys(name);

        return this;
    }

    public NewItemPage selectItemTypeByName(String itemName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='%s']".formatted(itemName)))).click();

        return this;
    }

    public boolean isOkButtonEnabled() {
        return okButton.isEnabled();
    }

    public FreestyleConfigurationPage clickOkButton() {
        okButton.click();

        return new FreestyleConfigurationPage(getDriver());
    }

    public ErrorPage clickOkButtonWithError() {
        okButton.click();

        return new ErrorPage(getDriver());
    }

    public FreestyleConfigurationPage selectFreestyleAndClickOk() {
        selectItemTypeByName(itemType.get(0));
        okButton.click();

        return new FreestyleConfigurationPage(getDriver());
    }

    public NewItemPage sendTextCopyForm(String text) {
        WebElement actualTextCopyForm = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("from")));
        actualTextCopyForm.sendKeys(text);

        return new NewItemPage(getDriver());
    }


}

