package util;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
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
                        remoteUrl = new URL("http://" + System.getProperty("JENKINS_USERNAME") + ":" + System.getProperty("JENKINS_PW") + "@" + System.getProperty("JENKINS_BASE_URL"));
                } catch (MalformedURLException e) {
                        e.printStackTrace();
                }
        }

        public WebDriver getWebDriver() {
                this.capabilities = DesiredCapabilities.chrome();
                this.capabilities.setBrowserName("chrome");
                this.capabilities.setPlatform(Platform.LINUX);

            try {
                this.webDriver = new RemoteWebDriver(new URL("172.18.0.3:40000"), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
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


