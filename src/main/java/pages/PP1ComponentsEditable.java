package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PP1ComponentsEditable extends Page {

    @FindBy(linkText="Private Project 1")
    private WebElement linkToPP1;

    @FindBy(name="name")
    private WebElement name;

    @FindBy(name="description")
    private WebElement description;

    @FindBy(xpath="//table[@id='project-config-components-table']")
    private WebElement table;

    @FindBy(
        xpath="//table[@id='project-config-components-table']//tbody/tr[1]/td[6]/input[@value='Add']"
    )
    private WebElement addButton;

    private By newRecord =
        By.xpath(
            "//table[@id='project-config-components-table']/tbody[2]/tr[@data-name='JIRAcucc']"
        );

    @FindBy(xpath="//table[@id='project-config-components-table']/tbody[2]/tr[@data-name='JIRAcucc']")
    private WebElement newRecordTableRow;

    private String newComponentId;

    public PP1ComponentsEditable(String route, WebDriver driver) {
        super(route, driver);
        PageFactory.initElements(driver, this);
    }

    public String getProjectNameFromPage() {
        return linkToPP1.getText();
    }

    public void addNewComponent(String name, String description) {
        this.name.sendKeys(name);
        this.description.sendKeys(description);
        this.addButton.click();
    }

    public Boolean isCreatedComponentInTable() {

        WebDriverWait wait = new WebDriverWait(driver, 3);

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(this.newRecord));
            this.newComponentId = this.newRecordTableRow.getAttribute("data-id");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNewComponentId() {
        return newComponentId;
    }
}
