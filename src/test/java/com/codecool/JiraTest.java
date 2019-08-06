package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
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
    public void UnsuccessfulLoginWithInvalidValues() throws InterruptedException {
        String errorMsg = "Sorry, your username and password are incorrect - please try again.";

        JiraLogin.login("user2019", "CoolCanvas19.");
        Thread.sleep(500);
        String actualErrorMsg = driver.findElement(By.xpath("//form[@id='login-form']/div/div/p")).getText();
        assertEquals(errorMsg, actualErrorMsg);

        JiraLogin.login("user14", "");
        Thread.sleep(500);
        assertEquals(errorMsg, actualErrorMsg);

        JiraLogin.login("", "CoolCanvas19.");
        Thread.sleep(500);
        assertEquals(errorMsg, actualErrorMsg);

        JiraLogin.login("", "");
        Thread.sleep(500);
        assertEquals(errorMsg, actualErrorMsg);

    }

    @Test
    public void BrowseIssuesWithAdvancedSearch() throws InterruptedException {
        String searchQuery = "project = TOUCAN";

        JiraLogin.login("user14", "CoolCanvas19.");
        Thread.sleep(500);

        driver.findElement(By.id("find_link")).click();
        Thread.sleep(500);
        driver.findElement(By.id("issues_new_search_link_lnk")).click();
        driver.findElement(By.id("advanced-search")).sendKeys(searchQuery);
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[text()='Search']")).click();
        Thread.sleep(1500);
/*
        List<WebElement> issues = driver.findElements(By.className("issue-list"));

        for (WebElement issue : issues) {
            if (issue.getAttribute("data-key").contains("TOUCAN-")) {
                System.out.println("There are only TOUCAN issues here.");
            }
        }

 */

    }

}
