package pages;

import com.codecool.pt.si3.util.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {
    private String pageRoute;
    private WebDriver driver;

    Page(String route, WebDriver driver) {
        this.driver = driver;
        Driver driverUtil = new Driver();
        this.pageRoute = driverUtil.getRoute(route);
    }
    public void goToPage() {
        driver.navigate().to(this.pageRoute);
        WebDriverWait driverWait = new WebDriverWait(driver, 10);
    }
}



