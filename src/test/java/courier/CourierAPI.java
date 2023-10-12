package courier;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.courier.Courier;
import org.example.courier.CourierSecond;

import static io.restassured.RestAssured.given;


public class CourierAPI {
        private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
        private static final String COURIER_PATH = "/api/v1/courier";
        Courier courier = new Courier("ninja", "1234", "saske");
        CourierSecond courierSecond = new CourierSecond("ninja");


        public Response createCourier(){
            Courier courier = new Courier("1234rouyr", "1234", "saske");
            return given().log().all()
                    .contentType(ContentType.JSON)
                    .baseUri(BAZE_URL)
                    .body(courier)
                    .when()
                    .post(COURIER_PATH);
        }

        public Response testCreateDuplicateCourier() {
            return given()
                    .contentType("application/json")
                    .body(courier)
                    .when()
                    .post(BAZE_URL + COURIER_PATH);
        }
        public Response testAvtorizationCourierWithMissingFields(Class courier) {
          return   given()
                    .contentType("application/json")
                    .body(courier)
                    .when()
                    .post(BAZE_URL + COURIER_PATH);
        }

        public Response testCreateCourierWithExistingLogin() {
           return given()
                    .contentType("application/json")
                    .body(courier)
                    .when()
                    .post(BAZE_URL + COURIER_PATH);
        }
}
