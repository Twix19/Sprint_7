package courier;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.courier.CourierAPI;
import org.junit.After;
import org.junit.Test;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;


public class CourierTest {
    CourierAPI courierAPI = new CourierAPI();

    @Test
    @DisplayName("Создание курьера")
    @Description("Метод отправляет данные курьера на сервер. В случае успешного выполнения запроса будет получен ответ со статусом 200.")
    public void createCourier(){
        courierAPI.sendCreationDataOfCourier()
                .then().log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok", String.valueOf(equalTo("true")));
    }
    @Test
    @DisplayName("Запрос с повторяющимся логином")
    @Description("Метод отправляет данные курьера на сервер. В запросе указаны параметры контента c повторяющимся логином, базовый URL и тело запроса.")
    public void testCreateDuplicateCourier() {
        courierAPI.sendCreationDataOfCourier();
        courierAPI.sendCreationDataOfCourier()
                .then()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("error", String.valueOf(equalTo("Этот логин уже используется")));

    }

    @Test
    @DisplayName("Запрос без логина или пароля")
    @Description("Метод отправляет данные курьера на сервер. В запросе указаны параметры контента без логина или пароля, базовый URL и тело запроса.")
    public void testCreateCourierWithExistingLogin() {
        courierAPI.sendCourierCreationRequest();
        courierAPI.sendCourierCreationRequest()
                .then()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("error", String.valueOf(equalTo("Учетная запись не найдена")));
    }
     @After
     @DisplayName("Удаление курьера")
     @Description("Метод отправляет запрос на удаление курьера. Для этого используется авторизация через API.")
     public void deleteCourier() throws IOException {
            courierAPI.deleteCourier()
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .path("ok");
        }
}