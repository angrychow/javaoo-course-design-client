package socket;

import clientEnum.Event;
import interfaces.Controller;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class ChatWebSocket extends WebSocketClient {
    private Controller controller;
//    public ChatWebSocket(URI serverUri, Draft draft) {
//        super(serverUri, draft);
//    }

    public ChatWebSocket(URI serverURI,Controller controller) {
        super(serverURI);
        this.controller = controller;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, it is me. Mario :)");
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        controller.handleMessage(message);
        System.out.println("received message: " + message);
    }

//    @Override
//    public void onMessage(ByteBuffer message) {
//        System.out.println("received ByteBuffer");
//    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

}
