import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonParcing {

    @Test
    public void testRestAssured(){
        JsonPath response = RestAssured
               .get("https://playground.learnqa.ru/api/get_json_homework")
               .jsonPath();
        String answer = response.get("messages[1].message");
        System.out.println(answer);
    }

}