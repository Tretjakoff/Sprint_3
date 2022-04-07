import io.qameta.allure.Step;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import model.Courier;
import model.Login;
import model.Order;

import static io.restassured.RestAssured.given;

public class Requests {
    @Step("Send POST request to /api/v1/courier")
    public Response createCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response authorizeCourier(Login login){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Send POST request to /api/v1/orders")
    public Response createOrder(Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Send GET request to /api/v1/orders")
    public Response getOrder(){
        return given()
                .when()
                .get("/api/v1/orders");
    }


}
