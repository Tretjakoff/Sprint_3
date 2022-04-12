import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import model.Login;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthorizeCourierTest {

    Courier courier;
    Login login;
    Integer id;

    @Before
    public void setUp() {
        courier = new Courier(Utils.randomString(10), Utils.randomString(10), Utils.randomString(10));
        new Requests().createCourier(courier);
        login = new Login(courier.getLogin(), courier.getPassword());
    }

    @After
    public void cleanUp() {
        new Requests().deleteCourier(id);
    }

    @Test
    @DisplayName("Courier authorization and check response")
    @Description("Basic test for /api/v1/courier/login")
    public void authorizeCourierTest() {
        Response response = new Requests().authorizeCourier(login);
        id = response.then().extract().path("id");
        response.then().assertThat()
                .statusCode(200)
                .body("id", Matchers.notNullValue());
    }

}
