package tests;

import clients.orderClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class GetOrdersTest {
    private final orderClient client = new orderClient();

    @Test
    @DisplayName("Список заказов содержит данные")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void getOrdersListNotEmpty() {
        Response response = client.getOrders();
        response.then()
                .statusCode(200)
                .body("orders", is(not(empty())))
                .body("orders[0].id", notNullValue())
                .body("orders[0].status", notNullValue());
    }
}