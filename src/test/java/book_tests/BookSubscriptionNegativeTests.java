package book_tests;

import client.Client;
import client.SocketData;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

public class BookSubscriptionNegativeTests {

    private static final String invalidCurrencyPair = "XBT/EURopa";
    private static final String message  = "{\n" +
            "  \"event\": \"subscribe\",\n" +
            "\"pair\": [\n" +
            "    \"" + invalidCurrencyPair + "\"\n" +
            "  ]," +
            "  \"subscription\": {\n" +
            "    \"name\": \"book\"\n" +
            "  }\n" +
            "}";

    static JsonPath messageWithError;

    private static final SocketData socketData = new SocketData();

    @BeforeAll
    public static void receiveData() throws ExecutionException, InterruptedException {
        Client.connectAndListen(message, socketData, 30);
        messageWithError = new JsonPath(socketData.getMessageList().get(1));
    }

    @Test
    public void errorMessageShouldBeReceived() {
        Assertions.assertEquals("Currency pair not supported " + invalidCurrencyPair, messageWithError.getString("errorMessage"));
    }

    @Test
    public void statusShouldBeError() {
        Assertions.assertEquals("error", messageWithError.getString("status"));
    }

}

//check what can be automated
//10 tests :)