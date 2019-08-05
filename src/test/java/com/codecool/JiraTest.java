package com.codecool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class JiraTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver",System.getenv("DRIVER_PATH"));
        driver = new ChromeDriver();
        driver.navigate().to("https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa");
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void firstTest() {

    }

}
