import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import model.Login;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreateCourierParametrizedTest {

    private final Courier courier;
    Integer id;

    @Parameterized.Parameters
    public static Object[][] getNewCourierData() {
        return new Object[][]{
                {new Courier(Utils.randomString(10), Utils.randomString(10), null)},
                {new Courier(Utils.randomString(10), null, Utils.randomString(10))},
                {new Courier(null, Utils.randomString(10), Utils.randomString(10))},
                {new Courier(Utils.randomString(10), Utils.randomString(10), "")},
                {new Courier(Utils.randomString(10), "", Utils.randomString(10))},
                {new Courier("", Utils.randomString(10), Utils.randomString(10))},
        };
    }

    public CreateCourierParametrizedTest(Courier courier) {
        this.courier = courier;
    }

    @After
    public void cleanUp() {
        Login login = new Login(courier.getLogin(), courier.getPassword());
        Response response = new Requests().authorizeCourier(login);
        id = response.then().extract().path("id");
        new Requests().deleteCourier(id);
    }

    @Test
    @DisplayName("Create Courier without one of the values")
    @Description("Parameterized test for /api/v1/courier without one of the values")
    public void createCourierTest() {

        Response response = new Requests().createCourier(courier);
        response.then().assertThat()
                .statusCode(400)
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));

    }


}
