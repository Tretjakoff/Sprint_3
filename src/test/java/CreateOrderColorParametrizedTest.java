import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Order;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;


@RunWith(Parameterized.class)
public class CreateOrderColorParametrizedTest {

    private final Order order = new Order(
            "����",
            "������",
            "������",
            "5",
            "79529976810",
            3,
            "2022-04-12T21:00:00.000Z",
            "",
            List.of()
    );

    private final List<String> colors;

    @Parameterized.Parameters
    public static Object[][] getNewOrderData() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("BLACK", "GRAY")},
                {List.of("GRAY")},
                {List.of()},
                null
        };
    }

    public CreateOrderColorParametrizedTest(List<String> colors) {
        this.colors = colors;
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @After
    public void cleanUp() {

    }

    @Test
    @DisplayName("Create order and check response")
    @Description("Basic test for /api/v1/orders")
    public void createOrderBlackColor() {
        order.setColor(colors);
        Response response = new Requests().createOrder(order);
        response.then().assertThat()
                .statusCode(201)
                .body("track", Matchers.notNullValue());
    }


}
