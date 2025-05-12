package my.project.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import my.project.common.order.OrderForTests;
import my.project.common.order.OrderUtils;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

@Listeners({FilterForTests.class, OrderForTests.class})
public abstract class BaseTest {

    private WebDriver driver;

    private WebDriverWait wait5;
    private WebDriverWait wait10;

    private OrderUtils.MethodsOrder<Method> methodsOrder;

    private void startDriver() {
        ProjectUtils.log("Browser open");
        driver = ProjectUtils.createDriver();
    }

    private void clearData() {
        ProjectUtils.log("Clear data");
        JenkinsUtils.clearData();
    }

    private void loginWeb() {
        ProjectUtils.log("Login");
        JenkinsUtils.login(getDriver());
    }

    private void getWeb() {
        ProjectUtils.log("Get web page");
        ProjectUtils.get(getDriver());
    }

    private void stopDriver() {
        try {
            JenkinsUtils.logout(getDriver());
        } catch (Exception ignore) {
        }

        closeDriver();
    }

    private void closeDriver() {
        if (driver != null) {
            driver.quit();

            driver = null;
            wait5 = null;
            wait10 = null;

            ProjectUtils.log("Browser closed");
        }
    }

    @BeforeClass
    protected void beforeClass() {
        methodsOrder = OrderUtils.createMethodsOrder(
                Arrays.stream(this.getClass().getMethods())
                        .filter(m -> m.getAnnotation(Test.class) != null && m.getAnnotation(Ignore.class) == null)
                        .collect(Collectors.toList()),
                m -> m.getName(),
                m -> m.getAnnotation(Test.class).dependsOnMethods());
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ProjectUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            if (!methodsOrder.isGroupStarted(method) || methodsOrder.isGroupFinished(method)) {
                clearData();
                startDriver();
                getWeb();
                loginWeb();
            } else {
                getWeb();
            }
        } catch (Exception e) {
            closeDriver();
            throw new RuntimeException(e);
        } finally {
            methodsOrder.markAsInvoked(method);
        }
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (!testResult.isSuccess() && ProjectUtils.isRunCI()) {
            ProjectUtils.takeScreenshot(getDriver(), testResult.getTestClass().getRealClass().getSimpleName(), testResult.getName());
        }

        if (methodsOrder.isGroupFinished(method) && (ProjectUtils.isRunCI() || testResult.isSuccess() || ProjectUtils.closeIfError())) {
            stopDriver();
        }

        ProjectUtils.logf("Execution time is %.3f sec", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWait5() {
        if (wait5 == null) {
            wait5 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        }

        return wait5;
    }

    protected WebDriverWait getWait10() {
        if (wait10 == null) {
            wait10 = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        }

        return wait10;
    }
}
