package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SimpleFormDemo extends Page {
    @FindBy(xpath="//*[@id=\"user-message\"]")
    WebElement message;
    @FindBy(xpath="//*[@id=\"get-input\"]/button")
    WebElement buttonShowMessage;
    @FindBy(xpath="//*[@id=\"display\"]")
    WebElement textMessage;


    public SimpleFormDemo(WebDriver driver) {
        super("basic-first-form-demo.html", driver);
        PageFactory.initElements(driver, this);
    }

    public void setMessage(String strMessage){
        message.sendKeys(strMessage);
        buttonShowMessage.click();
    }
    public String getMessage(){
        return textMessage.getText();
    }
}
