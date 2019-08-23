package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PP1Glass extends Page {

    @FindBy(linkText="JIRAcucc")
    private WebElement JIRAcuccLink;

    public PP1Glass(String route, WebDriver driver) {
        super(route, driver);
        PageFactory.initElements(driver, this);
    }

    public Boolean isJIRAcuccLinkInGlass() {
        return this.JIRAcuccLink.isDisplayed();
    }
}
