package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Search extends Page {
    WebDriverWait webDriverWait;
    @FindBy(xpath = "//div[@data-mode='basic']//a[contains(text(),'Advanced')]")
    WebElement advancedButton;
    @FindBy(id = "advanced-search")
    WebElement searchbar;
    @FindBy(xpath = "//div[@class='search-container']//button[@class='aui-button aui-button-primary search-button']")
    WebElement searchButton;
    @FindBy(linkText = "TOUCAN projekt")
    WebElement pageHeader;
    @FindBy(linkText = "basic")
    WebElement basicSwitcher;

    public Search(String route, WebDriver driver) {
        super(route, driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);


    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public void sendKeysToSearchBar(String key) {
        searchbar.sendKeys(key);

    }

    public void gotoBasicsearch() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Basic")))
                .click();
    }

    public void gotToAdvancedsearch() {
        if (advancedButton
                .isDisplayed()) {
            advancedButton
                    .click();
        }
    }

    public String getPageHeaderText() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("TOUCAN projekt")))
                .getText();
    }
}