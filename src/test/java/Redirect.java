import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.xml.soap.SAAJResult;
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