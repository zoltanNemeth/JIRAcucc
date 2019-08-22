package pages;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class Issue extends  Page{

    private final int TimeoutValue=10;
    @FindBy(xpath="//div[@class='search-criteria']//span[@id='fieldpid']")
    WebElement projectNameField;
    @FindBy(xpath = "//ul[@class='aui-nav']//a[@id='find_link']")
    WebElement issueFindLink;
    @FindBy(xpath = "//div[@id='issues_new']//a[@id='issues_new_search_link_lnk']")
    WebElement issueSearchLink;
    @FindBy(xpath = "//div[@data-mode='basic']//a[contains(text(),'Advanced')]")
    WebElement advancedButton;


    public Issue(String route, WebDriver driver) {
        super(route, driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10),this);

    }


    public String getNameFieldTExt(){
        return projectNameField.getText();
    }

    public void searchForIssues(){
        issueFindLink.click();
        issueSearchLink.click();
        if(advancedButton.isDisplayed()){
         advancedButton.click();
        }

    }


}
