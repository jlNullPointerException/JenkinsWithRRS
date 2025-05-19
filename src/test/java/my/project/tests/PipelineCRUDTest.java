package my.project.tests;

import my.project.common.BaseTest;
import my.project.page.HomePage;
import my.project.page.pipeline.PipelineConfigurationPage;
import my.project.page.pipeline.PipelinePage;
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
                .selectItemAndClickOk(1, new PipelineConfigurationPage(getDriver()))
                .clickSaveButton(new PipelinePage(getDriver()))
                .getProjectName();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test (dependsOnMethods = "createPipeline")
    public void updatePipelineConfig() {
        PipelinePage pipelinePage = new HomePage(getDriver())
                .clickOnAction(PROJECT_NAME, HomePage.jobMenu.get(2), new PipelineConfigurationPage(getDriver()))
                .addDescription(DESCRIPTION)
                .clickSaveButton(new PipelinePage(getDriver()));

        Assert.assertEquals(pipelinePage.getDescription(), DESCRIPTION);
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
