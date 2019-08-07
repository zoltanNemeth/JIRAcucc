package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JiraTest {

    private WebDriver driver = Driver.getInstance();
    private JiraLogin JiraLogin = new JiraLogin();
    private String appUrl;
    private String userName = "user17";
    private String password = "CoolCanvas19.";
    private int idVariable = 10591;

    @BeforeEach
    public void setup() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void LoginLogoutTest() throws InterruptedException {
        JiraLogin.login("user13", "CoolCanvas19.");
        JiraLogin.logout();
    }


    @Test
    public void SuccessfullyEditVersionDetails() throws InterruptedException {
        appUrl = "https://jira.codecool.codecanvas.hu/projects/PP1?selectedItem=com.atlassian.jira.jira-projects-plugin%3Arelease-page&status=unreleased";
        JiraLogin.login(userName, password);
        driver.get(appUrl);
        WebElement actualProjectName = driver.findElement(By.xpath("//a[contains(@title,'Private Project 1')]"));
        elementIsDisplay(actualProjectName);
        WebElement textField = driver.findElement(By.name("name"));
        String exPectedText = "RegressionTeam 1.0";
        textField.sendKeys(exPectedText);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#releases-add__version > div.releases-add__confirm > button"))).click();
        WebElement manageVersions = driver.findElement(By.xpath("//div[@class='aui-page-header-actions']/a"));
        manageVersions.click();

        WebElement pencilIcon = driver.findElement(By.xpath("//span[contains(text(),'RegressionTeam 1.0')]"));
        pencilIcon.click();

        WebDriverWait waitEditField = new WebDriverWait(driver, 3);
        String expextedVersion = "RegressionTeam 1.2";

        waitEditField.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value = 'RegressionTeam 1.0']"))).sendKeys(expextedVersion);
        WebDriverWait updateButton = new WebDriverWait(driver, 3);

        updateButton.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='project-config-versions-add-fields aui-restfultable-focused']//input[@value='Update']"))).click();
        appUrl = "https://jira.codecool.codecanvas.hu/projects/PP1?selectedItem=com.atlassian.jira.jira-projects-plugin%3Arelease-page&status=unreleased";
        driver.get(appUrl);

        WebElement versionLink = driver.findElement(By.xpath("//tr[@class='item-state-ready ']//a[contains(text(),'Regression')]"));
        String actualText = versionLink.getText();

        assertEquals(expextedVersion, actualText);

        WebElement threeDot = driver.findElement(By.xpath("//td[@class='dynamic-table__actions']//span[@class='aui-icon aui-icon-small aui-iconfont-more']"));
        threeDot.click();

        WebDriverWait deleteButton = new WebDriverWait(driver, 3);
        deleteButton.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"version-actions-" + idVariable + "\"]/ul/li/a[4]"))).click();

        WebDriverWait popUpfield = new WebDriverWait(driver, 3);
        popUpfield.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='version-" + idVariable + "-delete-dialog']//input[@value='Yes']"))).click();
        idVariable++;
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
