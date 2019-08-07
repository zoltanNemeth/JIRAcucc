package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;


public class JiraTest {

    private WebDriver driver = Driver.getInstance();
    private JiraLogin JiraLogin = new JiraLogin();
    private String userName = "user17";
    private String password = "CoolCanvas19.";

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
        JiraLogin.login(userName, password);
        JiraLogin.logout();
    }


    @Test
    public void SuccessfulIssueCreation() throws InterruptedException {
        JiraLogin.login(userName, password);
        WebElement createButton = driver.findElement(By.id("create_link"));
        createButton.click();
        WebElement iscreateIssueDialog = driver.findElement(By.id("create-issue-dialog"));
        WebElement issuetypeSingleSelectElement = driver.findElement(By.xpath("//*[@id=\"issuetype-single-select\"]/span"));
        elementIsDisplay(iscreateIssueDialog);
        issuetypeSingleSelectElement.click();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"issuetype-field\"]")));
        WebElement issuType = driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]"));
        elementIsDisplay(issuType);
        String actual = issuType.getAttribute("aria-activedescendant");
        String expected = "task-1";
        assertEquals(actual, expected);
        issuType.click();
        String SummaryText = "Summary";
        getSummaryField().sendKeys(SummaryText);


    }

    protected WebElement getSummaryField() {
        WebElement summaryField = driver.findElement(By.id("summary"));
        return summaryField;
    }

    protected boolean elementIsDisplay(WebElement webElement) {
        try {
            webElement.isDisplayed();
            return true;

        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

}
