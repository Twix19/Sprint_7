package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.ListOfOrdersAPI;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class ListOfOrdersTest {
    ListOfOrdersAPI listOfOrdersAPI = new ListOfOrdersAPI();
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Отправляет GET-запрос на получение списка заказов на указанный адрес")
    public void testGetOrders(){
        Response response = listOfOrdersAPI. sendGetOrdersRequest();
        response .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok", String.valueOf(equalTo("orders")));
    }
}
