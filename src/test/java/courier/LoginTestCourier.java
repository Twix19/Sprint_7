package courier;

import io.restassured.response.Response;
import org.example.courier.Courier;
import org.example.courier.LoginCourier;
import org.example.courier.LoginCourierAPI;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTestCourier {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier/login";
    LoginCourier loginCourier = new LoginCourier("n123inja12", "1234", "saske");
    Courier courier = new Courier("ninja", "1234", "saske");
    LoginCourierAPI loginCourierAPI = new LoginCourierAPI();
    @Test
    public  void testAvtorizationOfCourier(){
        Response response = loginCourierAPI.testAvtorizationOfCourier(String.valueOf(loginCourier.getClass()));
        response.then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id", String.valueOf(equalTo(" 224572")));
    }
    @Test
    public void testAvtorizationWithMissingField(){
    Response response = loginCourierAPI.testAvtorizationOfCourier(String.valueOf(loginCourier.getClass()));
    response .then()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("error", String.valueOf(equalTo("Недостаточно данных для создания учетной записи")));
    }
    @Test
    public void testAvtorizationWithWrongField() {
       Response response = loginCourierAPI.testAvtorizationWithWrongField();
       response  .then()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("error", String.valueOf(equalTo("Учетная запись не найдена")));
    }



}
