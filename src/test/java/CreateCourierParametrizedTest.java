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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;


@RunWith(Parameterized.class)
public class CreateCourierParametrizedTest {

    private final Courier courier;
    Integer id = null;

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


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @After
    public void cleanUp() {
        Login login = new Login(courier.getLogin(), courier.getPassword());
        Response response = new Requests().authorizeCourier(login);
        id = response.then().extract().path("id");
        given().when()
                .delete("/api/v1/courier/" + id);
    }

    @Test
    @DisplayName("Create Courier without one of the values")
    @Description("Parameterized test for /api/v1/courier without one of the values")
    public void createCourierTest() {

        Response response = new Requests().createCourier(courier);
        response.then().assertThat()
                .statusCode(400)
                .body("message", Matchers.is("Ќедостаточно данных дл€ создани€ учетной записи"));

    }


}
