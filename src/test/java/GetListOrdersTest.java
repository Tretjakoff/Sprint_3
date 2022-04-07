import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;


public class GetListOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Return list of orders and check response")
    @Description("Get test for /api/v1/orders")
    public void getOrtedTest(){
        Response response = new Requests().getOrder();
        response.then().assertThat()
                .statusCode(200)
                .body("orders", Matchers.hasSize(Matchers.greaterThan(0)));

    }

}
