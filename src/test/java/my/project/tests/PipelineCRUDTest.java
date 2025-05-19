package my.project.tests;

import my.project.common.BaseTest;
import my.project.page.HomePage;
import my.project.page.freestyle.FreestyleConfigurationPage;
import my.project.page.pipeline.PipelineConfigurationPage;
import my.project.page.saveItem.AllCreateProjectPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PipelineCRUDTest extends BaseTest {

    private static final String PROJECT_NAME = "666";
    private static final String DESCRIPTION = "test";

    @Test
    public void createPipeline() {
        String actualProjectName = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test (dependsOnMethods = "createPipeline")
    public void updatePipelineConfig() {
        AllCreateProjectPage allCreateProjectPage = new HomePage(getDriver())
                .clickOnAction(PROJECT_NAME, HomePage.jobMenu.get(2), new PipelineConfigurationPage(getDriver()))
                .addDescription(DESCRIPTION)
                .clickSaveButton();

        Assert.assertEquals(allCreateProjectPage.getDescription(), DESCRIPTION);
    }

    @Test (dependsOnMethods = "updatePipelineConfig")
    public void deletePipeline() {
        boolean projectDelete = new HomePage(getDriver())
                .clickOnAction(PROJECT_NAME, HomePage.jobMenu.get(4), new HomePage(getDriver()))
                .confirmDeleteProject()
                .getProjectNameList()
                .contains(PROJECT_NAME);

        Assert.assertFalse(projectDelete);
    }
}
