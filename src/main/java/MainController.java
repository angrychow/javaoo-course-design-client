import clientEnum.Event;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.WebSocketMsg;
import interfaces.Controller;
import socket.ChatWebSocket;
import socket.ChatWebSocketManager;
import widgets.addFriend.AddFriendWidget;
import widgets.createGroup.CreateGroupWidget;
import widgets.face.FaceWidget;
import widgets.joinGroup.JoinGroupWidget;
import widgets.login.LoginWidget;
import widgets.main.MainWidget;
import widgets.requestManagement.RequestManagement;

import java.net.URI;
import java.util.HashMap;

public class MainController implements Controller {
    private ChatWebSocketManager webSocketManager;
    private LoginWidget loginWidget;
    private MainWidget mainWidget;
    private AddFriendWidget addFriendWidget;
    private CreateGroupWidget createGroupWidget;
    private JoinGroupWidget joinGroupWidget;
    private RequestManagement requestManagement;

    private FaceWidget faceWidget;


    MainController() {

        webSocketManager = new ChatWebSocketManager(this);


        this.loginWidget = new LoginWidget(this);
        this.mainWidget = new MainWidget(this);

        this.requestManagement = new RequestManagement(this);
        this.addFriendWidget = new AddFriendWidget(this);
        this.createGroupWidget = new CreateGroupWidget(this);
        this.joinGroupWidget = new JoinGroupWidget(this);
//        try {
//            this.faceWidget = new FaceWidget(12345);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        loginWidget.showWidget();
    }

    @Override
    public void handleEvent(Event e) {
        switch (e) {
            case LOGIN_SUCCESS -> {
                webSocketManager.initChatWebSocket();
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

            case OPEN_REQUEST_MANAGEMENT -> {
                this.requestManagement.ShowWidget();
            }

            case USERLIST_CHANGE -> {
                this.mainWidget.handleUserListChange();
            }

            case SEND_MESSAGE -> {
                this.mainWidget.handleSendText();
            }

            case UPDATE_FRIEND_LIST -> {
                this.mainWidget.updateFriendsList();
            }
        }
    }
    @Override
    public void handleMessage(String text) {
        System.out.println(text);
        var objectMapper = new ObjectMapper();
        try {
//            var body = objectMapper.readValue(text, HashMap.class);
            var msg = objectMapper.readValue(text, WebSocketMsg.class);
            this.mainWidget.setNewMessage(msg.getSourceUser(),msg.getContent(),msg.isGroupMsg(),msg.getSendTime());
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getStackTrace());
            System.out.println("收到了非 json 字符串的数据");
        }

    }

    @Override
    public int getNowChatUid() {
        return this.mainWidget.getSelectedUserId();
    }

}
