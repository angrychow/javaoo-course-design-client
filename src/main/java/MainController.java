import clientEnum.Event;
import interfaces.Controller;
import socket.ChatWebSocket;
import widgets.chat.ChatWidget;
import widgets.login.LoginWidget;
import widgets.main.MainWidget;

import java.net.URI;
import java.util.ArrayList;

public class MainController implements Controller {
    private ChatWebSocket webSocket;
    private LoginWidget loginWidget;
    private MainWidget mainWidget;
    private ArrayList<ChatWidget> chatWidgetList;

    MainController() {
        chatWidgetList = new ArrayList<>();
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
                var name = this.mainWidget.getSelectName();
//                var newChatWidget = new ChatWidget(this,this.mainWidget.getSelectName());
//                newChatWidget.showWidget();
//                chatWidgetList.add(newChatWidget);
                mainWidget.setChatPanel(name);
//                ChatWidget newChatWidget = null;
//                for(var chatWidget : chatWidgetList) {
//                    if(chatWidget.getRemoteName().equals(name)) {
//                        newChatWidget = chatWidget;
//                        newChatWidget.showWidget();
//                    }
//                }
//                if(newChatWidget == null) {
//                    newChatWidget = new ChatWidget(this,this.mainWidget.getSelectName());
//                    newChatWidget.showWidget();
//                    chatWidgetList.add(newChatWidget);
//                }
            }
        }
    }
}
