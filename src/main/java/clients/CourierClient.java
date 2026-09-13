package clients;


public class CourierClient {
    private static final String BASE_URL = "https://qa-scooter.education-services.ru/api/v1/courier";

    @Step("Создание курьера")
    public Response createCourier(courier courier) {
        return given()
                .contentType(JSON)
                .body(courier)
                .when()
                .post(BASE_URL);
    }

    @Step("Логин курьера")
    public Response loginCourier(courier courier) {
        return given()
                .contentType(JSON)
                .body(courier)
                .when()
                .post(BASE_URL + "/login");
    }

    @Step("Удаление курьера по ID")
    public Response deleteCourier(int id) {
        return given()
                .when()
                .delete(BASE_URL + "/" + id);
    }

    @Step("Получение ID курьера из ответа")
    public int getCourierId(Response response) {
        return response.jsonPath().getInt("id");
    }
}