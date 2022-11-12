import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;


public class Redirect {

    @Test
    public void showHeaders(){
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .get("https://playground.learnqa.ru/api/long_redirect")
               .andReturn();

        String locationHeaders = response.getHeader("location");
        System.out.println(locationHeaders);
    }

}