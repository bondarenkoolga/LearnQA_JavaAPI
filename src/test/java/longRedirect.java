import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class longRedirect {

    @Test
    public void showHeaders(){
        int i =0;
        int status = 0;
        String locationHeaders = "https://playground.learnqa.ru/api/long_redirect";
while (status!=200) {
    Response response = RestAssured
            .given()
            .redirects()
            .follow(false)
            .get(locationHeaders)
            .andReturn();
    status = response.getStatusCode();
    locationHeaders = response.getHeader("location");
    i++;
    System.out.println(locationHeaders);
    System.out.println(status);
    System.out.println(i);
}
        System.out.println("Количество редиректов -  "+ i);
    }

}