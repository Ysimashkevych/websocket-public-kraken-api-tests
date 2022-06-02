package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketData {
    List<String> messageList=new ArrayList<>();
    int statusCode;

    public List<String> getMessageList() {
        return this.messageList;
    }

    public int getStatusCode()  {
        return this.statusCode;
    }
}
