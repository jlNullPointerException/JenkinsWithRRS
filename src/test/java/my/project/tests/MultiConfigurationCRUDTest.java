package my.project.tests;

import my.project.common.BaseTest;
import my.project.page.HomePage;
import my.project.page.multiconfiguration.MultiConfigurationConfigurationPage;
import my.project.page.multiconfiguration.MultiConfigurationPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MultiConfigurationCRUDTest extends BaseTest {

    private static final String PROJECT_NAME = "666";
    private static final String DESCRIPTION = "test";

    @Test
    public void createMultiConfiguration() {
        String actualProjectName = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectItemAndClickOk(2, new MultiConfigurationConfigurationPage(getDriver()))
                .clickSaveButton(new MultiConfigurationPage(getDriver()))
                .getProjectName();

        Assert.assertEquals(actualProjectName, "Project " + PROJECT_NAME);
    }

    @Test (dependsOnMethods = "createMultiConfiguration")
    public void updateMultiConfigurationConfig() {
        MultiConfigurationPage multiConfigurationPage = new HomePage(getDriver())
                .clickOnAction(PROJECT_NAME, HomePage.jobMenu.get(2), new MultiConfigurationConfigurationPage(getDriver()))
                .addDescription(DESCRIPTION)
                .clickSaveButton(new MultiConfigurationPage(getDriver()));

        Assert.assertEquals(multiConfigurationPage.getDescription(), DESCRIPTION);
    }

    @Test (dependsOnMethods = "updateMultiConfigurationConfig")
    public void deleteMultiConfiguration() {
        boolean projectDelete = new HomePage(getDriver())
                .clickOnAction(PROJECT_NAME, HomePage.jobMenu.get(8), new HomePage(getDriver()))
                .confirmDeleteProject()
                .getProjectNameList()
                .contains(PROJECT_NAME);

        Assert.assertFalse(projectDelete);
    }
}
