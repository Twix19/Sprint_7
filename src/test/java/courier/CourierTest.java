package courier;

import io.restassured.http.ContentType;
import org.example.courier.Courier;
import org.example.courier.CourierSecond;
import org.junit.After;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;
import io.restassured.response.Response;


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
        courierAPI.testCreateDuplicateCourier();
        courierAPI.testCreateDuplicateCourier()
                .then()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("error", String.valueOf(equalTo("Этот логин уже используется")));

    }
    // Тест на создание курьера с недостающими полями
    @Test
    public void testAvtorizationCourierWithMissingFields() {
       courierAPI.testAvtorizationCourierWithMissingFields(courier.getClass());
       courierAPI.testAvtorizationCourierWithMissingFields(courierSecond.getClass())
                .then()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("error", String.valueOf(equalTo("Недостаточно данных для входа")));
    }
    @Test
    public void testCreateCourierWithExistingLogin() {
        courierAPI.testCreateCourierWithExistingLogin();
        courierAPI.testCreateCourierWithExistingLogin()
                .then()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("error", String.valueOf(equalTo("Учетная запись не найдена")));
    }
   @After
    public void deleteCourier() {
            String jsonString = "{\"login\": \"n1212\", \"password\": \"1234\", \"firstName\": \"saske\"}";
            boolean creator = given().log().all()
                    .contentType(ContentType.JSON)
                    .baseUri(BAZE_URL)
                    .body(jsonString)
                    .when()
                    .put("/api/v1/courier/cancel")
                    .then().log().all()
                    .assertThat()
                    .statusCode(SC_CREATED)
                    .extract()
                    .path("ok");
        }
}