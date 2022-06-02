package book_tests;

import client.Client;
import client.SocketData;
import common.Common;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

public class BookSubscriptionInvalidCurrencyPairTests extends Common {

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
        Client.connectAndListen(message, socketData, 10);
        messageWithError = new JsonPath(socketData.getMessageList().get(1));
    }

    @Test
    public void errorMessageShouldBeReceived() {
        Assertions.assertEquals("Currency pair not supported " + invalidCurrencyPair, messageWithError.getString(errorMessageJsonPathLocator));
    }

    @Test
    public void statusShouldBeError() {
        Assertions.assertEquals(errorStatusString, messageWithError.getString(statusMessageJsonPathLocator));
    }

}