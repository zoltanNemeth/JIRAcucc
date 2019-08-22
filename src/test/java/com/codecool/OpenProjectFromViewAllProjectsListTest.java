package com.codecool;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.OpenProjectFromViewAllProjectsList;
import util.DbReader;
import util.Driver;
import waiter.Waiter;

import java.util.Map;

public class OpenProjectFromViewAllProjectsListTest {
    private static WebDriver driver;
    private static Driver driverUtil;
    private static WebDriverWait driverWait;
    private static DbReader db;

    private static OpenProjectFromViewAllProjectsList openProjectFromViewAllProjectsList;
    private Waiter waiter = new Waiter();

    @BeforeAll
    public static void setup(){
        driverUtil = new Driver();
        driver = driverUtil.getWebDriver();
        openProjectFromViewAllProjectsList = new OpenProjectFromViewAllProjectsList(driver);
        db = new DbReader();
    }
    @BeforeEach
    public void goToPage(){
        openProjectFromViewAllProjectsList.goToPage();
    }
    @AfterAll
    public static void close() {
        driverWait = new WebDriverWait(driver ,10);
        driver.close();
    }

    @ParameterizedTest
    @MethodSource("util.DbReader#getCredentials")
    public void login(Map data) {
        String username = data.get("username").toString();
        String password = data.get("password").toString();
        openProjectFromViewAllProjectsList.login(username, password);
        openProjectFromViewAllProjectsList.clickOnProjects();
        openProjectFromViewAllProjectsList.clickOnViewAllProjects();
        Assert.assertTrue(openProjectFromViewAllProjectsList.validateProjectsArePresent());
    }
}
