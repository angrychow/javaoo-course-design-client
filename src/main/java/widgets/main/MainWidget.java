package widgets.main;

import clientEnum.Event;
import interfaces.Controller;
import widgets.chat.ChatPanel;
import widgets.userList.UserListPanel;
import utils.LayoutTools;

import javax.swing.*;
import java.awt.*;

public class MainWidget extends JFrame {
    final private Controller controller;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private JPanel mainPanel;
    private JPanel listPanel;
    private JPanel chatPanel;
    private JList<String> chatList;
    private JMenuBar menuBar;
    private JMenu groupMenu;
    private JMenu friendMenu;
    private JMenuItem addFriendItem;
    private JMenuItem createGroupItem;
    private JMenuItem joinGroupItem;

    public MainWidget(Controller c) {
        super("Chat");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

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
        this.chatPanel = new ChatPanel("Angrychow");
        this.chatPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        LayoutTools.addItem(mainPanel, chatPanel, 1, 0, GridBagConstraints.REMAINDER, 1, 4, 1,GridBagConstraints.BOTH);

        // 菜单栏

        this.menuBar = new JMenuBar();
        this.friendMenu = new JMenu("好友");
        this.groupMenu = new JMenu("群组");
        this.addFriendItem = new JMenuItem("添加好友");
        addFriendItem.addActionListener((e)->{
            controller.handleEvent(Event.OPEN_ADD_FRIEND);
        });
        this.createGroupItem = new JMenuItem("创建群组");
        createGroupItem.addActionListener((e)->{
            controller.handleEvent(Event.OPEN_CREATE_GROUP);
        });
        this.joinGroupItem = new JMenuItem("加入群组");
        joinGroupItem.addActionListener((e)->{
            controller.handleEvent(Event.OPEN_JOIN_GROUP);
        });
        friendMenu.add(addFriendItem);
        groupMenu.add(createGroupItem);
        groupMenu.add(joinGroupItem);
        menuBar.add(friendMenu);
        menuBar.add(groupMenu);
        this.setJMenuBar(menuBar);
    }


    public void showWidget() {
        this.pack();
//        this.setSize(500,500);
//        this.setResizable(false);
        this.setVisible(true);
        this.mainPanel.updateUI();
    }

    public String getSelectedUser() {
        return ((UserListPanel) this.listPanel).getSelectedUser();
    }

    public void setChatPanel(String name) {
//        System.out.println(name);
        this.mainPanel.remove(this.chatPanel);
        this.chatPanel = new ChatPanel(name);
        Insets i = new Insets(0, 10, 0, 0);
        LayoutTools.addItem(mainPanel, chatPanel, 1, 0, GridBagConstraints.REMAINDER, 1, 4, 1, GridBagConstraints.BOTH);
        this.mainPanel.updateUI();
    }


}
