package book_tests;

import client.Client;
import client.SocketData;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

public class BookSubscriptionPositiveTests {

    private static final String currencyPair = "XBT/EUR";
    private static final String bookDepth = "10";
    private static final String message = "{\n" +
            "  \"event\": \"subscribe\",\n" +
            "\"pair\": [\n" +
            "    \"" + currencyPair + "\"\n" +
            "  ]," +
            "  \"subscription\": {\n" +
            "  \"depth\": " + bookDepth + "," +
            "    \"name\": \"book\"\n" +
            "  }\n" +
            "}";

    static JsonPath messageWithSubscriptionDetails;

    private static final SocketData socketData = new SocketData();

    @BeforeAll
    public static void receiveData() throws ExecutionException, InterruptedException {
        Client.connectAndListen(message, socketData, 5);
        messageWithSubscriptionDetails = new JsonPath(socketData.getMessageList().get(1));
    }

    @Test
    public void channelIdShouldBeReceived() {
        int channelId = messageWithSubscriptionDetails.getInt("channelID");
        Assertions.assertTrue(channelId != 0, "ChannelId is not received");
    }

    @Test
    public void channelNameShouldBeReceived() {
        Assertions.assertEquals("book-" + bookDepth, messageWithSubscriptionDetails.getString("channelName"));
    }

    @Test
    public void correctCurrencyPairShouldBeReceived() {
        Assertions.assertEquals(currencyPair, messageWithSubscriptionDetails.getString("pair"));
    }

    @Test
    public void subscriptionNameShouldBeCorrect() {
        Assertions.assertEquals("book", messageWithSubscriptionDetails.getString("subscription.name"));
    }

}