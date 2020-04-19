import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
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
            Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        }
    }

    @Test
    @DisplayName("Тест страницы 'Курс валют'")
    public void testCourseCurrency() {
        headerTest();
//        footerTest();
        getCurrentPageTest();
        getCurrencyFromTest();
        changeCurrencyFromTest();
        changeCurrencyToTest();
    }

    @Step("Проверка header") //Пункт 3
    public void headerTest() {
        ExchangePage exchangePage = new ExchangePage(driver);
        response(exchangePage.header1);
        response(exchangePage.header2);
    }

    @Step("Проверка footer") //Пункт 5
    public void footerTest() {
        ExchangePage exchangePage = new ExchangePage(driver);
        response(exchangePage.footer);
    }

//    @Step("Проверка текущего раздела") //Пункт 4
    @Test
    public void getCurrentPageTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        ExchangePage exchangePage = new ExchangePage(driver);
        wait.until(ExpectedConditions.visibilityOf(exchangePage.currentPage));
        Assert.assertEquals("Курсы валют", exchangePage.getCurrentPage());
    }

    @Step("Проверка валют выставленных по умолчанию") //Пункт 6
    public void getCurrencyFromTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        ExchangePage exchangePage = new ExchangePage(driver);
        wait.until(ExpectedConditions.visibilityOf(exchangePage.currencyFrom));
        Assert.assertEquals("Рубль", exchangePage.getCurrencyFrom());
        Assert.assertEquals("Евро", exchangePage.getCurrencyTo());
        Assert.assertEquals("₽ → €", exchangePage.getCourseFrom());
        Assert.assertEquals("€ → ₽", exchangePage.getCourseTo());
    }

    @Step("Проверка курса 'Евро' -> 'Рубль'") //Пункт 7
    public void changeCurrencyFromTest(){
        ExchangePage exchangePage = new ExchangePage(driver);
        exchangePage.changeCurrencyFrom();
        Assert.assertEquals("Евро", exchangePage.getCurrencyFrom());
        Assert.assertEquals("Рубль", exchangePage.getCurrencyTo());
    }

    @Step("Проверка курса 'Евро' -> 'Доллар'") //Пункт 8-10
    public void changeCurrencyToTest(){
        ExchangePage exchangePage = new ExchangePage(driver);
//        changeCurrencyFromTest();
        exchangePage.changeCurrencyTo();
        Assert.assertEquals("Евро", exchangePage.getCurrencyFrom());
        Assert.assertEquals("Доллар", exchangePage.getCurrencyTo());
        Assert.assertEquals("$ → €", exchangePage.getCourseFrom());
        Assert.assertEquals("€ → $", exchangePage.getCourseTo());
    }

}
