package courier;

import io.restassured.http.ContentType;
import org.example.courier.Courier;
import org.example.courier.CourierAPI;
import org.example.courier.CourierSecond;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;


public class CourierTest {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier";
    Courier courier = new Courier("ninja", "1234", "saske");
    CourierSecond courierSecond = new CourierSecond("ninja");
    CourierAPI courierAPI = new CourierAPI();


    @Test
    public void createCourier(){
        courierAPI.createCourier()
                .then().log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok", String.valueOf(equalTo("true")));
    }
    @Test
    public void testCreateDuplicateCourier() {
        courierAPI.createCourier();
        courierAPI.createCourier()
                .then()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("error", String.valueOf(equalTo("Этот логин уже используется")));

    }

    @Test
    public void testCreateCourierWithExistingLogin() {
        courierAPI.createCourierWithExistingLogin();
        courierAPI.createCourierWithExistingLogin()
                .then()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("error", String.valueOf(equalTo("Учетная запись не найдена")));
    }
     @After
     public void deleteCourier() throws IOException {
            courierAPI.deleteCourier()
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .path("ok");
        }
}