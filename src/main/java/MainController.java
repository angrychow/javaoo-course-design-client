import clientEnum.Event;
import interfaces.Controller;
import socket.ChatWebSocket;
import widgets.addFriend.AddFriendWidget;
import widgets.createGroup.CreateGroupWidget;
import widgets.joinGroup.JoinGroupWidget;
import widgets.login.LoginWidget;
import widgets.main.MainWidget;

import java.net.URI;

public class MainController implements Controller {
    private ChatWebSocket webSocket;
    private LoginWidget loginWidget;
    private MainWidget mainWidget;
    private AddFriendWidget addFriendWidget;
    private CreateGroupWidget createGroupWidget;
    private JoinGroupWidget joinGroupWidget;



    MainController() {

        try {
            this.webSocket = new ChatWebSocket(new URI("ws://localhost:8080"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connect to WS failed");
        }
        this.loginWidget = new LoginWidget(this);
        this.mainWidget = new MainWidget(this);

        this.addFriendWidget = new AddFriendWidget(this);
        this.createGroupWidget = new CreateGroupWidget(this);
        this.joinGroupWidget = new JoinGroupWidget(this);
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

            case OPEN_ADD_FRIEND -> {
                this.addFriendWidget.showWidget();
            }

            case OPEN_CREATE_GROUP -> {
                this.createGroupWidget.showWidget();
            }

            case OPEN_JOIN_GROUP -> {
                this.joinGroupWidget.showWidget();
            }
        }
    }
}
