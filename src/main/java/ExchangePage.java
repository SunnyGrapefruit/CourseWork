import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ExchangePage {

    private WebDriver driver;

    @FindBy(className = "header__3Gok1")
    public WebElement currentPage;

    @FindBy(id = "TCSid1")
    public WebElement currencyFrom;

    @FindBy(id = "TCSid3")
    public WebElement currencyTo;

    @FindBy(className = "Dropdown__dropdownList_shadow_1Tvwu")
    public WebElement currencyList;

    @FindBy(xpath = "//*[@id=\"TCSid1\"]/div[2]/div/div/div/div/div[2]/div[2]/div[1]")
    public WebElement changeCurrencyFrom;

//    @FindBy(xpath = "//*[@class=\"Dropdown__item_3VQrb\"][contains(text(), 'Евро')]")
//    public WebElement changeCurrencyFrom;

    @FindBy(xpath = "//*[@id=\"TCSid3\"]/div[2]/div/div/div/div/div[2]/div[3]/div[1]")
    public WebElement changeCurrencyTo;

    public ExchangePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getCurrentPage(){
        String current = currentPage.getText();
        return current;
    }

    public String getCurrencyFrom(){
        String currency = currencyFrom.getText();
        return currency;
    }

    public String getCurrencyTo(){
        String currency = currencyTo.getText();
        return currency;
    }

    public void changeCurrencyFrom(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        currencyFrom.click();
        wait.until(ExpectedConditions.visibilityOf(currencyList));
        changeCurrencyFrom.click();
//        wait.until(ExpectedConditions.invisibilityOf(currencyList));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("Dropdown__dropdownList_shadow_1Tvwu")));
    }

    public void changeCurrencyTo(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        currencyTo.click();
        wait.until(ExpectedConditions.visibilityOf(currencyList));
        changeCurrencyTo.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("Dropdown__dropdownList_shadow_1Tvwu")));
    }

    @FindBy(className = "header__1lZpj")
    public WebElement header1;

    @FindBy(className = "header__3Wh47")
    public WebElement header2;

    @FindBy(className = "footer__8Y9k5")
    public WebElement footer;

}
