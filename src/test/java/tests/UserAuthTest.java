package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import lib.Assertions;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import lib.ApiCoreRequests;

@Story("Work with user")
@Epic("Authorization cases")
@Feature("Authorization")
public class UserAuthTest extends BaseTestCase {

    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @BeforeEach
    public void loginUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth,"user_id");
                responseGetAuth.jsonPath().getInt("user_id");
    }

    @Test
    @Description("Successful way")
    @DisplayName("Test positive auth user")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testAuthUser() {
        Response responseCheckAuth = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/auth", header, cookie);
    Assertions.assertJsonByName(responseCheckAuth,"user_id",this.userIdOnAuth);

    }

    @Description("Test without cookie or header")
    @DisplayName("Test negative auth user")
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    @Severity(value = SeverityLevel.CRITICAL)
    public void testNegativeAuthUser(String condition){
        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if(condition.equals("cookie")) {
            Response responseForCheck = apiCoreRequests.makeGetRequestWithCookie("https://playground.learnqa.ru/api/user/auth", this.cookie);
        Assertions.assertJsonByName(responseForCheck, "user_id",0);
        }else if (condition.equals("headers")) {
            Response responseForCheck = apiCoreRequests.makeGetRequestWithToken("https://playground.learnqa.ru/api/user/auth", this.header);
            Assertions.assertJsonByName(responseForCheck, "user_id",0);
        }else {
            throw new IllegalArgumentException("Condition value is know "+condition);
        }

    }

}
