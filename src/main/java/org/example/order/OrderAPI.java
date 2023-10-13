package org.example.order;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class OrderAPI {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/api/v1/orders";

   private String color;

   public OrderAPI(String color) {
        this.color = color;
    }
    public Response testCreateOrder(String jsonString) {
       return RestAssured.given()
                .contentType(io.restassured.http.ContentType.JSON)
                .body(jsonString)
                .when()
                .post(BAZE_URL + ORDER_PATH);
    }
}
