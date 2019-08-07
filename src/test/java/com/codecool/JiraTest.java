package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

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
    public void manageComponents() throws InterruptedException {
        String recordName = "JIRAcucc";
        String recordDescription = "JIRAdescription";
        WebElement newRecord = null;

        JiraLogin.login("user13", "CoolCanvas19.");
        driver.get("https://jira.codecool.codecanvas.hu/plugins/servlet/project-config/PP1/components");

        String expectedProjectName = "Private Project 1";
        String actualProjectName = driver.findElement(By.linkText(expectedProjectName)).getText();
        assertEquals(expectedProjectName, actualProjectName);

        driver.findElement(By.name("name")).sendKeys(recordName);
        driver.findElement(By.name("description")).sendKeys(recordDescription);
        WebElement table = driver.findElement(By.id("project-config-components-table"));
        WebElement addButton = table.findElement(By.xpath("//tbody/tr[1]/td[6]/input[@value='Add']"));
        addButton.click();

        By xPath = By.xpath(String.format("//table[@id='project-config-components-table']/tbody/tr[@data-name='%s']", recordName));
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(xPath));
        List<WebElement> tableRows = table.findElements(By.xpath("//tbody/tr"));

        for (WebElement row : tableRows) {
            if (row.getText().contains(recordName)) {
                newRecord = row;
            }
        }

        assert newRecord != null;

        String recordId = newRecord.getAttribute("data-id");
        assertNotNull(table.findElement(By.xpath(String.format("//tbody/tr[@data-id='%s']", recordId))));

        driver.get("https://jira.codecool.codecanvas.hu/projects/PP1?selectedItem=com.codecanvas.glass:glass");
        assertNotNull(driver.findElement(By.linkText(recordName)));

        driver.get("https://jira.codecool.codecanvas.hu/projects/PP1?selectedItem=com.atlassian.jira.jira-projects-plugin:components-page");
        actualProjectName = driver.findElement(By.linkText(expectedProjectName)).getText();
        assertEquals(expectedProjectName, actualProjectName);

        table = driver.findElement(By.id("components-table"));
        newRecord = null;
        tableRows = table.findElements(By.xpath("//tbody/tr"));
        for (WebElement row : tableRows) {
            if (row.getText().contains(recordName)) {
                newRecord = row;
            }
        }
        assert newRecord != null;

        table.findElement(By.xpath(String.format("//tbody/tr[@data-component-id='%s']/td[6]/div/a", recordId))).click();
        driver.findElement(By.linkText("Delete")).click();
        driver.findElement(By.xpath("//input[@id='submit']")).click();

        driver.get("https://jira.codecool.codecanvas.hu/projects/PP1?selectedItem=com.codecanvas.glass:glass");

        tableRows = driver.findElements(By.xpath("//table[@id='components-table']/tbody/tr"));
        for (WebElement row : tableRows) {
            assertFalse(row.getText().contains(recordName));
        }
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

    @Test
    public void successfulCreateAnotherIssue() throws InterruptedException {
        String projectName = "Main Testing Project (MTP)";
        String summary = "This is a test issue summary";
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 1);

        JiraLogin.login("user13", "CoolCanvas19.");
        driver.findElement(By.id("create_link")).click();

        By createIssueId = By.id("create-issue-dialog");
        wait.until(ExpectedConditions.presenceOfElementLocated(createIssueId));

        driver.findElement(By.cssSelector("#project-single-select > .icon")).click();
        driver.findElement(By.linkText(projectName)).click();

        By summaryXPath = By.xpath("//input[@id='summary']");
        WebElement summaryInputField = wait.until(ExpectedConditions.elementToBeClickable(summaryXPath));

        actions.moveToElement(summaryInputField);
        actions.click();
        actions.build().perform();
        summaryInputField.sendKeys(summary);

        WebElement checkBox = driver.findElement(By.id("qf-create-another"));
        checkBox.click();

        driver.findElement(By.id("create-issue-submit")).click();

        By notificationLink = By.partialLinkText(summary);
        wait.until(ExpectedConditions.presenceOfElementLocated(notificationLink));
        wait.until(ExpectedConditions.elementToBeClickable(summaryXPath));

        summaryXPath = By.xpath("//input[@id='summary']");
        summaryInputField = wait.until(ExpectedConditions.elementToBeClickable(summaryXPath));

        actions.moveToElement(summaryInputField);
        actions.click();
        actions.build().perform();
        summaryInputField.sendKeys(summary);

        checkBox = driver.findElement(By.id("qf-create-another"));
        checkBox.click();

        driver.findElement(By.id("create-issue-submit")).click();

        By secondNotificationLink = By.xpath("//div[@class='aui-flag']/div[1]/a[2]");
        wait.until(ExpectedConditions.elementToBeClickable(secondNotificationLink)).click();
    }

}
