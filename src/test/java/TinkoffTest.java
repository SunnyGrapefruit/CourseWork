import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import io.restassured.RestAssured;

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
    public void tearDown(){
        driver.quit();
    }

    public void response(WebElement url){
        List<WebElement> links = url.findElements(By.cssSelector("a"));
        for(WebElement  link : links) {
            String href = link.getAttribute("href");
            int statusCode = RestAssured.get(href).statusCode();
            if(200 != statusCode) {
                System.out.println(href + " gave a response code of " + statusCode);
            }
        }
    }

    @Test //Пункт 3
    public void headerTest() {
        ExchangePage exchangePage = new ExchangePage(driver);
        response(exchangePage.header1);
        response(exchangePage.header2);
    }

    @Test //Пункт 5
    public void footerTest() {
        ExchangePage exchangePage = new ExchangePage(driver);
        response(exchangePage.footer);
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
        ExchangePage exchangePage = new ExchangePage(driver);
        exchangePage.changeCurrencyFrom();
        Assert.assertEquals("Евро", exchangePage.getCurrencyFrom());
        Assert.assertEquals("Рубль", exchangePage.getCurrencyTo());
    }

    @Test //Пункт 8-10
    public void changeCurrencyToTest() throws InterruptedException {
        ExchangePage exchangePage = new ExchangePage(driver);
        changeCurrencyFromTest();
        exchangePage.changeCurrencyTo();
        Assert.assertEquals("Евро", exchangePage.getCurrencyFrom());
        Assert.assertEquals("Доллар", exchangePage.getCurrencyTo());
    }

    @Test //Пункт 17
    public void courseComparisonTest() throws InterruptedException {
        ExchangePage exchangePage = new ExchangePage(driver);
        CbrCourse cbrCourse = new CbrCourse();
        double usdRate = cbrCourse.saveRates().get("UsdRate");
        double eurRate = cbrCourse.saveRates().get("EurRate");
        Assert.assertEquals(eurRate, exchangePage.courseRate);
    }
}
