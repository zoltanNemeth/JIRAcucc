package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class JiraTest {

    private WebDriver driver = Driver.getInstance();
    private JiraLogin JiraLogin = new JiraLogin();

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver",System.getenv("DRIVER_PATH"));
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
    public void browseExistingIssuesWithSearch() throws InterruptedException {
        JiraLogin.login("user13", "CoolCanvas19.");
        driver.get("https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa");
        Thread.sleep(1000);
        driver.findElement(By.id("find_link")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("issues_new_search_link_lnk")).click();
        Thread.sleep(1000);
        String actual = driver.findElement(By.id("jira-share-trigger")).getText();
        assertEquals("Share this search by emailing other users Share", actual);
    }


}
