package widgets.main;

import interfaces.Controller;
import widgets.chat.ChatPanel;
import widgets.userList.UserListWidget;
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
        this.listPanel = new UserListWidget(c);
        LayoutTools.addItem(mainPanel, listPanel, 0, 0, 1, 1, 0.2, 0);


        this.chatPanel = new ChatPanel("Angrychow");
        this.chatPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
//        JTextArea text2 = new JTextArea("聊天框");
//        this.chatPanel.add(text2);
        LayoutTools.addItem(mainPanel, chatPanel, 1, 0, GridBagConstraints.REMAINDER, 1, 0.8, 0);
    }


    public void showWidget() {
//        this.pack();
        this.setVisible(true);
    }

    public String getSelectedUser() {
        return ((UserListWidget) this.listPanel).getSelectedUser();
    }

    public void setChatPanel(String name) {
        System.out.println(name);
        this.mainPanel.remove(this.chatPanel);
        this.chatPanel = new ChatPanel(name);
        LayoutTools.addItem(mainPanel, chatPanel, 1, 0, GridBagConstraints.REMAINDER, 1, 0.8, 0);
        this.mainPanel.updateUI();
    }


}
