package clients;


public class OrderClient {
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