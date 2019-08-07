package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    public void mainProjectIssueEditable() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //String newDescription = "Now is possible to Create a 'Task' typed issue for the JETI project.";
        JiraLogin.login("user16", "CoolCanvas19.");
        driver.get("https://jira.codecool.codecanvas.hu/projects/MTP/issues/MTP-63?filter=allopenissues");
        driver.findElement(By.xpath("//section[@id='content']/div[2]/div/div/div/div/div/div/div/div/div/div/div/div[2]/div/ol/li/a[1][@href]")).click();
        By editIssue = By.xpath("//a[@id='edit-issue']/span");
        wait.until(ExpectedConditions.visibilityOfElementLocated(editIssue));
        wait.until(ExpectedConditions.elementToBeClickable(editIssue)).click();
        //driver.findElement(By.xpath("//iframe[@id='mce_0_ifr']")).clear();
        //driver.findElement(By.xpath("//iframe[@id='mce_0_ifr']")).sendKeys(newDescription);


    }

}
