package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Login extends Page {
    @FindBy(id="login-form-username")
    WebElement usernameField;
    @FindBy(id="login-form-password")
    WebElement passwordField;
    @FindBy(id="login-form-submit")
    WebElement btnSubmit;



    public Login(WebDriver driver) {
        super("login.jsp", driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        btnSubmit.click();
    }
}
