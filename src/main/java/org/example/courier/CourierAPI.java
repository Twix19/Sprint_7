package org.example.courier;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;

public class CourierAPI {
        private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
        private static final String COURIER_PATH = "/api/v1/courier";
        Courier courier = new Courier("000912", "P@ssw0rd123", "Sparrow");
        LoginCourierAPI loginCourierAPI = new LoginCourierAPI();

        ObjectMapper mapper = new ObjectMapper();


    public Response sendCreationDataOfCourier(){
            return RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .baseUri(BAZE_URL)
                    .body(courier)
                    .when()
                    .post(COURIER_PATH);
        }

        public Response sendCourierCreationRequest() {
           return RestAssured.given()
                    .contentType("application/json")
                    .body(courier)
                    .when()
                    .post(BAZE_URL + COURIER_PATH);
        }

        public Response deleteCourier() throws IOException {
            String value = mapper.writeValueAsString(courier);
            String responseBody = loginCourierAPI.sendAvtorizationDates(value).getBody().asString();
            String loginId = responseBody.replaceAll("\\D+", "");
            String jsonString = "{\"login\": \"n1212\", \"password\": \"1234\", \"firstName\": \"saske\"}";
            return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .baseUri(BAZE_URL)
                    .body(jsonString)
                    .when()
                    .delete("/api/v1/courier/" + loginId);
        }
}
