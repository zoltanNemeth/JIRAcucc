package util;


        import com.github.shyiko.dotenv.DotEnv;
        //import org.junit.jupiter.api.Assertions;
        import org.openqa.selenium.By;
        import org.openqa.selenium.Keys;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.Map;
        import java.util.concurrent.TimeUnit;

        import java.util.concurrent.TimeUnit;

public class Driver {
        private WebDriver webDriver;
        private WebDriverWait driverWait;
        private String baseRoute = "https://jira.codecool.codecanvas.hu/";


        public Driver() {
                Map<String, String> dotEnv = DotEnv.load();

                System.setProperty(dotEnv.get("DRIVER_TYPE"), dotEnv.get("WEBDRIVER_PROPERTY"));
        }


        public WebDriver getWebDriver() {
                this.webDriver = new ChromeDriver();
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


