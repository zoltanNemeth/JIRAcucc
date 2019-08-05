package com.codecool;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Driver {
    private static final WebDriver DRIVER = new ChromeDriver();

    public static WebDriver getInstance() {
        return DRIVER;
    }

}
