package pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class Issue extends Page {

    private final int TimeoutValue = 10;
    @FindBy(xpath = "//div[@class='search-criteria']//span[@id='fieldpid']")
    WebElement projectNameField;
    @FindBy(xpath = "//ul[@class='aui-nav']//a[@id='find_link']")
    WebElement issueFindLink;
    @FindBy(xpath = "//div[@id='issues_new']//a[@id='issues_new_search_link_lnk']")
    WebElement issueSearchLink;
    @FindBy(id = "jira-share-trigger")
    WebElement shareTrigger;
    @FindBy(xpath = "//div[@id='inline-dialog-share-entity-popup-issuenav']//form[@action='#']//input[@value='https://jira.codecool.codecanvas.hu/issues/?jql=']")
    WebElement linkToFileterDialog;

    public Issue(String route, WebDriver driver) {
        super(route, driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);

    }


    public String getNameFieldTExt() {
        return projectNameField.getText();
    }

    public void searchForIssues() {
        issueFindLink.click();
        issueSearchLink.click();

    }

    public void clickShareTrigger() {
        shareTrigger.click();
    }


    public String getSharePanelIdalogValue() {

        return linkToFileterDialog.getAttribute("value");
    }

}
