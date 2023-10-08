package courier;

import io.restassured.http.ContentType;
import org.junit.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierTest {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier";


    @Test
    public void createCourier(){
        String jsonString = "{\"login\": \"n1212\", \"password\": \"1234\", \"firstName\": \"saske\"}";
        var jsonMap = Map.of("login", "Ja", "password", "P@ass0rd123", "firstName", "Sparrow");
        boolean creator = given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BAZE_URL)
                .body(jsonString)
                .when()
                .post(COURIER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok", String.valueOf(equalTo("true")));
    }
    @Test
    public void testCreateDuplicateCourier() {
        given()
                .contentType("application/json")
                .body("{ \"login\": \"John\", \"password\": \"P@ass0rd123\", \"firstName\": \"Spar2row\" }")
                .when()
                .post(BAZE_URL + COURIER_PATH);

        given()
                .contentType("application/json")
                .body("{ \"login\": \"John\", \"password\": \"P@ass0rd123\", \"firstName\": \"Spar2row\" }")
                .when()
                .post(BAZE_URL + COURIER_PATH)
                .then()
                .statusCode(409)
                .extract()
                .path("error", String.valueOf(equalTo("Этот логин уже используется")));

    }
    // Тест на создание курьера с недостающими полями
    @Test
    public void testAvtorizationCourierWithMissingFields() {
        given()
                .contentType("application/json")
                .body("{ \"login\": \"ninja\", \"password\": \"1234\" }")
                .when()
                .post(BAZE_URL + COURIER_PATH);

        given()
                .contentType("application/json")
                .body("{ \"login\": \"ninja\" }")
                .when()
                .post(BAZE_URL +  COURIER_PATH)
                .then()
                .statusCode(400)
                .extract()
                .path("error", String.valueOf(equalTo("Недостаточно данных для входа")));
    }
    @Test
    public void testCreateCourierWithExistingLogin() {
        given()
                .contentType("application/json")
                .body("{ \"login\": \"ninja\", \"password\": \"1234\", \"firstNam\": \"saske\" }")
                .when()
                .post(BAZE_URL + COURIER_PATH);

        given()
                .contentType("application/json")
                .body("{ \"login\": \"ninja\", \"password\": \"1234\", \"firstNam\": \"saske\" }")
                .when()
                .post(BAZE_URL +  COURIER_PATH)
                .then()
                .statusCode(409)
                .extract()
                .path("error", String.valueOf(equalTo("Учетная запись не найдена")));
    }
    @Test
    public void deleteCourier() {
            String jsonString = "{\"login\": \"n1212\", \"password\": \"1234\", \"firstName\": \"saske\"}";
            var jsonMap = Map.of("login", "Ja", "password", "P@ass0rd123", "firstName", "Sparrow");
            boolean creator = given().log().all()
                    .contentType(ContentType.JSON)
                    .baseUri(BAZE_URL)
                    .body(jsonString)
                    .when()
                    .put("/api/v1/courier/cancel")
                    .then().log().all()
                    .assertThat()
                    .statusCode(201)
                    .extract()
                    .path("ok");
        }
}