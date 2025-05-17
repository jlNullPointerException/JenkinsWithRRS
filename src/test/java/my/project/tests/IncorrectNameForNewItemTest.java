package my.project.tests;

import my.project.common.BaseTest;
import my.project.common.TestUtils;
import my.project.page.HomePage;
import my.project.page.newitem.NewItemPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        WebDriver driver = getDriver();

        final String input = "676&&@#3224";
        final String unacceptableSpecChar = "!@;:[]<>$%&?*|\\/";
        char ch = '\u0000';

        for (int i = 0; i < input.length(); i++) {
            if (unacceptableSpecChar.indexOf(input.charAt(i)) != -1) {
                ch = input.charAt(i);
                break;
            }
        }

        final String errorText = "» ‘" + ch  + "’ is an unsafe character";

        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        driver.findElement(By.id("name")).sendKeys(input);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));

        Assert.assertEquals(
                driver.findElement(By.id("itemname-invalid")).getText(), errorText);
        Assert.assertEquals(
                driver.findElement(By.cssSelector("#itemname-invalid")).getCssValue("color"), RED);
        Assert.assertFalse(
                driver.findElement(By.id("ok-button")).isEnabled());
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
    public void doubleError() {
        WebDriver driver = getDriver();
        final String input = "gfrth666вапвп(9)";
        final String errorText = "» A job already exists with the name ‘" + input +"’";

        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        driver.findElement(By.id("name")).sendKeys(input);
        driver.findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("Submit")).click();
        getWait10();
        TestUtils.gotoHomePage(this);
        getWait5();

        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        driver.findElement(By.id("name")).sendKeys(input);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));

        Assert.assertEquals(
                driver.findElement(By.id("itemname-invalid")).getText(), errorText);
        Assert.assertEquals(
                driver.findElement(By.cssSelector("#itemname-invalid")).getCssValue("color"), RED);
    }
}
