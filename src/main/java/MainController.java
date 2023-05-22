import clientEnum.Event;
import interfaces.Controller;
import socket.ChatWebSocket;
import socket.ChatWebSocketManager;
import widgets.addFriend.AddFriendWidget;
import widgets.createGroup.CreateGroupWidget;
import widgets.face.FaceWidget;
import widgets.joinGroup.JoinGroupWidget;
import widgets.login.LoginWidget;
import widgets.main.MainWidget;

import java.net.URI;

public class MainController implements Controller {
    private ChatWebSocketManager webSocketManager;
    private LoginWidget loginWidget;
    private MainWidget mainWidget;
    private AddFriendWidget addFriendWidget;
    private CreateGroupWidget createGroupWidget;
    private JoinGroupWidget joinGroupWidget;

    private FaceWidget faceWidget;


    MainController() {

        webSocketManager = new ChatWebSocketManager(this);


        this.loginWidget = new LoginWidget(this);
        this.mainWidget = new MainWidget(this);


        this.addFriendWidget = new AddFriendWidget(this);
        this.createGroupWidget = new CreateGroupWidget(this);
        this.joinGroupWidget = new JoinGroupWidget(this);
        try {
            this.faceWidget = new FaceWidget(12345);

        } catch (Exception e) {
            e.printStackTrace();
        }
        loginWidget.showWidget();
    }

    @Override
    public void handleEvent(Event e) {
        switch (e) {
            case LOGIN_SUCCESS -> {
//                webSocketManager.initChatWebSocket();
//                webSocketManager.sendMessage("Test");
                this.mainWidget.updateFriendsList();
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

            case OPEN_FACE_WIDGET -> {
//                this.mainWidget.showFaceWidget();

            }
        }
    }
    @Override
    public void handleMessage(String text) {
        System.out.println(text);
    }

}
