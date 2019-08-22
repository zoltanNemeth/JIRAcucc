
package com.codecool;


import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.Issue;
import pages.Login;
import util.DbReader;
import util.Driver;
import waiter.Waiter;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class IssueTest {
    private static WebDriver driver;
    private static Driver driverUtil;
    private static WebDriverWait driverWait;
    private static DbReader db;
    private static Login login;
    private static Issue issues;
    private Waiter waiter = new Waiter();

    @BeforeAll
    public static void setup() {
        driverUtil = new Driver();
        driver = driverUtil.getWebDriver();
        login = new Login(driver);
        db = new DbReader();
    }

    @AfterAll
    public static void close() {
        driverWait = new WebDriverWait(driver, 10);
        driver.close();
    }

    public static void doLogin() {
        String userName = DbReader.getAll("credentials").get(0).get("username").toString();
        String password = DbReader.getAll("credentials").get(0).get("password").toString();
        login.login(userName, password);
    }

    @BeforeEach
    public void goToPage() {
        login.goToPage();
    }

    @ParameterizedTest
    @MethodSource("util.DbReader#getCredentials")
    public void login(Map data) {
        String username = data.get("username").toString();
        String password = data.get("password").toString();
        login.login(username, password);
    }

    @Test
    public void BrowseExistingIssues() {
        doLogin();
        issues = new Issue("browse/TOUCAN-57?jql=project%20%3D%20TOUCAN", driver);
        issues.goToPage();
        String actualProjectName = issues.getNameFieldTExt();
        String expectedProjectName = DbReader.getAll("issue").get(0).get("issue_name").toString();
        assertEquals(expectedProjectName, actualProjectName);
    }

    @Test
    public void BrowseIssuesWithAdvancedSearch() {
        doLogin();
        issues = new Issue("", driver);
        issues.goToPage();
        issues.BrowseIssuesWithAdvancedSearch();
    }


}
