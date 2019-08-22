package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import waiter.Waiter;


public class ProjectIssues extends Page {
    @FindBy(className="issue-list")
    WebElement issueList;
    @FindBy(xpath="//*[@id=\"header\"]/nav/div/div[1]/ul")
    WebElement menu;
    @FindBy(xpath="//*[@id=\"key-val\"]")
    WebElement issueHeader;



    public ProjectIssues(WebDriver driver) {
        super("projects", driver);
        PageFactory.initElements(driver, this);
    }

    public String getProjectList(String project){
        goToSubPage(project + "/issues?filter=allopenissues");
        return issueList.getText();
    }
    public String getIssueHeader(String project, String issue){
        goToSubPage(project + "/issues/" + issue + "?filter=allopenissues");
        waiter.waitForElementToBeDisplayed(issueHeader,this.driver);
        return issueHeader.getText();
    }

}
