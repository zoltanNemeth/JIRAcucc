
package com.codecool;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Login;
import pages.ProjectIssues;
import util.DbReader;
import util.Driver;
import waiter.Waiter;

import java.util.Map;


public class ProjectIssuesTest {
    private static WebDriver driver;
    private static Driver driverUtil;
    private static WebDriverWait driverWait;
    private static DbReader db;

    private static Login login;
    private static ProjectIssues projectIssues;
    private static Waiter waiter = new Waiter();

    @BeforeAll
    public static void setup(){
        driverUtil = new Driver();
        driver = driverUtil.getWebDriver();
        login = new Login(driver);
        projectIssues = new ProjectIssues(driver);
        db = new DbReader();
        login.goToPage();
        login.login("user15", "CoolCanvas19.");


    }


    @AfterAll
    public static void close() {
        driverWait = new WebDriverWait(driver ,10);
        // driver.close();
    }

    @ParameterizedTest
    @MethodSource("util.DbReader#getProjects")
    public void checkProjectIssueListExist(Map data) {
        String projectName = data.get("project_name").toString();
        Assertions.assertNotNull(projectIssues.getProjectList(projectName));
    }

    @ParameterizedTest
    @MethodSource("util.DbReader#getProjectIssues")
    public void checkProjectIssueExist(Map data) {
        String projectName = data.get("project_name").toString();
        String issue = data.get("issue").toString();
        Assertions.assertEquals(issue, projectIssues.getIssueHeader(projectName, issue));

    }

}
