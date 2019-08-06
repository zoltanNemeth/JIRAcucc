package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
    public void unsuccessfulLoginWithCaptcha() throws InterruptedException {
        JiraLogin.login("user16", "asdfghjk");
        Thread.sleep(100);
        JiraLogin.login("user16", "asdfghjk");
        Thread.sleep(100);
        JiraLogin.login("user16", "asdfghjk");
        Thread.sleep(100);
        JiraLogin.login("user16", "CoolCanvas19.");
        Thread.sleep(100);
        String actual = driver.findElement(By.xpath("//form[@id='login-form']/div/div/p")).getText();
        assertEquals("Sorry, your userid is required to answer a CAPTCHA question correctly.", actual);
    }

}
