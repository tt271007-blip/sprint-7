package tests;

import clients.courierClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.courier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.dataGenerator;


import static org.hamcrest.Matchers.*;

public class loginCourierTest {
    private final courierClient client = new courierClient();
    private int courierId;

    @AfterEach
    public void cleanUp() {
        if (courierId != 0) {
            client.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверка успешной авторизации курьера")
    public void loginCourierSuccess() {
        courier courier = new courier(
                dataGenerator.generateRandomLogin(),
                dataGenerator.generateRandomPassword(),
                dataGenerator.generateRandomFirstName()
        );

        client.createCourier(courier);

        Response response = client.loginCourier(courier);
        courierId = client.getCourierId(response);

        response.then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неверным паролем")
    @Description("Проверка ошибки при авторизации с неверным паролем")
    public void loginCourierWrongPassword() {
        courier courier = new courier(
                dataGenerator.generateRandomLogin(),
                dataGenerator.generateRandomPassword(),
                dataGenerator.generateRandomFirstName()
        );

        client.createCourier(courier);

        courier wrongCourier = new courier(courier.getLogin(), "wrong_password");
        Response response = client.loginCourier(wrongCourier);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться без пароля")
    @Description("Проверка ошибки при авторизации без пароля")
    public void loginCourierWithoutPassword() {
        courier courier = new courier(
                dataGenerator.generateRandomLogin(),
                null
        );

        Response response = client.loginCourier(courier);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться с несуществующим пользователем")
    @Description("Проверка ошибки при авторизации с несуществующим логином")
    public void loginCourierNonExistent() {
        courier courier = new courier(
                "nonexistent_" + dataGenerator.generateRandomLogin(),
                dataGenerator.generateRandomPassword()
        );

        Response response = client.loginCourier(courier);
        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}