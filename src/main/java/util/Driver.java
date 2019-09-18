package util;

import com.github.shyiko.dotenv.DotEnv;
import org.openqa.selenium.*;
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
            remoteUrl = new URL("http://" + System.getenv("JENKINS_USERNAME") + ":" + System.getenv("JENKINS_PW") + "@" + System.getenv("JENKINS_BASE_URL"));
            System.out.println(remoteUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

        public WebDriver getWebDriver() {
                this.capabilities = DesiredCapabilities.chrome();
                this.capabilities.setBrowserName("chrome");
                this.capabilities.setPlatform(Platform.LINUX);
                this.webDriver = new RemoteWebDriver(remoteUrl, capabilities);
                this.driverWait = new WebDriverWait(webDriver, 10);
                this.webDriver.manage().window().maximize();
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


