import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import model.Login;
import model.Order;

import static io.restassured.RestAssured.given;

public class Requests extends RestAssuredClient{

    @Step("Send POST request to /api/v1/courier")
    public Response createCourier(Courier courier){
        return given()
                .spec(getBaseSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response authorizeCourier(Login login){
        return given()
                .spec(getBaseSpec())
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Send POST request to /api/v1/orders")
    public Response createOrder(Order order){
        return given()
                .spec(getBaseSpec())
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Send GET request to /api/v1/orders")
    public Response getOrder(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/api/v1/orders");
    }

    @Step("Send DELETE request to /api/v1/courier/:id")
    public Response deleteCourier(Integer id){
        return given()
                .spec(getBaseSpec())
                .when()
                .delete("/api/v1/courier/"+id);
    }

}
