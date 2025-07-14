package my.project.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import my.project.page.base.BasePage;

import my.project.page.newitem.NewItemPage;


import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItemButtonOnLeftSidePanel;

    @FindBy(xpath = "//*[@id='jenkins']/dialog[2]/div[3]/button[1]")
    private WebElement buttonYesDeleteProject;

    public static List<String> jobMenu = List.of(
            "Changes",
            "Build Now",
            "Configure",
            "Delete Project",
            "Delete Pipeline",
            "Stages",
            "Rename",
            "Pipeline Syntax",
            "Delete Multi-configuration project",
            "New Item",
            "Delete Folder",
            "Build History",
            "Credentials",
            "Scan Multibranch Pipeline Log",
            "Multibranch Pipeline Events",
            "Delete Multibranch Pipeline",
            "Move",
            "Scan Organization Folder Log",
            "Organization Folder Events",
            "Delete Organization Folder"
    );

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isJobListEmpty() {
        return getWait5().until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(
                By.id("main-panel")))).getText().contains("Welcome to Jenkins!");
    }

    public NewItemPage clickNewItemOnLeftSidePanel() {
        getWait10().until(ExpectedConditions.elementToBeClickable(newItemButtonOnLeftSidePanel)).click();

        return new NewItemPage(getDriver());
    }

    public void selectItemByNameAndClickMenu(String nameItem) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='%s']".formatted(nameItem)))).click();
    }

    public void selectActionWithItemAndClick(String actionName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(@class, 'jenkins-dropdown__item') and contains(normalize-space(.), '%s')]"
                        .formatted(actionName)))).click();
    }

    public <T> T clickOnAction(String nameItem, String actionName, T resultPage) {
        selectItemByNameAndClickMenu(nameItem);
        selectActionWithItemAndClick(actionName);

        return resultPage;
    }

    public HomePage confirmDeleteProject() {
        buttonYesDeleteProject.click();
        return this;
    }

    public List<String> getProjectNameList() {
        if (isJobListEmpty()) {
            return List.of();
        }
        return getDriver().findElements(By.cssSelector(".jenkins-table__link > span:nth-child(1)")).stream()
                .map(WebElement::getText).toList();
    }

    public <T> T clickOnItem(String itemName, T resultPage) {
       WebElement item = getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='job_%s']/td[3]/a/span"
                        .formatted(itemName))));
       Actions actions = new Actions(getDriver());
       actions.moveToElement(item).perform();
       item.click();

        return resultPage;
    }
}
