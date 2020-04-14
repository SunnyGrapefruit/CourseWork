import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;


public class CbrCourse {

    public RequestSpecification requestSpecification;

//    @Before
//    public void configureRestAssured() {
//        RequestSpecification requestSpecification = new RequestSpecBuilder()
//                .setBaseUri("https://www.cbr-xml-daily.ru/daily_json.js")
//                .setContentType(ContentType.ANY)
//                .log(LogDetail.ALL)
//                .build();
//    }
//        RestAssured.baseURI = "http://cookiemonster.com";
//        RestAssured.requestSpecification = requestSpecification.given()
//                .header("Language", "en");
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());


    public ValidatableResponse getWith200Status(String endPoint){
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return requestSpecification.get(endPoint).then()
                // .statusCode(HttpStatus.SC_OK)
                .assertThat().statusCode(200);
    }

    @Test //Проверка значения статуса
    public void getWith200StatusTest(){
        getWith200Status("https://www.cbr-xml-daily.ru/daily_json.js")
                .body("Valute.USD.ID", equalTo("R01235"))
                .body("Valute.EUR.ID", equalTo("R01239"));
    }

    public ValidatableResponse getCourse(String endPoint){
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return requestSpecification.get(endPoint).then()
                .body("Valute.USD.ID", equalTo("R01235"))
                .body("Valute.EUR.ID", equalTo("R01239"));
    }

    @Test //Проверка наличия евро и доллара
    public void getCourseTest(){
        getCourse("https://www.cbr-xml-daily.ru/daily_json.js");
    }

    public ValidatableResponse getName(String endPoint){
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return requestSpecification.get(endPoint)
                .then()
                .header("Content-Type", equalTo("application/javascript; charset=utf-8"));
    }

    @Test //Проверка заголовка
    public void getNameTest(){
        getName("https://www.cbr-xml-daily.ru/daily_json.js");
    }

    public ValidatableResponse getDate(String endPoint){
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return requestSpecification.get(endPoint)
                .then()
                .body("Timestamp", equalTo("2020-04-13T23:00:00+03:00"))
                .body("Date", equalTo("2020-04-14T11:30:00+03:00"));
    }

    @Test //Проверка даты
    public void getDateTest(){
        getDate("https://www.cbr-xml-daily.ru/daily_json.js");
    }

}