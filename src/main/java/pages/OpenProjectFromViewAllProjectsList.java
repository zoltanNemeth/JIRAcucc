package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpenProjectFromViewAllProjectsList extends Page {
    @FindBy(id="login-form-username")
    WebElement usernameField;
    @FindBy(id="login-form-password")
    WebElement passwordField;
    @FindBy(id="login-form-submit")
    WebElement btnSubmit;
    @FindBy(id = "browse_link")
    WebElement projectsBtn;
    @FindBy(id = "project_view_all_link_lnk")
    WebElement viewAllProjectsBtn;

    @FindBy(xpath = "//a[contains(., 'COALA')]")
    WebElement coala;
    @FindBy(xpath = "//a[contains(., 'JETI')]")
    WebElement jeti;
    @FindBy(xpath = "//a[contains(., 'TOUCAN')]")
    WebElement toucan;

    public OpenProjectFromViewAllProjectsList(WebDriver driver) {
        super("login.jsp", driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        btnSubmit.click();
    }

    public void clickOnProjects() {
        projectsBtn.click();
    }

    public void clickOnViewAllProjects() {
        viewAllProjectsBtn.click();
    }

    public boolean validateProjectsArePresent() {
        if (coala.isDisplayed() & jeti.isDisplayed() & toucan.isDisplayed()) {
            return true;
        }
        return false;
    }

}
