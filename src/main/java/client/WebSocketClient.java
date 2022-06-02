package client;

import java.net.http.WebSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletionStage;

public class WebSocketClient implements WebSocket.Listener {
    SocketData socketData;
    public WebSocketClient(SocketData socketData) {
        this.socketData = socketData;
    }
    private String getCurrentTimeStamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println(getCurrentTimeStamp() + " Connection established.");
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println(getCurrentTimeStamp() + " Message received " + ": " + data);
        this.socketData.messageList.add(data.toString());
        return WebSocket.Listener.super.onText(webSocket, data, false);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println(getCurrentTimeStamp() + " Connection closed");
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println(getCurrentTimeStamp() + " ERROR OCCURRED: " + error.getMessage());
        WebSocket.Listener.super.onError(webSocket, error);
    }
}
