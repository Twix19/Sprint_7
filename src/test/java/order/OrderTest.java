package order;

import io.qameta.allure.Description;
import io.restassured.response.Response;
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
import io.qameta.allure.junit4.DisplayName;


@RunWith(Parameterized.class)
public class OrderTest {
    OrderAPI orderAPI = new OrderAPI();

    private String color;

    public OrderTest(String color) {
       this.color = color;
    }
    ObjectMapper mapper = new ObjectMapper();

    String tempOrderNumber;


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
    @DisplayName("Создание заказа")
    @Description("Отправляет данные о создании заказа на сервер")
    public void testCreateOrder() throws IOException {
        Order order = new Order("Naruto","Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", "5", "2020-06-06", "Saske, come back to Konoha", color = color);
        Response response = orderAPI.sendCreationDataOfOrder(order);
        String responseBody = response.getBody().asString();
        tempOrderNumber = responseBody.replaceAll("\\D+", "");
        response
                .then()
                .assertThat()
                .statusCode(SC_CREATED);
    }
    @After
    @DisplayName("Отмена заказа")
    public void deleteOrder() throws IOException  {
        Order order = new Order("Naruto","Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", "5", "2020-06-06", "Saske, come back to Konoha", color = color);
        orderAPI.sendCreationDataOfOrder(order);
        orderAPI.deleteOrder(order, tempOrderNumber)
                  .then()
                  .assertThat()
                  .statusCode(200)
                  .extract()
                  .path("ok");
    }
}


