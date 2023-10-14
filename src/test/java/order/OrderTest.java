package order;

import org.codehaus.jackson.map.ObjectMapper;
import org.example.order.Order;
import org.example.order.OrderAPI;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;


@RunWith(Parameterized.class)
public class OrderTest {
    OrderAPI orderAPI = new OrderAPI();

    private String color;

    public OrderTest(String color) {
       this.color = color;
    }
    ObjectMapper mapper = new ObjectMapper();


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"[\"BLACK\","+" \"GREY\"]"},
                {"[\"BLACK\"]"},
                {"[\"GREY\"]"},
                {"[\"\"]"}
        };
        return Arrays.asList(data);
    }

    @Test
    public void testCreateOrder() throws IOException {
        Order order = new Order("Naruto","Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", "5", "2020-06-06", "Saske, come back to Konoha", color = color);
        orderAPI.sendCreationDataOfOrder(order)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .path("track", String.valueOf(equalTo("124124")));
    }
    @After //эндпоинт не работает
    public void deleteOrder() throws IOException  {
        Order order = new Order("Naruto","Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", "5", "2020-06-06", "Saske, come back to Konoha", color = color);
          orderAPI.deleteOrder(String.valueOf(order))
                  .then()
                  .assertThat()
                  .statusCode(200)
                  .extract()
                  .path("ok");
    }
}


