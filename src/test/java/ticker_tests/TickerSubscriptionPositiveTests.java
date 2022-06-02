package ticker_tests;

import client.Client;
import client.SocketData;
import common.Common;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tests_utils.TestsUtils;

import java.util.concurrent.ExecutionException;

public class TickerSubscriptionPositiveTests extends Common {

    private static final String currencyPair = "XBT/USD";
    private static final String message = "{\n" +
            "  \"event\": \"subscribe\",\n" +
            "\"pair\": [\n" +
            "    \"" + currencyPair + "\"\n" +
            "  ]," +
            "  \"subscription\": {\n" +
            "    \"name\": \"ticker\"\n" +
            "  }\n" +
            "}";

    private static JsonPath messageWithSubscriptionDetails;
    private static JsonPath messageWithData;
    private static int channelId;

    private static final SocketData socketData = new SocketData();

    @BeforeAll
    public static void receiveData() throws ExecutionException, InterruptedException {
        Client.connectAndListen(message, socketData, 2);
        messageWithSubscriptionDetails = new JsonPath(socketData.getMessageList().get(1));
        channelId = messageWithSubscriptionDetails.getInt(chanelIDJsonPathLocator);
        messageWithData = new JsonPath(
                TestsUtils.findFirstDataMessage(socketData.getMessageList()).get(0)
        );
    }

    @Test
    public void channelIdShouldBeReceived() {
        Assertions.assertTrue(channelId != 0, "ChannelId is not received");
    }

    @Test
    public void subscriptionNameShouldBeCorrect() {
        Assertions.assertEquals("ticker", messageWithSubscriptionDetails.getString(subscriptionNameJsonPathLocator));
    }

    @Test
    public void correctCurrencyPairShouldBeReceived() {
        Assertions.assertEquals(currencyPair, messageWithSubscriptionDetails.getString(currencyPairJsonPathLocator));
    }

    @Test
    public void spreadDataForCorrectCurrencyPairShouldBeReceived(){
        Assertions.assertEquals(currencyPair, messageWithData.getString("[3]"));
    }

    @Test
    public void spreadFeedIdentificationShouldBeReceived(){
        Assertions.assertEquals("ticker", messageWithData.getString("[2]"));
    }

    @Test
    public void spreadDataForCorrectChannelIDShouldBeReceived(){
        Assertions.assertEquals(channelId, messageWithData.getInt("[0]"));
    }
}