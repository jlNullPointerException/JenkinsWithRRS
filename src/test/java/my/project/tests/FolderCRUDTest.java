package my.project.tests;

import my.project.common.BaseTest;
import my.project.page.HomePage;
import my.project.page.folder.FolderConfigurationPage;
import my.project.page.folder.FolderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FolderCRUDTest extends BaseTest {

    private static final String PROJECT_NAME = "666";
    private static final String DESCRIPTION = "test";

    @Test
    public void createFolder() {
        String actualFolderName = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectItemAndClickOk(3, new FolderConfigurationPage(getDriver()))
                .clickSaveButton(new FolderPage(getDriver()))
                .getFolderName();

        Assert.assertEquals(actualFolderName, PROJECT_NAME);
    }

    @Test (dependsOnMethods = "createFolder")
    public void updateFolderConfig() {
        FolderPage folderPage = new HomePage(getDriver())
                .clickOnAction(PROJECT_NAME, HomePage.jobMenu.get(2), new FolderConfigurationPage(getDriver()))
                .addDescription(DESCRIPTION)
                .clickSaveButton(new FolderPage(getDriver()));

        Assert.assertEquals(folderPage.getFolderDescription(), DESCRIPTION);
    }

    @Test (dependsOnMethods = "updateFolderConfig")
    public void deleteFolder() {
        boolean projectDelete = new HomePage(getDriver())
                .clickOnAction(PROJECT_NAME, HomePage.jobMenu.get(10), new HomePage(getDriver()))
                .confirmDeleteProject()
                .getProjectNameList()
                .contains(PROJECT_NAME);

        Assert.assertFalse(projectDelete);
    }
}
