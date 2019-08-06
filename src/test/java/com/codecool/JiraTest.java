package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
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

}
