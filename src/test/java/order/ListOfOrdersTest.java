package order;

import io.restassured.response.Response;
import org.example.order.ListOfOrders;
import org.example.order.ListOfOrdersAPI;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ListOfOrdersTest {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/v1/orders?courierId=1";
    ListOfOrders listOfOrders = new ListOfOrders("4","null","ваыпывп","ывпывп","пывпывп","2", "423424234432", "4", "2020-06-21T21:00:00.000Z", "400443");
    ListOfOrdersAPI listOfOrdersAPI = new ListOfOrdersAPI();
    @Test
    public void testGetOrders(){
        Response response = listOfOrdersAPI.testGetOrders();
        response .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok", String.valueOf(equalTo("orders")));
    }
}
