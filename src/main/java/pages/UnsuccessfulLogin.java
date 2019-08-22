package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UnsuccessfulLogin extends Page {
    @FindBy(id="login-form-username")
    WebElement usernameField;
    @FindBy(id="login-form-password")
    WebElement passwordField;
    @FindBy(id="login-form-submit")
    WebElement btnSubmit;
    @FindBy(xpath = "//form[@id='login-form']/div/div/p")
    WebElement errorMsg;


    public UnsuccessfulLogin(WebDriver driver) {
        super("login.jsp", driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        btnSubmit.click();
    }

    public boolean validateUnsuccessfulLogin(String errorMessage) {
        return errorMsg.getText().equals(errorMessage);
    }
}
