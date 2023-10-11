package courier;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.courier.Courier;
import org.example.courier.LoginCourier;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginCourierAPI {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier/login";
    LoginCourier loginCourier = new LoginCourier("1234courier", "1234", "saske");
    Courier courier = new Courier("ninja", "1234", "saske");

    public Response testAvtorizationOfCourier(){
       return given()
                .contentType(ContentType.JSON)
                .baseUri(BAZE_URL)
                .body(loginCourier)
                .when()
                .post(COURIER_PATH);
    }

    public Response testAvtorizationWithMissingField() {
       return given()
                .contentType("application/json")
                .body("{ \"login\": \"ninja\", \"password\": }")
                .when()
                .post(BAZE_URL +  COURIER_PATH);
    }

    public Response testAvtorizationWithWrongField() {
       return given()
                .contentType("application/json")
                .body(courier)
                .when()
                .post(BAZE_URL +  COURIER_PATH);
    }

}
