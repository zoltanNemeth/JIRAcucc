package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        //assertEquals(errorMsg, driver.findElement(By.className("aui-message error")));
        //assertEquals(errorMsg, driver.findElement(By.xpath("//*[@class='aui-message error']//*[text()='Sorry, your username and password are incorrect - please try again.']")));
        //assertEquals(errorMsg, driver.findElement(By.xpath("//p[text(),'Sorry, your username and password are incorrect - please try again.']")));
        //assertEquals(errorMsg, driver.findElement(By.xpath("//p[contains(text(),'Sorry, your username and password are incorrect - please try again.')]"))); // ***

        JiraLogin.login("user14", "");
        Thread.sleep(500);

        JiraLogin.login("", "CoolCanvas19.");
        Thread.sleep(500);

        JiraLogin.login("", "");
        Thread.sleep(500);

    }

}
