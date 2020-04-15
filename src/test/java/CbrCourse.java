import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;


public class CbrCourse {

    private static ValidatableResponse requestSpecification;

    @BeforeClass
    public static void setupResponse() {
        requestSpecification = RestAssured.given().contentType(ContentType.JSON)
                .get("https://www.cbr-xml-daily.ru/daily_json.js")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test //Проверка значения статуса
    public void getWith200StatusTest() {
        requestSpecification.assertThat().statusCode(200);
    }

    @Test //Проверка наличия евро и доллара
    public void getCourseTest() {
        requestSpecification
                .body("Valute.USD.ID", equalTo("R01235"))
                .body("Valute.EUR.ID", equalTo("R01239"));
    }

    @Test //Проверка заголовка
    public void getNameTest() {
        requestSpecification
                .header("Content-Type", equalTo("application/javascript; charset=utf-8"));
    }

    @Test //Проверка даты
    public void getDateTest() {
//        requestSpecification
//                .body("Timestamp", equalTo(LocalDate.now()))
//                .body("Date", equalTo(LocalDate.now()
//                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")));

        Assert.assertEquals(
                LocalDate.now().plusDays(1),
                LocalDate.parse(
                        requestSpecification.extract()
                                .jsonPath()
                                .getString("Date"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
                )
        );
    }

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

    @Test //Проверка наличия евро и доллара
    public void saveRates() {
        double usdRate = requestSpecification.extract()
                .jsonPath()
                .getDouble("Valute.USD.Value");
        double eurRate = requestSpecification.extract()
                .jsonPath()
                .getDouble("Valute.EUR.Value");
//        System.out.println("USD Rate: " + usdRate);
//        System.out.println("EUR Rate: " + eurRate);

        ExchangePage exchangePage = new ExchangePage(driver);
        Assert.assertEquals(Math.floor(eurRate), exchangePage.courseRate.getText());
        exchangePage.changeCurrencyFrom();
        Assert.assertEquals(Math.floor(usdRate), exchangePage.courseRate.getText());
    }

}