package client;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Client {
    private static final String apiURL = "wss://ws.kraken.com";

    public static void connectAndListen(String payload, SocketData socketData, int timeout) throws InterruptedException, ExecutionException {
        CountDownLatch latch = new CountDownLatch(1);
        var uri = URI.create(apiURL);
        var webSocket = HttpClient.newHttpClient().newWebSocketBuilder()
                .buildAsync(uri, new WebSocketClient(socketData))
                .get();
        webSocket.sendText(payload, true);
        latch.await(timeout, TimeUnit.SECONDS);
    }
}
