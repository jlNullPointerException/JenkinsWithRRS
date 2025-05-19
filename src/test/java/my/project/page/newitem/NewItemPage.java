package my.project.page.newitem;

import my.project.page.base.BasePage;
import my.project.page.error.ErrorPage;

import my.project.page.freestyle.FreestyleConfigurationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;

public class NewItemPage extends BasePage {

    public static List<String> itemType = List.of(
            "Freestyle project",
            "Pipeline",
            "Multi-configuration project",
            "Folder",
            "Multibranch Pipeline",
            "Organization Folder"
    );
    public static List<String> unacceptableSpecChar = List.of(
            "!", "@", ";", ":", "[", "]", "<", ">",
            "$", "%", "&", "?", "*", "|", "\\" ,"/"
    );

    @FindBy(id = "name")
    private WebElement itemName;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(id = "itemname-required")
    private WebElement emptyError;

    @FindBy(id = "itemname-invalid")
    private WebElement errorText;

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

    public <T> T clickOkButton(T resultPage) {
        okButton.click();

        return resultPage;
    }

    public <T> T selectItemAndClickOk(int itemIndex, T resultPage) {
        selectItemTypeByName(itemType.get(itemIndex));
        okButton.click();

        return resultPage;
    }

    public NewItemPage sendTextCopyForm(String text) {
        WebElement actualTextCopyForm = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("from")));
        actualTextCopyForm.sendKeys(text);

        return new NewItemPage(getDriver());
    }

    public String getEmptyErrorText() {
        return getWait5()
                .until(ExpectedConditions.visibilityOf(emptyError))
                .getText();
    }

    public String getEmptyErrorColor() {
       return getWait5()
               .until(ExpectedConditions.visibilityOf(emptyError))
               .getCssValue("color");
    }

    public String getErrorText() {
        return getWait5()
                .until(ExpectedConditions.visibilityOf(errorText))
                .getText();
    }

    public String getErrorColor() {
        return getWait5()
                .until(ExpectedConditions.visibilityOf(errorText))
                .getCssValue("color");
    }

    public String findUnacceptableChar(String itemName) {

        Optional<String> firstUnacceptableChar = itemName.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .filter(unacceptableSpecChar::contains)
                .findFirst();

        return firstUnacceptableChar.orElse(null);

    }

}

