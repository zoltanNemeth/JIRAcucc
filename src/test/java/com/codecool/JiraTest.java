package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
    public void successfulIssueCreation() throws InterruptedException {
        JiraLogin.login("user16", "CoolCanvas19.");
        driver.findElement(By.id("browse_link")).click();
        driver.findElement(By.id("admin_main_proj_link_lnk")).click();
        driver.findElement(By.id("create_link")).click();
        WebElement inputFieldData = driver.findElement(By.xpath("//div[@id='project-single-select']/input"));
        String inputFieldValue = inputFieldData.getAttribute("value");
        assertEquals("Main Testing Project (MTP)", inputFieldValue);
        driver.findElement(By.xpath("//form[@class='aui']/div[2]/div/a")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@id='page']/header/nav/div/div/ul/li/a[@id='browse_link']")).click();
        driver.findElement(By.id("proj_lnk_10002_lnk")).click();
        driver.findElement(By.id("create_link")).click();
        WebElement inputFieldData2 = driver.findElement(By.xpath("//div[@id='project-single-select']/input"));
        String inputFieldValue2 = inputFieldData2.getAttribute("value");
        assertNotEquals("JETI Project", inputFieldValue2);


    }

}
