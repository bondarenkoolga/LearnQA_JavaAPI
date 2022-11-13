import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class Tokens {

    @Test
    public void showHeaders() throws InterruptedException{
        JsonPath responseForToken = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
               .jsonPath();
       // responseForToken.prettyPrint();
        String token = responseForToken.get("token");
        System.out.println("Токен - "+token);
        int seconds = responseForToken.get("seconds");
        System.out.println("Количество секунд - "+seconds);

        JsonPath responseBeforeTask = RestAssured
                .given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
       // responseBeforeTask.prettyPrint();
        String status1 = responseBeforeTask.get("status");
        System.out.println("Статус до создания задачи - "+status1);

        Thread.sleep(seconds*1000);
        JsonPath responseAfterTask = RestAssured
                .given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
       // responseAfterTask.prettyPrint();
        String status2 = responseAfterTask.get("status");
        String result = responseAfterTask.get("result");
        System.out.println("Статус после создания задачи - "+status2);
        if (result!=null) {
            System.out.println("Результат - " + result);
        }
    }

}