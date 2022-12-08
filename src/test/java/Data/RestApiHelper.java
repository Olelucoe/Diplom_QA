package Data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestApiHelper {
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(System.getProperty("sut.url"))
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static String paymentRequest (Data.Card card) {
        return given()
                .spec(requestSpec)
                .body(card)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(200)
                .extract().response().asString();
    }

    public static String creditRequest (Data.Card card) {
        return given()
                .spec(requestSpec)
                .body(card)
                .when()
                .post("/api/v1/credit")
                .then()
                .statusCode(200)
                .extract().response().asString();
    }
}
