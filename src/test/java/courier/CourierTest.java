package courier;


import org.example.courier.CourierAPI;
import org.junit.After;
import org.junit.Test;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;


public class CourierTest {
    CourierAPI courierAPI = new CourierAPI();

    @Test
    public void createCourier(){
        courierAPI.sendCreationDataOfCourier()
                .then().log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok", String.valueOf(equalTo("true")));
    }
    @Test
    public void testCreateDuplicateCourier() {
        courierAPI.sendCreationDataOfCourier();
        courierAPI.sendCreationDataOfCourier()
                .then()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("error", String.valueOf(equalTo("Этот логин уже используется")));

    }

    @Test
    public void testCreateCourierWithExistingLogin() {
        courierAPI.sendCourierCreationRequest();
        courierAPI.sendCourierCreationRequest()
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