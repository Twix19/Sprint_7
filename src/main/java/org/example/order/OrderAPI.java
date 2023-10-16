package org.example.order;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class OrderAPI {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String ORDER_CANCEL_URL = "https://qa-scooter.praktikum-services.ru/api/v1/orders/cancel";

    ObjectMapper mapper = new ObjectMapper();

    public String regexEditJson(String json){
        json = json.replaceAll("\"\\[\\\\", "[");
        json = json.replaceAll("\\\\\"\\]\"", "\"]");
        json = json.replaceAll("\\\\","");
        json = json.replaceAll("\"\"","");
        return json;
    }

    public Response sendCreationDataOfOrder(Object object) throws IOException {
        String objAsString = mapper.writeValueAsString(object);
        String json = regexEditJson(objAsString);
        return RestAssured.given()
                .contentType(io.restassured.http.ContentType.JSON)
                .body(json)
                .when()
                .post(BAZE_URL + ORDER_PATH);
    }
    public Response deleteOrder(Object object, String orderTrack) throws IOException  {
        String objAsString = mapper.writeValueAsString(object);
        String json = regexEditJson(objAsString);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .put(ORDER_CANCEL_URL + "?track=" + orderTrack);
    }
}
