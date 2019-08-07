package com.codecool;

import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class JiraTest {

    private WebDriver driver;
    private JiraLogin jiraLogin;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        jiraLogin = new JiraLogin(driver, "user15", "CoolCanvas19.");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void LoginLogoutTest() throws InterruptedException {
        jiraLogin.login();
        jiraLogin.logout();
    }

    @Test
    public void browseExistingProjects() throws InterruptedException {
        jiraLogin.login();
        driver.navigate().to("https://jira.codecool.codecanvas.hu/secure/BrowseProjects.jspa?selectedCategory=all&selectedProjectType=all");

        driver.findElement(By.xpath("//a[contains(., 'JETI')]"))
                .click();

        Boolean isThereTheProjectLogo = driver.findElements(By.xpath("//div[@class='aui-sidebar-wrapper']//img[@src='/secure/projectavatar?pid=10002&avatarId=10205']")).size() > 0;
        Boolean areThereDetails = driver.findElements(By.xpath("//div[@class='details-layout']//div")).size() > 0;
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

}
