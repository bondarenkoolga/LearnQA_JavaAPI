import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ShortPhraseTest {

   public static  String myMethod(InputStream inputStream) {
            Scanner inputScanner = new Scanner(inputStream);
            String input = inputScanner.nextLine();
            return input;
    }

    public static void main() {
       /* Scanner scanner = new Scanner(System.in);
        System.out.print("Введите строку: ");
        String str = scanner.nextLine();
        assertTrue(str.length()>=15, "Строка короче 15 символов");*/
    }
    @Test
    public void phrase() {
        assertTrue( myMethod(new ByteArrayInputStream("Mock input\n".getBytes())).length()>=15,"Строка короче 15 символов");
    }
}

