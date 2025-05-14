package my.project.tests;

import my.project.common.BaseTest;
import my.project.page.HomePage;
import my.project.page.freestyle.FreestyleProjectPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FreestyleProjectCRUDTest extends BaseTest {

    private static final String PROJECT_NAME = "666";
    private static final String DESCRIPTION = "test";

    @Test
    public void CreateFreestyleProject() {
        String actualProjectName = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test (dependsOnMethods = "CreateFreestyleProject")
    public void updateFreestyleProject() {
        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickOnJobInListOfItems(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickDescriptionButton()
                .sendDescription(DESCRIPTION)
                .clickSaveButton();

        Assert.assertEquals(freestyleProjectPage.getProjectName(), PROJECT_NAME);
        Assert.assertEquals(freestyleProjectPage.getDescription(), DESCRIPTION);

    }


}
