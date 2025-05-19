package my.project.tests;

import my.project.common.BaseTest;
import my.project.page.HomePage;
import my.project.page.error.ErrorPage;
import my.project.page.freestyle.FreestyleConfigurationPage;
import my.project.page.freestyle.FreestylePage;
import my.project.page.newitem.NewItemPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CopyFromFreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "666";
    private static final String DESCRIPTION = "test";
    private static final String TIME_PERIOD = "Minute";

    @Test
    public void freestyleProjectWithConfig() {
        FreestylePage freestylePage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectItemAndClickOk(0, new FreestyleConfigurationPage(getDriver()))
                .addDescription(DESCRIPTION)
                .clickThrottleBuilds()
                .selectTimePeriod(TIME_PERIOD)
                .clickSaveButton(new FreestylePage(getDriver()));

        Assert.assertEquals(freestylePage.getProjectName(), PROJECT_NAME);
        Assert.assertEquals(freestylePage.getDescription(), DESCRIPTION);
    }

    @Test (dependsOnMethods = "freestyleProjectWithConfig")
    public void successfulCopyWithConfig () {
        final String projectNameForCopy = "777";

        FreestyleConfigurationPage freestyleConfigurationPage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(projectNameForCopy)
                .selectItemTypeByName(NewItemPage.itemType.get(0))
                .sendTextCopyForm(PROJECT_NAME)
                .clickOkButton();

        Assert.assertEquals(freestyleConfigurationPage.getDescriptionText(), DESCRIPTION);
        Assert.assertTrue(freestyleConfigurationPage.getSelectThrottleBuilds());
        Assert.assertEquals(freestyleConfigurationPage.getTimePeriod(), TIME_PERIOD);
    }

    @Test(dependsOnMethods = "freestyleProjectWithConfig")
    public void unsuccessfulCopy() {
        final String projectNameForCopy = "888";
        final String nonexistentName = "999";

        ErrorPage errorPage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(projectNameForCopy)
                .selectItemTypeByName(NewItemPage.itemType.get(0))
                .sendTextCopyForm(nonexistentName)
                .clickOkButtonWithError();

        Assert.assertEquals(errorPage.getTitle(),"Error");
        Assert.assertEquals(errorPage.getErrorText(), "No such job: %s".formatted(nonexistentName));
    }
}
