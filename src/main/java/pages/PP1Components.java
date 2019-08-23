package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PP1Components extends Page {

    @FindBy(linkText="Private Project 1")
    private WebElement linkToPP1;

    @FindBy(xpath="//table[@id='components-table']//tbody/tr[contains(name, 'JIRAcucc')]")
    private WebElement newComponentTableRow;

    public PP1Components(String route, WebDriver driver) {
        super(route, driver);
        PageFactory.initElements(driver, this);
    }

    public Boolean isProjectNameOnPage(String projectName) {
        return (linkToPP1.getText().equals(projectName));
    }

    public Boolean isNewComponentOnPage() {
        return (newComponentTableRow != null);
    }

    public void deleteNewComponent(String newComponentId) {
        driver.findElement(
            By.xpath(
                "//table[@id='components-table']//tbody/tr[@data-component-id='" + newComponentId + "']/td[6]/div/a"
            )).click();

        driver.findElement(By.xpath("//a[@id='deletecomponent_" + newComponentId + "']")).click();
    }
}
