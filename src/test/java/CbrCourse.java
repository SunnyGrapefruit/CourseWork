import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;



public class CbrCourse {

    public RequestSpecification requestSpecification;

//    @Test  //Проверить, что АПИ вернуло 200 ОК
//    public void getWith200Status(){
//        String href = "https://www.cbr-xml-daily.ru/";
//        int statusCode = RestAssured.get(href).statusCode();
//        if(200 != statusCode) {
//            System.out.println(href + " gave a response code of " + statusCode);
//        }
//
//    }

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

    public Response getLog(String endPoint){
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return requestSpecification
                .get(endPoint);
    }

    @Test //Проверка значения
    public void getLogTest(){
        getName("https://www.cbr-xml-daily.ru/daily_json.js");
    }




}