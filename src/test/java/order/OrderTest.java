package order;

import org.example.order.OrderAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;


@RunWith(Parameterized.class)
public class OrderTest {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/api/v1/orders";
    OrderAPI orderAPI = new OrderAPI("");

    private String color;

    public OrderTest(String color) {
       this.color = color;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"\"BLACK\", \"GREY\""},
                {"\"BLACK\""},
                {"\"GREY\""},
                {""}
        };
        return Arrays.asList(data);
    }

    @Test
    public void testCreateOrder() {
        String jsonString = "{\"firstName\": \"Naruto\", \"lastName\": \"Uchiha\", \"address\": \"Konoha, 142 apt.\", \"metroStation\": \"4\", \"phone\": \"+7 800 355 35 35\", \"rentTime\": \"5\", \"deliveryDate\": \"2020-06-06\", \"comment\": \"Saske, come back to Konoha\", \"color\":[" + color + "]}";
        orderAPI.testCreateOrder(jsonString)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .path("track", String.valueOf(equalTo("124124")));
    }

   // @After
  // public void deleteOrder() {
    //   String jsonString = "{\"firstName\": \"Naruto\", \"lastName\": \"Uchiha\", \"address\": \"Konoha, 142 apt.\", \"metroStation\": \"4\", \"phone\": \"+7 800 355 35 35\", \"rentTime\": \"5\", \"deliveryDate\": \"2020-06-06\", \"comment\": \"Saske, come back to Konoha\", \"color\":[" + color + "]}";
      //  given()
        //        .contentType(ContentType.JSON)
          //      .body(jsonString )
            //    .when()
              //  .put(BAZE_URL + "/api/v1/orders/cancel")
              //  .then()
              //  .statusCode(SC_CREATED)
              //  .extract()
              //  .path("ok");
    //}
}


