package order;

import io.restassured.http.ContentType;
import org.junit.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ListOfOrdersTest {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/v1/orders?courierId=1";
    @Test
    public void testGetOrders(){
        String jsonString = "{\"id\": \"4\", \"courierId\": \"null\",\"firstName\": \"ваыпывп\", \"lastName\": \"ывпывп\", \"address\": \"пывпывп\", \"metroStation\": \"2\"}";
        var jsonMap = Map.of("id", "4", "courierId", "null", "firstName", "ваыпывп","lastName", "ывпывп", "address" ,"пывпывп", "metroStation", "2" );
        given()
                .contentType(ContentType.JSON)
                .baseUri(BAZE_URL)
                .body(jsonString)
                .when()
                .get(ORDER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok", String.valueOf(equalTo("orders")));
    }
}
