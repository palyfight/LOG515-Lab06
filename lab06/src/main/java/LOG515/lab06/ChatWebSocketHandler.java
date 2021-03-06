package	LOG515.lab06;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class ChatWebSocketHandler {
	private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
    	Map<String, List<String>> q = user.getUpgradeRequest().getParameterMap();
        String username = q.get("user").get(0);
        Chat.userUsernameMap.put(user, username);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = Chat.userUsernameMap.get(user);
        Chat.userUsernameMap.remove(user);
        Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        Chat.broadcastMessage(sender = Chat.userUsernameMap.get(user), msg = message);
    }
}
