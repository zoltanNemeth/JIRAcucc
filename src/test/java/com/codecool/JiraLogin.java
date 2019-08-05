package com.codecool;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JiraLogin {
    private WebDriver driver;

    public JiraLogin() {
        driver = Driver.getInstance();
    }

    public void login(String username, String password) throws InterruptedException {
        driver.get("https://jira.codecool.codecanvas.hu/login.jsp");
        driver.findElement(By.id("login-form-username")).sendKeys(username);
        Thread.sleep(500);
        driver.findElement(By.id("login-form-password")).sendKeys(password);
        Thread.sleep(500);
        driver.findElement(By.id("login-form-submit")).click();
    }

    public void logout() throws InterruptedException {
        driver.findElement(By.id("header-details-user-fullname")).click();
        Thread.sleep(500);
        driver.findElement(By.id("log_out")).click();
        Thread.sleep(500);
    }
}
