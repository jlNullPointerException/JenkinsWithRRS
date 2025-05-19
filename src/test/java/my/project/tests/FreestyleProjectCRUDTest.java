package my.project.tests;

import my.project.common.BaseTest;
import my.project.page.HomePage;
import my.project.page.freestyle.FreestyleConfigurationPage;
import my.project.page.freestyle.FreestylePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FreestyleProjectCRUDTest extends BaseTest {

    private static final String PROJECT_NAME = "666";
    private static final String DESCRIPTION = "test";

    @Test
    public void createFreestyleProject() {
        String actualProjectName = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectItemAndClickOk(0, new FreestyleConfigurationPage(getDriver()))
                .clickSaveButton(new FreestylePage(getDriver()))
                .getProjectName();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test (dependsOnMethods = "createFreestyleProject")
    public void updateFreestyleProjectConfig() {
        FreestylePage freestylePage = new HomePage(getDriver())
                .clickOnAction(PROJECT_NAME, HomePage.jobMenu.get(2), new FreestyleConfigurationPage(getDriver()))
                .addDescription(DESCRIPTION)
                .clickSaveButton(new FreestylePage(getDriver()));

        Assert.assertEquals(freestylePage.getDescription(), DESCRIPTION);
    }

    @Test (dependsOnMethods = "updateFreestyleProjectConfig")
    public void deleteFreestyleProject() {
        boolean projectDelete = new HomePage(getDriver())
                .clickOnAction(PROJECT_NAME, HomePage.jobMenu.get(3), new HomePage(getDriver()))
                .confirmDeleteProject()
                .getProjectNameList()
                .contains(PROJECT_NAME);

        Assert.assertFalse(projectDelete);
    }
}
