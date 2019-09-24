package util;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {
    private WebDriver webDriver;
    private WebDriverWait driverWait;
    private String baseRoute = "https://jira.codecool.codecanvas.hu/";
    private URL remoteUrl;
    private DesiredCapabilities capabilities;

    {
        try {
            remoteUrl = new URL("https://" + System.getenv("JENKINS_USERNAME") + ":" + System.getenv("JENKINS_PW") + "@" + System.getenv("JENKINS_BASE_URL"));
            System.out.println(remoteUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public WebDriver getWebDriver() {
        this.capabilities = DesiredCapabilities.chrome();
        this.driverWait = new WebDriverWait(webDriver, 10);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        this.webDriver = new RemoteWebDriver(remoteUrl, capabilities);
        return this.webDriver;
    }

    public void goTo(String route) {
        webDriver.navigate().to(baseRoute + route);
        this.driverWait = new WebDriverWait(webDriver, 10);
    }

    public String getRoute(String route) {
        return baseRoute + route;
    }


}


