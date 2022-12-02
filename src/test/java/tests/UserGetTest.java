package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Story("Work with user")
@Epic("Get cases")
@Feature("Get information")
@Owner(value = "Me")
public class UserGetTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("Try to get data without authorization")
    @DisplayName("Get data without authorization")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testGetUserDataNotAuth(){
        Response responseUserData= RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        Assertions.assertJsonHasField(responseUserData,"username");
        Assertions.assertJsonHasNotField(responseUserData,"firstName");
        Assertions.assertJsonHasNotField(responseUserData,"lastName");
        Assertions.assertJsonHasNotField(responseUserData,"email");
    }

    @Test
    @Description("Get data about myself")
    @DisplayName("Positive case for get data")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testGetUserDetailsAuthAsSameUser() {
        Map<String,String> authData=new HashMap<>();
        authData.put("email","vinkotov@example.com");
        authData.put("password","1234");
        Response responseGetAuth=RestAssured
        .given()
        .body(authData)
        .post("https://playground.learnqa.ru/api/user/login")
        .andReturn();

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData=RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid",cookie)
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();
        String[] expectedFields = {"username","firstName", "lastName", "email" };
        Assertions.assertJsonHasFields(responseUserData,expectedFields);
    }

//Тест, который авторизовывается одним пользователем, но получает данные другого
    @Test
    @Description("Try to get data of another user")
    @DisplayName("Get data of another user")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testGetUserDetailsAuthAsAnotherUser() {
        Map<String,String> authData=new HashMap<>();
        authData.put("email","vinkotov@example.com");
        authData.put("password","1234");
         Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/3",
                                                this.getHeader(responseGetAuth, "x-csrf-token"),
                                                this.getCookie(responseGetAuth, "auth_sid"));
        Assertions.assertResponseCodeEquals(responseUserData, 404);
        Assertions.assertResponseTextEquals(responseUserData, "User not found");
    }
}
