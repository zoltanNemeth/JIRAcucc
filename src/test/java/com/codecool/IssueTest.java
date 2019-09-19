
package com.codecool;


import org.junit.jupiter.api.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.Issue;
import pages.Login;
import pages.Search;
import util.DbReader;
import util.Driver;
import waiter.Waiter;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class IssueTest {
    private static WebDriver driver;
    private static Driver driverUtil;
    private static WebDriverWait driverWait;
    private static DbReader db;
    private static Login login;
    private static Issue issues;
    private static Search search;
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
        String dataKey = DbReader
                .getAll("issue").get(1).get("issue_name")
                .toString();
        String exPectedprojectName = DbReader
                .getAll("issue").get(2).get("issue_name")
                .toString();
        doLogin();
        issues = new Issue("", driver);
        issues.goToPage();
        issues.searchForIssues();
        search = new Search("browse/WEAKS-8?jql=", driver);
        search.gotToAdvancedsearch();
        search.sendKeysToSearchBar(dataKey);
        search.clickSearchButton();
        String actualProjectName = search.getPageHeaderText();
        assertEquals(exPectedprojectName, actualProjectName);
        search.gotoBasicsearch();


    }

    @Test
    public void BrowseExistingIssuesWithSea() {
        doLogin();
        issues= new Issue("secure/Dashboard.jspa",driver);
        issues.goToPage();
        issues.searchForIssues();
        issues.clickShareTrigger();
        String actualDialogtext = issues.getSharePanelIdalogValue();
        String expectedDialogtext = DbReader.getAll("issue")
                .get(3).get("issue_name")
                .toString();
        assertEquals(expectedDialogtext,actualDialogtext);



    }
}