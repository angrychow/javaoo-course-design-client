package widgets.main;

import clientEnum.Event;
import entity.DateString;
import entity.Msg;
import interfaces.Controller;
import socket.ChatWebSocket;
import utils.*;
import widgets.chat.ChatPanel;
import widgets.userList.UserListPanel;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class MainWidget extends JFrame {
    final private Controller controller;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private JPanel mainPanel;
    private UserListPanel listPanel;
    private ChatPanel chatPanel;
    private JList<String> chatList;
    private JMenuBar menuBar;
    private JMenu groupMenu;
    private JMenu friendMenu;
    private JMenuItem addFriendItem;
    private JMenuItem createGroupItem;
    private JMenuItem joinGroupItem;
    private JMenuItem handleFriendItem;
    private HashMap<Integer,String> recordMap;

    public void updateFriendsList() {
        this.listPanel.updateUserList();
//        this.pack();
    }

    public MainWidget(Controller c) {
        super("Chat");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.recordMap = new HashMap<>();
//        this.setSize(400, 400);
        this.pack();
        this.controller = c;

        // 布局
        this.layout = new GridBagLayout();
        this.constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        // 主Panel，border用于设置最外层边界
        this.mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        this.mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setContentPane(this.mainPanel);

        // 好友列表
        this.listPanel = new UserListPanel(c);
//        this.listPanel.setSize(200,500);
        LayoutTools.addItem(mainPanel, listPanel, 0, 0, 1, 1, 1, 1,GridBagConstraints.BOTH);



        // 对话者姓名
        this.chatPanel = new ChatPanel(" ",this.controller);
        this.chatPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        LayoutTools.addItem(mainPanel, chatPanel, 1, 0, GridBagConstraints.REMAINDER, 1, 4, 1,GridBagConstraints.BOTH);

        // 菜单栏

        this.menuBar = new JMenuBar();
        this.friendMenu = new JMenu("好友");
        this.groupMenu = new JMenu("群组");
        this.addFriendItem = new JMenuItem("添加好友");
        this.handleFriendItem = new JMenuItem("处理好友申请");
        addFriendItem.addActionListener((e)->{
            controller.handleEvent(Event.OPEN_ADD_FRIEND);
        });
        handleFriendItem.addActionListener((e)->{
            controller.handleEvent(Event.OPEN_REQUEST_MANAGEMENT);
        });
        this.createGroupItem = new JMenuItem("创建群聊");
        createGroupItem.addActionListener((e)->{
            controller.handleEvent(Event.OPEN_CREATE_GROUP);
        });
        this.joinGroupItem = new JMenuItem("管理群组");
        joinGroupItem.addActionListener((e)->{
            controller.handleEvent(Event.OPEN_JOIN_GROUP);
        });
        friendMenu.add(addFriendItem);
        friendMenu.add(handleFriendItem);
        groupMenu.add(createGroupItem);
        groupMenu.add(joinGroupItem);
        menuBar.add(friendMenu);
        menuBar.add(groupMenu);
        this.setJMenuBar(menuBar);
    }


    public void showWidget() {

        for(var e: Bus.friendList) {
            var params = new HashMap<String,Object>();
//            params.put("")
            if(e.ID<10000) {
                params.put("from",e.ID);
                params.put("to",Bus.Uid);
                var result = ClientHttp.Post(BaseUrl.GetUrl("/history/friend"),null,params);
                result = (HashMap<String,Object>)result.get("body");
                ArrayList<HashMap<String,Object>> data = (ArrayList<HashMap<String,Object>>)result.get("data");

//                var params2 = new HashMap<String,Object>();
//                params2.put("from",Bus.Uid);
//                params2.put("to",e.ID);
//                var result2 = ClientHttp.Post(BaseUrl.GetUrl("/history/friend"),null,params2);
//                System.out.println(result2);
//                result2 = (HashMap<String,Object>)result2.get("body");
//                ArrayList<HashMap<String,Object>> data2 = (ArrayList<HashMap<String,Object>>)result2.get("data");
//                System.out.println(data);
//                System.out.println(data2);
//                data.addAll(data2);
                data.sort((a,b)->((String)a.get("sendTime")).compareTo((String)b.get("sendTime")));
//                var temp = (HashMap<String,Object>[])(data.toArray());
//                Arrays.sort(temp,(a,b)->((String)b.get("sendTime")).compareTo((String)a.get("sendTime")));
                System.out.println(data);
                System.out.println("********************\n********************\n********************\n********************");
                for(var ele: data) {
                    setNewMessage(e.ID,(String)ele.get("content"),(boolean)ele.get("groupMsg"),(String)ele.get("sendTime"),(int)ele.get("sourceUser"));
                }
            } else {
                params.put("group",e.ID);
                var result = ClientHttp.Post(BaseUrl.GetUrl("/history/group"),null,params);
                result = (HashMap<String,Object>)result.get("body");
                ArrayList<HashMap<String,Object>> data = (ArrayList<HashMap<String,Object>>)result.get("data");
                System.out.println(data);
                for(var ele: data) {
                    setNewMessage((int)ele.get("dest"),(String)ele.get("content"),(boolean)ele.get("groupMsg"),(String)ele.get("sendTime"),(int)ele.get("sourceUser"));
                }
            }
        }


        this.pack();
//        this.setSize(500,500);
//        this.setResizable(false);
        this.setVisible(true);
        this.mainPanel.updateUI();
    }

    public String getSelectedUser() {
        return ((UserListPanel) this.listPanel).getSelectedUser();
    }
    public int getSelectedUserId() { return GetUser.getUid(((UserListPanel) this.listPanel).getSelectedUser()); }

    public void setChatPanel(String name) {
        System.out.println(name);
        this.chatPanel.setName(name);
//        this.mainPanel.remove(this.chatPanel);
//        this.chatPanel = new ChatPanel(name,this.controller);
//        Insets i = new Insets(0, 10, 0, 0);
//        LayoutTools.addItem(mainPanel, chatPanel, 1, 0, GridBagConstraints.REMAINDER, 1, 4, 1, GridBagConstraints.BOTH);
        this.mainPanel.updateUI();
    }

    public void setNewMessage(int uid,String content,boolean isGroup,String sendTime,int realUid) {

//        var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.fff ");
//        Date date = null;
        DateString date = new DateString();
        date.dateString = sendTime;
        var displayDateString = date.getMonth()+"月"+date.getDate()+"日 "+date.getHours()+":"+date.getMinutes();

        if(this.listPanel.getSelectedUserId() == uid) {
            if(this.recordMap.containsKey(uid)) {
                this.recordMap.replace(uid,this.recordMap.get(uid)+'\n'+ GetUser.getName(realUid)+" "+displayDateString+"\n"+content+"\n\n");
            } else {
                this.recordMap.put(uid, GetUser.getName(realUid)+" "+displayDateString+"\n"+content+"\n\n");
            }
            this.chatPanel.replaceText(this.recordMap.get(uid));
        } else {
            this.listPanel.setSelectedById(uid);
            if(this.recordMap.containsKey(uid)) {
                this.recordMap.replace(uid,this.recordMap.get(uid)+'\n'+ GetUser.getName(realUid)+" "+displayDateString+"\n"+content+"\n\n");
            } else {
                this.recordMap.put(uid, GetUser.getName(realUid)+" "+displayDateString+"\n"+content+"\n\n");
            }
            this.chatPanel.replaceText(this.recordMap.get(uid));
        }
    }

    public void handleUserListChange() {

        int uid = this.listPanel.getSelectedUserId();
        System.out.println("chat"+uid);
        String text = "";
        if(this.recordMap.containsKey(uid)) {
            text = this.recordMap.get(uid);
            System.out.println( this.recordMap.get(uid));
        }
        this.chatPanel.replaceText(text);

    }

    public void handleSendText() {
        var date = new Date();
        var displayDateString = (date.getMonth()+1) +"月"+date.getDate()+"日 "+date.getHours()+":"+date.getMinutes();
        System.out.println(displayDateString);
        var content = this.chatPanel.getChatText();
        this.chatPanel.clearChatText();
        int uid = this.listPanel.getSelectedUserId();
        if(uid<10000) {
            this.chatPanel.appendText(Bus.UserName+" "+displayDateString+"\n"+content+"\n\n");
            if(this.recordMap.containsKey(uid)) {
                this.recordMap.replace(uid,this.recordMap.get(uid)+'\n'+ Bus.UserName+" "+displayDateString+"\n"+content+"\n\n");
            } else {
                this.recordMap.put(uid, Bus.UserName+" "+displayDateString+"\n"+content+"\n\n");
            }
            this.chatPanel.replaceText(this.recordMap.get(uid));
        }
    }

    public void updateFriendList() {
        this.listPanel.updateUserList();
    }

}
