package order;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;

public class OrderAPI {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/api/v1/orders";

    private String color;

    public OrderAPI(String color) {
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


    public Response testCreateOrder(String jsonString) {
       return given()
                .contentType(ContentType.JSON)
                .body(jsonString)
                .when()
                .post(BAZE_URL + ORDER_PATH);
    }
}
