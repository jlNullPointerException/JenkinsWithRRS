package my.project.tests;

import my.project.common.BaseTest;
import my.project.page.HomePage;
import my.project.page.freestyle.FreestyleConfigurationPage;
import my.project.page.freestyle.FreestylePage;
import my.project.page.newitem.NewItemPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IncorrectNameForNewItemTest extends BaseTest {

    private static final String RED = "rgba(230, 0, 31, 1)";

    @Test
    public void emptyFieldError() {
        final String errorText = "» This field cannot be empty, please enter a valid name";

        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .selectItemTypeByName(NewItemPage.itemType.get(0));

        Assert.assertEquals(newItemPage.getEmptyErrorText(), errorText);
        Assert.assertEquals(newItemPage.getEmptyErrorColor(), RED);
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void dotError() {
        final String errorText = "» “.” is not an allowed name";
        final String name = ".";

        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(name);

        Assert.assertEquals(newItemPage.getErrorText(), errorText);
        Assert.assertEquals(newItemPage.getErrorColor(), RED);
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void specialCharactersError() {
        final String name = "676&&@#3224";

        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(name);

        Assert.assertEquals(newItemPage.getErrorText(),
                "» ‘" + newItemPage.findUnacceptableChar(name)  + "’ is an unsafe character");
        Assert.assertEquals(newItemPage.getErrorColor(), RED);
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void dotEndError() {
        final String errorText = "» A name cannot end with ‘.’";
        final String name = "gfrth546546.";

        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(name);

        Assert.assertEquals(newItemPage.getErrorText(), errorText);
        Assert.assertEquals(newItemPage.getErrorColor(), RED);
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void createProject() {
        final String name = "gfrth666вапвп(9)";

        String actualProjectName = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(name)
                .selectItemAndClickOk(0, new FreestyleConfigurationPage(getDriver()))
                .clickSaveButton(new FreestylePage(getDriver()))
                .getProjectName();

        Assert.assertEquals(actualProjectName, name);
    }

        @Test(dependsOnMethods = "createProject")
        public void doubleError() {
            final String name = "gfrth666вапвп(9)";

            NewItemPage newItemPage = new HomePage(getDriver())
                    .clickNewItemOnLeftSidePanel()
                    .sendItemName(name);

            Assert.assertEquals(newItemPage.getErrorText(), "» A job already exists with the name ‘%s’".formatted(name));
            Assert.assertEquals(newItemPage.getErrorColor(), RED);
            Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }
}
