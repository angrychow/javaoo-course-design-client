import clientEnum.Event;
import interfaces.Controller;
import socket.ChatWebSocket;
import widgets.login.LoginWidget;
import widgets.main.MainWidget;

import java.net.URI;
import java.util.ArrayList;

public class MainController implements Controller {
    private ChatWebSocket webSocket;
    private LoginWidget loginWidget;
    private MainWidget mainWidget;


    MainController() {

        try {
            this.webSocket = new ChatWebSocket(new URI("ws://localhost:8080"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connect to WS failed");
        }
        this.loginWidget = new LoginWidget(this);
        this.mainWidget = new MainWidget(this);
        loginWidget.showWidget();
    }

    @Override
    public void handleEvent(Event e) {
        switch (e) {
            case LOGIN_SUCCESS -> {
                this.loginWidget.hideWidget();
                this.mainWidget.showWidget();
            }

            case OPEN_CHAT_WIDGET -> {
                String name = this.mainWidget.getSelectedUser();
                this.mainWidget.setChatPanel(name);
            }
        }
    }
}
