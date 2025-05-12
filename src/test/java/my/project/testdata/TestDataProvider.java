package my.project.testdata;

import org.testng.annotations.DataProvider;

import java.util.Arrays;

public class TestDataProvider {

    private static final String MAX_PROJECT_NAME = "a".repeat(255);

    @DataProvider
    public Object[][] safeCharacters() {
        return new Object[][] {{"Q"}, {"q"}, {"1"}, {"`"}, {"~"}, {"("}, {")"}, {"_"},
                {"-"}, {"+"}, {"="}, {"{"}, {"}"},  {"'"}, {"\""}, {"a.a"}, {","}};
    }

    @DataProvider(name = "provideInvalidCharacters")
    public static Object[][] provideInvalidCharacters() {
        return new Object[][]{
                {"!"}, {"?"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"},{"*"},
                {"["}, {"]"}, {"|"}, {";"}, {":"}, {"<"}, {">"}, {"/"}};
    }

    @DataProvider(name = "projectNames")
    public Object[][] projectNames() {
        return new Object[][]{
                {"A"},
                {"New1.Project2(name)with_all-valid+chars=test,test'a"},
                {MAX_PROJECT_NAME}
        };
    }

    @DataProvider(name = "itemTypes")
    public Object[][] itemTypes() {
        return new Object[][]{
                {"Freestyle project"},
                {"Pipeline"},
                {"Multi-configuration project"},
                {"Folder"},
                {"Multibranch Pipeline"},
                {"Organization Folder"}
        };
    }

    @DataProvider(name = "itemDescriptions")
    public Object[][] itemDescriptions() {
        return new Object[][]{
                {Arrays.asList(
                        "Classic, general-purpose job type that checks out from up to one SCM, executes build steps serially, followed by post-build steps like archiving artifacts and sending email notifications.",
                        "Orchestrates long-running activities that can span multiple build agents. Suitable for building pipelines (formerly known as workflows) and/or organizing complex activities that do not easily fit in free-style job type.",
                        "Suitable for projects that need a large number of different configurations, such as testing on multiple environments, platform-specific builds, etc.",
                        "Creates a container that stores nested items in it. Useful for grouping things together. Unlike view, which is just a filter, a folder creates a separate namespace, so you can have multiple things of the same name as long as they are in different folders.",
                        "Creates a set of Pipeline projects according to detected branches in one SCM repository.",
                        "Creates a set of multibranch project subfolders by scanning for repositories."
                )}
        };
    }
}
