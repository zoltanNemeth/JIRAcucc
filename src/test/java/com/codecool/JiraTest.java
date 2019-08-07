package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class JiraTest {

    private WebDriver driver;
    private JiraLogin jiraLogin;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        jiraLogin = new JiraLogin(driver, "", "CoolCanvas19.");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {
            driver.manage().window().maximize();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void ManageComponents() throws InterruptedException {
        String recordName = "JIRAcucc";
        String recordDescription = "JIRAdescription";
        WebElement newRecord = null;

        jiraLogin.setUser("user13");
        jiraLogin.login();
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
        jiraLogin.setUser("user14");
        jiraLogin.login();
        driver.get("https://jira.codecool.codecanvas.hu/browse/TOUCAN-65?jql=project%20%3D%20TOUCAN");

        WebDriverWait wait = new WebDriverWait(driver, 3);

        By issueOneXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='TOUCAN-1']");
        By issueTwoXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='TOUCAN-2']");
        By issueThreeXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='TOUCAN-3']");

        wait.until(ExpectedConditions.presenceOfElementLocated(issueOneXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueTwoXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueThreeXPath));
    }

    @Test
    public void JetiProjectContainsIssues() throws InterruptedException {
        jiraLogin.setUser("user13");
        jiraLogin.login();
        driver.get("https://jira.codecool.codecanvas.hu/issues/?jql=project%20%3D%20JETI");

        WebDriverWait wait = new WebDriverWait(driver, 3);

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
        jiraLogin.setUser("user13");
        jiraLogin.login();
        driver.get("https://jira.codecool.codecanvas.hu/issues/?jql=project%20%3D%20COALA");

        WebDriverWait wait = new WebDriverWait(driver, 3);

        By issueOneXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='COALA-1']");
        By issueTwoXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='COALA-2']");
        By issueThreeXPath = By.xpath("//div[@class='list-content']/ol/li[@data-key='COALA-3']");

        wait.until(ExpectedConditions.presenceOfElementLocated(issueOneXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueTwoXPath));
        wait.until(ExpectedConditions.presenceOfElementLocated(issueThreeXPath));
    }

    @Test
    public void SuccessfulCreateAnotherIssue() throws InterruptedException {
        String projectName = "Main Testing Project (MTP)";
        String summary = "This is a test issue summary";
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 3);

        jiraLogin.setUser("user13");
        jiraLogin.login();
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

    @Test
    public void BrowseExistingProjects() throws InterruptedException {
        jiraLogin.setUser("user15");
        jiraLogin.login();
        driver.navigate().to("https://jira.codecool.codecanvas.hu/secure/BrowseProjects.jspa?selectedCategory=all&selectedProjectType=all");

        driver.findElement(By.xpath("//a[contains(., 'JETI')]"))
                .click();

        boolean isThereTheProjectLogo = driver.findElements(By.xpath("//div[@class='aui-sidebar-wrapper']//img[@src='/secure/projectavatar?pid=10002&avatarId=10205']")).size() > 0;
        boolean areThereDetails = driver.findElements(By.xpath("//div[@class='details-layout']//div")).size() > 0;
        jiraLogin.logout();
        assertAll(() -> assertTrue(isThereTheProjectLogo), () -> assertTrue(areThereDetails));

//         These lines compare whether the selected nav item and the detail section on the right side are related
//        String navElementName = driver.findElement(By.xpath("//li[@class='aui-nav-selected']//span[2]")).getText().toLowerCase();
//        String element2 = driver.findElement(By.xpath("//span[@class='subnavigator-title']")).getText();
//        Boolean sthing = element2.contains(navElementName);
//        JiraLogin.logout();
//
//        assertTrue(sthing);
    }

    @Test
    public void OpenProjectFromViewAllProjectsList() throws InterruptedException {
        jiraLogin.setUser("user15");
        jiraLogin.login();
        driver.findElement(By.id("browse_link")).click();
        driver.findElement(By.id("project_view_all_link_lnk")).click();

        Boolean actual = false;

        try {
            WebElement linkToCoala = driver.findElement(By.xpath("//a[contains(., 'COALA')]"));
            WebElement linkToJeti = driver.findElement(By.xpath("//a[contains(., 'JETI')]"));
            WebElement linkToToucan = driver.findElement(By.xpath("//a[contains(., 'TOUCAN')]"));
            actual = true;
        } catch (Exception e) {
            actual = false;
        }

        jiraLogin.logout();

        assertTrue(actual);
    }
    @Test
    public void UnsuccessfulLoginWithInvalidValues() throws InterruptedException {
        String errorMsg = "Sorry, your username and password are incorrect - please try again.";

        jiraLogin.setUser("user2019");
        jiraLogin.setPassword("CoolCanvas19.");
        jiraLogin.login();
        Thread.sleep(500);
        String actualErrorMsg = driver.findElement(By.xpath("//form[@id='login-form']/div/div/p")).getText();
        assertEquals(errorMsg, actualErrorMsg);

        jiraLogin.setUser("user14");
        jiraLogin.setPassword("");
        jiraLogin.login();
        Thread.sleep(500);
        assertEquals(errorMsg, actualErrorMsg);

        jiraLogin.setUser("");
        jiraLogin.setPassword("CoolCanvas19.");
        jiraLogin.login();
        Thread.sleep(500);
        assertEquals(errorMsg, actualErrorMsg);

        jiraLogin.setUser("");
        jiraLogin.setPassword("");
        jiraLogin.login();
        Thread.sleep(500);
        assertEquals(errorMsg, actualErrorMsg);

    }

    private void checkSearchResults(String issueName) {
        List<WebElement> issues = driver.findElements(By.className("issue-link-key"));

        for (WebElement issue : issues) {
            if (issue.getText().contains(issueName)) {
                System.out.println("There are only TOUCAN issues here.");
            }
        }
    }

    @Test
    public void BrowseExistingIssues() throws InterruptedException {
        String issueName = "TOUCAN-";

        jiraLogin.setUser("user14");
        jiraLogin.setPassword("CoolCanvas19.");
        jiraLogin.login();
        Thread.sleep(500);

        driver.get("https://jira.codecool.codecanvas.hu/browse/TOUCAN-57?jql=project%20%3D%20TOUCAN");

        checkSearchResults(issueName);

    }

    @Test
    public void BrowseIssuesWithAdvancedSearch() throws InterruptedException {
        String searchQuery = "project = TOUCAN";
        String issueName = "TOUCAN-";

        jiraLogin.setUser("user14");
        jiraLogin.setPassword("CoolCanvas19.");
        jiraLogin.login();
        Thread.sleep(500);

        driver.findElement(By.id("find_link")).click();
        Thread.sleep(500);
        driver.findElement(By.id("issues_new_search_link_lnk")).click();
        Thread.sleep(500);

        if (driver.findElement(By.xpath("//a[text()='Basic']")).isDisplayed()) {
            driver.findElement(By.xpath("//a[text()='Basic']")).click();
            Thread.sleep(500);
        }

        driver.findElement(By.xpath("//a[text()='Advanced']")).click();
        driver.findElement(By.id("advanced-search")).sendKeys(searchQuery);
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[text()='Search']")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//a[text()='Basic']")).click();
        Thread.sleep(500);

        checkSearchResults(issueName);

    }

    @Test
    public void UnsuccessfulIssueCreationWithMissingInfo() throws InterruptedException {
        String projectName = "Main Testing Project (MTP)";
        String expectedErrorMessage = "You must specify a summary of the issue.";
        WebDriverWait wait = new WebDriverWait(driver, 3);

        jiraLogin.setUser("user13");
        jiraLogin.login();

        driver.findElement(By.id("create_link")).click();

        By createIssueId = By.id("create-issue-dialog");
        wait.until(ExpectedConditions.presenceOfElementLocated(createIssueId));

        driver.findElement(By.cssSelector("#project-single-select > .icon")).click();
        driver.findElement(By.linkText(projectName)).click();

        By createButtonId = By.id("create-issue-submit");
        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(createButtonId));
        createButton.click();

        By messageXPath = By.xpath("//form[@name='jiraform']/div[1]/div[2]/div[1]/div");
        WebElement actualMessage = wait.until(ExpectedConditions.presenceOfElementLocated(messageXPath));
        assertEquals(expectedErrorMessage, actualMessage.getText());
    }

}
