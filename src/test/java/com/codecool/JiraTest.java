package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JiraTest {

    private WebDriver driver = Driver.getInstance();
    private JiraLogin JiraLogin = new JiraLogin();
    private String appUrl;

    @BeforeEach
    public void setup() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();


    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void LoginLogoutTest() throws InterruptedException {
        JiraLogin.login("user13", "CoolCanvas19.");
        JiraLogin.logout();
    }

    @Test
    public void openProjectRecentProject() throws InterruptedException {
        String expectedProjectName = "JETI Project (JETI)";
        JiraLogin.login("user13", "CoolCanvas19.");
        appUrl = "https://jira.codecool.codecanvas.hu/secure/RapidBoard.jspa";
        driver.get(appUrl);
        WebElement projectsButton = driver.findElement(By.xpath("//*[@id=\"browse_link\"]"));
        projectsButton.click();
        WebElement actualProjectName = driver.findElement(By.id("proj_lnk_10002_lnk"));
        String actualProjectNameText = actualProjectName.getText();

        try {
            assertEquals(expectedProjectName, actualProjectNameText);

            actualProjectName.click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        try {
            String expected = "JETI Project";
            WebElement jetiCion = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div/div[1]/header/div/div[2]/h1/div/div/a"));
            String actual = jetiCion.getText();
            assertEquals(expected, actual);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }



}



