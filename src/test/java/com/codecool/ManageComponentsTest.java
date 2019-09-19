package com.codecool;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.WebDriver;
import pages.Login;
import pages.PP1Components;
import pages.PP1ComponentsEditable;
import pages.PP1Glass;

import util.DbReader;
import util.Driver;
import waiter.Waiter;

public class ManageComponentsTest {
    private static WebDriver driver;
    private static DbReader db;

    private static Login login;
    private Waiter waiter = new Waiter();

    @BeforeAll
    public static void setup(){
        driver = new Driver().getWebDriver();
        login = new Login(driver);
        db = new DbReader();
    }

    @BeforeEach
    public void goToPage(){
        login.goToPage();
    }

    @AfterAll
    public static void close() {
        driver.quit();
    }

    @Test
    public void manageComponents() {
        Login login = new Login(driver);
        login.login(
                db.getAll("credentials").get(0).get("username").toString(),
                db.getAll("credentials").get(0).get("password").toString()
        );
        PP1ComponentsEditable pp1ComponentsEditable =
                new PP1ComponentsEditable("/plugins/servlet/project-config/PP1/components", driver);
        pp1ComponentsEditable.goToPage();

        assertEquals("Private Project 1", pp1ComponentsEditable.getProjectNameFromPage());

        pp1ComponentsEditable.addNewComponent("JIRAcucc", "JIRAdescription");

        assertTrue(
            pp1ComponentsEditable.isCreatedComponentInTable(),
            "The new added component can not be found!"
        );

        String newComponentId = pp1ComponentsEditable.getNewComponentId();

        PP1Glass pp1Glass =
            new PP1Glass(
                "projects/PP1?selectedItem=com.codecanvas.glass:glass",
                driver
            );
        pp1Glass.goToPage();

        assertTrue(pp1Glass.isJIRAcuccLinkInGlass());

        PP1Components pp1Components =
            new PP1Components(
                "projects/PP1?selectedItem=com.atlassian.jira.jira-projects-plugin:components-page",
                driver
            );

        pp1Components.goToPage();

        assertTrue(pp1Components.isProjectNameOnPage("Private Project 1"));
        assertTrue(pp1Components.isNewComponentOnPage());

        pp1Components.deleteNewComponent(newComponentId);
    }
}
