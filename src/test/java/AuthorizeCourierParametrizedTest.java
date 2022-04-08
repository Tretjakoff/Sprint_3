import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import model.Login;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AuthorizeCourierParametrizedTest {

    private final String nick;
    private final String password;
    private final String message;
    private final Integer statusCode;
    private static Courier courier = new Courier(Utils.randomString(10), Utils.randomString(10), Utils.randomString(10));

    static Integer id;

    @Parameterized.Parameters
    public static Object[][] getNewCourierData() {
        return new Object[][]{
                {null, courier.getPassword(), "Недостаточно данных для входа", 400},
                {courier.getLogin(), null, "Недостаточно данных для входа", 400},
                {courier.getLogin(), "qwerty", "Учетная запись не найдена", 404},
                {"qwerty", courier.getPassword(), "Учетная запись не найдена", 404},
                {"qwerty", "asdfgh", "Учетная запись не найдена", 404},
        };
    }
    public AuthorizeCourierParametrizedTest(String nick, String password, String message, Integer statusCode) {
        this.nick = nick;
        this.password = password;
        this.message = message;
        this.statusCode = statusCode;
    }

    @BeforeClass
    public static void setUp() {
        new Requests().createCourier(courier);
    }

    @AfterClass
    public static void cleanUp() {
        Login login = new Login(courier.getLogin(), courier.getPassword());
        Response response = new Requests().authorizeCourier(login);
        id = response.then().extract().path("id");
        new Requests().deleteCourier(id);
    }



    @Test
    @DisplayName("Courier authorization and check response")
    @Description("Test for /api/v1/courier/login")
    public void authWithoutLoginTest() {
        Login login = new Login(nick, password);
        Response response = new Requests().authorizeCourier(login);
        response.then().assertThat()
                .statusCode(statusCode)
                .body("message", Matchers.is(message));
    }

}