package order;

import io.restassured.response.Response;
import org.example.order.ListOfOrdersAPI;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class ListOfOrdersTest {
    ListOfOrdersAPI listOfOrdersAPI = new ListOfOrdersAPI();
    @Test
    public void testGetOrders(){
        Response response = listOfOrdersAPI. sendGetOrdersRequest();
        response .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok", String.valueOf(equalTo("orders")));
    }
}
