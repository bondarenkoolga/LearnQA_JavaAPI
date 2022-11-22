
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkWithCookies {

    @Test
    public void WorkWithCookies(){
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        response.prettyPrint();
        Map<String, String> cookies = response.getCookies();
        System.out.println(cookies);
        String cookie = response.getCookie("HomeWork");
        System.out.println(cookie);
        assertEquals("hw_value",cookie ,"Неверный куки");
    }

}