import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;


public class Redirect {

    @Test
    public void showHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "myValue1");
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