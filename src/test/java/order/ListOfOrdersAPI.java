package order;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.order.ListOfOrders;

import static io.restassured.RestAssured.given;

public class ListOfOrdersAPI {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/v1/orders?courierId=1";
    ListOfOrders listOfOrders = new ListOfOrders("4", "null", "ваыпывп", "ывпывп", "пывпывп", "2", "423424234432", "4", "2020-06-21T21:00:00.000Z", "400443");

    public Response testGetOrders() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BAZE_URL)
                .body(listOfOrders)
                .when()
                .get(BAZE_URL + ORDER_PATH);
    }
}