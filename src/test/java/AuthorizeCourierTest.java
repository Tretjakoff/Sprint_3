import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import model.Login;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AuthorizeCourierTest {

    Courier courier;
    Integer id = null;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        courier = new Courier(Utils.randomString(10), Utils.randomString(10), Utils.randomString(10));
        new Requests().createCourier(courier);
    }

    @After
    public void cleanUp() {
        given().when()
                .delete("/api/v1/courier/" + id);
    }

    @Test
    @DisplayName("Courier authorization and check response")
    @Description("Basic test for /api/v1/courier/login")
    public void authorizeCourierTest() {
        Login login = new Login(courier.getLogin(), courier.getPassword());
        Response response = new Requests().authorizeCourier(login);
        id = response.then().extract().path("id");
        response.then().assertThat()
                .statusCode(200)
                .body("id", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Courier authorization without Login and check response")
    @Description("Test for /api/v1/courier/login without Login")
    public void authWithoutLoginTest() {
        Login login = new Login(null, courier.getPassword());
        Response response = new Requests().authorizeCourier(login);
        response.then().assertThat()
                .statusCode(400)
                .body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Courier authorization without Password and check response")
    @Description("Test for /api/v1/courier/login without Password")
    public void authWithoutPasswordTest() {
        Login login = new Login(courier.getLogin(), null);
        Response response = new Requests().authorizeCourier(login);
        response.then().assertThat()
                .statusCode(400)
                .body("message", Matchers.is("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Courier authorization with incorrect Password and check response")
    @Description("Test for /api/v1/courier/login with incorrect Password")
    public void authWithIncorrectPasswordTest() {
        Login login = new Login(courier.getLogin(), "qwerty");
        Response response = new Requests().authorizeCourier(login);
        response.then().assertThat()
                .statusCode(404)
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Courier authorization with incorrect Login and check response")
    @Description("Test for /api/v1/courier/login with incorrect Login")
    public void authWithIncorrectLoginTest() {
        Login login = new Login("qwerty", courier.getPassword());
        Response response = new Requests().authorizeCourier(login);
        response.then().assertThat()
                .statusCode(404)
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Courier authorization with incorrect Login and Password")
    @Description("Test for /api/v1/courier/login with incorrect Login and Password")
    public void authWithIncorrectLoginAndPasswordTest() {
        Login login = new Login("qwerty", "asdfgh");
        Response response = new Requests().authorizeCourier(login);
        response.then().assertThat()
                .statusCode(404)
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

}
