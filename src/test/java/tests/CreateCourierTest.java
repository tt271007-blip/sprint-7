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

public class CreateCourierTest {
    private final courierClient client = new courierClient();
    private int courierId;

    @AfterEach
    public void cleanUp() {
        if (courierId != 0) {
            client.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Проверка успешного создания курьера")
    public void createCourierSuccess() {
        courier courier = new courier(
                dataGenerator.generateRandomLogin(),
                dataGenerator.generateRandomPassword(),
                dataGenerator.generateRandomFirstName()
        );

        Response response = client.createCourier(courier);
        response.then()
                .statusCode(201)
                .body("ok", is(true));

        Response loginResponse = client.loginCourier(courier);
        courierId = client.getCourierId(loginResponse);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Проверка ошибки при создании дубликата курьера")
    public void createDuplicateCourier() {
        courier courier = new courier(
                dataGenerator.generateRandomLogin(),
                dataGenerator.generateRandomPassword(),
                dataGenerator.generateRandomFirstName()
        );

        // Создаем первого курьера
        Response firstResponse = client.createCourier(courier);
        firstResponse.then().statusCode(201);

        // Получаем ID для удаления
        Response loginResponse = client.loginCourier(courier);
        courierId = client.getCourierId(loginResponse);

        // Пытаемся создать второго такого же курьера
        Response secondResponse = client.createCourier(courier);
        secondResponse.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля")
    @Description("Проверка ошибки при создании курьера без пароля")
    public void createCourierWithoutPassword() {
        courier courier = new courier(
                dataGenerator.generateRandomLogin(),
                null,
                dataGenerator.generateRandomFirstName()
        );

        Response response = client.createCourier(courier);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    @Description("Проверка ошибки при создании курьера без логина")
    public void createCourierWithoutLogin() {
        courier courier = new courier(
                null,
                dataGenerator.generateRandomPassword(),
                dataGenerator.generateRandomFirstName()
        );

        Response response = client.createCourier(courier);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}