package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.order;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class orderClient {
    private static final String BASE_URL = "https://qa-scooter.education-services.ru/api/v1/orders";

    @Step("Создание заказа")
    public Response createOrder(order order) {
        return given()
                .contentType(JSON)
                .body(order)
                .when()
                .post(BASE_URL);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
                .when()
                .get(BASE_URL);
    }

    @Step("Получение track из ответа")
    public int getTrack(Response response) {
        return response.jsonPath().getInt("track");
    }
}