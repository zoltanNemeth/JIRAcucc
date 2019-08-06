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
    public void ToucanProjectContainsIssues() throws InterruptedException {
        JiraLogin.login("user14", "CoolCanvas19.");
        driver.get("https://jira.codecool.codecanvas.hu/browse/TOUCAN-65?jql=project%20%3D%20TOUCAN");

        WebDriverWait wait = new WebDriverWait(driver, 1);

        By issueOneXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='TOUCAN-1']");
        By issueTwoXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='TOUCAN-2']");
        By issueThreeXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='TOUCAN-3']");

        wait.until(ExpectedConditions.presenceOfElementLocated(issueOneXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueTwoXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueThreeXPath));
    }

    @Test
    public void JetiProjectContainsIssues() throws InterruptedException {
        JiraLogin.login("user13", "CoolCanvas19.");
        driver.get("https://jira.codecool.codecanvas.hu/issues/?jql=project%20%3D%20JETI");

        WebDriverWait wait = new WebDriverWait(driver, 1);

        By nextPageXPath = By.xpath("//div[@data-displayable-total='55']/a[2]/span");
        wait.until(ExpectedConditions.elementToBeClickable(nextPageXPath)).click();

        By issueOneXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='JETI-1']");
        By issueTwoXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='JETI-2']");
        By issueThreeXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='JETI-3']");

        wait.until(ExpectedConditions.presenceOfElementLocated(issueOneXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueTwoXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueThreeXPath));
    }

    @Test
    public void CoalaProjectContainsIssues() throws InterruptedException {
        JiraLogin.login("user13", "CoolCanvas19.");
        driver.get("https://jira.codecool.codecanvas.hu/issues/?jql=project%20%3D%20COALA");

        WebDriverWait wait = new WebDriverWait(driver, 1);

        By issueOneXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='COALA-1']");
        By issueTwoXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='COALA-2']");
        By issueThreeXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='COALA-3']");

        wait.until(ExpectedConditions.presenceOfElementLocated(issueOneXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueTwoXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueThreeXPath));
    }

}
