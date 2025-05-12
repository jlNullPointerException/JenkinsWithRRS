package my.project.page.freestyle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import my.project.common.BasePage;
import my.project.common.TestUtils;
import my.project.page.HomePage;


import java.time.Duration;
import java.util.List;

public class FreestyleProjectPage extends BasePage {

    @FindBy(id = "description-link")
    private WebElement descriptionButton;

    @FindBy(css = "textarea[name='description']")
    private WebElement descriptionTextarea;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(css = "#description > div")
    private WebElement descriptionText;

    @FindBy(className = "page-headline")
    private WebElement projectName;

    @FindBy(xpath = "//span[text()='Rename']/..")
    private WebElement renameButton;

    @FindBy(name = "newName")
    private WebElement newNameField;

    @FindBy(name = "Submit")
    private WebElement renameSubmitButton;

    @FindBy(xpath = "//span[text()='Delete Project']")
    private WebElement deleteButton;

    @FindBy(css = "[data-id='ok']")
    private WebElement popUpYesDeleteProjectButton;

    @FindBy(xpath = "//span[text()='Build Now']/..")
    private WebElement leftSideMenuBuildNowButton;

    @FindBy(xpath = "//*[@id='notification-bar']")
    private WebElement buildScheduled;

    @FindBy(css = "a.task-link.task-link--active")
    private WebElement status;

    @FindBy(xpath = "//*[@href='lastBuild/']")
    private WebElement lastBuild;

    @FindBy(xpath = "//a[contains(@href, 'console')]")
    private WebElement consoleOutput;

    @FindBy (xpath = "//a[contains(@href, 'confirmDelete')]")
    private WebElement deleteBuild;

    @FindBy (xpath = "//a[contains(@href,'changes')]")
    private WebElement changes;

    @FindAll({
            @FindBy(xpath = "//*[@id='out']/div[2]"),
            @FindBy(xpath = "//*[@id='out']/div"),
            @FindBy(id = "out")
    })
    private WebElement consoleOutputFinished;

    @FindBy(xpath = "//div[@id='tasks']/div/span/a/span[2]")
    private List<WebElement> leftMenuElementsList;

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return getWait5().until(ExpectedConditions.visibilityOf(projectName)).getText();
    }

    public String getDescription() {
        return descriptionText.getText();
    }

    public FreestyleProjectPage clickEditDescriptionButton() {
        descriptionButton.click();

        return this;
    }

    public FreestyleProjectPage sendDescription(String text) {
        descriptionTextarea.clear();
        descriptionTextarea.sendKeys(text);

        return this;
    }

    public FreestyleProjectPage deleteDescription() {
        descriptionTextarea.clear();

        return this;
    }

    public FreestyleProjectPage clickPreviewDescription() {
        getDriver().findElement(By.className("textarea-show-preview")).click();

        return this;
    }

    public FreestyleProjectPage clickHidePreviewDescription() {
        getDriver().findElement(By.className("textarea-hide-preview")).click();

        return this;
    }

    public boolean isPreviewDescriptionBlockDisplayed() {
        return getDriver().findElement(By.className("textarea-preview")).isDisplayed();
    }

    public boolean isHidePreviewLinkAvailable() {

        return getDriver().findElement(By.className("textarea-hide-preview")).isDisplayed();
    }

    public String getTextFromPreviewDescriptionBlock() {
        return getDriver().findElement(By.className("textarea-preview")).getText();
    }

    public FreestyleProjectPage clickSave() {
        saveButton.click();

        return this;
    }

    public FreestyleProjectPage clickLeftSideMenuRename() {
        renameButton.click();

        return this;
    }

    public FreestyleProjectPage sendName(String text) {
        newNameField.clear();
        newNameField.sendKeys(text);

        return this;
    }

    public FreestyleProjectPage clickRename() {
        renameSubmitButton.click();

        return this;
    }

    public FreestyleProjectPage clickLeftSideMenuDelete() {
        deleteButton.click();

        return this;
    }

    public HomePage clickPopUpYesDeleteProject() {
        popUpYesDeleteProjectButton.click();

        return new HomePage(getDriver());
    }

    public FreestyleConfigurationPage clickConfigure() {
        getDriver().findElement(By.cssSelector("a[href*='configure']")).click();

        return new FreestyleConfigurationPage(getDriver());
    }

    public FreestyleProjectPage clickProjectBreadcrumbsDropDownMenu() {

        Actions actions = new Actions(getDriver());
        actions.pause(Duration.ofSeconds(1)).perform();

        WebElement arrow = getWait5().until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".jenkins-breadcrumbs__list-item:nth-child(3) .jenkins-menu-dropdown-chevron")));

        TestUtils.moveAndClickWithJS(getDriver(), arrow);

        return this;
    }

    public List<String> getDropDownMenuItemsText() {
        List<WebElement> menuItems = getWait5()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".jenkins-dropdown__item")));

        return menuItems.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    public String[] getMainMenuItemsText() {
        List<WebElement> menuItems = getDriver().findElements(By.cssSelector(".task span:nth-of-type(2)"));

        // the first element found with the locator above is Status which is not in the drop-down menu
        // (and is not technically a menu item) so we need to reduce size by one
        String[] menuItemsText = new String[menuItems.size() - 1];

        // start with i = 1 since the first element found is Status (which is not a menu item)
        for (int i = 1; i < menuItems.size(); i++) {
            menuItemsText[i - 1] = menuItems.get(i).getText();
        }

        return menuItemsText;
    }

    public String getDisabledWarningMessageText() {
        String fullText = getDriver().findElement(By.xpath("//div[@class='warning']/form")).getText();

        return fullText.split("\\n")[0];
    }

    public FreestyleProjectPage clickEnableButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        getWait5().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='warning']/form")));

        return this;
    }

    public List<WebElement> getWarningMessageList(String projectName) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='jenkins-app-bar']//h1[text()='%s']".formatted(projectName))));
        return getDriver().findElements(By.xpath("//div[@class='warning']/form"));
    }

    public FreestyleProjectPage waitUntilTextNameProjectToBePresentInH1(String text) {
        getWait5().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), text));

        return this;
    }

    public FreestyleProjectPage clickLeftSideMenuBuildNow() {
        getWait5().until(ExpectedConditions.elementToBeClickable(leftSideMenuBuildNowButton)).click();

        return this;
    }


    public List<String> getLeftSideMenuNameList() {
        getWait10().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("side-panel")));

        return leftMenuElementsList.stream()
                .map(WebElement::getText).toList();
    }

    public List<String> getLeftSideMenuWithoutStatus() {
        List<String> fullLeftMenu = getLeftSideMenuNameList();
        return fullLeftMenu.subList(1, fullLeftMenu.size());
    }

    public FreestyleProjectPage clickBuildNowButton(int expectedClicks) {
        WebElement button = getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']"));
        By logLocator = By.className("app-builds-container__item__inner__link");
        String previousLogNumber = "";

        for (int i = 1; i <= expectedClicks; i++) {
            button.click();
            getWait10().until(ExpectedConditions.presenceOfElementLocated(logLocator));
            WebElement latestLog = getDriver().findElements(logLocator).get(0);
            String currentLogNumber = latestLog.getText();

            if (!previousLogNumber.isEmpty()) {
                getWait10().until(ExpectedConditions.not(
                        ExpectedConditions.textToBePresentInElement(latestLog, previousLogNumber)
                ));
            }
            previousLogNumber = currentLogNumber;
        }
        return this;
    }

    public List<WebElement> getListOfBuilds() {
        return getDriver().findElements(By.className("app-builds-container__item"));
    }

    public List<String> getBuildNameList() {
        return getDriver().findElements(By.className("app-builds-container__item")).stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> waitForBuildToAppear( int timeoutSeconds) {
        for (int i = 0; i < timeoutSeconds; i += 10) {
            List<String> builds = this.getBuildNameList();
            if (!builds.isEmpty()) {
                return builds;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        throw new AssertionError("Build did not start within expected time");
    }

    public boolean isTextBuildScheduled() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return buildScheduled.getText().equals("Build scheduled");
    }

    public FreestyleProjectPage clickStatus() {
        status.click();

        return this;
    }

    public FreestyleProjectPage clickLastBuild() {
        getWait10().until(ExpectedConditions.elementToBeClickable(lastBuild)).click();

        return this;
    }

    public FreestyleProjectPage clickConsoleOutput() {
        consoleOutput.click();

        return this;
    }

    public boolean isFinishedSuccess(){
        return getWait10()
                .until(ExpectedConditions.elementToBeClickable(consoleOutputFinished))
                .getText()
                .contains("Finished: SUCCESS");
    }

    public FreestyleProjectPage clickDeleteBuild() {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteBuild)).click();
        return this;
    }

    public FreestyleProjectPage clickSubmitDeleteBuild() {
        getDriver().findElement(By.name("Submit")).click();
        return this;
    }

    public FreestyleProjectPage clickChanges() {
        getWait10().until(ExpectedConditions.elementToBeClickable(changes)).click();
        return this;
    }

    public boolean isBuildDeleted() {
        return getWait10()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("main-panel")))
                .getText()
                .contains("No builds");
    }
}
