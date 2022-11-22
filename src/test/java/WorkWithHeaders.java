import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkWithHeaders {

    @Test
    public void WorkWithCookies(){
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers headers = response.getHeaders();
        String secretHeader = response.getHeader("x-secret-homework-header");
        System.out.println(secretHeader);
        assertEquals("Some secret value",secretHeader ,"Неверный header");
    }

}