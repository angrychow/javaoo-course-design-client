import clientEnum.Event;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;
import entity.WebSocketMsg;
import interfaces.Controller;
import socket.ChatWebSocket;
import socket.ChatWebSocketManager;
import utils.BaseUrl;
import utils.Bus;
import utils.ClientHttp;
import widgets.addFriend.AddFriendWidget;
import widgets.createGroup.CreateGroupWidget;
import widgets.face.FaceWidget;
import widgets.joinGroup.JoinGroupWidget;
import widgets.login.LoginWidget;
import widgets.main.MainWidget;
import widgets.requestManagement.RequestManagement;

import javax.swing.*;
import java.net.URI;
import java.util.ArrayList;
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
        try {
            this.faceWidget = new FaceWidget("squidward", 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                new Thread(()->{
                    while(true) {
                        var tempFriendList = new ArrayList<User>();
                        var friendsParams = new HashMap<String,Object>();
                        friendsParams.put("user", Bus.Uid);
                        var friendsResult = ClientHttp.Post(BaseUrl.GetUrl("/relation/friends"),null,friendsParams);
                        if(friendsResult.get("statusCode").equals(200)) {
                            var friendsRawList = (ArrayList<HashMap<String,Object>>)((HashMap<String,Object>)friendsResult.get("body")).get("data");
//                        System.out.println(friendsRawList);
                            friendsRawList.stream().forEach((item)->{
                                var ret = new User((String)item.get("name"),(int)item.get("id"));
//                            ret.name = (String)item.get("name");
//                            ret.
//                            return ret;
                                tempFriendList.add(ret);
                            });
                            System.out.println(Bus.friendList);
                        }
                        var groupsResult = ClientHttp.Post(BaseUrl.GetUrl("/group/groups"),null,friendsParams);
                        if(friendsResult.get("statusCode").equals(200)) {
                            var groupsRawList = (ArrayList<HashMap<String,Object>>)((HashMap<String,Object>)groupsResult.get("body")).get("data");
                            groupsRawList.stream().forEach((item)->{
                                var isGroupCreator= "等级 "+item.get("level")+" ";
                                if((int)item.get("owner") == Bus.Uid) {
                                    isGroupCreator += "(群主)";
                                }
                                var ret = new User((String)item.get("groupName")+isGroupCreator,(int)item.get("id"));
                                tempFriendList.add(ret);
                            });
                            System.out.println(Bus.friendList);
                        }
                        Bus.friendList = tempFriendList;
                        MainController.this.handleEvent(Event.UPDATE_FRIEND_LIST);// 每 10 秒更新一次好友列表
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
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
            System.out.println(msg);
            if(msg.isGroupMsg()) {
                this.mainWidget.setNewMessage(msg.getDest(),msg.getContent(),msg.isGroupMsg(),msg.getSendTime(),msg.getSourceUser());
            } else {
                this.mainWidget.setNewMessage(msg.getSourceUser(),msg.getContent(),msg.isGroupMsg(),msg.getSendTime(),msg.getSourceUser());
            }

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
