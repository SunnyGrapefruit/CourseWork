import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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


    //Проверка статуса
    public Response getWith200Status(String endPoint){
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return requestSpecification.get(endPoint);
    }

    @Test //Проверка значения
    public void getCBRCoursesTest(){
        getWith200Status("https://www.cbr-xml-daily.ru/daily_json.js").then()
//                .statusCode(HttpStatus.SC_OK)
                .assertThat().statusCode(200)
                .body("Valute.USD.ID", equalTo("R01235"))
                .body("Valute.EUR.ID", equalTo("R01239"));
    }


    public Response getName(String endPoint){
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return requestSpecification
                .get(endPoint);
    }

    @Test //Проверка значения заголовка
    public void getNameTest(){
        getName("https://www.cbr-xml-daily.ru/daily_json.js").then()
                .header("Content-Type", equalTo("application/javascript; charset=utf-8"));
    }

    public RequestSpecification getSome(String endPoint){
        RequestSpecification requestSpec = requestSpecification.given()
                .baseUri("http://cookiemonster.com")
                .header("Language", "en");
        return requestSpec;
    }
}