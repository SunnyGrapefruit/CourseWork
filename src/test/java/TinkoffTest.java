import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TinkoffTest {

    public static WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "config\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.tinkoff.ru/about/exchange/");
    }

    @After
    public void tearDown() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        driver.quit();
    }

    @Test //Пункт 4
    public void getCurrentPageTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        ExchangePage exchangePage = new ExchangePage(driver);
        wait.until(ExpectedConditions.visibilityOf(exchangePage.currentPage));
        Assert.assertEquals("Курсы валют", exchangePage.getCurrentPage());
    }

    @Test //Пункт 6
    public void getCurrencyFromTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        ExchangePage exchangePage = new ExchangePage(driver);
        wait.until(ExpectedConditions.visibilityOf(exchangePage.currencyFrom));
        Assert.assertEquals("Рубль", exchangePage.getCurrencyFrom());
        Assert.assertEquals("Евро", exchangePage.getCurrencyTo());
    }

    @Test //Пункт 7
    public void changeCurrencyFromTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        ExchangePage exchangePage = new ExchangePage(driver);
        exchangePage.changeCurrencyFrom();
        TimeUnit.SECONDS.sleep(3);
        Assert.assertEquals("Евро", exchangePage.getCurrencyFrom());
        Assert.assertEquals("Рубль", exchangePage.getCurrencyTo());
    }

    @Test //Пункт 8-10
    public void changeCurrencyToTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        ExchangePage exchangePage = new ExchangePage(driver);
        changeCurrencyFromTest();
        exchangePage.changeCurrencyTo();
        TimeUnit.SECONDS.sleep(3);
        Assert.assertEquals("Евро", exchangePage.getCurrencyFrom());
        Assert.assertEquals("Доллар", exchangePage.getCurrencyTo());
    }

}
