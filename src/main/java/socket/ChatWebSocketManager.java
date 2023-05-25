package socket;

import interfaces.Controller;
import utils.Bus;

import java.net.URI;

public class ChatWebSocketManager {
    private ChatWebSocket chatWebSocket;
    private Controller controller;

    public ChatWebSocketManager(Controller controller) {
        this.controller = controller;
    }

    public void initChatWebSocket() {

        try {
            var temp = String.valueOf(Bus.Uid);
            chatWebSocket = new ChatWebSocket(new URI("ws://10.28.166.24:19560/ws/"+temp),controller);
            chatWebSocket.connect();
            while(!chatWebSocket.isOpen());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("WebSocket Initialize failed!");
        }
    }

    public void sendMessage(String str) {
        while(!chatWebSocket.isOpen());
        chatWebSocket.send(str);
    }

    public void closeWebSocket() {
        try {
            chatWebSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
