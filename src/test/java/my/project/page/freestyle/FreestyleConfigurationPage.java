package my.project.page.freestyle;

import org.checkerframework.common.value.qual.IntRange;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import my.project.common.BasePage;

import java.util.List;

public class FreestyleConfigurationPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement buttonSave;

    @FindBy(css = "button[suffix='builder']")
    private WebElement buttonAddBuildStep;

    @FindBy(id = "//*[@id='tasks']/div[4]/span/button")
    private WebElement buttonEnvironment;

    @FindBy(css = "button[suffix='publisher']")
    private WebElement buttonAddPostBuildAction;

    @FindBy(css = ".jenkins-button.apply-button")
    private WebElement buttonApply;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement fieldDescription;

    @FindBy(xpath = "//*[@id='post-build-actions']")
    private WebElement headerPostBuildActions;

    @FindBy(xpath = "//div[@class='setting-main']/textarea[@name='_.spec']")
    private WebElement buildPeriodicallyScheduleInput;

    @FindBy(xpath = "//div[@class='setting-main']/textarea[@name='_.scmpoll_spec']")
    private WebElement pollSCMScheduleInput;

    @FindBy(xpath = "//label[contains(text(), 'Trigger builds remotely')]")
    private WebElement triggerBuildsRemotelyLabel;

    @FindBy(xpath = "//label[contains(text(), 'Build after other projects are built')]")
    private WebElement buildAfterProjectsAreBuiltLabel;

    @FindBy(xpath = "//label[contains(text(), 'Build periodically')]")
    private WebElement buildPeriodicallyLabel;

    @FindBy(xpath = "//label[contains(text(), 'GitHub hook trigger for GITScm polling')]")
    private WebElement gitHubHookTriggerForGITScmPollingLabel;

    @FindBy(xpath = "//label[contains(text(), 'Poll SCM')]")
    private WebElement pollSCMLabel;

    @FindBy(xpath = "//input[@name='_.upstreamProjects']")
    private WebElement projectsToWatchInput;

    @FindBy(xpath = "//div/input[@name='_.projectUrlStr']")
    private WebElement gitHubProjectURL;

    private void clickItemNumber(WebElement webElement, int itemNumber) {
        webElement.click();
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='jenkins-dropdown__item '][%d]".formatted(itemNumber)))).click();
    }

    public FreestyleConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleConfigurationPage addDescription(String text) {
        fieldDescription.sendKeys(text);

        return this;
    }

    public FreestyleProjectPage clickSaveButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return new FreestyleProjectPage(getDriver());
    }

    public FreestyleConfigurationPage clickDiscardOldBuilds(int buildLogLimit) {
        getDriver().findElement(By.xpath("//label[contains(text(),'Discard old builds')]")).click();
        getDriver().findElement(By.name("_.numToKeepStr")).sendKeys(String.valueOf(buildLogLimit));

        return this;
    }

    public FreestyleConfigurationPage scrollToSourceCodeManagementItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='source-code-management']")));

        return this;
    }

    public FreestyleConfigurationPage scrollToBuildTriggers() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.className("jenkins-checkbox-help-wrapper")));

        return this;
    }

    public FreestyleConfigurationPage scrollToBuildEnvironmentItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='environment']")));

        return this;
    }

    public FreestyleConfigurationPage scrollToBuildStepsItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='build-steps']")));

        return this;
    }

    public FreestyleConfigurationPage clickPostBuildActionsItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='post-build-actions']")));


        return this;
    }

    public FreestyleConfigurationPage checkTriggerBuildsRemotelyCheckbox() {
        triggerBuildsRemotelyLabel.click();

        return this;
    }

    public FreestyleConfigurationPage checkBuildAfterProjectsCheckbox() {
        buildAfterProjectsAreBuiltLabel.click();

        return this;
    }

    public FreestyleConfigurationPage setBuildPeriodicallyCheckbox() {
        buildPeriodicallyLabel.click();

        return this;
    }

    public FreestyleConfigurationPage checkGithubHookCheckbox() {
        gitHubHookTriggerForGITScmPollingLabel.click();

        return this;
    }

    public FreestyleConfigurationPage checkPollSCMCheckbox() {
        pollSCMLabel.click();

        return this;
    }

    public FreestyleConfigurationPage checkIgnorePostCommitHooksCheckbox() {
        getWait10().until(ExpectedConditions.elementToBeClickable(By
                .xpath("//label[contains(text(),'Ignore post-commit hooks')]"))).click();

        return this;
    }

    public boolean isPollSCMCheckboxSelected() {
        return getDriver().findElement(By.name("_.ignorePostCommitHooks")).isSelected();
    }

    public FreestyleConfigurationPage sendScheduleText(String text) {
        buildPeriodicallyScheduleInput.clear();
        buildPeriodicallyScheduleInput.sendKeys(text);

        return this;
    }

    public FreestyleConfigurationPage sendScheduleTextForPollSCM(String text) {
        pollSCMScheduleInput.clear();
        pollSCMScheduleInput.sendKeys(text);

        return this;
    }

    public String sendScheduleTextForThrottleBuilds() {
        return pollSCMScheduleInput.getText();
    }

    public String sendScheduleActualText() {
        return buildPeriodicallyScheduleInput.getText();
    }

    public FreestyleConfigurationPage clickEnableDisableToggle() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='enable-disable-project']"))).click();

        return this;
    }

    public String getToggleStatus() {
        List<WebElement> statusList = getDriver().findElements(By.cssSelector("span[class^='jenkins-toggle-switch__label']"));
        for (WebElement e : statusList) {
            if (e.isDisplayed()) {
                return e.getText();
            }
        }
        return "";
    }

    public String getProjectStatus() {
        return getDriver().findElement(By.className("jenkins-toggle-switch__label__checked-title")).getText();
    }

    public FreestyleConfigurationPage waitUntilTextConfigureToBePresentInH1() {
        getWait10().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "Configure"));

        return this;
    }

    public String getSectionNameTriggers() {
        return getDriver().findElement(By.id("triggers")).getText();
    }

    public String getSubtitleTextTriggers() {
        return getDriver()
                .findElement(By.xpath("//div[contains(text(), 'Set up')]"))
                .getText();
    }

    public boolean isTriggerBuildsRemotelyCheckboxDisplayed() {
        return getDriver().findElement(By.id("cb13")).isDisplayed();
    }

    public boolean isTriggerBuildsRemotelyCheckboxEnabled() {
        return getDriver().findElement(By.id("cb13")).isEnabled();
    }

    public String getTriggerBuildsRemotelyLabelText() {
        return triggerBuildsRemotelyLabel.getText();
    }

    public int countHelperIconsTriggersSection() {
        return getDriver().findElements(By.xpath("//div[@class='jenkins-section__title' and @id='triggers']/following-sibling::div//a[@class='jenkins-help-button']")).size();
    }

    public String getTriggerBuildsRemotelyHelpIconTitle() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[3]/div[3]/div[1]/div/a"))
                .getDomAttribute("title");
    }

    public boolean isBuildAfterProjectsCheckboxDisplayed() {
        return getDriver().findElement(By.id("cb14")).isDisplayed();
    }

    public boolean isBuildAfterProjectsCheckboxEnabled() {
        return getDriver().findElement(By.id("cb14")).isEnabled();
    }

    public String getBuildAfterProjectsLabelText() {
        return buildAfterProjectsAreBuiltLabel.getText();
    }

    public String getBuildAfterProjectsHelpIconTitle() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[3]/div[4]/div[1]/div/a"))
                .getDomAttribute("title");
    }

    public boolean isBuildPeriodicallyCheckboxDisplayed() {
        return getDriver().findElement(By.id("cb15")).isDisplayed();
    }

    public boolean isBuildPeriodicallyCheckboxEnabled() {
        return getDriver().findElement(By.id("cb15")).isEnabled();
    }

    public String getBuildPeriodicallyLabelText() {
        return gitHubHookTriggerForGITScmPollingLabel.getText();
    }

    public String getBuildPeriodicallyHelpIconTitle() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[3]/div[5]/div[1]/div/a"))
                .getDomAttribute("title");
    }

    public boolean isGithubHookTriggerCheckboxDisplayed() {
        return getDriver().findElement(By.id("cb16")).isDisplayed();
    }

    public boolean isPollSCMCheckboxDisplayed() {
        return getDriver().findElement(By.id("cb17")).isDisplayed();
    }

    public boolean isGithubHookTriggerCheckboxEnabled() {
        return getDriver().findElement(By.id("cb16")).isEnabled();
    }

    public boolean isPollSCMCheckboxEnabled() {
        return getDriver().findElement(By.id("cb17")).isEnabled();
    }

    public String getGithubHookTriggerLabelText() {
        return gitHubHookTriggerForGITScmPollingLabel.getText();
    }

    public String getGithubHookTriggerHelpIconTitle() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[3]/div[6]/div[1]/div/a"))
                .getDomAttribute("title");
    }

    public FreestyleConfigurationPage addBuildSteps(@IntRange(from = 1, to = 7) int itemNumber) {
        for (int i = 0; i < 5; i++) {
            try {
                new Actions(getDriver())
                        .scrollToElement(getWait5().until(ExpectedConditions.visibilityOf(headerPostBuildActions)))
                        .perform();
                clickItemNumber(buttonAddBuildStep, itemNumber);

                return this;
            } catch (Exception e) {
                try {
                    buttonEnvironment.click();
                    clickItemNumber(buttonAddBuildStep, itemNumber);

                    return this;
                } catch (Exception y) {
                    System.err.println("Ошибка, пробуем снова... " + y.getMessage());
                }
            }
        }

        throw new RuntimeException("Failed to add build step");
    }

    public List<String> getChunkHeaderList() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Ошибка: sleep прерван", e);
        }

        return getDriver().findElements(By.cssSelector(".repeated-chunk__header")).stream()
                .map(WebElement::getText)
                .map(text -> text.replace("?", ""))
                .filter(text -> !text.trim().isEmpty())
                .toList();
    }

    public FreestyleConfigurationPage setProjectToWatch(String projectName) {
        projectsToWatchInput.clear();
        projectsToWatchInput.sendKeys(projectName);
        getWait5().until(ExpectedConditions.attributeToBe(projectsToWatchInput, "aria-expanded", "true"));
        projectsToWatchInput.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

        return this;
    }

    public FreestyleConfigurationPage setWrongProjectToWatch(String projectName) {
        getDriver().findElement(By.xpath("//input[@name='_.upstreamProjects']")).clear();
        getDriver().findElement(By.xpath("//input[@name='_.upstreamProjects']")).sendKeys(projectName);

        return this;
    }

    public FreestyleConfigurationPage clickAllReverseBuildTriggerLabels() {
        getDriver().findElements(By.xpath("//input[@name='ReverseBuildTrigger.threshold']/following-sibling::label"))
                .forEach(WebElement::click);
        return this;
    }

    public FreestyleProjectPage clickFreestyleLink(String link) {
        getDriver().findElement(By.xpath(String.format("//a[text()='%s']", link))).click();

        return new FreestyleProjectPage(getDriver());
    }

    public FreestyleConfigurationPage enterAuthToken(String token) {
        getDriver().findElement(By.xpath("//input[@name='authToken']")).sendKeys(token);

        return this;
    }

    public String getAuthenticationTokenLabelText() {
        return getDriver().findElement(By.xpath("//div[text()='Authentication Token']")).getText();
    }

    public String getAuthTokenDomValue() {
        return getDriver().findElement(By.xpath("//input[@name='authToken']")).getDomAttribute("value");
    }

    public String getTriggerInfoText() {
        return getDriver()
                .findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[3]/div[3]/div[4]/div/div[2]"))
                .getText()
                .trim();
    }

    public String getCurrentProjectName() {
        return getDriver().findElement(By.xpath("//input[@name='_.upstreamProjects']"))
                .getDomAttribute("value");
    }

    public boolean isLastRadioButtonSelected() {
        List<WebElement> radios = getDriver().findElements(By.name("ReverseBuildTrigger.threshold"));
        return radios.get(radios.size() - 1).getDomAttribute("checked").equals("true");
    }

    public FreestyleConfigurationPage addPostBuildActions(@IntRange(from = 1, to = 11) int itemNumber) {
        final String locator = ".jenkins-dropdown__disabled, button.jenkins-dropdown__item";
        List<WebElement> elements = List.of();

        for (int i = 0; i < 5; i++) {
            try {
                new Actions(getDriver()).scrollToElement(buttonAddPostBuildAction).sendKeys(Keys.END).perform();
                getWait10().until(ExpectedConditions.elementToBeClickable(buttonAddPostBuildAction)).click();
                elements = getDriver().findElements(By.cssSelector(locator));

                if (elements.size() == 11) {
                    elements.get(--itemNumber).click();

                    return this;
                }
            } catch (Exception e) {
                System.err.println("Ошибка, пробуем снова..." + e.getMessage());
            }
        }

        throw new RuntimeException("Failed to add post build actions");
    }

    public FreestyleConfigurationPage clickToReverseBuildTriggerAndSetUpStreamProject(String jobName) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='triggers']/parent::section")));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[name = 'jenkins-triggers-ReverseBuildTrigger']"))));


        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[name = 'jenkins-triggers-ReverseBuildTrigger']"))));

        getDriver().findElement(By.name("_.upstreamProjects")).sendKeys(jobName);

        return this;
    }

    public FreestyleConfigurationPage clickBuildNow() {
        getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']")).click();

        return this;
    }

    public FreestyleConfigurationPage waitJobStarted() {
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.id("no-builds")));
        getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(" #jenkins-build-history a[title='Success']")));

        return this;
    }

    public List<String> getBuildList() {
        return getDriver().findElements(By.cssSelector("div[page-entry-id]")).stream()
                .map(WebElement::getText).toList();
    }

    public String getBuildStatusText() {
        return getDriver().findElement(By.id("jenkins-build-history")).getText();
    }

    public FreestyleConfigurationPage checkGitHubProjectCheckbox() {
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("main-panel")));
        getDriver().findElement(By.xpath("//label[contains(text(),'GitHub project')]")).click();
        
        return this;
    }

    public FreestyleConfigurationPage sentGitHubProjectURL(String projectURL) {
        gitHubProjectURL.sendKeys(projectURL);

        return this;
    }

    public FreestyleConfigurationPage clickThrottleBuilds() {
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/div[12]/div[1]/div/span/label")).click();

        return this;
    }

    public FreestyleConfigurationPage selectTimePeriod(String period) {
        WebElement timePeriod = getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.name("_.durationName"))));
        timePeriod.click();
        new Select(timePeriod).selectByVisibleText(period);

        return this;
    }

    public String getDescriptionText() {
        return getWait10().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.name("description")))).getText();
    }

    public boolean getSelectThrottleBuilds() {
        return getDriver().findElement(By.name("_.throttle")).isSelected();
    }

    public String getTimePeriod() {
        return new Select(getDriver().findElement(By.name("_.durationName"))).getFirstSelectedOption().getText();
    }

    public FreestyleConfigurationPage selectNoneSCM() {
        new Actions(getDriver()).moveToElement(getDriver().findElement(
                By.xpath("//label[text()='None']"))).perform();

        return this;
    }

    public FreestyleConfigurationPage scrollToIgnorePostCommitHooksCheckbox() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});",
                getDriver().findElement(By.cssSelector("div.jenkins-section__title#environment")));

        return this;
    }

    public boolean isGithubHookCheckboxSelected() {
        return getDriver().findElement(By.name("com-cloudbees-jenkins-GitHubPushTrigger")).isSelected();
    }

    public FreestyleConfigurationPage clickApply() {
        new Actions(getDriver()).sendKeys(Keys.END).perform();
        buttonApply.click();

        return this;
    }

    public boolean isNoSuchProjectErrorVisible() {
        return getWait10().until(driver -> driver.findElement(
                        By.xpath("//div[contains(@class, 'form-container')]//div[contains(@class, 'validation-error-area--visible')]//div[contains(@class, 'error') and contains(text(), 'No such project')]"))
                .getText()
                .startsWith("No such project"));
    }

    public FreestyleConfigurationPage clickOnDropdownToClose() {
        getWait10().until(driver -> driver.findElement(By.cssSelector(".jenkins-dropdown"))).click();

        return this;
    }

    public boolean isScheduleSpecErrorVisible() {
        return getDriver().findElement(By.xpath(
                "//div[contains(@class, 'validation-error-area') and contains(., 'Invalid input')]"
        )).isDisplayed();
    }

    public FreestyleConfigurationPage hoverHelpIcon(String featureName) {
        new Actions(getDriver())
                .moveToElement(getDriver()
                        .findElement(By.cssSelector("a[tooltip='Help for feature: " + featureName + "']")))
                .perform();

        return this;
    }

    public boolean isTooltipVisibleWithText(String expectedText) {
        return getWait10()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-tippy-root] .tippy-content")))
                .getText()
                .equals(expectedText);
    }

    public int numberHelpTooltips() {
        int numberTrueTooltipVisibleWithText = 0;

        List<WebElement> visibleButtonsHelp = getDriver().findElements(By.cssSelector(".jenkins-help-button"))
                .stream()
                .filter(WebElement::isDisplayed)
                .toList();

        Actions actions = new Actions(getDriver());

        for (int i = 0; i < visibleButtonsHelp.size(); i++) {
            actions.moveToElement(visibleButtonsHelp.get(i)).perform();

            boolean isVisibleText = getWait10()
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-tippy-root] .tippy-content")))
                    .getText()
                    .contains("Help");

            if(isVisibleText) numberTrueTooltipVisibleWithText++;

            try {
                actions.scrollToElement(visibleButtonsHelp.get(i + 3)).perform();
            }catch (Exception e){
                new Actions(getDriver()).sendKeys(Keys.END).perform();;
            }
        }

        return numberTrueTooltipVisibleWithText;
    }
}