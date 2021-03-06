
package com.codecool;

import org.junit.jupiter.api.Test;




import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.Login;
import util.DbReader;
import util.Driver;
import waiter.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Auth {
    private static WebDriver driver;
    private static Driver driverUtil;
    private static WebDriverWait driverWait;
    private static DbReader db;

    private static Login login;
    private Waiter waiter = new Waiter();

    @BeforeAll
    public static void setup(){
        driverUtil = new Driver();
        driver = driverUtil.getWebDriver();
        login = new Login(driver);
        db = new DbReader();
    }
    @BeforeEach
    public void goToPage(){
        login.goToPage();
    }
    @AfterAll
    public static void close() {
        driverWait = new WebDriverWait(driver ,10);
    }

    @ParameterizedTest
    @MethodSource("util.DbReader#getCredentials")
    public void login(Map data) {
        String username = data.get("username").toString();
        String password = data.get("password").toString();
        login.login(username, password);
    }

}
