package tests;

import clients.orderClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import models.order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;

public class createOrderTest {
    private final orderClient client = new orderClient();

    private static Stream<Arguments> provideOrderColors() {
        return Stream.of(
                Arguments.of(Arrays.asList("BLACK")),
                Arguments.of(Arrays.asList("GREY")),
                Arguments.of(Arrays.asList("BLACK", "GREY")),
                Arguments.of(Collections.emptyList())
        );
    }

    @ParameterizedTest
    @MethodSource("provideOrderColors")
    @DisplayName("Создание заказа с разными цветами")
    @Description("Проверка создания заказа с указанием цветов BLACK, GREY, обоими и без цвета")
    public void createOrderWithColors(List<String> colors) {
        order order = createTestOrder(colors);

        Response response = client.createOrder(order);
        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что список заказов возвращается")
    public void getOrdersList() {
        Response response = client.getOrders();
        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
    }

    private order createTestOrder(List<String> colors) {
        return new order(
                "Тест",
                "Тестов",
                "ул. Тестовая, д. 1",
                "4",
                "+79001234567",
                5,
                "2026-12-31",
                colors,
                "Тестовый заказ"
        );
    }
}