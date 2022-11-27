package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    public void testCreateUserWithExistingEmail(){
        String email="vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData=DataGenerator.getRegistrationData(userData);
        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email +"' already exists");
    }

    @Test
    public void testCreateUserSuccessfully(){
        String email= DataGenerator.getRandomEmail();

        Map<String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth,"id");
    }

    //Создание пользователя с некорректным email - без символа @
    @Test
    public void testCreateUserWithIncorrectEmail(){
        String email= DataGenerator.getRandomEmail();
        email = email.replace("@","");
        System.out.println(email);
        Map <String,String> authData= new HashMap<>();
        authData.put("email", email);
        Map<String, String> userData = DataGenerator.getRegistrationData(authData);
        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }

    //Создание пользователя без указания одного из полей
    @ParameterizedTest
    @ValueSource(strings = {"username", "firstName", "lastName","email", "password"})
    public void testCreateUserWithoutOneField(String attribs){
        Map<String, String> userData = DataGenerator.getRegistrationData();
        userData.remove(attribs);
        System.out.println(userData);
        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        System.out.println(responseCreateAuth.asString());
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The following required params are missed: " + attribs);
    }


    //Создание пользователя с очень коротким именем в один символ
    @ParameterizedTest
    @ValueSource(strings = {"username", "firstName", "lastName"})
    public void testCreateUserWithShortName(String attribs){
        Map <String,String> authData= new HashMap<>();
        String name = RandomStringUtils.randomAlphabetic(1);
        authData.put(attribs, name);
        Map<String, String> userData = DataGenerator.getRegistrationData(authData);
        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of '" + attribs +"' field is too short");
    }

    //Создание пользователя с очень длинным именем - длиннее 250 символов
    @ParameterizedTest
    @ValueSource(strings = {"username", "firstName", "lastName"})
    public void testCreateUserWithLongName(String attribs){
        Map <String,String> authData= new HashMap<>();
        String name = RandomStringUtils.randomAlphabetic(251);
        authData.put(attribs, name);
        Map<String, String> userData = DataGenerator.getRegistrationData(authData);
        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of '" + attribs +"' field is too long");
    }
}
