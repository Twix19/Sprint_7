package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.example.courier.LoginCourier;
import org.example.courier.LoginCourierAPI;
import org.junit.Test;
import java.io.IOException;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTestCourier {
    private static final String BAZE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier/login";
    LoginCourier loginCourier = new LoginCourier("n123inja12", "1234", "saske");
    LoginCourierAPI loginCourierAPI = new LoginCourierAPI();
    ObjectMapper mapper = new ObjectMapper();
    @Test
    @DisplayName("Авторизация курьера в системе")
    @Description("Отправляет данные авторизации курьера на сервер")
    public  void testAvtorizationOfCourier() throws IOException {
        String value = mapper.writeValueAsString(loginCourier);
        Response response = loginCourierAPI.sendAvtorizationDates(value);
        response.then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id", String.valueOf(equalTo(" 224572")));
    }
    @Test
    @DisplayName("Запрос без логина или пароля")
    @Description("Отправляет данные авторизации курьера без логина или пароля на сервер")
    public void testAvtorizationWithMissingField(){
    Response response = loginCourierAPI.sendAvtorizationDates(String.valueOf(loginCourier.getClass()));
    response .then()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("error", String.valueOf(equalTo("Недостаточно данных для создания учетной записи")));
    }
    @Test
    @DisplayName("Запрос c несуществующим логином/паролем")
    @Description("Отправляет данные авторизации курьера c несуществующим логином/паролем на сервер")
    public void testAvtorizationWithWrongField() {
       Response response = loginCourierAPI.sendAvtorizationWrongData();
       response  .then()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("error", String.valueOf(equalTo("Учетная запись не найдена")));
    }
}
