package util;

import com.github.shyiko.dotenv.DotEnv;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Driver {
    private WebDriver webDriver;
    private WebDriverWait driverWait;
    private String baseRoute = "https://jira.codecool.codecanvas.hu/";
    private URL remoteUrl;
    private DesiredCapabilities capabilities;

    {
        try {
            remoteUrl = new URL("http://" + System.getProperty("JENKINS_USERNAME") + ":" + System.getProperty("JENKINS_PW") + "@" + System.getProperty("JENKINS_BASE_URL"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public WebDriver getWebDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        capabilities.setPlatform(Platform.LINUX);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
        firefoxOptions.merge(capabilities);
        webDriver = new RemoteWebDriver(remoteUrl, firefoxOptions);
        return webDriver;
    }

    public void goTo(String route) {
        webDriver.navigate().to(baseRoute + route);
        this.driverWait = new WebDriverWait(webDriver, 10);
    }

    public String getRoute(String route) {
        return baseRoute + route;
    }


}


