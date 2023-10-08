package order;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/api/v1/orders";

    private String color;

    public OrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {{"BLACK"}, {"GREY"}};
        return Arrays.asList(data);
    }
    @Test
    public void testCreateOrderWithoutColor() {
        String jsonString = "{\"firstName\": \"Naruto\", \"lastName\": \"Uchiha\", \"address\": \"Konoha, 142 apt.\", \"metroStation\": \"4\", \"phone\": \"+7 800 355 35 35\", \"rentTime\": \"5\", \"deliveryDate\": \"2020-06-06\", \"comment\": \"Saske, come back to Konoha\"}";
        given()
                .contentType(ContentType.JSON)
                .body(jsonString)
                .when()
                .post(BAZE_URL + ORDER_PATH)
                .then()
                .statusCode(201)
                .extract()
                .path("track",String.valueOf(equalTo("124124")));
    }
    @Test
    public void createOrderWithOneColor(){ //500
        String jsonString = "{\"firstName\": \"Naruto\", \"lastName\": \"Uchiha\", \"address\": \"Konoha, 142 apt.\", \"metroStation\": \"4\", \"phone\": \"+7 800 355 35 35\", \"rentTime\": \"5\", \"deliveryDate\": \"2020-06-06\", \"comment\": \"Saske, come back to Konoha\", \"color\": [ \"BLACK\" ]}";
        var jsonMap = Map.of("firstName", "Naruto", "lastName", "Uchiha", "address", "Konoha, 142 apt", "metroStation","4", "phone", "+7 800 355 35 35", "rentTime", "5", "deliveryDate", "2020-06-06", "comment", "Saske, come back to Konoh", "color", "BLACK");
       given()
                .contentType(ContentType.JSON)
                .baseUri(BAZE_URL)
                .body(jsonString)
                .when()
                .post(ORDER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }
    @Test
    public void testCreateOrderWithBothColor() {
        String jsonString = "{\"firstName\": \"Naruto\", \"lastName\": \"Uchiha\", \"address\": \"Konoha, 142 apt.\", \"metroStation\": \"4\", \"phone\": \"+7 800 355 35 35\", \"rentTime\": \"5\", \"deliveryDate\": \"2020-06-06\", \"comment\": \"Saske, come back to Konoha\", \"color\": [ \"BLACK\" ], \"color2\": [ \"GREY\" ]}";
        given()
                .contentType(ContentType.JSON)
                .body(jsonString)
                .when()
                .post(BAZE_URL + ORDER_PATH)
                .then()
                .statusCode(201)
                .extract()
                .path("track",String.valueOf(equalTo("124124")));
    }
    @Test
    public void deleteOrder() {
        String jsonString = "{\"firstName\": \"Naruto\", \"lastName\": \"Uchiha\", \"address\": \"Konoha, 142 apt.\", \"metroStation\": \"4\", \"phone\": \"+7 800 355 35 35\", \"rentTime\": \"5\", \"deliveryDate\": \"2020-06-06\", \"comment\": \"Saske, come back to Konoha\", \"color\": [ \"BLACK\" ], \"color2\": [ \"GREY\" ]}";
        given()
                .contentType(ContentType.JSON)
                .body(jsonString)
                .when()
                .put(BAZE_URL + "/api/v1/orders/cancel")
                .then()
                .statusCode(201)
                .extract()
                .path("ok");
    }
}
