package pages;

import org.openqa.selenium.WebDriver;
import util.Driver;
import waiter.Waiter;


public abstract class Page {
    protected WebDriver driver;
    static Waiter waiter = new Waiter();
    protected String pageRoute;

    Page(String route, WebDriver driver) {
        this.driver = driver;
        Driver driverUtil = new Driver();
        this.pageRoute = driverUtil.getRoute(route);
    }
    public void goToPage() {
        waiter.get(this.pageRoute, driver, 10);
    }
    public void goToSubPage(String subPage) {
        waiter.get(this.pageRoute + "/" + subPage, driver, 10);
    }
}



