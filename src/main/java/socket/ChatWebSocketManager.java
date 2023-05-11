package socket;

import interfaces.Controller;

import java.net.URI;

public class ChatWebSocketManager {
    private ChatWebSocket chatWebSocket;
    private Controller controller;

    public ChatWebSocketManager(Controller controller) {
        this.controller = controller;
    }

    public void initChatWebSocket() {

        try {
            chatWebSocket = new ChatWebSocket(new URI("ws://127.0.0.1:6788"),controller);
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
