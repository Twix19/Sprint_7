package courier;

import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTestCourier {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier/login";
    @Test
    public void testAvtorizationOfCourier(){
        String jsonString = "{\"login\": \"n123inja12\", \"password\": \"1234\"}";
        var jsonMap = Map.of("login", "n123inja12", "password", "1234");
        given()
                .contentType(ContentType.JSON)
                .baseUri(BAZE_URL)
                .body(jsonString)
                .when()
                .post(COURIER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id", String.valueOf(equalTo(" 224572")));
    }
    @Test
    public void testAvtorizationWithMissingField() { // 504
        given()
                .contentType("application/json")
                .body("{ \"login\": \"ninja\", \"password\": }")
                .when()
                .post(BAZE_URL +  COURIER_PATH)
                .then()
                .statusCode(400)
                .extract()
                .path("error", String.valueOf(equalTo("Недостаточно данных для создания учетной записи")));
    }
    @Test
    public void testAvtorizationWithWrongField() {
        given()
                .contentType("application/json")
                .body("{ \"login\": \"nina\", \"password\": \"1234\" }")
                .when()
                .post(BAZE_URL +  COURIER_PATH)
                .then()
                .statusCode(404)
                .extract()
                .path("error", String.valueOf(equalTo("Учетная запись не найдена")));
    }

}
