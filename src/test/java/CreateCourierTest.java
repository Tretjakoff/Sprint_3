import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import model.Login;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class CreateCourierTest {

    Courier courier;
    Integer id;


    @After
    public void cleanUp() {
        Login login = new Login(courier.getLogin(), courier.getPassword());
        Response response = new Requests().authorizeCourier(login);
        id = response.then().extract().path("id");
        new Requests().deleteCourier(id);
    }

    @Test
    @DisplayName("Create courier and check response")
    @Description("Basic test for /api/v1/courier")
    public void createCourierTest() {
        courier = new Courier(Utils.randomString(10), Utils.randomString(10), Utils.randomString(10));

        Response response = new Requests().createCourier(courier);
        response.then().assertThat()
                .statusCode(201);

        Assert.assertTrue(response.jsonPath().get("ok"));
    }

    @Test
    @DisplayName("Create duplicate courier and check response")
    @Description("Test for /api/v1/courier with duplicate courier")
    public void createDuplicateCourierTest() {
        courier = new Courier(Utils.randomString(10), Utils.randomString(10), Utils.randomString(10));

        new Requests().createCourier(courier);
        Response responseTwo = new Requests().createCourier(courier);
        responseTwo.then().assertThat()
                .statusCode(409)
                .body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."));

    }

}
