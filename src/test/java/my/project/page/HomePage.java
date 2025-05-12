package my.project.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import my.project.common.BasePage;
import my.project.common.TestUtils;

import my.project.page.freestyle.FreestyleProjectPage;

import my.project.page.newitem.NewItemPage;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    @FindBy(css = "#description > div:nth-child(1)")
    private WebElement descriptionText;

    @FindBy(xpath = "//span[text()='log out']")
    private WebElement logOutButton;

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newItemButtonOnLeftSidePanel;

    @FindBy(name = "description")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//td//a[@tooltip][contains(@href,'build')]")
    private WebElement scheduleBuild;

    @FindBy(id = "notification-bar")
    private WebElement buildScheduled;

    @FindBy(css = "a[aria-label='New View']")
    private WebElement newViewPlus;

    @FindBy(css = "footer .jenkins_ver")
    private WebElement jenkinsVersionButton;

    @FindBy(xpath = "//a[@href='/manage/about']")
    private WebElement jenkinsAboutOptionInJenkinsVersionDropDownMenu;

    private final static String JOB_PATTERN = "//tr[@id='job_%s']";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickAddDescriptionButton() {
        getDriver().findElement(By.id("description-link")).click();

        return this;
    }

    public boolean isJobListEmpty() {
        return getWait5().until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(
                By.id("main-panel")))).getText().contains("Welcome to Jenkins!");
    }

    public HomePage sendDescription(String text) {
        descriptionTextArea.sendKeys(text);

        return this;
    }

    public HomePage clearDescription() {
        descriptionTextArea.clear();

        return this;
    }

    public HomePage clickSaveDescriptionButton() {
        getDriver().findElement(By.cssSelector("#description > form > div:nth-child(3) > button"))
                .click();

        return this;
    }

    public String getDescriptionText() {
        return descriptionText.getText();
    }

    public Boolean isDescriptionDisplayed() {
        return descriptionText.isDisplayed();
    }

    public String getNameProject() {

        return getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(By
                        .xpath("//*[@id='job_My name']/td[3]/a"))))
                .getText();
    }

    public NewItemPage clickCreateJob() {
        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        return new NewItemPage(getDriver());
    }

    public NewItemPage clickNewItemOnLeftSidePanel() {
        getWait10().until(ExpectedConditions.elementToBeClickable(newItemButtonOnLeftSidePanel)).click();

        return new NewItemPage(getDriver());
    }

    public <T> T clickOnJobInListOfItems(String nameItem, T resultPage) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='%s']".formatted(nameItem)))).click();

        return resultPage;
    }


    public List<String> getProjectNameList() {
        if (isJobListEmpty()) {
            return List.of();
        }

        return getDriver().findElements(By.cssSelector(".jenkins-table__link > span:nth-child(1)")).stream()
                .map(WebElement::getText).toList();
    }


    public String getNameOfView() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".tab.active"))).getText();
    }

    public boolean isJobDisplayed(String jobName) {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='job_" + jobName + "']/td[3]/a/span"))).isDisplayed();
    }

    public NewItemPage clickOnNewItemLinkWithChevron(String projectName) {
        WebElement jobTableLink = getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.cssSelector(String.format("a[href='job/%s/'].jenkins-table__link", projectName)))));

        new Actions(getDriver()).moveToElement(jobTableLink).perform();
        TestUtils.moveAndClickWithJS(getDriver(), getDriver().findElement(By.cssSelector(String.format("button[data-href$='job/%s/']", projectName))));
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.cssSelector(".jenkins-dropdown a[href$='/newJob'")));

        return new NewItemPage(getDriver());
    }

    public String getProjectName() {
        return getWait5()
                .until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.cssSelector("a[href*='job'].jenkins-table__link"))))
                .getText();
    }

    public HomePage showDropdownOnHoverByJobName(String jobName) {
        By dropdownButtonLocator = By.xpath("//tr[@id='job_%s']//button".formatted(jobName));

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//tr[@id='job_%s']//a".formatted(jobName)))).perform();

        WebElement dropdownButton = getWait5().until(ExpectedConditions.elementToBeClickable(dropdownButtonLocator));
        TestUtils.moveAndClickWithJS(getDriver(), dropdownButton);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tippy-6 .jenkins-dropdown__item")));

        return this;
    }

    public List<WebElement> getDropdownOptionsList() {
        return getDriver().findElements(By.cssSelector("#tippy-6 .jenkins-dropdown__item"));
    }

    public boolean isOptionPresentedInDropdown(String optionName) {
        List<WebElement> dropdownOptionsList = getDropdownOptionsList();

        return dropdownOptionsList.stream()
                .anyMatch(webElement -> webElement.getText().contains(optionName));
    }

    public HomePage clickDeleteItemFromDropdown(String itemName) {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//button[@href='/job/%s/doDelete']".formatted(itemName))))).click();

        return this;
    }

    public HomePage clickYesOnDeletionConfirmationPopup() {
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        return new HomePage(getDriver());
    }



    public List<String> getColumnNames() {

        return getDriver().findElements(By.xpath("//th/a")).stream()
                .map(WebElement::getText).toList();
    }

    public String getLogOutButtonText() {
        return logOutButton.getText();
    }

    public boolean isSvgIconDifferentBetweenProjects(String firstProjectName, String secondProjectName) {
        return getSvgTitle(firstProjectName).equals(getSvgTitle(secondProjectName));
    }

    private String getSvgTitle(String projectName) {
        String xpath = String.format("//a[@href='job/%s/']/ancestor::tr//*[name()='svg']", projectName);

        return getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
                .getDomAttribute("title");
    }

    public void clickColumnNameInDashboardTable(String columnName) {
        getDriver().findElement(By.xpath(String.format("//th/a[text()='%s']", columnName))).click();
    }

    public boolean verifyAscendingSortingSign(String columnName) {
        String sign = getDriver().findElement(By.xpath(String.format("//th/a[text()='%s']", columnName)))
                .findElement(By.cssSelector("span.sortarrow")).getText();

        return sign.contains("↓");
    }

    public String getJobIconTitle(String jobName) {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//tr[@id='job_%s']/td[1]/div/*[name()='svg']".formatted(jobName))))
                .getDomAttribute("title");
    }

    public List<String> getListHealthReportFromDashboard() {
        if (isJobListEmpty()) {
            return List.of();
        }
        return getDriver().findElements(By.cssSelector(".healthReport")).stream()
                .map(element -> element.getDomAttribute("data")).collect(Collectors.toList());
    }

    public List<String> getListStatusLastBuildFromDashboard() {
        if (isJobListEmpty()) {
            return List.of();
        }

        List<String> list = getDriver()
                .findElements(By.xpath("//tbody//td[@class='jenkins-table__cell--tight jenkins-table__icon']"))
                .stream().map(element -> element.getDomAttribute("data")).collect(Collectors.toList());
        list.removeIf(Objects::isNull);

        return list;
    }

    public boolean isBuildQueueDisplayed() {
        return getDriver().findElement(By.id("buildQueue")).isDisplayed();
    }

    public String getBuildQueueBlockText() {
        return getDriver().findElement(By.xpath("//td[@class='pane']")).getText();
    }

    public HomePage clickScheduleBuild() {
        scheduleBuild.click();

        return this;
    }

    public boolean isTextBuildScheduled() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return buildScheduled.getText().equals("Build scheduled");
    }

    public FreestyleProjectPage clickProjectName(String projectName) {
        getDriver().findElement(By.linkText(projectName)).click();

        return new FreestyleProjectPage(getDriver());
    }

    public String getJobLastSuccess(String jobName) {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(JOB_PATTERN.formatted(jobName)))).findElement(By.xpath(".//td[4]")).getText()
                + getDriver().findElement(By.xpath(JOB_PATTERN.formatted(jobName)))
                .findElement(By.xpath(".//a")).getText();
    }

    public String getJobLastFailure(String jobName) {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(JOB_PATTERN.formatted(jobName)))).findElement(By.xpath(".//td[5]")).getText()
                + getDriver().findElement(By.xpath(JOB_PATTERN.formatted(jobName)))
                .findElement(By.xpath(".//a")).getText();
    }

    public HomePage clickJenkinsVersionButton() {
        getWait10().until(ExpectedConditions.elementToBeClickable(jenkinsVersionButton)).click();

        return this;
    }

    public List<String> getJenkinsVersionDropDownOptions() {
        return getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector(".jenkins-dropdown a")))
                .stream().map(WebElement::getText).map(String::trim).toList();
    }


}
